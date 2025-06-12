import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LancerCalculateur {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("192.168.1.138");
            ServiceServeur serveur = (ServiceServeur) reg.lookup("ServeurNoeuds");

            ServiceCalcul calc = new CalculateurImpl();
            serveur.registerNode(calc);

            System.out.println("Nœud de calcul enregistré.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
