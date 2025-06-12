import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.concurrent.*;
import java.time.Instant;
import java.time.Duration;

import raytracer.Image;
import raytracer.Scene;
import raytracer.Disp;

public class ProgrammePrincipal {

    public static void main(String[] args) throws Exception {
        String fichier = "simple.txt";
        int largeur = 512, hauteur = 512;

        if (args.length > 0) {
            fichier = args[0];
            if (args.length > 1) largeur = Integer.parseInt(args[1]);
            if (args.length > 2) hauteur = Integer.parseInt(args[2]);
        }

        final int largeurFinal = largeur;
        final int hauteurFinal = hauteur;

        Disp disp = new Disp("Raytracer distribué", largeurFinal, hauteurFinal);
        Scene scene = new Scene(fichier, largeurFinal, hauteurFinal);

        Registry reg = LocateRegistry.getRegistry("localhost"); 
        ServiceServeur serveur = (ServiceServeur) reg.lookup("ServeurNoeuds");

        List<ServiceCalcul> noeuds = serveur.getAvailableNodes();
        if (noeuds.isEmpty()) {
            System.out.println("Aucun nœud de calcul disponible !");
            return;
        }

        int nbNoeuds = noeuds.size();
        int hauteurBloc = hauteurFinal / nbNoeuds;

        ExecutorService pool = Executors.newFixedThreadPool(nbNoeuds);
        CompletionService<ImageBloc> service = new ExecutorCompletionService<>(pool);

        Instant debut = Instant.now();

        for (int i = 0; i < nbNoeuds; i++) {
            final int y0 = i * hauteurBloc;
            final int h = (i == nbNoeuds - 1) ? hauteurFinal - y0 : hauteurBloc;
            final ServiceCalcul calc = noeuds.get(i);

            service.submit(() -> {
                Image img = calc.compute(scene, 0, y0, largeurFinal, h);
                return new ImageBloc(img, 0, y0);
            });
        }

        for (int i = 0; i < nbNoeuds; i++) {
            Future<ImageBloc> result = service.take();
            ImageBloc bloc = result.get();
            disp.setImage(bloc.image, bloc.x, bloc.y);
        }

        Instant fin = Instant.now();
        long duree = Duration.between(debut, fin).toMillis();
        System.out.println("Image calculée en parallèle en : " + duree + " ms");

        pool.shutdown();
    }

    static class ImageBloc {
        Image image;
        int x, y;

        public ImageBloc(Image image, int x, int y) {
            this.image = image;
            this.x = x;
            this.y = y;
        }
    }
}
