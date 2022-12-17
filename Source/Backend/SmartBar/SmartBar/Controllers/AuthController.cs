using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using SmartBar.Models;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace SmartBar.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly IConfiguration _config;

        public AuthController(IConfiguration config)
        {
            _config = config;
        }

        [HttpPost]
        public IActionResult Login(LoginModel user)
        {
            string jwtToken = string.Empty;
            ResponseModel response = new()
            {
                StatusCode = 401,
                Message = "Unauthorized"
            };

            if(user.Email == string.Empty || user.Password == string.Empty)
            {
                response.Message = "Credênciais inválidas!";
                return Unauthorized(response);
            }

            if(user.Email != null && user.Password != null)
            {

                // Falta verificar se os dados inseridos (email e password) estão corretos, se tiverem gera o token, senão return unauthorized

                jwtToken = GenerateToken(25);
                return Ok(jwtToken);
            }
            return Unauthorized(response);
        }

        private string GenerateToken(int a)
        {
            var claims = new[] { new Claim("Id", a.ToString()) };
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
