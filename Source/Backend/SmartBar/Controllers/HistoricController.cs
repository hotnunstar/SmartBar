using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{
    /// <summary>
    /// Controller do histórico
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    public class HistoricController : ControllerBase
    {
        private readonly HistoricService _historicService;
        /// <summary>
        /// Construtor do controller do histórico
        /// </summary>
        /// <param name="historicService"></param>
        public HistoricController(HistoricService historicService) => _historicService = historicService;

        /// <summary>
        /// Obter o Histórico Global
        /// </summary>
        /// <returns>O Histórico ou NotFound</returns>
        [Route("GetHistoric")]
        [HttpGet, Authorize]
        public async Task<IActionResult> GetAll()
        {
            var historic = await _historicService.GetAsync();
            if (historic.Count > 0)
            {
                return Ok(historic);
            }
            return NotFound("Vazio"); 
        }

        /// <summary>
        /// Obter o Histórico de um determinado cliente
        /// </summary>
        /// <returns>O Histórico do Cliente ou NotFound</returns>
        [Route("GetHistoricByClient")]
        [HttpGet, Authorize]
        public async Task<IActionResult> GetAllByClient()
        {
            string idClient = GetUserID();

            if (idClient != null)
            {
                var historic = await _historicService.GetAsyncByClient(idClient);
                if(historic.Count > 0)
                {
                    return Ok(historic);
                }
            }
            return NotFound("Vazio");
        }

        /// <summary>
        /// Obter o Histórico de pedidos por idBar e por dateRequest caso este não seja nulo
        /// </summary>
        /// <param name="dateReq"></param>
        /// <returns></returns>
        [HttpGet("GetHistoricByBarAndDate"), Authorize]
        public async Task<IActionResult> GetAllByBarAndDate(string dateReq)
        {
            string idBar = GetUserID();

            if (dateReq == null)
            {
                var list = await _historicService.GetAsyncByBar(idBar);
                if (list.Count > 0)
                {
                    return Ok(list);
                }
                else return NotFound("Vazio");
            }
            else
            {
                var list = await _historicService.GetAsyncByBarAndDate(idBar, dateReq);
                if (list.Count > 0)
                {
                    return Ok(list);
                }
                else return NotFound("Vazio");
            }
            
        }

        private string GetUserID() { return this.User.Claims.First(i => i.Type == "id").Value; }

    }
}
