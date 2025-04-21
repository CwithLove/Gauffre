import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

class NiveauGraphique extends JComponent {
    private Niveau niveau;
    int cellHeight;
    int cellWidth;

	public NiveauGraphique(Jeu jeu) {
        this.niveau = jeu.niveau();
    }

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D drawable = (Graphics2D) g;

        drawable.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getSize().width;
		int height = getSize().height;

        int lignes = niveau.getLignes();
        int colonnes = niveau.getColonnes();

        cellWidth = width / colonnes;
        cellHeight = height / lignes;

		// On efface tout
		drawable.clearRect(0, 0, width, height);

        Rectangle2D rectangle;

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                if (niveau.get(i, j) == Niveau.GAUFFRE || niveau.get(i, j) == Niveau.EMPOIS) {
                    rectangle = new Rectangle2D.Double(j*cellWidth, i*cellHeight, cellWidth, cellHeight);
                    drawable.setColor(Color.ORANGE);
                    drawable.fill(rectangle);
                    drawable.setColor(Color.BLACK);
                    drawable.draw(rectangle);
                }
                if (niveau.get(i, j) == Niveau.EMPOIS) {
                    drawable.setColor(Color.GREEN);
                    drawable.fillOval(j*cellWidth + cellWidth / 3, i*cellHeight + cellHeight / 3,
                     cellWidth - (cellWidth / 3) * 2, cellHeight - (cellHeight / 3) * 2);
                    drawable.setColor(Color.BLACK);
                    drawable.drawOval(j*cellWidth + cellWidth / 3, i*cellHeight + cellHeight / 3,
                     cellWidth - (cellWidth / 3) * 2, cellHeight - (cellHeight / 3) * 2);
                }
            }
        }
	}

    int hauteurCase() {
        return cellHeight;
    }

    int largeurCase() {
        return cellWidth;
    }
}