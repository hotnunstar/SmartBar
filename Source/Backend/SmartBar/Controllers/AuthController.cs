using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using MongoDB.Bson.IO;
using Newtonsoft.Json.Linq;
using SmartBar.Models;
using SmartBar.Services;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System.Text.Json.Nodes;

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
        private readonly BarService _barService;

        /// <summary>
        /// Contrutor do controlador de autenticação
        /// </summary>
        /// <param name="config"></param>
        /// <param name="userService"></param>
        /// <param name="barService"></param>
        public AuthController(IConfiguration config, UserService userService, BarService barService)
        {
            _config = config;
            _userService = userService;
            _barService = barService;
        }

        /// <summary>
        /// HttpPOST do login
        /// </summary>
        /// <param name="login"></param>
        /// <returns>Token ou NotFound</returns>
        [HttpPost]
        public async Task<IActionResult> Login(LoginModel login)
        {
            if (login.Email != string.Empty && login.Password != string.Empty && login.UserType != string.Empty)
            {
                string token;
                if (login.UserType == "CLIENTE")
                {
                    UserController userController = new(_userService);
                    var userData = await userController.GetUserByEmail(login.Email, login.Password);
                    if (userData != null)
                    {
                        token = GenerateToken(userData.Id, "CLIENTE", login.FirebaseToken);
                        return Ok(token);
                    }
                    else return NotFound();
                }
                else if (login.UserType == "COLABORADOR")
                {
                    BarController barController = new(_barService);
                    var userData = await barController.GetBarByEmail(login.Email, login.Password);
                    if (userData != null)
                    {
                        token = GenerateToken(userData.Id, "COLABORADOR", login.FirebaseToken);
                        return Ok(token);
                    }
                    else return NotFound();
                }
                else return NotFound();
            }
            return NotFound();
        }

        private string GenerateToken(string id, string userType, string firebaseToken)
        {
            var claims = new[] { new Claim("id", id), new Claim("userType", userType), new Claim("firebaseToken", firebaseToken) };
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
