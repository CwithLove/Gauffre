// ### NIVEAU -> Carte (Anthony, Mathis)
// - Attributs: int[][] Tab; final int VIDE, GAUFFRE, EMPOIS.

// - Methode Constructor(lig, col)
// - Methode redimensionne(lig, col) => Appel Contructor(lig, col) 
// - Methode getter(lig, col)
// - Methode setter(lig, col, val) 
// - Methode final() -> return tab[0][0] == VIDE
// - Methode d'affichage() -> ( ' ' pour Vide, '#' pour Gauffre, '@' pour Empoisonne)

public class Niveau {

    private int[][] tab;
    public static final int VIDE = 0;
    public static final int GAUFFRE = 1;
    public static final int EMPOIS = 2;

    // Constructeur

    public Niveau() {
        this.tab = new int[4][5];
        for (int i = 0; i < this.tab.length; i++) {
            for (int j = 0; j < this.tab[i].length; j++) {
                this.tab[i][j] = GAUFFRE;
            }
        }
        this.tab[0][0] = EMPOIS;
    }
    public Niveau(int lig, int col) {
        this.tab = new int[lig][col];
        for (int i = 0; i < this.tab.length; i++) {
            for (int j = 0; j < this.tab[i].length; j++) {
                this.tab[i][j] = GAUFFRE;
            }
        }
        this.tab[0][0] = EMPOIS;
    }

    // Redimensionne le tableau
    public void redimensionne(int lig, int col) {
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
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
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