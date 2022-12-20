using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductController : ControllerBase
    {
        private readonly ProductService _productService;
        /// <summary>
        /// Construtor do controlador de produtos
        /// </summary>
        /// <param name="productService"></param>
        public ProductController(ProductService productService) => _productService = productService;


        [HttpGet]
        public async Task<List<ProductModel>> GetProducts()
        {
            List<ProductModel> lista;
            lista = await _productService.GetAsync();
            return lista;
        }
        [HttpPost]
        public async Task<IActionResult> PostProduct(ProductModel product)
        {
            ProductModel aux = new ProductModel();
            product.Id = "";
            ResponseModel badRequest = new()
            {
                StatusCode = 400,
                Message = "Parâmetros inválidos"

            };
            ResponseModel response = new()
            {
                StatusCode = 200,
                Message = "Product inserted successfully"
            };
            // confirm all product attributes
            if(product.Name == "")
            {
                badRequest.Message = "please fill in the name";
                return BadRequest(badRequest);
            }
           
            if(product.Price<=0)
            {
                badRequest.Message = "please fill in the correct price";
                return BadRequest(badRequest);
            }
            //check if there are equals products
            aux = await _productService.GetAsyncByName(product.Name);
            if(aux!= null)
            {
                badRequest.Message = "Já existe um produto igual";
                return BadRequest(badRequest);
            }
            
            //inserir
            await _productService.CreateAsync(product);
            return Ok(response);

        }
    }
}
