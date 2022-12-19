using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using SmartBar.Models;
using SmartBar.Services;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace SmartBar.Controllers
{
    /// <summary>
    /// Controlador de autenticação
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly IConfiguration _config;
        private readonly UserService _userService;
        private readonly ColaboratorService _colaboratorService;

        /// <summary>
        /// Contrutor do controlador de autenticação
        /// </summary>
        /// <param name="config"></param>
        /// <param name="userService"></param>
        /// <param name="colaboratorService"></param>
        public AuthController(IConfiguration config, UserService userService, ColaboratorService colaboratorService)
        {
            _config = config;
            _userService = userService;
            _colaboratorService = colaboratorService;
        }

        /// <summary>
        /// HttpPOST do login
        /// </summary>
        /// <param name="login"></param>
        /// <returns>Token ou NotFound</returns>
        [HttpPost]
        public async Task<IActionResult> Login(LoginModel login)
        {
            ResponseModel response = new()
            {
                StatusCode = 401,
                Message = "Credênciais inválidas!"
            };

            if(login.Email != string.Empty && login.Password != string.Empty && login.UserType != string.Empty)
            {
                if(login.UserType == "CLIENTE")
                {
                    UserController userController = new(_userService);
                    var userData = await userController.GetUser(login.Email, login.Password);
                    if (userData != null)
                    {
                        string jwtToken = GenerateToken(userData.Id);
                        return Ok(jwtToken);
                    }
                    else return NotFound(response);
                }
                else if(login.UserType == "COLABORADOR")
                {
                    ColaboratorController colaboratorController = new(_colaboratorService);
                    var userData = await colaboratorController.GetColaborator(login.Email, login.Password);
                    if (userData != null)
                    {
                        string jwtToken = GenerateToken(userData.Id);
                        return Ok(jwtToken);
                    }
                    else return NotFound(response);
                }
                else return NotFound(response);
            }
            return NotFound(response);
        }

        private string GenerateToken(string id)
        {
            var claims = new[] { new Claim("Id", id) };
            var issuer = _config["Jwt:Issuer"];
            var audience = _config["Jwt:Audience"];
            var expiry = DateTime.Now.AddMonths(3); // Atribui uma validade de 3 meses ao token
            var securityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["Jwt:Key"]));
            var credentials = new SigningCredentials(securityKey, SecurityAlgorithms.HmacSha256);
            var token = new JwtSecurityToken(issuer: issuer, audience: audience, claims, expires: expiry, signingCredentials: credentials);
            var tokenHandler = new JwtSecurityTokenHandler();
            var jwtToken = tokenHandler.WriteToken(token);

            return jwtToken;
        }
    }
}
