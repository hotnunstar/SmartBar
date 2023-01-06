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
        private readonly HistoricService _historicService;


        /// <summary>
        /// Construtor do controlador de pedidos
        /// </summary>
        /// <param name="resquestService"></param>
        /// <param name="productService"></param>
        public RequestController(RequestService resquestService, ProductService productService, HistoricService historicService)
        {
            _resquestService = resquestService;
            _productService = productService;
            _historicService = historicService;
        }
     
        [HttpGet]
        public async Task<List<RequestModel>> GetAll()
        {
            var list = await _resquestService.GetAsync();
            return list;
        }

        [HttpPost,Authorize]
        public async Task<IActionResult> PostRequest(RequestModel request)
        {
            List<ProductModel> productsList = await _productService.GetAsync(); //lista de produtos
            List<ProductRequest> productRequest = new List<ProductRequest>(); 
            productRequest = request.ProductAndQuantity; // lista de produtos ao pedido do cliente 
            int aux = 0;

            foreach (ProductModel product in productsList)
            {
                foreach (ProductRequest productRequestItem in productRequest)
                {
                    if(product.Id == productRequestItem.IdProduct)
                    {
                        aux = product.Stock - productRequestItem.Quantity;
                        if(aux < 0)
                        {
                            return BadRequest($"Não existe stock suficiente do produto {product.Name}");
                        }
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
        /// <param name="idRequest"></param>
        /// <returns></returns>
        [HttpPut, Authorize]
        public async Task<ActionResult> PutRequest(string idRequest)
        {
            if (GetUserType() == "CLIENTE") return Unauthorized();
           
            RequestModel request = await _resquestService.GetAsyncByRequestId(idRequest);

            if (GetUserType() == "COLABORADOR")
            {
                if (request == null ) return BadRequest("Pedido não encontrado");
                try{
                    if (request.State == 1)
                    {
                        request.State++;
                        // FALTA ENVIAR NOTIFICAÇÃO PARA O UTILIZADOR A CERCA DA ATUALIZAÇÃO DO PEDIDO
                        return Accepted();
                    }
                    if (request.State == 2)
                    {
                        request.State++;
                        // FALTA ENVIAR NOTIFICAÇÃO PARA O UTILIZADOR A CERCA DA ATUALIZAÇÃO DO PEDIDO
                        return Accepted();
                    }
                    if (request.State == 3)
                    {
                        HistoricModel historic = new()
                        {
                            IdClient = request.IdCliente,
                            IdRequest = request.IdRequest,
                            ProductAndQuantity = request.ProductAndQuantity,
                            DateExpected = request.DatePickUp,
                            DateRequest = request.DateRequest,
                            TotalPrice = request.Value,
                            State = request.State
                        };

                        await _historicService.CreateAsync(historic);
                        await _resquestService.DeleteAsync(idRequest);

                        return Accepted();
                    }
                    if (request.State > 3) { return BadRequest("Estado Impossível"); }
                }
                catch { return BadRequest("Erro na atualização do pedido"); }
            }
            return NotFound("Pedido não encontrado");
        }

        private string GetUserType() { return this.User.Claims.First(i => i.Type == "userType").Value; }
    }
}
