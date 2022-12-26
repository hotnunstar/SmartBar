using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace SmartBar.Models
{
    /// <summary>
    /// Modelo do bar
    /// </summary>
    public class BarModel
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        string id;
        string email;
        string password;
        string description;

        /// <summary>
        /// Construtor do bar vazio
        /// </summary>
        public BarModel() { }

        /// <summary>
        /// Construtor do bar default
        /// </summary>
        /// <param name="Id"></param>
        /// <param name="Email"></param>
        /// <param name="Password"></param>
        /// <param name="Description"></param>
        public BarModel(string Id, string Email, string Password, string Description)
        {
            this.id = Id;
            this.email = Email;
            this.password = Password;
            this.description = Description;
        }

        /// <summary>
        /// ID do Bar
        /// </summary>
        public string Id { get { return id; } set { id = value; } }

        /// <summary>
        /// Email de acesso do bar
        /// </summary>
        public string Email { get { return email; } set { email = value; } }

        /// <summary>
        /// Password de acesso do bar
        /// </summary>
        public string Password { get { return password; } set { password = value; } }

        /// <summary>
        /// Descrição do bar
        /// </summary>
        public string Description { get { return description; } set { description = value; } }
    }
}
