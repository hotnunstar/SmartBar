using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{
    /// <summary>
    /// Controlador de pedidos
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    public class RequestController : ControllerBase
    {
        private readonly RequestService _resquestService;
        private readonly ProductService _productService;
        private readonly UserService _userService;
        private readonly HistoricService _historicService;


        /// <summary>
        /// Construtor do controlador de pedidos
        /// </summary>
        /// <param name="resquestService"></param>
        /// <param name="productService"></param>
        /// <param name="userService"></param>
        /// <param name="historicService"></param>
        public RequestController(RequestService resquestService, ProductService productService, UserService userService, HistoricService historicService)
        {
            _resquestService = resquestService;
            _productService = productService;
            _userService = userService;
            _historicService = historicService;
        }


        /// <summary>
        /// Obter todos os pedidos em aberto
        /// </summary>
        /// <returns></returns>
        [HttpGet, Authorize]
        public async Task<IActionResult> GetAll()
        {
            var list = await _resquestService.GetAsync();
            if (list.Count > 0)
            {
                return Ok(list);
            }
            else return NotFound("Não foram encontrados pedidos ativos");
        }

        /// <summary>
        /// Obter pedidos em aberto por determinado estado
        /// </summary>
        /// <param name="state"></param>
        /// <returns></returns>
        [HttpGet("{state}"), Authorize]
        public async Task<IActionResult> GetByState(int state)
        {
            var list = await _resquestService.GetAsyncByState(state);
            if (list.Count > 0)
            {
                return Ok(list);
            }
            else return NotFound("Não foram encontrados pedidos ativos");
        }

        /// <summary>
        /// Inserção de um pedido
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        [HttpPost,Authorize]
        public async Task<IActionResult> PostRequest(RequestModel request)
        {
            UserModel user = new();
            List<ProductModel> productsList = await _productService.GetAsync(); //lista de produtos
            List<ProductRequest> productRequest = new List<ProductRequest>();
            productRequest = request.ProductAndQuantity; // lista de produtos ao pedido do cliente 
            request.IdCliente = GetUtilizadorID();
            request.FirebaseToken = GetFirebaseToken();
            user = await _userService.GetAsyncById(request.IdCliente);
            if (user == null) return BadRequest("Utilizador não encontrado");
  
            double auxSaldo = 0;
            string dateRequest = DateTime.Now.ToString("dd/MM/yyyy");
            double auxSaldoInicial = user.Balance;
            int aux = 0;
            auxSaldo = user.Balance - request.Value;
            if(auxSaldo < 0)
            {
                return BadRequest($"Não existe saldo suficiente para efetuar a compra");
            }
            else
            {
                user.Balance = auxSaldo;
                await _userService.UpdateAsync(request.IdCliente, user);
            }
            foreach (ProductModel product in productsList)
            {
                foreach (ProductRequest productRequestItem in productRequest)
                {
                    if(product.Id == productRequestItem.IdProduct)
                    {
                        aux = product.Stock - productRequestItem.Quantity;
                        if(aux < 0)
                        {
                            user.Balance = auxSaldoInicial;
                            await _userService.UpdateAsync(request.IdCliente, user);
                            return BadRequest($"Não existe stock suficiente do produto {product.Name}");
                        }
                        product.Stock = aux;
                       await _productService.UpdateAsync(productRequestItem.IdProduct,product);
                    }
                }
            }
            request.IdRequest = ""; //Atribuir ID default
            request.State = 1; //Estado Inicial
            request.DateRequest = DateTime.Now.ToString("dd/MM/yyyy");
            await _resquestService.CreateAsync(request);
            return CreatedAtAction(nameof(GetAll), new { id = request.IdRequest }, request);
            
        }

        /// <summary>
        /// Incrementa o estado do Pedido até "concluído" (estado 3) e depois é convertido em histórico
        /// Se o pedido for cancelado, o seu estado será o 4
        /// </summary>
        /// <param name="idRequest"></param>
        /// <returns></returns>
        [HttpPut, Authorize]
        public async Task<ActionResult> PutRequest(string idRequest)
        {
            if (GetUserType() == "CLIENTE") return Unauthorized();
           
            RequestModel request = await _resquestService.GetAsyncByRequestId(idRequest);
            if (request == null) return NotFound("Pedido não encontrado");

            if (GetUserType() == "COLABORADOR")
            {
                if (request == null ) return BadRequest("Pedido não encontrado");
                try
                {
                    if (request.State == 1)
                    {
                        request.State++;
                        await _resquestService.UpdateAsync(request.IdRequest, request);
                        
                        // NOTIFICAR O UTILIZADOR QUE O PEDIDO SE ENCONTRA EM PREPARAÇÃO

                        return Accepted();
                    }
                    else if (request.State == 2)
                    {
                        request.State++;
                        HistoricModel historic = new()
                        {
                            IdClient = request.IdCliente,
                            IdRequest = request.IdRequest,
                            ProductAndQuantity = request.ProductAndQuantity,
                            Horas = request.Horas,
                            DateRequest = request.DateRequest,
                            TotalPrice = request.Value,
                            State = request.State
                        };

                        await _historicService.CreateAsync(historic);
                        await _resquestService.DeleteAsync(idRequest);

                        // NOTIFICAR O UTILIZADOR QUE O PEDIDO SE ENCONTRA PRONTO PARA LEVANTAMENTO

                        return Accepted();
                    }
                    else return BadRequest("Erro no estado do pedido");
                }
                catch { return BadRequest("Erro na atualização do pedido"); }
            }
            return NotFound("Não tem autorização para alterar o estado do pedido");
        }

        private string GetUserType() { return this.User.Claims.First(i => i.Type == "userType").Value; }
        private string GetUtilizadorID() { return this.User.Claims.First(i => i.Type == "id").Value; }
        private string GetFirebaseToken() { return this.User.Claims.First(i => i.Type == "firebaseToken").Value; }

    }
}
