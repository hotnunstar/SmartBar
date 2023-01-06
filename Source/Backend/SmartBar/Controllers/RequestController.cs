using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RequestController : ControllerBase
    {
        private readonly RequestService _resquestService;
        private readonly ProductService _productService;
<<<<<<< HEAD
        private readonly UserService _userService;
=======
        private readonly HistoricService _historicService;

>>>>>>> 56c7d320236170be0b13710874a42340d7500dc5

        /// <summary>
        /// Construtor do controlador de pedidos
        /// </summary>
        /// <param name="resquestService"></param>
        /// <param name="productService"></param>
<<<<<<< HEAD
        public RequestController(RequestService resquestService, ProductService productService, UserService userService)
        {
            _resquestService = resquestService;
            _productService = productService;
            _userService = userService;
=======
        public RequestController(RequestService resquestService, ProductService productService, HistoricService historicService)
        {
            _resquestService = resquestService;
            _productService = productService;
            _historicService = historicService;
>>>>>>> 56c7d320236170be0b13710874a42340d7500dc5
        }




        [HttpGet]
        public async Task<List<RequestModel>> GetAll()
        {
            var list = await _resquestService.GetAsync();
            return list;
        }

        /*[HttpGet]
        public async Task<List<RequestModel>> GetByIdClientAndState(string idClient, int state)
        {
            return await _resquestService.GetAsyncByClientAndState(idClient, state);
        }*/
        [HttpPost,Authorize]
        
        public async Task<IActionResult> PostRequest(RequestModel request)
        {
            List<ProductModel> productsList = await _productService.GetAsync(); //lista de produtos
            List<ProductRequest> productRequest = new List<ProductRequest>(); 
            productRequest = request.ProductAndQuantity; // lista de produtos ao pedido do cliente 
            UserModel user = await _userService.GetAsyncById(request.IdCliente);
            double auxSaldo = 0;
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
            request.DateRequest = DateTime.Now;
            request.DatePickUp = request.DateRequest.AddHours(1);
            await _resquestService.CreateAsync(request);
            return CreatedAtAction(nameof(GetAll), new { id = request.IdRequest }, request);
        }

        /// <summary>
        /// Encrementa o State do Pedido até o Concluído(3) e quando este chega a 3 é passado a Histórico
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        [HttpPut, Authorize]
        public async Task<ActionResult> PutRequest(string idRequest)
        {
            HistoricModel historic = new();
            RequestModel request = new();

            if (GetUserType() == "CLIENTE") return Unauthorized();
            if (GetUserType() == "COLABORADOR")
            {
                if (await _resquestService.GetAsyncByRequestId(idRequest) == null) return BadRequest("1");
                else
                {
                    try
                    {
                        request.State = request.State + 1;
                        if (request.State == 3)
                        {
                            historic.IdClient = request.IdCliente;
                            historic.IdRequest = request.IdRequest;
                            historic.ProductAndQuantity = request.ProductAndQuantity;
                            historic.DateExpected = request.DatePickUp;
                            historic.DateRequest = request.DateRequest;
                            historic.TotalPrice = request.Value;
                            historic.State = request.State;
                            await _historicService.CreateAsync(historic);
                            await _resquestService.DeleteAsync(request.IdRequest);
                            return Ok();
                        }
                        if (request.State > 3) { return BadRequest("Estado Impossível"); }
                        else
                        {
                            await _resquestService.UpdateAsync(request.IdRequest, request);
                            return Ok();
                        }
                    }
                    catch { return BadRequest("2"); }
                }
            }
            else return NotFound();
        }

        private string GetUserType() { return this.User.Claims.First(i => i.Type == "userType").Value; }

    }
}
