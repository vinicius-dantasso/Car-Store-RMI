import java.util.HashMap;

import database.Database;
import servers.AuthServer;
import servers.GatewayServer;
import servers.StoreServer;

public class InitializeServices {
    public static void main(String[] args) {
        
        // Inicializando o banco de dados
        Database.users = new HashMap<>();
        Database.carRena = new HashMap<>();

        // Inicializando os servidores
        AuthServer.initialize();
        StoreServer.initialize();
        GatewayServer.initialize();

    }   
}
