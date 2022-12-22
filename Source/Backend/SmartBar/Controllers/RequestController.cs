using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RequestController : ControllerBase
    {
        private readonly RequestService _resquestService;
        /// <summary>
        /// Construtor do controlador de pedidos
        /// </summary>
        /// <param name="requestService"></param>
        public RequestController(RequestService requestService) => _resquestService = requestService;

        [HttpGet]
        public async Task<List<RequestModel>> GetAll()
        {
            var list = await _resquestService.GetAsync();
            return list;
        }

        /*[HttpGet]
        public async Task<List<RequestModel>> GetByIdClientAndState(string idClient, int state)
        {
            return await _resquestService.GetAsyncByClientAndState(idClient, state);
        }*/
        [HttpPost]
        public async Task<IActionResult> PostRequest(RequestModel request)
        {
            request.IdRequest = ""; //Atribuir ID default
            request.State = 1; //Estado Inicial
            request.DateRequest = DateTime.Now;
            request.DatePickUp = request.DateRequest.AddHours(1);
            request.IdCliente = "1";
            request.Value = 10.0;
            request.IdProduct.Add("1");
            await _resquestService.CreateAsync(request);
            return CreatedAtAction(nameof(GetAll), new { id = request.IdRequest }, request);
        }

    }
}
