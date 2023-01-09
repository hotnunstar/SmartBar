using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace SmartBar.Models
{
    /// <summary>
    /// Modelo de um historico
    /// </summary>
    public class HistoricModel
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        string idRequest;
        string idClient;
        string idBar;
        List<ProductRequest> productAndQuantity;
        string dateRequest;
        double totalPrice;
        int state;
        string horas;

        /// <summary>
        /// Construtor vazio do Histórico
        /// </summary>
        public HistoricModel() { }

        /// <summary>
        /// Construtor default do Histórico
        /// </summary>
        /// <param name="idRequest"></param>
        /// <param name="idClient"></param>
        /// <param name="idBar"></param>
        /// <param name="productAndQuantity"></param>
        /// <param name="dateRequest"></param>
        /// <param name="totalPrice"></param>
        /// <param name="state"></param>
        /// <param name="horas"></param>
        public HistoricModel(string idRequest, string idClient, string idBar, List<ProductRequest> productAndQuantity, string dateRequest, double totalPrice, int state, string horas)
        {
            this.idRequest = idRequest;
            this.idClient = idClient;
            this.idBar = idBar;
            this.productAndQuantity = productAndQuantity;
            this.dateRequest = dateRequest;
            this.totalPrice = totalPrice;
            this.state = state;
            this.horas = horas;
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
        /// ID do bar a que pertence o Pedido
        /// </summary>
        public string IdBar { get { return idBar; } set { idBar = value; } }

        /// <summary>
        /// Lista de ID´s de Produtos que o Pedido tem
        /// </summary>
        public List<ProductRequest> ProductAndQuantity { get { return productAndQuantity; } set { productAndQuantity = value; } }

        /// <summary>
        /// Data que o Pedido foi efetuado
        /// </summary>
        public string DateRequest { get { return dateRequest; } set { dateRequest = value; } }

        /// <summary>
        /// Preço total do Pedido
        /// </summary>
        public double TotalPrice { get { return totalPrice; } set { totalPrice = value; } }

        /// <summary>
        /// Estado que o pedido se encontra
        /// </summary>
        public int State { get { return state; } set { state = value; } }

        /// <summary>
        /// Hora prevista para o levantamento do Pedido
        /// </summary>
        public string Horas { get { return horas; } set { horas = value; } }
    }
}

