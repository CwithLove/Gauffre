package Controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Modele.*;
import Vue.*;

public class AdaptateurSouris extends MouseAdapter{
	Jeu j;
    NiveauGraphique n;

	AdaptateurSouris(Jeu jeu, NiveauGraphique niv) {
		j = jeu;
		n = niv;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getY() / n.hauteurCase();
		int y = e.getX() / n.largeurCase();
		
		j.manger(x, y);

		n.repaint();
	}

}
