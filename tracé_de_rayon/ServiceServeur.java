import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServiceServeur extends Remote {
    void registerNode(ServiceCalcul node) throws RemoteException;
    List<ServiceCalcul> getAvailableNodes() throws RemoteException;
}
