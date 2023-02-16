using Microsoft.Extensions.Options;
using MongoDB.Driver;
using SmartBar.Models;

namespace SmartBar.Services
{
    /// <summary>
    /// Serviço de push notifications
    /// </summary>
    public class PushNotificationService
    {
        private readonly IMongoCollection<PushNotificationModel> _pushNotificationCollection;

        /// <summary>
        /// Contrutor do service de push notifications
        /// </summary>
        /// <param name="smartBarDatabaseSettingsModel"></param>
        public PushNotificationService(IOptions<SmartBarDatabaseSettingsModel> smartBarDatabaseSettingsModel)
        {
            var mongoClient = new MongoClient(smartBarDatabaseSettingsModel.Value.ConnectionString);
            var mongoDatabase = mongoClient.GetDatabase(smartBarDatabaseSettingsModel.Value.DatabaseName);
            _pushNotificationCollection = mongoDatabase.GetCollection<PushNotificationModel>(smartBarDatabaseSettingsModel.Value.PushNotificationCollectionName);
        }

        /// <summary>
        /// Inserir uma nova notificação
        /// </summary>
        /// <param name="newNotification"></param>
        /// <returns>O resultado da inserção</returns>
        public async Task CreateAsync(PushNotificationModel newNotification) =>
            await _pushNotificationCollection.InsertOneAsync(newNotification);

        /// <summary>
        /// Obter todas as notificações por id de utilizador
        /// </summary>
        /// <param name="userId"></param>
        /// <returns>Lista de notificações</returns>
        public async Task<List<PushNotificationModel>> GetAsyncByUser(string userId) =>
            await _pushNotificationCollection.Find(x => x.UserId == userId).ToListAsync();

        /// <summary>
        /// Apagar determinada notificação
        /// </summary>
        /// <param name="idNotification"></param>
        /// <returns>O resultado da operação</returns>
        public async Task DeleteAsync(string idNotification) =>
            await _pushNotificationCollection.DeleteOneAsync(x => x.Id == idNotification);

    }
}
