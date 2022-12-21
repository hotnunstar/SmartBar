using Microsoft.Extensions.Options;
using MongoDB.Driver;
using SmartBar.Models;

namespace SmartBar.Services
{
    /// <summary>
    /// Serviço de ligação à coleção dos bares
    /// </summary>
    public class ColaboratorService
    {
        private readonly IMongoCollection<ColaboratorModel> _colaboratorCollection;

        /// <summary>
        /// Construtor default de ligação à coleção dos bares
        /// </summary>
        /// <param name="smartBarDatabaseSettingsModel"></param>
        public ColaboratorService(IOptions<SmartBarDatabaseSettingsModel> smartBarDatabaseSettingsModel)
        {
            var mongoClient = new MongoClient(smartBarDatabaseSettingsModel.Value.ConnectionString);
            var mongoDatabase = mongoClient.GetDatabase(smartBarDatabaseSettingsModel.Value.DatabaseName);
            _colaboratorCollection = mongoDatabase.GetCollection<ColaboratorModel>(smartBarDatabaseSettingsModel.Value.ColaboratorCollectionName);
        }

        /// <summary>
        /// Obter todos os bares
        /// </summary>
        /// <returns>Uma lista de bares</returns>
        public async Task<List<ColaboratorModel>> GetAsync() =>
            await _colaboratorCollection.Find(_ => true).ToListAsync();

        /// <summary>
        /// Obter determinado bar
        /// </summary>
        /// <param name="email"></param>
        /// <returns>O bar, se existir</returns>
        public async Task<ColaboratorModel?> GetAsync(string email) =>
        await _colaboratorCollection.Find(x => x.Email == email).FirstOrDefaultAsync();

        /// <summary>
        /// Criar um novo bar
        /// </summary>
        /// <param name="newColaborator"></param>
        /// <returns>O novo bar criado</returns>
        public async Task CreateAsync(ColaboratorModel newColaborator) =>
            await _colaboratorCollection.InsertOneAsync(newColaborator);

        /// <summary>
        /// Atualizar determinado bar
        /// </summary>
        /// <param name="id"></param>
        /// <param name="updatedColaborator"></param>
        /// <returns>O bar atualizado</returns>
        public async Task UpdateAsync(string id, ColaboratorModel updatedColaborator) =>
            await _colaboratorCollection.ReplaceOneAsync(x => x.Id == id, updatedColaborator);

        /// <summary>
        /// Remover determinado bar
        /// </summary>
        /// <param name="id"></param>
        /// <returns>True ou False</returns>
        public async Task RemoveAsync(string id) =>
            await _colaboratorCollection.DeleteOneAsync(x => x.Id == id);
    }
}
