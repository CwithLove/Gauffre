import javax.swing.*;
import java.awt.*;

public class TestNiveauGraphique {

    public static void main(String[] args) {
        // Test de la classe Niveau
        int dimx = 7;
        int dimy = 10;
        System.out.println("Création d'une gauffre de dimension " + dimx + " " + dimy + "...");
        Niveau niveau = new Niveau(dimx, dimy);
        NiveauGraphique niveauGraphique = new NiveauGraphique(niveau);
        JFrame frame = new JFrame("Niveau Graphique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(niveauGraphique);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}