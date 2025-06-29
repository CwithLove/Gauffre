/*
 * @Author: ThearchyHelios work@thearchyhelios.com
 * @Date: 2025-04-18 15:42:12
 * @LastEditors: ThearchyHelios work@thearchyhelios.com
 * @LastEditTime: 2025-04-18 16:01:22
 * @FilePath: /Gauffre/Jeu.java
 * @Description: 
 */
import java.util.Scanner;

class Jeu {
	private Niveau niveau;
	private int tour;
	private Scanner scanner;

	public Jeu() {
		this.niveau = new Niveau();
		this.tour = 0;
		this.scanner = new Scanner(System.in);
	}

	public Niveau niveau() {
		return niveau;
	}

	public boolean manger(int lig, int col) {
		if (lig < 0 || lig >= this.niveau.getLignes() || col < 0 || col >= this.niveau.getColonnes()) {
			System.out.println("Position invalide");
			return false;
		}
		
		if (this.niveau.get(lig, col) == Niveau.VIDE) {
			System.out.println("Cette position a été mangée, veuillez ressayer :)");
			return false;
		}
		
		for (int i = lig; i < this.niveau.getLignes(); i++) {
			for (int j = col; j < this.niveau.getColonnes(); j++) {
				this.niveau.set(i, j, Niveau.VIDE);
			}
		}
		return true;
	}

	public boolean verifyFinal() {
		return this.niveau.finalNiveau();
	}

	public void lancer() {
		System.out.println("Jeu commence");
		while (!this.verifyFinal()) {
			int currentPlayer = (this.tour % 2); // La personne (tour%2) gagne
			
			System.out.println("\nNiveau actuel\n");
			this.niveau.afficher();
			System.out.println("Tour " + currentPlayer + " :");
			
			System.out.print("Veuillez entrer la position a manger (ligne colonne): ");
			try {
				int lig = scanner.nextInt();
				int col = scanner.nextInt();
				boolean peuManger = manger(lig, col);
				if (peuManger) {
					this.tour++;
				}
			} catch (Exception e) {
				System.out.println("Entree invalide");
				scanner.nextLine();
			}
		}
		
		int winner = (this.tour % 2);
		System.out.println("\nGame over ! Joueur " + winner + " a gagne !");
		scanner.close();
	}

	void jouer(int x, int y) {		// Utilisé par l'Ecouteur de souris pour jouer
		int currentPlayer = (this.tour % 2); // La personne (tour%2) gagne

		System.out.println("Tour " + currentPlayer + " :");
		
		try {
			boolean peuManger = manger(x, y);
			if (peuManger) {
				this.tour++;
			}
		} catch (Exception e) {
			System.out.println("Entree invalide");
		}

		if (verifyFinal()) {
			int winner = (this.tour % 2);
			System.out.println("\nGame over ! Joueur " + winner + " a gagne !");
		}
	}
	
	public static void main(String[] args) {
		Jeu jeu = new Jeu();
		jeu.lancer();
	}
}