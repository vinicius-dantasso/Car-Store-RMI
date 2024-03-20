package servers;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import implement.ImplStore;
import interfaces.Store;

public class StoreServer {
    
    private static final int storePort = 50007;

    private static void config() {
        System.setProperty("java.rmi.server.hostname", "localhost");
        System.setProperty("java.security.policy", "java.policy");
    }

    public static void initialize() {

        config();

        try {

            ImplStore refStore = new ImplStore();
            Store skeleton = (Store) UnicastRemoteObject.exportObject(refStore, 0);

            LocateRegistry.createRegistry(storePort);

            Registry registry = LocateRegistry.getRegistry(storePort);
            registry.bind("Store", skeleton);

            System.out.println("Servidor da loja pronto!");

        }
        catch(Exception e) {
            System.err.println("Servidor: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
