using System.Data.SqlTypes;
using System.Security.Cryptography;
using System.Text;

namespace SmartBar.Helpers
{

    /// <summary>
    /// Funções auxiliares
    /// </summary>
    public class Functions
    {
        /// <summary>
        /// Encriptar passwords
        /// </summary>
        /// <param name="password"></param>
        /// <returns>Password encriptada</returns>
        /// <exception cref="Exception"></exception>
        public static string EncodePassword(string password)
        {
            var md5 = MD5.Create();
            byte[] bytes = Encoding.ASCII.GetBytes(password);
            byte[] hash = md5.ComputeHash(bytes);

            StringBuilder sb = new StringBuilder();
            for(int i=0; i<hash.Length; i++)
            {
                sb.Append(hash[i].ToString("X2"));
            }
            return sb.ToString();
        }

        /// <summary>
        /// Verificar se determinado email está num formato correto
        /// </summary>
        /// <param name="email"></param>
        /// <returns>True ou False</returns>
        public static bool CheckEmail(string email)
        {
            try
            {
                var addr = new System.Net.Mail.MailAddress(email);
                return true;
            }
            catch { return false; }
        }
    }
}
