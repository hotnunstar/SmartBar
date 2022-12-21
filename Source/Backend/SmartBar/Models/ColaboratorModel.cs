using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace SmartBar.Models
{
    /// <summary>
    /// Modelo do colaborador
    /// </summary>
    public class ColaboratorModel
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        string id;
        string email;
        string password;
        string name;

        /// <summary>
        /// Construtor do colaborador vazio
        /// </summary>
        public ColaboratorModel() { }

        /// <summary>
        /// Construtor do colaborador default
        /// </summary>
        /// <param name="Id"></param>
        /// <param name="Email"></param>
        /// <param name="Password"></param>
        /// <param name="Name"></param>
        public ColaboratorModel(string Id, string Email, string Password, string Name)
        {
            this.id = Id;
            this.email = Email;
            this.password = Password;
            this.name = Name;
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
        /// Nome de apresentação do bar
        /// </summary>
        public string Name { get { return name; } set { name = value; } }
    }
}
