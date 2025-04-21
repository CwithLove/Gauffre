/*
 * @Author: ThearchyHelios work@thearchyhelios.com
 * @Date: 2025-04-21 15:55:15
 * @LastEditors: ThearchyHelios work@thearchyhelios.com
 * @LastEditTime: 2025-04-21 16:12:07
 * @FilePath: /Gauffre/IA.java
 * @Description: 
 */
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

    public int getScore() {
        int score = 0;
        for (int i = 0; i < niveau.getLignes(); i++) {
            for (int j = 0; j < niveau.getColonnes(); j++) {
                if (niveau.get(i, j) == Niveau.GAUFFRE) {
                    score++;
                }
            }
        }
        return score;
    }

    protected void mangerVirtuel(int lig, int col) {
        for (int i = lig; i < niveau.getLignes(); i++) {
            for (int j = col; j < niveau.getColonnes(); j++) {
                niveau.set(i, j, Niveau.VIDE);
            }
        }
    }
}
