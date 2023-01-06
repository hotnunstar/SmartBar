using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace SmartBar.Models
{
    public class ProductRequest
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        string idProduct;
        int quantity;


        /// <summary>
        /// Construtor dos produtos selecionados no pedido vazio
        /// </summary>
        public ProductRequest() { }

        /// <summary>
        /// construtor default
        /// </summary>
        /// <param name="idProduct"></param>
        /// <param name="quantity"></param>
        public ProductRequest(string idProduct, int quantity)
        {
            this.idProduct = idProduct;
            this.quantity = quantity;
        }

        /// <summary>
        /// Id do produto pertencente ao pedido
        /// </summary>

        public string IdProduct { get { return this.idProduct; } }

        /// <summary>
        /// quantidade de produtos selecionados 
        /// </summary>
        public int Quantity { get { return this.quantity; } }   

    }
}
