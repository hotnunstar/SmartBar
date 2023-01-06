namespace SmartBar.Models
{
    /// <summary>
    /// Modelo de configurações da base de dados
    /// </summary>
    public class SmartBarDatabaseSettingsModel
    { 
        /// <summary>
        /// String de conexeção
        /// </summary>
        public string ConnectionString { get; set; } = null!;

        /// <summary>
        /// Nome da base de dados
        /// </summary>
        public string DatabaseName { get; set; } = null!;

        /// <summary>
        /// Coleção de colaboradores
        /// </summary>
        public string ColaboratorCollectionName { get; set; } = null!;

        /// <summary>
        /// Coleção de histórico
        /// </summary>
        public string HistoricCollectionName { get; set; } = null!;

        /// <summary>
        /// Coleção de notificações
        /// </summary>
        public string NotificationCollectionName { get; set; } = null!;

        /// <summary>
        /// Coleção de produtos
        /// </summary>
        public string ProductCollectionName { get; set; } = null!;

        /// <summary>
        /// Coleção de pedidos
        /// </summary>
        public string RequestCollectionName { get; set; } = null!;

        /// <summary>
        /// Coleção de utilizadores
        /// </summary>
        public string UserCollectionName { get; set; } = null!;
    }
}