using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class HistoricController : ControllerBase
    {
        private readonly HistoricService _historicService;

        public HistoricController(HistoricService historicService) => _historicService = historicService;

        [HttpGet]
        public async Task<List<HistoricModel>> GetAll()
        {
            List<HistoricModel> list;
            list = await _historicService.GetAsync();
            return list;
        }

        /// <summary>
        /// Obter o Histórico de um determinado cliente
        /// </summary>
        /// <returns></returns>
        [Route("GetHistoricByClient")]
        [HttpGet, Authorize]
        public async Task<IActionResult> GetAllByClient()
        {
            const int MAXLENGTH = 10;
            string aux, aux2;
            string idClient = GetUserID();

            if (idClient != null)
            {
                var historic = await _historicService.GetAsyncByClient(idClient);
                if(historic.Count > 0)
                {
                    foreach (var item in historic)
                    {
                        aux = item.DateRequest.ToString();
                        if(aux.Length > MAXLENGTH)
                        {
                            aux.Substring(0, MAXLENGTH);
                        }
                        item.DateRequest = Convert.ToDateTime(aux.Substring(0, MAXLENGTH));
                        

                        aux2 = item.DateExpected.ToString();
                        if(aux2.Length > MAXLENGTH)
                        {
                            aux2.Substring(0, MAXLENGTH);
                        }
                        item.DateExpected = Convert.ToDateTime(aux.Substring(0, MAXLENGTH));
                        
                    }
                    return Ok(historic);
                }
            }
            return NotFound("2");
        }

        [HttpPost]
        public async Task<IActionResult> PostHistoric(HistoricModel historic)
        {
            string aux;
           
            historic.IdRequest = ""; //Atribuir ID default
            historic.State = 4; //Estado Final
            historic.DateRequest = DateTime.Now;
            historic.DateExpected = historic.DateRequest.AddHours(1);
            historic.IdClient = "63a4ca2eae2ed6f20722a1b4";
            historic.TotalPrice = 10.0;
            historic.IdProduct.Add("1");
            await _historicService.CreateAsync(historic);
            return CreatedAtAction(nameof(GetAll), new { id = historic.IdRequest}, historic);

        }

        private string GetUserID() { return this.User.Claims.First(i => i.Type == "id").Value; }
    }
}
