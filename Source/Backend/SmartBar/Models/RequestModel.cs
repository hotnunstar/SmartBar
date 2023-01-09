using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace SmartBar.Models
{
    public class RequestModel
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        string idRequest;
        string idCliente;
        string idBar;
        List<ProductRequest> productAndQuantity;
        string dateRequest;
        double value;
        int state;
        string horas;
        string firebaseToken;

        /// <summary>
        /// Construtor vazio de um Pedido
        /// </summary>
        public RequestModel() { }

        /// <summary>
        /// Construtor default de um Pedido
        /// </summary>
        /// <param name="IdRequest"></param>
        /// <param name="IdCliente"></param>
        /// <param name="productAndQuantity"></param>
        /// <param name="DateRequest"></param>
        /// <param name="Value"></param>
        /// <param name="State"></param>
        /// <param name="FirebaseToken"></param>
        public RequestModel(string IdRequest, string IdCliente, List<ProductRequest> productAndQuantity, string DateRequest, double Value, int State, string FirebaseToken, string idBar, string horas)
        {
            idRequest = IdRequest;
            idCliente = IdCliente;
            this.productAndQuantity = productAndQuantity;
            dateRequest = DateRequest;
            value = Value;
            state = State;
            firebaseToken = FirebaseToken;
            this.idBar = idBar;
            this.horas = horas;
        }

        /// <summary>
        /// ID do Pedido
        /// </summary>
        public string IdRequest { get { return idRequest; } set { idRequest = value; } }

        /// <summary>
        /// ID do Cliente a que pertence o Pedido
        /// </summary>
        public string IdCliente { get { return idCliente; } set { idCliente = value; } }

        /// <summary>
        /// Lista de ID´s e quantidades de Produtos que o Pedido tem
        /// </summary>
        public List<ProductRequest> ProductAndQuantity { get { return productAndQuantity; } set { productAndQuantity = value; } }

        /// <summary>
        /// Data que o Pedido foi efetuado
        /// </summary>
        public string DateRequest { get { return dateRequest; } set { dateRequest = value;} }

        /// <summary>
        /// Valor total do Pedido
        /// </summary>
        public double Value { get { return value; } set { this.value = value; } }

        /// <summary>
        /// Estado que o pedido se encontra
        /// </summary>
        public int State { get { return state; } set { state = value; } }

        /// <summary>
        /// Token da firebase (para push notification)
        /// </summary>
        public string FirebaseToken { get { return firebaseToken; } set { firebaseToken = value; } }

        /// <summary>
        /// Id do bar
        /// </summary>
        public string IdBar { get { return idBar; } set { idBar = value; } }

        /// <summary>
        /// hora prevista para o levantamento do pedido
        /// </summary>
        public string Horas { get { return horas; } set { horas = value; } }
    }
}
