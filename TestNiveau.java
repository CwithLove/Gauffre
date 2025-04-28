


public class TestNiveau {
    public static void main(String[] args){
        // Test de la classe Niveau
        int dimx = 7;
        int dimy = 7;
        System.out.println("Création d'une gauffre de dimension "+dimx+" "+dimy+"...");
        Niveau niveau = new Niveau(dimx, dimy);
        niveau.afficher();
        dimx = 5;
        dimy = 6;
        System.out.println("Redimensionnement du tableau "+dimx+" "+dimy+"...");
        niveau.redimensionne(dimx, dimy);
        niveau.afficher();
        int modx = 2;
        int mody = 3;
        System.out.println("Modification d'une case "+modx+" "+mody+"...");
        niveau.set(modx, mody, Niveau.VIDE);
        niveau.afficher();
        System.out.println("Vérification de la fin du niveau...");
        System.out.println("Niveau terminé : " + niveau.finalNiveau());
        //enlever le 0 0
        niveau.set(0, 0, Niveau.VIDE);
        niveau.afficher();
        System.out.println("Vérification de la fin du niveau...");
        System.out.println("Niveau terminé : " + niveau.finalNiveau());

        Jeu jeu = new Jeu();
        jeu.niveau = new Niveau(5, 6);
        jeu.lancer();



    }
}
