using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Models;
using SmartBar.Services;
using System.Data;

namespace SmartBar.Controllers
{
    /// <summary>
    /// Controlador de produtos
    /// </summary>
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

        /// <summary>
        /// Obter a lista de todos os produtos existentes
        /// </summary>
        /// <returns>Ok(List) ou NotFound()</returns>
        [HttpGet, Authorize]
        public async Task<IActionResult> GetProducts()
        {
            List<ProductModel> productsList = await _productService.GetAsync();
            if (productsList.Count > 0)return Ok(productsList);
            else return NotFound();
        }
 
        /// <summary>
        /// Obter a lista de menus (tipo 1)
        /// </summary>
        /// <returns>Ok(List) ou NotFound()</returns>
        [Route("Menus")]
        [HttpGet, Authorize]
        public async Task<IActionResult> GetProductsHotFood()
        {
            List<ProductModel> filtredList = new();
            List<ProductModel>  originalList = await _productService.GetAsync();
            if(originalList.Count > 0) {
                foreach(var product in originalList) {
                    if(product.Type == 1) filtredList.Add(product);
                }
                if (filtredList.Count > 0) return Ok(filtredList);
                else return NotFound();
            }
            else return NotFound();
        }

        /// <summary>
        /// Obter a lista de snacks (tipo 2)
        /// </summary>
        /// <returns>Ok(List) ou NotFound()</returns>
        [Route("Snacks")]
        [HttpGet, Authorize]
        public async Task<IActionResult> GetProductsPackaged()
        {
            List<ProductModel> filtredList = new();
            List<ProductModel> originalList = await _productService.GetAsync();
            if (originalList.Count > 0)
            {
                foreach (var product in originalList)
                {
                    if (product.Type == 2) filtredList.Add(product);
                }
                if (filtredList.Count > 0) return Ok(filtredList);
                else return NotFound();
            }
            else return NotFound();
        }

        /// <summary>
        /// Obter a lista de bebidas quentes (tipo 3)
        /// </summary>
        /// <returns>Ok(List) ou NotFound()</returns>
        [Route("HotDrink")]
        [HttpGet, Authorize]
        public async Task<IActionResult> GetProductsHotDrink()
        {
            List<ProductModel> filtredList = new();
            List<ProductModel> originalList = await _productService.GetAsync();
            if (originalList.Count > 0)
            {
                foreach (var product in originalList)
                {
                    if (product.Type == 2) filtredList.Add(product);
                }
                if (filtredList.Count > 0) return Ok(filtredList);
                else return NotFound();
            }
            else return NotFound();
        }

        /// <summary>
        /// Obter a lista de bebidas frias (tipo 4)
        /// </summary>
        /// <returns>Ok(List) ou NotFound()</returns>
        [Route("ColdDrink")]
        [HttpGet, Authorize]
        public async Task<IActionResult> GetProductsColdDrink()
        {
            List<ProductModel> filtredList = new();
            List<ProductModel> originalList = await _productService.GetAsync();
            if (originalList.Count > 0)
            {
                foreach (var product in originalList)
                {
                    if (product.Type == 2) filtredList.Add(product);
                }
                if (filtredList.Count > 0) return Ok(filtredList);
                else return NotFound();
            }
            else return NotFound();
        }

        /// <summary>
        /// Adicionar um produto
        /// </summary>
        /// <param name="product"></param>
        /// <returns>Ok(), BadRequest() ou NotFound()</returns>
        [HttpPost, Authorize]
        public async Task<IActionResult> PostProduct(ProductModel product)
        {
            if (GetUserType() == "CLIENTE") return Unauthorized();
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
        /// <returns>Ok(), BadRequest() ou NotFound()</returns>
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
