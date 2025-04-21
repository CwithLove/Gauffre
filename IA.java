public abstract class IA {
    public Jeu jeu;
    public Niveau niveau;

    public static IA setIA(Niveau n, String name) {
        IA ia = null;
        switch (name) {
            case "easy":
                ia = new IAEasy(n);
                break;
            case "medium":
                ia = new IAMedium(n);
                break;
            case "hard":
                ia = new IAHard(n);
                break;
            case "expert":
                ia = new IAExpert(n);
                break;
            default:
                throw new IllegalArgumentException("Invalid AI level: " + name);
        }
        return ia;
    }

    int[] run() {
        return null;
    }

    // private int[] medium() {
        
    //     return new int[] { 0, 0 }; // Example move
    // }

    // private int minimax(int depth, boolean IAturn) {
    //     if (niveau.finalNiveau()) {
    //         return IAturn ? -1 : 1;
    //     }

    //     int bestScore = IAturn ? Integer.MIN_VALUE : Integer.MAX_VALUE;



    // }

    // // --------------------------------
    // private int[] hard() {
    //     // Hard AI logic
    //     return new int[] { 0, 0 }; // Example move
    // }

    // private int[] expert() {
    //     // Expert AI logic
    //     return new int[] { 0, 0 }; // Example move
    // }

    // // Les methodes utilitaires
    // private int[][] cloneEtat() {
    //     int[][] clone = new int[niveau.getLignes()][niveau.getColonnes()];
    //     for (int i = 0; i < niveau.getLignes(); i++) {
    //         for (int j = 0; j < niveau.getColonnes(); j++) {
    //             clone[i][j] = niveau.get(i, j);
    //         }
    //     }
    //     return clone;
    // }

    // private void restoreEtat(int[][] clone) {
    //     for (int i = 0; i < niveau.getLignes(); i++) {
    //         for (int j = 0; j < niveau.getColonnes(); j++) {
    //             niveau.set(i, j, clone[i][j]);
    //         }
    //     }
    // }

    // private int getScore() {
    //     int score = 0;
    //     for (int i = 0; i < niveau.getLignes(); i++) {
    //         for (int j = 0; j < niveau.getColonnes(); j++) {
    //             if (niveau.get(i, j) == Niveau.GAUFFRE) {
    //                 score++;
    //             }
    //         }
    //     }
    //     return score;
    // }

    // private void mangerVirtuel(int lig, int col) {
    //     for (int i = lig; i < niveau.getLignes(); i++) {
    //         for (int j = col; j < niveau.getColonnes(); j++) {
    //             niveau.set(i, j, Niveau.VIDE);
    //         }
    //     }
    // }
}
