namespace SmartBar.Models
{
    /// <summary>
    /// Modelo da resposta
    /// </summary>
    public class ResponseModel
    {
        int statusCode;
        string message;

        /// <summary>
        /// Construtor vazio da resposta
        /// </summary>
        public ResponseModel() { }

        /// <summary>
        /// Construtor default da resposta
        /// </summary>
        /// <param name="StatusCode"></param>
        /// <param name="Message"></param>
        public ResponseModel(int StatusCode, string Message)
        {
            this.statusCode = StatusCode;
            this.message = Message;
        }

        /// <summary>
        /// Status code da resposta
        /// </summary>
        public int StatusCode { get { return statusCode; } set { statusCode = value; } }

        /// <summary>
        /// Mensagem da resposta
        /// </summary>
        public string Message { get { return message; } set { message = value; } }
    }
}
