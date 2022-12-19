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

        /// <summary>
        /// Construtor do Modelo do Login
        /// </summary>
        /// <param name="UserType"></param>
        /// <param name="Email"></param>
        /// <param name="Password"></param>
        public LoginModel(string UserType, string Email, string Password)
        {
            userType = UserType;
            email = Email;
            password = Password;
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
    }
}
