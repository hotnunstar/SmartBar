namespace SmartBar.Models
{
    public class SmartBarDatabaseSettingsModel
    {
        public string ConnectionString { get; set; } = null!;
        public string DatabaseName { get; set; } = null!;
        public string UserCollectionName { get; set; } = null!;
        public string ColaboratorCollectionName { get; set; } = null!;
    }
}
