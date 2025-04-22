package Modele;

import java.util.List;


public class IAHard extends IA {
    //  De base, IA Hard et IA Medium sont presque identiques
    //  mais IA Hard va plus loin dans la profondeur de recherche
    //  car algorithme permet de couper les branches non prometteuses
    //  et IA Medium ne le fait pas
    //  IA Hard est donc plus rapide que IA Medium => ce qui permet de 
    //  chercher plus profondément

    // Complexite de l'IA Medium:
    // - O(b^(d/2)) avec b le nombre de coups possibles
    // -            avec d la profondeur de recherche
    private final int MAX_DEPTH = 8;

    public IAHard(Niveau n) {
        this.niveau = n;
    }

    @Override
    public int[] run() {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        int best = Integer.MIN_VALUE;
        int[] bestCoup = null;

        for (int[] move: getCoupPossibles()) {
            
            int[][] clone = cloneEtat();
            mangerVirtuel(move[0], move[1]);

            int score = alphabeta(MAX_DEPTH - 1, alpha, beta, false);
            restoreEtat(clone);

            if (score > best) {
                best = score;
                bestCoup = move;
            }
            alpha = Math.max(alpha, score);
        }

        return bestCoup != null ? bestCoup : new int[] { 0, 0 };
    }

    private int alphabeta(int profondeur, int alpha, int beta, boolean iaTurn) {
        if (niveau.finalNiveau()) {
            return iaTurn ? -1000 + profondeur: 1000 - profondeur;
        }

        if (profondeur == 0) {
            return heuristique();
        }

        List<int[]> coups = getCoupPossibles();
        if (coups.isEmpty()) {
            return iaTurn ? -1000 + profondeur : 1000 - profondeur;
        }
        
        int best = iaTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int[] move: getCoupPossibles()) {
            int[][] clone = cloneEtat();
            mangerVirtuel(move[0], move[1]);

            if (iaTurn) {
                best = Math.max(best, alphabeta(profondeur - 1, alpha, beta, false));
                alpha = Math.max(alpha, best);
            } else {
                best = Math.min(best, alphabeta(profondeur - 1, alpha, beta, true));
                beta = Math.min(beta, best);
            }

            restoreEtat(clone);
            if (beta <= alpha) {
                break; // Couper la branche
            }
        }
        return best;
    }

    private int heuristique() {
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
}
