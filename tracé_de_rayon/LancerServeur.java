import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LancerServeur {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); 
            ServiceServeur serveur = new ServeurImpl();
            Registry reg = LocateRegistry.getRegistry();
            reg.rebind("ServeurNoeuds", serveur);
            System.out.println("Serveur RMI prêt !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
