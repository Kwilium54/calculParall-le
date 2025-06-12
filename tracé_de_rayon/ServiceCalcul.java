import java.rmi.Remote;
import java.rmi.RemoteException;
import raytracer.Image;
import raytracer.Scene;

public interface ServiceCalcul extends Remote {
    Image compute(Scene scene, int x, int y, int w, int h) throws RemoteException;
}
