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


        /// <summary>
        /// Construtor do controlador de pedidos
        /// </summary>
        /// <param name="resquestService"></param>
        /// <param name="productService"></param>
        public RequestController(RequestService resquestService, ProductService productService)
        {
            _resquestService = resquestService;
            _productService = productService;
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

    }
}
