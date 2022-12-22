﻿using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;
/*


=============================================================
            FALTA TRABALHAR A IMAGEM
============================================================



*/
namespace SmartBar.Models
{
    /// <summary>
    /// Modelo de um produto
    /// </summary>
    public class ProductModel
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        string id;
        string name;
       // byte[] picture;
        decimal price;
        int stock;
        int type;

        /// <summary>
        /// Construtor vazio de um produto
        /// </summary>
        public ProductModel() { }

        /// <summary>
        /// Construtor default de um produto
        /// </summary>
        /// <param name="Id"></param>
        /// <param name="Name"></param>
        /// <param name="Picture"></param>
        /// <param name="Price"></param>
        /// <param name="Stock"></param>
        public ProductModel(string Id, string Name, /*byte[] Picture*/ decimal Price, int Stock, int type)
        {
            id = Id;
            name = Name;
            //picture = Picture;
            price = Price;
            stock = Stock;
            this.type = type;
        }


        /// <summary>
        /// ID do produto
        /// </summary>
        public string Id { get { return id; } set { id = value; } }

        /// <summary>
        /// Nome do produto
        /// </summary>
        public string Name { get { return name; } set { name = value; } }

        /// <summary>
        /// Imagem do produto
        /// </summary>
        //public byte[] Picture { get { return picture; } set { picture = value; } }

        /// <summary>
        /// Preço do produto
        /// </summary>
        public decimal Price { get { return price; } set { price = value; } }

        /// <summary>
        /// Stock do produto
        /// </summary>
        public int Stock { get { return stock; } set { stock = value; } }

        /// <summary>
        /// Tipo de Produto -> hot food =1; Packaged = 2; hot drink =3; cold drink =4
        /// </summary>
        public int Type { get { return type; } set { type = value; } }
    }
}
