/*
 * @Author: ThearchyHelios work@thearchyhelios.com
 * @Date: 2025-04-18 15:42:12
 * @LastEditors: ThearchyHelios work@thearchyhelios.com
 * @LastEditTime: 2025-04-18 16:01:22
 * @FilePath: /Gauffre/Jeu.java
 * @Description: 
 */
import java.util.*;

class Jeu {
	public Niveau niveau;
	public int tour;
	private Scanner scanner;
	public ArrayList<Couple<Integer,Integer>> historique = new ArrayList<>();
	private IARandom ia = new IARandom();

	public Jeu() {
		this.niveau = new Niveau();
		this.tour = 0;
		this.scanner = new Scanner(System.in);
	}

	public boolean manger(int lig, int col) {
		if (lig < 0 || lig >= this.niveau.getLignes() || col < 0 || col >= this.niveau.getColonnes()) {
			System.out.println("Position invalide");
			return false;
		}
		
		if (this.niveau.get(lig, col) == Niveau.VIDE) {
			if((this.tour%2) == 0)
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
			Couple<Integer,Integer> coordonnes = null;
			
			System.out.println("\nNiveau actuel\n");
			this.niveau.afficher();
			System.out.println("Joueur n° " + (currentPlayer+1) + " :");

			try {
				if (currentPlayer == 0){
					System.out.print("Veuillez entrer la position a manger (ligne colonne): ");
					int lig = scanner.nextInt();
					int col = scanner.nextInt();
					boolean peuManger = manger(lig, col);
					if (peuManger) {
						coordonnes = new Couple<>(lig, col);
						this.tour++;
					}
				}
				else {
					coordonnes = ia.IAcoup(this);
				}

				if (coordonnes != null)
					this.historique.add(coordonnes);

			} catch (Exception e) {
				System.out.println("Entree invalide");
				scanner.nextLine();
			}
		}
		System.out.println("Historique : " + this.historique);
		int winner = (this.tour % 2);
		System.out.println("\nGame over ! Joueur " + (winner+1) + " a gagne !");
		scanner.close();
	}
	
	public static void main(String[] args) {
		Jeu jeu = new Jeu();
		jeu.lancer();
	}
}