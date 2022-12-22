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
 
        [HttpGet("hotfood")]
        public async Task<List<ProductModel>> GetProductsHotFood()
        {
            List<ProductModel> lista;
            List<ProductModel> aux = new List<ProductModel>();
            lista = await _productService.GetAsync();
            foreach (ProductModel product in lista)
            {
                if(product.Type ==1)
                {
                    aux.Add(product);
                }
            }
            //Tipo de Produto -> hot food =1; Packaged = 2; hot drink =3; cold drink =4
            return aux;
        }
        [HttpGet("packaged")]
        public async Task<List<ProductModel>> GetProductsPackaged()
        {
            List<ProductModel> lista;
            List<ProductModel> aux = new List<ProductModel>();
            lista = await _productService.GetAsync();
            foreach (ProductModel product in lista)
            {
                if (product.Type == 2)
                {
                    aux.Add(product);
                }
            }
            //Tipo de Produto -> hot food =1; Packaged = 2; hot drink =3; cold drink =4
            return aux;
        }
        [HttpGet("coldrink")]
        public async Task<List<ProductModel>> GetProductsColdDrink()
        {
            List<ProductModel> lista;
            List<ProductModel> aux = new List<ProductModel>();
            lista = await _productService.GetAsync();
            foreach (ProductModel product in lista)
            {
                if (product.Type == 4)
                {
                    aux.Add(product);
                }
            }
            //Tipo de Produto -> hot food =1; Packaged = 2; hot drink =3; cold drink =4
            return aux;
        }

        [HttpGet("hotdrink")]
        public async Task<List<ProductModel>> GetProductsHotDrink()
        {
            List<ProductModel> lista;
            List<ProductModel> aux = new List<ProductModel>();
            lista = await _productService.GetAsync();
            foreach (ProductModel product in lista)
            {
                if (product.Type == 3)
                {
                    aux.Add(product);
                }
            }
            //Tipo de Produto -> hot food =1; Packaged = 2; hot drink =3; cold drink =4
            return aux;
        }
        [HttpPost]
        public async Task<IActionResult> PostProduct(ProductModel product)
        {
            ProductModel aux = new ProductModel();
            product.Id = "";
            ResponseModel badRequest = new()
            {
                StatusCode = 400,
                Message = "Invalid parameters"

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
                badRequest.Message = "the product already exists";
                return BadRequest(badRequest);
            }
            
            //insert
            await _productService.CreateAsync(product);
            return Ok(response);

        }
    }
}
