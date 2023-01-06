using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace SmartBar.Models
{
    public class HistoricModel
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        string idRequest;
        string idClient;
        List<ProductRequest> productAndQuantity;
        DateTime dateExpected;
        DateTime dateRequest;
        double totalPrice;
        int state;

        /// <summary>
        /// Construtor vazio do Histórico
        /// </summary>
        public HistoricModel() { }

        /// <summary>
        /// Construtor default do Histórico
        /// </summary>
        /// <param name="idRequest"></param>
        /// <param name="idClient"></param>
        /// <param name="idProduct"></param>
        /// <param name="dateExpected"></param>
        /// <param name="dateRequest"></param>
        /// <param name="totalPrice"></param>
        /// <param name="state"></param>
        public HistoricModel(string idRequest, string idClient, List<ProductRequest> productAndQuantity, DateTime dateExpected, DateTime dateRequest, double totalPrice, int state)
        {
            this.idRequest = idRequest;
            this.idClient = idClient;
            this.productAndQuantity = productAndQuantity;
            this.dateExpected = dateExpected;
            this.dateRequest = dateRequest;
            this.totalPrice = totalPrice;
            this.state = state;
        }

        /// <summary>
        /// ID do Pedido
        /// </summary>
        public string IdRequest { get { return idRequest; } set { idRequest = value; } }

        /// <summary>
        /// ID do Cliente a que pertence o Pedido
        /// </summary>
        public string IdClient { get { return idClient; } set { idClient = value; } }

        /// <summary>
        /// Lista de ID´s de Produtos que o Pedido tem
        /// </summary>
        public List<ProductRequest> IdProduct { get { return productAndQuantity; } set { productAndQuantity = value; } }

        /// <summary>
        /// Data de levantamento do Pedido
        /// </summary>
        public DateTime DateExpected { get { return dateExpected; } set { dateExpected = value; } }

        /// <summary>
        /// Data que o Pedido foi efetuado
        /// </summary>
        public DateTime DateRequest { get { return dateRequest; } set { dateRequest = value; } }

        /// <summary>
        /// Preço total do Pedido
        /// </summary>
        public double TotalPrice { get { return totalPrice; } set { totalPrice = value; } }

        /// <summary>
        /// Estado que o pedido se encontra
        /// </summary>
        public int State { get { return state; } set { state = value; } }
    }
}

