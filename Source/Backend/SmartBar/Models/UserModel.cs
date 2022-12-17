using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System.Runtime.Serialization;

namespace SmartBar.Models
{
    public class UserModel
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        string id;
        string email;
        string password;
        string name;
        decimal balance;


        public UserModel() { }

        public UserModel(string Id, string Email, string Password, string Name, decimal Balance)
        {
            this.id = Id;
            this.email = Email;
            this.password = Password;
            this.name = Name;
            this.balance = Balance;
        }

        public string Id { get { return id; } set { id = value; } }
        public string Email { get { return email; } set { email = value; } }
        public string Password { get { return password; } set { password = value; } }
        public string Name { get { return name; } set { name= value; } }
        public decimal Balance { get { return balance; } set { balance = value; } }
    }
}
