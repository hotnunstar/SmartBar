using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Helpers;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{

    /// <summary>
    /// Controlador de colaboradores
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    public class ColaboratorController : ControllerBase
    {
        private readonly ColaboratorService _colaboratorService;
        /// <summary>
        /// Construtor do controlador de colaboradores
        /// </summary>
        /// <param name="colaboratorService"></param>
        public ColaboratorController(ColaboratorService colaboratorService) => _colaboratorService = colaboratorService;

        /// <summary>
        /// Obter determinado colaborador
        /// </summary>
        /// <param name="email"></param>
        /// <param name="password"></param>
        /// <returns>Null ou Colaborador</returns>
        [HttpGet]
        public async Task<ColaboratorModel> GetColaborator(string? email, string? password)
        {
            if (email != null && password != null)
            {
                password = Functions.EncodePasswordToBase64(password);
                var colaborator = await _colaboratorService.GetAsync(email);
                if (colaborator == null || colaborator.Password != password) return null;
                else return colaborator;
            }
            return null;
        }

        /// <summary>
        /// Inserir um colaborador
        /// </summary>
        /// <param name="colaborator"></param>
        /// <returns>BadRequest ou Colaborador</returns>
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
