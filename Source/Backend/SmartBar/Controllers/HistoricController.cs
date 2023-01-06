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
            return BadRequest("Vazio"); 
        }

        /// <summary>
        /// Obter o Histórico de um determinado cliente
        /// </summary>
        /// <returns>O Histórico do Cliente ou NotFound</returns>
        [Route("GetHistoricByClient")]
        [HttpGet, Authorize]
        public async Task<IActionResult> GetAllByClient()
        {
            const int MAXLENGTH = 19;
            string auxDateRequest, auxDateExpected;
            string idClient = GetUserID();

            if (idClient != null)
            {
                var historic = await _historicService.GetAsyncByClient(idClient);
                if(historic.Count > 0)
                {
                    foreach (var item in historic)
                    {
                        //Formatação para o kotlin aceitar as datas
                        auxDateRequest = item.DateRequest.ToString();
                        if(auxDateRequest.Length > MAXLENGTH)
                        {
                            auxDateRequest.Substring(0, MAXLENGTH);
                        }
                        item.DateRequest = Convert.ToDateTime(auxDateRequest.Substring(0, MAXLENGTH));


                        auxDateExpected = item.DateExpected.ToString();
                        if(auxDateExpected.Length > MAXLENGTH)
                        {
                            auxDateExpected.Substring(0, MAXLENGTH);
                        }
                        item.DateExpected = Convert.ToDateTime(auxDateExpected.Substring(0, MAXLENGTH));
                        
                    }
                    return Ok(historic);
                }
            }
            return BadRequest("Vazio");
        }

        /*[HttpPost, Authorize]
        public async Task<IActionResult> PostHistoric(HistoricModel historic)
        {
            RequestModel request = new RequestModel();
            if (await _requestService.GetAsyncByRequestId(request.IdRequest) == null)
            {
                return BadRequest("ID inexistente");
            }
            else
            {
                try
                {
                    historic.IdRequest = request.IdRequest;
                    historic.IdClient = request.IdClient;
                    historic.IdProduct = request.IdProduct;
                    historic.DateExpected = request.DateExpected;
                    historic.DateRequest = request.DateRequest;
                    historic.TotalPrice = request.TotalPrice;
                    historic.State = request.State;

                    if (historic.State != 3) //Estado Concluído
                    {
                        return BadRequest("O Estado não é o Concluído");
                    }
                    await _historicService.CreateAsync(historic);
                    return Ok();
                }
                catch { return BadRequest(); }
            }
        }*/

        private string GetUserID() { return this.User.Claims.First(i => i.Type == "id").Value; }
    }
}
