using Microsoft.Extensions.Options;
using MongoDB.Driver;
using SmartBar.Models;

/// <summary>
/// Serviço de ligação à coleção dos pedidos
/// </summary>
public class RequestService
{
    private readonly IMongoCollection<RequestModel> _requestCollection;

    /// <summary>
    /// Construtor default de ligação à coleção dos produtos
    /// </summary>
    /// <param name="smartBarDatabaseSettingsModel"></param>
    public RequestService(IOptions<SmartBarDatabaseSettingsModel> smartBarDatabaseSettingsModel)
    {
        var mongoClient = new MongoClient(smartBarDatabaseSettingsModel.Value.ConnectionString);
        var mongoDatabase = mongoClient.GetDatabase(smartBarDatabaseSettingsModel.Value.DatabaseName);
        _requestCollection = mongoDatabase.GetCollection<RequestModel>(smartBarDatabaseSettingsModel.Value.RequestCollectionName);
    }

    /// <summary>
    /// Obter todos os pedidos
    /// </summary>
    /// <returns>A lista dos pedidos</returns>
    public async Task<List<RequestModel>> GetAsync() =>
        await _requestCollection.Find(_ => true).ToListAsync();

    /// <summary>
    /// Obter todos os pedidos de determinado bar, por estado
    /// </summary>
    /// <param name="idBar"></param>
    /// <param name="state"></param>
    /// <returns>A lista dos pedidos</returns>
    public async Task<List<RequestModel>> GetAsyncByBarAndState(string idBar, int state) =>
        await _requestCollection.Find(x => x.IdBar == idBar && x.State == state).ToListAsync();


    /// <summary>
    /// Obter o Pedido por IdRequest
    /// </summary>
    /// <param name="idRequest"></param>
    /// <returns></returns>
    public async Task<RequestModel> GetAsyncByRequestId(string idRequest) =>
        await _requestCollection.Find(x => x.IdRequest == idRequest).FirstOrDefaultAsync();

    /// <summary>
    /// Obter os pedidos de cada cliente em determinado estado
    /// </summary>
    /// <param name="state"></param>
    /// <param name="idClient"></param>
    /// <returns>Pedidos de determinado cliente e determinado estado</returns>
    public async Task<List<RequestModel>> GetAsyncByClientAndState(string idClient, int state) =>
        await _requestCollection.Find(x => x.IdCliente == idClient && x.State == state).ToListAsync();

    /// <summary>
    /// Obter os pedidos por estado
    /// </summary>
    /// <param name="state"></param>
    /// <returns></returns>
    public async Task<List<RequestModel>> GetAsyncByState(int state) =>
        await _requestCollection.Find(x => x.State == state).ToListAsync();

     /// <summary>
     /// Criar um novo Pedido
     /// </summary>
     /// <param name="newRequest"></param>
     /// <returns>Pedido Criado</returns>
     public async Task CreateAsync(RequestModel newRequest) =>
         await _requestCollection.InsertOneAsync(newRequest);

    /// <summary>
    /// Atualizar um Pedido
    /// </summary>
    /// <param name="idRequest"></param>
    /// <param name="updatedRequest"></param>
    /// <returns>Pedido Atualizado</returns>
    public async Task UpdateAsync(string idRequest, RequestModel updatedRequest) =>
        await _requestCollection.ReplaceOneAsync(x => x.IdRequest == idRequest, updatedRequest);

    /// <summary>
    /// Eliminar um Pedido
    /// </summary>
    /// <param name="idRequest"></param>
    /// <returns>True or False</returns>
    public async Task DeleteAsync(string idRequest) =>
        await _requestCollection.DeleteOneAsync(x => x.IdRequest == idRequest);

}


