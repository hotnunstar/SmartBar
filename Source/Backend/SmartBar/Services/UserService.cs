using Microsoft.Extensions.Options;
using MongoDB.Driver;
using SmartBar.Models;

namespace SmartBar.Services
{

    /// <summary>
    /// Serviço de ligação à coleção dos clientes
    /// </summary>
    public class UserService
    {
        private readonly IMongoCollection<UserModel> _userCollection;

        /// <summary>
        /// Construtor default de ligação à coleção dos clientes
        /// </summary>
        /// <param name="smartBarDatabaseSettingsModel"></param>
        public UserService(IOptions<SmartBarDatabaseSettingsModel> smartBarDatabaseSettingsModel)
        {
            var mongoClient = new MongoClient(smartBarDatabaseSettingsModel.Value.ConnectionString);
            var mongoDatabase = mongoClient.GetDatabase(smartBarDatabaseSettingsModel.Value.DatabaseName);
            _userCollection = mongoDatabase.GetCollection<UserModel>(smartBarDatabaseSettingsModel.Value.UserCollectionName);
        }

        /// <summary>
        /// Obter todos os clientes
        /// </summary>
        /// <returns>Uma lista de clientes</returns>
        public async Task<List<UserModel>> GetAsync() =>
            await _userCollection.Find(_ => true).ToListAsync();

        /// <summary>
        /// Obter determinado cliente através do email
        /// </summary>
        /// <param name="email"></param>
        /// <returns>O cliente, se existir</returns>
        public async Task<UserModel?> GetAsyncByEmail(string email) =>
        await _userCollection.Find(x => x.Email == email).FirstOrDefaultAsync();

        /// <summary>
        /// Obter determinado cliente através do id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public async Task<UserModel?> GetAsyncById(string id) =>
        await _userCollection.Find(x => x.Id == id).FirstOrDefaultAsync();

        /// <summary>
        /// Criar um novo cliente
        /// </summary>
        /// <param name="newColaborator"></param>
        /// <returns>O novo cliente criado</returns>
        public async Task CreateAsync(UserModel newUser) =>
            await _userCollection.InsertOneAsync(newUser);

        /// <summary>
        /// Atualizar determinado cliente
        /// </summary>
        /// <param name="id"></param>
        /// <param name="updatedColaborator"></param>
        /// <returns>O cliente atualizado</returns>
        public async Task UpdateAsync(string id, UserModel updatedUser) =>
            await _userCollection.ReplaceOneAsync(x => x.Id == id, updatedUser);

        /// <summary>
        /// Remover determinado cliente
        /// </summary>
        /// <param name="id"></param>
        /// <returns>True ou False</returns>
        public async Task RemoveAsync(string id) =>
            await _userCollection.DeleteOneAsync(x => x.Id == id);
    }
}
