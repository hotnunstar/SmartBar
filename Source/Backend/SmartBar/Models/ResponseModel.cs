namespace SmartBar.Models
{
    public class ResponseModel
    {
        int statusCode;
        string message;

        public ResponseModel() { }

        public ResponseModel(int StatusCode, string Message)
        {
            this.statusCode = StatusCode;
            this.message = Message;
        }

        public int StatusCode { get { return statusCode; } set { statusCode = value; } }

        public string Message { get { return message; } set { message = value; } }
    }
}
