package Modele;
import java.util.Random;
import java.util.Scanner;

public class Jeu {

    private Niveau niveau;
    private int tour;
    private Scanner scanner;
    private Random rand;
    IA ia, ia2;

    public Jeu() {
        this.niveau = new Niveau();
        this.tour = 0;
        this.scanner = new Scanner(System.in);
        this.rand = new Random();
    }

    public Jeu(Niveau niveau) {
        this.niveau = niveau;
        this.tour = 0;
        this.scanner = new Scanner(System.in);
        this.rand = new Random();
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

    private void JvsJ() {
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
    }

    public void JvsIA(String lv) {
        ia = IA.setIA(this.niveau, lv);

        int currentPlayer = rand.nextInt(2);

        while (!this.verifyFinal()) {
            System.out.println("\nNiveau actuel\n");
            this.niveau.afficher();

            if (currentPlayer == 0) {
                System.out.println("Tour Joueur :");
                System.out.print("Veuillez entrer la position a manger (ligne colonne): ");
                try {
                    int lig = scanner.nextInt();
                    int col = scanner.nextInt();
                    boolean peuManger = manger(lig, col);
                    if (peuManger) {
                        currentPlayer = 1; // Switch to IA
                    }
                } catch (Exception e) {
                    System.out.println("Entree invalide");
                    scanner.nextLine();
                }
            } else {
                System.out.println("Tour IA :");
                int[] coup = ia.run();
                System.out.println("IA mange en position: " + coup[0] + " " + coup[1]);
                manger(coup[0], coup[1]);
                currentPlayer = 0; // Switch to Joueur
            }
        }

        String winner = (currentPlayer == 0) ? "Joueur" : "IA";
        System.out.println("\nGame over ! " + winner + " a gagne !");
    }

    public void IAvsIA(String IA1, String IA2) {
        ia = IA.setIA(this.niveau, IA1);
        ia2 = IA.setIA(this.niveau, IA2);
        int currentPlayer = rand.nextInt(2);

        while (!this.verifyFinal()) {
            System.out.println("\nNiveau actuel\n");
            this.niveau.afficher();

            if (currentPlayer == 0) {
                System.out.println("Tour IA 1 :");
                int[] coup = ia.run();
                System.out.println("IA 1 mange en position: " + coup[0] + " " + coup[1]);
                manger(coup[0], coup[1]);
                currentPlayer = 1; // Switch to IA2
            } else {
                System.out.println("Tour IA 2 :");
                int[] coup = ia2.run();
                System.out.println("IA 2 mange en position: " + coup[0] + " " + coup[1]);
                manger(coup[0], coup[1]);
                currentPlayer = 0; // Switch to IA1
            }
        }

        String winner = (currentPlayer == 0) ? "IA 1" : "IA 2";
        System.out.println("\nGame over ! " + winner + " a gagne !");

    }

    public void lancer() {
        int nombreDeJoueurs = 2;

        // Determiner le nombre de joueurs
        // 0 = IA vs IA, 1 = Joueur vs IA, 2 = Joueur vs Joueur
        try {
            do {
                System.out.print("Entree nombres de joueurs: ");
                nombreDeJoueurs = scanner.nextInt();
                if (nombreDeJoueurs > 2 || nombreDeJoueurs < 0) {
                    System.out.println("Entree invalide");
                    System.out.print("Entree nombres de joueurs: ");
                }
            } while (nombreDeJoueurs > 2 || nombreDeJoueurs < 0);
        } catch (Exception e) {
            System.out.println("Entree invalide");
            scanner.nextLine();
        }

        if (nombreDeJoueurs == 0) {
            String niveauIA1 = "easy";
            String niveauIA2 = "easy";
            try {
                do {
                    System.out.print("Niveau IA 1 (easy, medium, hard, expert): ");
                    niveauIA1 = scanner.next();
                    if (!niveauIA1.matches("easy|medium|hard|expert")) {
                        System.out.println("Entree invalide");
                    }
                } while (!niveauIA1.matches("easy|medium|hard|expert"));
            } catch (Exception e) {
                scanner.nextLine();
            }

            try {
                do {
                    System.out.print("Niveau IA 2 (easy, medium, hard, expert): ");
                    niveauIA2 = scanner.next();
                    if (!niveauIA2.matches("easy|medium|hard|expert")) {
                        System.out.println("Entree invalide");
                    }
                } while (!niveauIA2.matches("easy|medium|hard|expert"));
            } catch (Exception e) {
                scanner.nextLine();
            }
            this.IAvsIA(niveauIA1, niveauIA2);

        } else if (nombreDeJoueurs == 1) {
            String niveauIA = "easy";
            try {
                do {
                    System.out.print("Niveau IA (easy, medium, hard, expert): ");
                    niveauIA = scanner.next();
                    if (!niveauIA.matches("easy|medium|hard|expert")) {
                        System.out.println("Entree invalide");
                    }
                } while (!niveauIA.matches("easy|medium|hard|expert"));
            } catch (Exception e) {
                scanner.nextLine();
            }
            this.JvsIA(niveauIA);

        } else if (nombreDeJoueurs == 2) {
            this.JvsJ();
        }
        scanner.close();
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        jeu.lancer();
    }
}
