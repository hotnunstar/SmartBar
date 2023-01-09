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
        List<ProductRequest> productAndQuantity;
        string dateRequest;
        String horas;
        double totalPrice;
        int state;

        /// <summary>
        /// Construtor vazio do Histórico
        /// </summary>
        public HistoricModel() { }

        /// <summary>
        /// Construtor default do Histórico
        /// </summary>
        /// <param name="IdRequest"></param>
        /// <param name="IdClient"></param>
        /// <param name="ProductAndQuantity"></param>
        /// <param name="DateRequest"></param>
        /// <param name="Horas"></param>
        /// <param name="TotalPrice"></param>
        /// <param name="State"></param>
        public HistoricModel(string IdRequest, string IdClient, List<ProductRequest> ProductAndQuantity, string DateRequest, string Horas, double TotalPrice, int State)
        {
            idRequest = IdRequest;
            idClient = IdClient;
            productAndQuantity = ProductAndQuantity;
            dateRequest = DateRequest;
            horas = Horas;
            totalPrice = TotalPrice;
            state = State;
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
        public List<ProductRequest> ProductAndQuantity { get { return productAndQuantity; } set { productAndQuantity = value; } }

        /// <summary>
        /// Data de levantamento do Pedido
        /// </summary>
        public string DateRequest { get { return dateRequest; } set { dateRequest = value; } }

        /// <summary>
        /// Data que o Pedido foi efetuado
        /// </summary>
        public string Horas { get { return horas; } set { horas = value; } }

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

