using System.Text;

namespace SmartBar.Helpers
{

    /// <summary>
    /// Funções auxiliares
    /// </summary>
    public class Functions
    {
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

        /// <summary>
        /// Encriptar passwords
        /// </summary>
        /// <param name="password"></param>
        /// <returns>Password encriptada</returns>
        /// <exception cref="Exception"></exception>
        public static string EncodePasswordToBase64(string password)
        {
            try
            {
                byte[] encData_byte = new byte[password.Length];
                encData_byte = Encoding.UTF8.GetBytes(password);
                string encodedData = Convert.ToBase64String(encData_byte);
                return encodedData;
            }
            catch (Exception ex)
            {
                throw new Exception("Error in base64Encode" + ex.Message);
            }
        }

        /// <summary>
        /// Desencriptar password
        /// </summary>
        /// <param name="encodedData"></param>
        /// <returns>Password desencriptada</returns>
        public string DecodeFrom64(string encodedData)
        {
            UTF8Encoding encoder = new UTF8Encoding();
            Decoder utf8Decode = encoder.GetDecoder();
            byte[] todecode_byte = Convert.FromBase64String(encodedData);
            int charCount = utf8Decode.GetCharCount(todecode_byte, 0, todecode_byte.Length);
            char[] decoded_char = new char[charCount];
            utf8Decode.GetChars(todecode_byte, 0, todecode_byte.Length, decoded_char, 0);
            string result = new (decoded_char);
            return result;
        }
    }
}
