namespace SmartBar.Models
{
    /// <summary>
    /// Modelo para resposta de um token de autenticação
    /// </summary>
    public class TokenResponseModel
    {
        int statusCode;
        string message;
        string token;

        /// <summary>
        /// Construtor vazio da resposta
        /// </summary>
        public TokenResponseModel() { }

        /// <summary>
        /// Construtor default da resposta
        /// </summary>
        /// <param name="StatusCode"></param>
        /// <param name="Message"></param>
        public TokenResponseModel(int StatusCode, string Message, string token)
        {
            this.statusCode = StatusCode;
            this.message = Message;
            this.token = token;
        }

        /// <summary>
        /// Status code da resposta
        /// </summary>
        public int StatusCode { get { return statusCode; } set { statusCode = value; } }

        /// <summary>
        /// Mensagem da resposta
        /// </summary>
        public string Message { get { return message; } set { message = value; } }

        /// <summary>
        /// Token de autenticação
        /// </summary>
        public string Token { get { return token; } set { token = value; } }
    }
}
