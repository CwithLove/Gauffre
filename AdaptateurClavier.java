import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdaptateurClavier extends KeyAdapter {
    Jeu j;
    NiveauGraphique n;

	AdaptateurClavier(Jeu jeu, NiveauGraphique niv) {
		j = jeu;
		n = niv;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				j.niveau().redimensionne(j.niveau().getLignes() * 2, j.niveau().getColonnes() * 2);
                n.repaint();
				break;
			case KeyEvent.VK_DOWN:
                j.niveau().redimensionne(j.niveau().getLignes() / 2, j.niveau().getColonnes() / 2);
                n.repaint();
				break;
		}
	}

}
