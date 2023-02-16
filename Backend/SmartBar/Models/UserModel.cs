using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System.Runtime.Serialization;

namespace SmartBar.Models
{
    /// <summary>
    /// Modelo do cliente
    /// </summary>
    public class UserModel
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        string id;
        string email;
        string password;
        string name;
        double balance;

        /// <summary>
        /// Construtor vazio do cliente
        /// </summary>
        public UserModel() { }

        /// <summary>
        /// Construtor default do cliente
        /// </summary>
        /// <param name="Id"></param>
        /// <param name="Email"></param>
        /// <param name="Password"></param>
        /// <param name="Name"></param>
        /// <param name="Balance"></param>
        public UserModel(string Id, string Email, string Password, string Name, double Balance)
        {
            this.id = Id;
            this.email = Email;
            this.password = Password;
            this.name = Name;
            this.balance = Balance;
        }

        /// <summary>
        /// Id do cliente
        /// </summary>
        public string Id { get { return id; } set { id = value; } }
        
        /// <summary>
        /// Email do cliente
        /// </summary>
        public string Email { get { return email; } set { email = value; } }
        
        /// <summary>
        /// Password do cliente
        /// </summary>
        public string Password { get { return password; } set { password = value; } }
        
        /// <summary>
        /// Nome do cliente
        /// </summary>
        public string Name { get { return name; } set { name= value; } }
        
        /// <summary>
        /// Saldo do cliente
        /// </summary>
        public double Balance { get { return balance; } set { balance = value; } }
    }
}
