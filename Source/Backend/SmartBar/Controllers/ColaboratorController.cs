using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Helpers;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ColaboratorController : ControllerBase
    {
        private readonly ColaboratorService _colaboratorService;
        public ColaboratorController(ColaboratorService colaboratorService) => _colaboratorService = colaboratorService;

        [HttpGet]
        public async Task<ColaboratorModel> GetColaborator(string? email, string? password)
        {
            if (email != null && password != null)
            {
                password = Functions.EncodePasswordToBase64(password);
                var user = await _colaboratorService.GetAsync(email);
                if (user == null || user.Password != password) return null;
                else return user;
            }
            return null;
        }

        [HttpPost]
        public async Task<IActionResult> PostColaborator(ColaboratorModel colaborator)
        {
            colaborator.Id = ""; // Para atribuir ID default
            colaborator.Password = Functions.EncodePasswordToBase64(colaborator.Password);

            ResponseModel response = new()
            {
                StatusCode = 400,
                Message = "Parâmetros inválidos"
            };
            // FALTA FAZER VERIFICAÇÕES DOS DADOS DE ENTRADA
            if (Functions.CheckEmail(colaborator.Email))
            {
                await _colaboratorService.CreateAsync(colaborator);
                return CreatedAtAction(nameof(GetColaborator), new { id = colaborator.Id }, colaborator);
            }
            return BadRequest(response);
        }
    }
}
