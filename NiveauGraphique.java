import javax.swing.*;
import java.awt.*;

class NiveauGraphique extends JComponent {
    private Niveau niveau;

	public NiveauGraphique(Niveau niveau) {
        this.niveau = niveau;
    }

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D drawable = (Graphics2D) g;

        int width = getSize().width;
		int height = getSize().height;

        int lignes = niveau.getLig();
        int colonnes = niveau.getCol();

        int cellWidth = width / colonnes;
        int cellHeight = height / lignes;

		// On efface tout
		drawable.clearRect(0, 0, width, height);
        drawable.drawRect(0, 0, width - 1, height - 1);

        for (int i = 1; i < lignes; i++) {
            int y = i * cellHeight;
            drawable.drawLine(0, y, width, y);
        }
        
        for (int j = 1; j < colonnes; j++) {
            int x = j * cellWidth;
            drawable.drawLine(x, 0, x, height);
        }
	}
}