import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ServeurImpl extends UnicastRemoteObject implements ServiceServeur {

    private List<ServiceCalcul> nodes;

    public ServeurImpl() throws RemoteException {
        nodes = new ArrayList<>();
    }

    public synchronized void registerNode(ServiceCalcul node) throws RemoteException {
        nodes.add(node);
        System.out.println("Nœud enregistré, total : " + nodes.size());
    }

    public synchronized List<ServiceCalcul> getAvailableNodes() throws RemoteException {
        return new ArrayList<>(nodes); 
    }
}
