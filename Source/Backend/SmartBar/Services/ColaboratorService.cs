using Microsoft.Extensions.Options;
using MongoDB.Driver;
using SmartBar.Models;

namespace SmartBar.Services
{
    public class ColaboratorService
    {
        private readonly IMongoCollection<ColaboratorModel> _colaboratorCollection;

        public ColaboratorService(IOptions<SmartBarDatabaseSettingsModel> smartBarDatabaseSettingsModel)
        {
            var mongoClient = new MongoClient(smartBarDatabaseSettingsModel.Value.ConnectionString);
            var mongoDatabase = mongoClient.GetDatabase(smartBarDatabaseSettingsModel.Value.DatabaseName);
            _colaboratorCollection = mongoDatabase.GetCollection<ColaboratorModel>(smartBarDatabaseSettingsModel.Value.ColaboratorCollectionName);
        }

        public async Task<List<ColaboratorModel>> GetAsync() =>
            await _colaboratorCollection.Find(_ => true).ToListAsync();

        public async Task<ColaboratorModel?> GetAsync(string email) =>
        await _colaboratorCollection.Find(x => x.Email == email).FirstOrDefaultAsync();

        public async Task CreateAsync(ColaboratorModel newColaborator) =>
            await _colaboratorCollection.InsertOneAsync(newColaborator);

        public async Task UpdateAsync(string id, ColaboratorModel updatedColaborator) =>
            await _colaboratorCollection.ReplaceOneAsync(x => x.Id == id, updatedColaborator);

        public async Task RemoveAsync(string id) =>
            await _colaboratorCollection.DeleteOneAsync(x => x.Id == id);
    }
}
