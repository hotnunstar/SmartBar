using Microsoft.Extensions.Options;
using MongoDB.Driver;
using SmartBar.Models;

namespace SmartBar.Services
{
    /// <summary>
    /// Serviço de ligação à coleção dos produtos
    /// </summary>
    public class ProductService
    {
        private readonly IMongoCollection<ProductModel> _productCollection;

        /// <summary>
        /// Construtor default de ligação à coleção dos produtos
        /// </summary>
        /// <param name="smartBarDatabaseSettingsModel"></param>
        public ProductService(IOptions<SmartBarDatabaseSettingsModel> smartBarDatabaseSettingsModel)
        {
            var mongoClient = new MongoClient(smartBarDatabaseSettingsModel.Value.ConnectionString);
            var mongoDatabase = mongoClient.GetDatabase(smartBarDatabaseSettingsModel.Value.DatabaseName);
            _productCollection = mongoDatabase.GetCollection<ProductModel>(smartBarDatabaseSettingsModel.Value.ProductCollectionName);
        }

        /// <summary>
        /// Obter todos os produtos
        /// </summary>
        /// <returns>A lista dos produtos</returns>
        public async Task<List<ProductModel>> GetAsync() =>
            await _productCollection.Find(_ => true).ToListAsync();

        /// <summary>
        /// Obter determinado produto através do id
        /// </summary>
        /// <param name="id"></param>
        /// <returns>O produto, se existir</returns>
        public async Task<ProductModel?> GetAsync(string id) =>
        await _productCollection.Find(x => x.Id == id).FirstOrDefaultAsync();

        /// <summary>
        /// Obter determinado produto através do nome
        /// </summary>
        /// <param name="name"></param>
        /// <returns>O produto se existir</returns>
        public async Task<ProductModel?> GetAsyncByName(string name) =>
        await _productCollection.Find(x => x.Name == name).FirstOrDefaultAsync();

        /// <summary>
        /// Criar um novo produto
        /// </summary>
        /// <param name="newProduct"></param>
        /// <returns>O novo produto criado</returns>
        public async Task CreateAsync(ProductModel newProduct) =>
            await _productCollection.InsertOneAsync(newProduct);

        /// <summary>
        /// Atualizar determinado produto
        /// </summary>
        /// <param name="id"></param>
        /// <param name="updatedProduct"></param>
        /// <returns>O produto atualizado</returns>
        public async Task UpdateAsync(string id, ProductModel updatedProduct) =>
            await _productCollection.ReplaceOneAsync(x => x.Id == id, updatedProduct);

        /// <summary>
        /// Remover determinado produto
        /// </summary>
        /// <param name="id"></param>
        /// <returns>True ou False</returns>
        public async Task RemoveAsync(string id) =>
            await _productCollection.DeleteOneAsync(x => x.Id == id);
    }
}
