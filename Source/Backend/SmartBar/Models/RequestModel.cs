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
        List<string> idProduct;
        DateTime datePickUp;
        DateTime dateRequest;
        double value;
        int state;

        /// <summary>
        /// Construtor vazio de um Pedido
        /// </summary>
        public RequestModel() { }

        /// <summary>
        /// Construtor default de um Pedido
        /// </summary>
        /// <param name="IdRequest"></param>
        /// <param name="IdCliente"></param>
        /// <param name="IdProduct"></param>
        /// <param name="DatePickUp"></param>
        /// <param name="DateRequest"></param>
        /// <param name="Value"></param>
        /// <param name="State"></param>
        public RequestModel(string IdRequest, string IdCliente, List<string> IdProduct, DateTime DatePickUp, DateTime DateRequest, double Value, int State)
        {
            idRequest = IdRequest;
            idCliente = IdCliente;
            idProduct = IdProduct;
            datePickUp = DatePickUp;
            dateRequest = DateRequest;
            value = Value;
            state = State;
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
        /// Lista de ID´s de Produtos que o Pedido tem
        /// </summary>
        public List<string> IdProduct { get { return idProduct; } set { idProduct = value; } }

        /// <summary>
        /// Data de levantamento do Pedido
        /// </summary>
        public DateTime DatePickUp { get { return datePickUp; } set { datePickUp = value; } }

        /// <summary>
        /// Data que o Pedido foi efetuado
        /// </summary>
        public DateTime DateRequest { get { return dateRequest; } set { dateRequest = value;} }

        /// <summary>
        /// Valor total do Pedido
        /// </summary>
        public double Value { get { return value; } set { this.value = value; } }

        /// <summary>
        /// Estado que o pedido se encontra
        /// </summary>
        public int State { get { return state; } set { state = value; } }
    }
}
