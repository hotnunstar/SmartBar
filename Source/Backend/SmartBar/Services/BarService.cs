using Microsoft.Extensions.Options;
using MongoDB.Driver;
using SmartBar.Models;

namespace SmartBar.Services
{
    /// <summary>
    /// Serviço de ligação à coleção dos bares
    /// </summary>
    public class BarService
    {
        private readonly IMongoCollection<BarModel> _barCollection;

        /// <summary>
        /// Construtor default de ligação à coleção dos bares
        /// </summary>
        /// <param name="smartBarDatabaseSettingsModel"></param>
        public BarService(IOptions<SmartBarDatabaseSettingsModel> smartBarDatabaseSettingsModel)
        {
            var mongoClient = new MongoClient(smartBarDatabaseSettingsModel.Value.ConnectionString);
            var mongoDatabase = mongoClient.GetDatabase(smartBarDatabaseSettingsModel.Value.DatabaseName);
            _barCollection = mongoDatabase.GetCollection<BarModel>(smartBarDatabaseSettingsModel.Value.ColaboratorCollectionName);
        }

        /// <summary>
        /// Obter todos os bares
        /// </summary>
        /// <returns>Uma lista de bares</returns>
        public async Task<List<BarModel>> GetAsync() =>
            await _barCollection.Find(_ => true).ToListAsync();

        /// <summary>
        /// Obter determinado bar
        /// </summary>
        /// <param name="email"></param>
        /// <returns>O bar, se existir</returns>
        public async Task<BarModel?> GetAsyncByEmail(string email) =>
        await _barCollection.Find(x => x.Email == email).FirstOrDefaultAsync();

        /// <summary>
        /// Obter determinado bar através do id
        /// </summary>
        /// <param name="id"></param>
        /// <returns>O bar, se existir</returns>
        public async Task<BarModel?> GetAsyncById(string id) =>
        await _barCollection.Find(x => x.Id == id).FirstOrDefaultAsync();

        /// <summary>
        /// Criar um novo bar
        /// </summary>
        /// <param name="newBar"></param>
        /// <returns>O novo bar criado</returns>
        public async Task CreateAsync(BarModel newBar) =>
            await _barCollection.InsertOneAsync(newBar);

        /// <summary>
        /// Atualizar determinado bar
        /// </summary>
        /// <param name="id"></param>
        /// <param name="updatedBar"></param>
        /// <returns>O bar atualizado</returns>
        public async Task UpdateAsync(string id, BarModel updatedBar) =>
            await _barCollection.ReplaceOneAsync(x => x.Id == id, updatedBar);

        /// <summary>
        /// Remover determinado bar
        /// </summary>
        /// <param name="id"></param>
        /// <returns>True ou False</returns>
        public async Task RemoveAsync(string id) =>
            await _barCollection.DeleteOneAsync(x => x.Id == id);
    }
}
