using Microsoft.Extensions.Options;
using MongoDB.Driver;
using SmartBar.Models;

namespace SmartBar.Services
{
    /// <summary>
    /// Serviço de ligação à coleção do histórico
    /// </summary>
    public class HistoricService
    {
        private readonly IMongoCollection<HistoricModel> _historicCollection;

        /// <summary>
        /// Construtor default de ligação à coleção do histórico
        /// </summary>
        /// <param name="smartBarDatabaseSettingsModel"></param>
        public HistoricService(IOptions<SmartBarDatabaseSettingsModel> smartBarDatabaseSettingsModel)
        {
            var mongoClient = new MongoClient(smartBarDatabaseSettingsModel.Value.ConnectionString);
            var mongoDatabase = mongoClient.GetDatabase(smartBarDatabaseSettingsModel.Value.DatabaseName);
            _historicCollection = mongoDatabase.GetCollection<HistoricModel>(smartBarDatabaseSettingsModel.Value.HistoricCollectionName);
        }

        /// <summary>
        /// Obter todos os pedidos em Histórico
        /// </summary>
        /// <returns></returns>
        public async Task<List<HistoricModel>> GetAsync() =>
            await _historicCollection.Find(_ => true).ToListAsync();

        /// <summary>
        /// Obter o histórico de pedidos de um cliente
        /// </summary>
        /// <param name="idClient"></param>
        /// <returns></returns>
        public async Task<List<HistoricModel>> GetAsyncByClient(string idClient) =>
            await _historicCollection.Find(x => x.IdClient == idClient).ToListAsync();

        /// <summary>
        /// Obter o Histórico de pedidos por idBar
        /// </summary>
        /// <param name="idBar"></param>
        /// <returns></returns>
        public async Task<List<HistoricModel>> GetAsyncByBar(string idBar) =>
            await _historicCollection.Find(x => x.IdBar == idBar).ToListAsync();

        /// <summary>
        /// Obter o Histórico de pedidos por idBar e por dateRequest
        /// </summary>
        /// <param name="idBar"></param>
        /// <param name="dateRequest"></param>
        /// <returns></returns>
        public async Task<List<HistoricModel>> GetAsyncByBarAndDate(string idBar, string dateRequest) =>
            await _historicCollection.Find(x => x.IdBar == idBar && x.DateRequest == dateRequest).ToListAsync();

        /// <summary>
        /// Criar um novo item ao Histórico
        /// </summary>
        /// <param name="newHistoric"></param>
        /// <returns></returns>
        public async Task CreateAsync(HistoricModel newHistoric) =>
            await _historicCollection.InsertOneAsync(newHistoric);
    }
}
