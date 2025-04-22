package Modele;

import java.util.ArrayList;
import java.util.List;

public abstract class IA {
    public Jeu jeu;
    public Niveau niveau;

    public static IA setIA(Niveau n, String name) {
        IA ia = null;
        // Si le niveau n'est pas specifie, on utilise le niveau medium
        String level = (name != null) ? name : "medium";
        
        switch (level) {
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
                ia = new IAMedium(n);
                break;
        }
        return ia;
    }

    public int[] run() {
        return null;
    }

    // Les methodes utilitaires
    protected int[][] cloneEtat() {
        int[][] clone = new int[niveau.getLignes()][niveau.getColonnes()];
        for (int i = 0; i < niveau.getLignes(); i++) {
            for (int j = 0; j < niveau.getColonnes(); j++) {
                clone[i][j] = niveau.get(i, j);
            }
        }
        return clone;
    }

    protected void restoreEtat(int[][] clone) {
        for (int i = 0; i < niveau.getLignes(); i++) {
            for (int j = 0; j < niveau.getColonnes(); j++) {
                niveau.set(i, j, clone[i][j]);
            }
        }
    }

    protected void mangerVirtuel(int lig, int col) {
        for (int i = lig; i < niveau.getLignes(); i++) {
            for (int j = col; j < niveau.getColonnes(); j++) {
                niveau.set(i, j, Niveau.VIDE);
            }
        }
    }

    protected List<int[]> getCoupPossibles() {
        List<int[]> coups = new ArrayList<>();
        for (int i = 0; i < niveau.getLignes(); i++) {
            for (int j = 0; j < niveau.getColonnes(); j++) {
                if (niveau.get(i, j) != Niveau.VIDE && (i != 0 || j != 0)) {
                    coups.add(new int[] { i, j });
                }
            }
        }
        return coups;
    }
}
