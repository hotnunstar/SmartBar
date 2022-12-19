using Microsoft.AspNetCore.Mvc;
using SmartBar.Helpers;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{

    /// <summary>
    /// Controlador de clientes
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly UserService _userService;

        /// <summary>
        /// Construtor do controlador de clientes
        /// </summary>
        /// <param name="userService"></param>
        public UserController(UserService userService) => _userService = userService;


        /// <summary>
        /// Obter determinado utilizador
        /// </summary>
        /// <param name="email"></param>
        /// <param name="password"></param>
        /// <returns>Null ou Utilizador</returns>
        [HttpGet]
        public async Task<UserModel> GetUser(string? email, string? password)
        {
            if(email != null && password != null)
            {
                password = Functions.EncodePasswordToBase64(password);
                var user = await _userService.GetAsync(email);
                if (user == null || user.Password != password) return null;
                else return user;
            }
            return null;
        }


        /// <summary>
        /// Inserir um cliente
        /// </summary>
        /// <param name="user"></param>
        /// <returns>BadRequest ou Cliente</returns>
        [HttpPost]
        public async Task<IActionResult> PostUser(UserModel user)
        {
            user.Id = ""; // Para atribuir ID default
            user.Password = Functions.EncodePasswordToBase64(user.Password);

            ResponseModel response = new()
            {
                StatusCode = 400,
                Message = "Parâmetros inválidos"
            };
            // FALTA FAZER VERIFICAÇÕES DOS DADOS DE ENTRADA
            if(Functions.CheckEmail(user.Email))
            {
                await _userService.CreateAsync(user);
                return CreatedAtAction(nameof(GetUser), new { id = user.Id }, user);
            }
            return BadRequest(response);
        }
    }
}
