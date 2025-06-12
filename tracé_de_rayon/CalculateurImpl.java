import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import raytracer.Image;
import raytracer.Scene;

public class CalculateurImpl extends UnicastRemoteObject implements ServiceCalcul {

    public CalculateurImpl() throws RemoteException {
        super();
    }

    public Image compute(Scene scene, int x, int y, int w, int h) throws RemoteException {
        return scene.compute(x, y, w, h);
    }
}
