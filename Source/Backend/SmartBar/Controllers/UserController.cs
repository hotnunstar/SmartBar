using Microsoft.AspNetCore.Authorization;
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
        /// Obter determinado utilizador através do email
        /// </summary>
        /// <param name="email"></param>
        /// <param name="password"></param>
        /// <returns>Null ou Cliente</returns>
        [Route("GetUserByEmail")]
        [HttpGet]
        public async Task<UserModel> GetUserByEmail(string? email, string? password)
        {
            if(email != null && password != null)
            {
                password = Functions.EncodePasswordToBase64(password);
                var user = await _userService.GetAsyncByEmail(email);
                if (user == null || user.Password != password) return null;
                else
                {
                    user.Password = "";
                    return user;
                }
            }
            return null;
        }

        /// <summary>
        /// Obter determinado cliente através do ID
        /// </summary>
        /// <returns>NotFound ou Cliente</returns>
        [Route("GetUserById")]
        [HttpGet, Authorize]
        public async Task<IActionResult> GetUserById()
        {
            string id = GetUtilizadorID();

            if (id != null)
            {
                var user = await _userService.GetAsyncById(id);
                if (user != null)
                {
                    user.Password = "";
                    return Ok(user);
                }
                    return NotFound();
            }
            return NotFound();
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

            // FALTA FAZER VERIFICAÇÕES DOS DADOS DE ENTRADA
            if(Functions.CheckEmail(user.Email))
            {
                await _userService.CreateAsync(user);
                return CreatedAtAction(nameof(GetUserByEmail), new { id = user.Id }, user);
            }
            return BadRequest();
        }

        private string GetUtilizadorID() { return this.User.Claims.First(i => i.Type == "id").Value; }
    }
}
