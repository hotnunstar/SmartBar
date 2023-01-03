using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Models;
using SmartBar.Services;
using System.Data;

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


        [HttpGet, Authorize]
        public async Task<List<ProductModel>> GetProducts()
        {
            List<ProductModel> lista;
            lista = await _productService.GetAsync();
            return lista;
        }
 
        [Route("hotfood")]
        [HttpGet, Authorize]
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

        [Route("packaged")]
        [HttpGet, Authorize]
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
        [Route("coldrink")]
        [HttpGet, Authorize]
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

        [Route("hotdrink")]
        [HttpGet, Authorize]
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

        /// <summary>
        /// Adicionar um produto
        /// </summary>
        /// <param name="product"></param>
        /// <returns>Action Result</returns>
        [HttpPost, Authorize]
        public async Task<IActionResult> PostProduct(ProductModel product)
        {
            if(GetUserType() == "CLIENTE") return Unauthorized();
            if(GetUserType() == "COLABORADOR")
            {
                if(product.Name == string.Empty || product.Name == null) return BadRequest();

                var productName = product.Name.ToLower();
                if(await _productService.GetAsyncByName(productName) == null)
                {
                    try
                    {
                        product.Id = "";
                        await _productService.CreateAsync(product);
                        return Ok();
                    }
                    catch { return BadRequest(); }
                }  
                else return BadRequest();
            }
            else return NotFound();
        }

        /// <summary>
        /// Alterar um produto
        /// </summary>
        /// <param name="product"></param>
        /// <returns>Action Result </returns>
        [HttpPut, Authorize]
        public async Task<IActionResult> PutProduct(ProductModel product)
        {
            if (GetUserType() == "CLIENTE") return Unauthorized();
            if (GetUserType() == "COLABORADOR")
            {
                if (product.Name == string.Empty || product.Name == null) return BadRequest();
                if (await _productService.GetAsync(product.Id) == null) return BadRequest();
                else
                {
                    try
                    {
                        await _productService.UpdateAsync(product.Id, product);
                        return Ok();
                    }
                    catch { return BadRequest(); }
                }
            }
            else return NotFound();
        }
        private string GetUserType() { return this.User.Claims.First(i => i.Type == "userType").Value; }
    }
}
