using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace SmartBar.Models
{
    /// <summary>
    /// Modelo de uma push notification
    /// </summary>
    public class PushNotificationModel
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        string id;
        string token;
        string userId;
        string title;
        string message;
        string date;

        /// <summary>
        /// Construtor de push notification vazio
        /// </summary>
        public PushNotificationModel() { }

        /// <summary>
        /// Contrutor de push notification default
        /// </summary>
        /// <param name="Id"></param>
        /// <param name="Token"></param>
        /// <param name="UserId"></param>
        /// <param name="Title"></param>
        /// <param name="Message"></param>
        /// <param name="Date"></param>
        public PushNotificationModel(string Id, string Token, string UserId, string Title, string Message, string Date) 
        { 
            id = Id;
            token = Token;
            userId = UserId;
            title = Title;
            message = Message;
            date = Date;
        }

        /// <summary>
        /// Id da notificação
        /// </summary>
        public string Id { get { return id; } set { id = value; } }

        /// <summary>
        /// Token firebase do dispositivo
        /// </summary>
        public string Token { get { return token; } set { token = value; } }

        /// <summary>
        /// ID do utilizador que recebe a push notification
        /// </summary>
        public string UserId { get { return userId; } set { userId = value; } }

        /// <summary>
        /// Título da push notification
        /// </summary>
        public string Title { get { return title; } set { title = value; } }

        /// <summary>
        /// Mensagem da push notification
        /// </summary>
        public string Message { get { return message; } set { message = value; } }

        /// <summary>
        /// Data da push notification
        /// </summary>
        public string Date { get { return date; } set { date = value; } }
    }
}
