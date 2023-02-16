using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Helpers;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{

    /// <summary>
    /// Controlador de bares
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    public class BarController : ControllerBase
    {
        private readonly BarService _barService;
        /// <summary>
        /// Construtor do controlador do bar
        /// </summary>
        /// <param name="barService"></param>
        public BarController(BarService barService) => _barService = barService;

        /// <summary>
        /// Obter determinado bar
        /// </summary>
        /// <param name="email"></param>
        /// <param name="password"></param>
        /// <returns>Null ou Bar</returns>
        [HttpGet("GetBarByEmail")]
        public async Task<BarModel> GetBarByEmail(string? email, string? password)
        {
            if (email != null && password != null)
            {
                password = Functions.EncodePassword(password);
                var bar = await _barService.GetAsyncByEmail(email);
                if (bar == null || bar.Password != password) return null;
                else return bar;
            }
            return null;
        }

        /// <summary>
        /// Obter determinado bar através do ID
        /// </summary>
        /// <returns>NotFound ou Bar</returns>
        [Route("GetBarById")]
        [HttpGet, Authorize]
        public async Task<IActionResult> GetUserById()
        {
            string id = GetBarID();

            if (id != null)
            {
                var bar = await _barService.GetAsyncById(id);
                if (bar != null)
                {
                    bar.Password = "";
                    return Ok(bar);
                }
                return NotFound();
            }
            return NotFound();
        }

        [HttpGet, Authorize]
        public async Task<IActionResult> GetAllBares()
        {
                var bar = await _barService.GetAsync();
                if (bar != null)
                {
                    return Ok(bar);
                }
                return NotFound("Nao existem bares");
        }
          
        

        /// <summary>
        /// Inserir um bar
        /// </summary>
        /// <param name="bar"></param>
        /// <returns>BadRequest ou Colaborador</returns>
        [HttpPost]
        public async Task<IActionResult> PostColaborator(BarModel bar)
        {
            bar.Id = ""; // Para atribuir ID default

            var checkUser = await _barService.GetAsyncByEmail(bar.Email);
            if (checkUser == null)
            {
                bar.Password = Functions.EncodePassword(bar.Password);
                if (Functions.CheckEmail(bar.Email))
                {
                    await _barService.CreateAsync(bar);
                    return CreatedAtAction(nameof(GetBarByEmail), new { id = bar.Id }, bar);
                }
                else return BadRequest("EMAIL EM FORMATO INCORRETO");
            }
            else return BadRequest("EMAIL JÁ REGISTADO");
        }

        private string GetBarID() { return this.User.Claims.First(i => i.Type == "id").Value; }
    }
}
