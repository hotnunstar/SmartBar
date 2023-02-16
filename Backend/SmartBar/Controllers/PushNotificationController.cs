using FirebaseAdmin.Messaging;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SmartBar.Models;
using SmartBar.Services;

namespace SmartBar.Controllers
{
    /// <summary>
    /// Controlador de push notifications
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    public class PushNotificationController : ControllerBase
    {
        private readonly PushNotificationService _pushNotificationService;

        /// <summary>
        /// Contrutor do controlador de push notifications
        /// </summary>
        /// <param name="pushNotificationService"></param>
        public PushNotificationController(PushNotificationService pushNotificationService)
        {
            _pushNotificationService = pushNotificationService;
        }

        /// <summary>
        /// Obter notificações por utilizador
        /// </summary>
        /// <returns>Ok, BadRequest ou NotFound</returns>
        [HttpGet, Authorize]
        public async Task<IActionResult> GetAllByUserId()
        {
            var userId = GetUtilizadorID();
            if (userId == null) return BadRequest("Erro na identificação do utilizador");

            var list = await _pushNotificationService.GetAsyncByUser(userId);
            if (list.Count > 0)
            {
                return Ok(list);
            }
            else return NotFound("Não foram encontradas notificações");
        }

        /// <summary>
        /// Inserir uma notificação
        /// </summary>
        /// <param name="pushNotification"></param>
        /// <returns>Ok ou BadRequest</returns>
        [HttpPost, Authorize]
        public async Task<IActionResult> PostNotification(PushNotificationModel pushNotification)
        {
            try
            {
                await _pushNotificationService.CreateAsync(pushNotification);
                return Ok("Notificação criada");
            }
            catch
            {
                return BadRequest("Erro ao criar a notificação");
            }
        }

        /// <summary>
        /// Apagar uma notificação
        /// </summary>
        /// <param name="notificationId"></param>
        /// <returns>Ok ou BadRequest</returns>
        [HttpDelete, Authorize]
        public async Task<IActionResult> DeleteNotification(string notificationId)
        {
            try
            {
                await _pushNotificationService.DeleteAsync(notificationId);
                return Ok("Notificação removida");
            }
            catch
            {
                return BadRequest("Erro ao apagar a notificação");
            } 
        }

        private string GetUtilizadorID() { return this.User.Claims.First(i => i.Type == "id").Value; }
    }
}
