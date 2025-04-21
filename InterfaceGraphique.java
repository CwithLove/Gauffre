import javax.swing.*;

public class InterfaceGraphique implements Runnable {
    Jeu j;

    public InterfaceGraphique(Jeu jeu) {
        j = jeu;
    }

    public static void demarrer(Jeu j) {
		InterfaceGraphique vue = new InterfaceGraphique(j);
		SwingUtilities.invokeLater(vue);
	}

    
    public void run() {
		JFrame frame = new JFrame("Jeu de la gauffre");
		NiveauGraphique niv = new NiveauGraphique(j);
		niv.addMouseListener(new AdaptateurSouris(j, niv));
		//frame.addKeyListener(new AdaptateurClavier(control));

		// Mise en place de l'interface
		frame.add(niv);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
	}

}
