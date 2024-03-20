package servers;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import implement.ImplAuth;
import interfaces.Auth;

public class AuthServer {
    
    private static final int authPort = 50006;

    private static void config() {
        System.setProperty("java.rmi.server.hostname", "localhost");
        System.setProperty("java.security.policy", "java.policy");
    }

    public static void initialize() {

        config();

        try {

            ImplAuth refAuth = new ImplAuth();
            Auth skeleton = (Auth) UnicastRemoteObject.exportObject(refAuth, 0);

            LocateRegistry.createRegistry(authPort);

            Registry registry = LocateRegistry.getRegistry(authPort);
            registry.bind("Auth", skeleton);

            System.out.println("Servidor de autenticação pronto!");

        }
        catch(Exception e) {
            System.err.println("Servidor: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
