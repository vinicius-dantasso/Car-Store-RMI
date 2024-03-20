package servers;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import implement.ImplGateway;
import interfaces.Gateway;

public class GatewayServer {
    
    private static final int gatePort = 50005;

    private static void config() {
        System.setProperty("java.rmi.server.hostname", "localhost");
        System.setProperty("java.security.policy", "java.policy");
    }

    public static void initialize() {
        
        config();

        try {

            ImplGateway refGateway = new ImplGateway();
            Gateway skeleton = (Gateway) UnicastRemoteObject.exportObject(refGateway, 0);

            LocateRegistry.createRegistry(gatePort);

            Registry registry = LocateRegistry.getRegistry(gatePort);
            registry.bind("Gateway", skeleton);

            System.out.println("Servidor de gateway pronto!");

        }
        catch(Exception e) {
            System.err.println("Servidor: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
