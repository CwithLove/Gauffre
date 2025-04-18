// ### NIVEAU -> Carte (Anthony, Mathis)
// - Attributs: int[][] Tab; final int VIDE, GAUFFRE, EMPOIS.

// - Methode Constructor(lig, col)
// - Methode redimensionne(lig, col) => Appel Contructor(lig, col) 
// - Methode getter(lig, col)
// - Methode setter(lig, col, val) 
// - Methode final() -> return tab[0][0] == VIDE
// - Methode d'affichage() -> ( ' ' pour Vide, '#' pour Gauffre, '@' pour Empoisonne)

public class Niveau {
    private static final int MAXLINE  = 99;
    private int[][] tab;
    public static final int VIDE = 0;
    public static final int GAUFFRE = 1;
    public static final int EMPOIS = 2;

    // Constructeur

    public Niveau(int lig, int col) {
        this.tab = new int[lig][col];
        for (int i = 0; i < this.tab.length; i++) {
            for (int j = 0; j < this.tab[i].length; j++) {
                this.tab[i][j] = GAUFFRE;
            }
        }
        this.tab[0][0] = EMPOIS;
    }

    public Niveau() {
        // Constructeur par défaut
        // Crée une gauffre de 3 lignes et 4 colonnes
        // avec la case (0,0) empoisonnée
        this(11, 15);
    }

    // Redimensionne le tableau
    public void redimensionne(int lig, int col) {
        if (lig <= 0 || col <= 0 || lig > MAXLINE || col > MAXLINE) {
            throw new IllegalArgumentException("Les dimensions doivent être positives ou < " + MAXLINE);
        }

        this.tab = new int[lig][col];
        for (int i = 0; i < lig; i++) {
            for (int j = 0; j < col; j++) {
                this.tab[i][j] = GAUFFRE;
            }
        }
        this.tab[0][0] = EMPOIS;
    }

    // Getter
    public int get(int lig, int col) {
        return this.tab[lig][col];
    }

    public int getLignes() {
        return this.tab.length;
    }

    public int getColonnes() {
        return this.tab[0].length;
    }

    // Setter
    public void set(int lig, int col, int val) {
        this.tab[lig][col] = val;
    }

    // Vérifie si le niveau est terminé
    public boolean finalNiveau() {
        return this.tab[0][0] == VIDE;
    }

    // Affichage du niveau
    public void afficher() {
        System.out.print("   ");
        for (int i = 0; i < tab[0].length; i++) {
            if (i < 10) {
                System.out.print("  " + i);
            } else {
                System.out.print(" " + i);
            }
        }

        System.out.println();
        for (int i = 0; i < tab.length; i++) {
            // Affichage des lignes
            if (i < 10) {
                System.out.print("  " + i);
            } else {
                System.out.print(" " + i);
            }
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print("  ");
                // Affichage des cases
                switch (tab[i][j]) {
                    case VIDE:
                        System.out.print(" ");
                        break;
                    case GAUFFRE:
                        System.out.print("#");
                        break;
                    case EMPOIS:
                        System.out.print("@");
                        break;
                }
            }
            System.out.println();
        }
    }
}