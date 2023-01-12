namespace SmartBar.Models
{
    /// <summary>
    /// Modelo de uma push notification
    /// </summary>
    public class PushNotificationModel
    {
        string token;
        string title;
        string message;

        /// <summary>
        /// Construtor de push notification vazio
        /// </summary>
        public PushNotificationModel() { }

        /// <summary>
        /// Contrutor de push notification default
        /// </summary>
        /// <param name="Token"></param>
        /// <param name="Title"></param>
        /// <param name="Message"></param>
        public PushNotificationModel(string Token, string Title, string Message) 
        { 
            this.token = Token;
            this.title = Title;
            this.message = Message;
        }

        /// <summary>
        /// Token firebase do dispositivo
        /// </summary>
        public string Token { get { return token; } set { token = value; } }

        /// <summary>
        /// Título da push notification
        /// </summary>
        public string Title { get { return title; } set { title = value; } }

        /// <summary>
        /// Mensagem da push notification
        /// </summary>
        public string Message { get { return message; } set { message = value; } }
    }
}
