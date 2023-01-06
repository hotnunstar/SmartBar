namespace SmartBar.Models
{
    /// <summary>
    /// Modelo do login
    /// </summary>
    public class LoginModel
    {
        string userType;
        string email;
        string password;
        string firebaseToken;

        /// <summary>
        /// Construtor do Modelo do Login
        /// </summary>
        /// <param name="UserType"></param>
        /// <param name="Email"></param>
        /// <param name="Password"></param>
        /// /// <param name="FirebaseToken"></param>
        public LoginModel(string UserType, string Email, string Password, string FirebaseToken)
        {
            userType = UserType;
            email = Email;
            password = Password;
            firebaseToken = FirebaseToken;
        }

        /// <summary>
        /// Tipo de utilizador (Cliente ou Colaborador)
        /// </summary>
        public string UserType { get { return userType; } set { userType = value; } }

        /// <summary>
        /// Email do utilizador
        /// </summary>
        public string Email { get { return email; } set { email = value; } }

        /// <summary>
        /// Password do utilizador
        /// </summary>
        public string Password { get { return password; } set { password = value; } }

        /// <summary>
        /// Token do firebase (para push notification)
        /// </summary>
        public string FirebaseToken { get { return firebaseToken; } set { firebaseToken = value; } }
    }
}
