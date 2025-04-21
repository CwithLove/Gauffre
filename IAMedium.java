public class IAMedium extends IA {
    private final int MAX_DEPTH = 6;

    public IAMedium(Niveau n) {
        this.niveau = n;
    }

    @Override
    int[] run() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestCoup = null;

        for (int i = 0; i < niveau.getLignes(); i++) {
            for (int j = 0; j < niveau.getColonnes(); j++) {
                if (niveau.get(i, j) != Niveau.VIDE && (i != 0 || j != 0)) {
                    int[][] clone = cloneEtat();
                    mangerVirtuel(i, j);
                    
                    int score = minimax(false, MAX_DEPTH - 1);
                    restoreEtat(clone);

                    if (score > bestScore) {
                        bestScore = score;
                        bestCoup = new int[] { i, j };
                    }
                }
            }
        }
        return bestCoup != null ? bestCoup : new int[] { 0, 0 };
    }

    private int minimax(boolean IAtour, int profondeur) {
        // Le personne qui mange (0,0) perd
        // Stratégie de l'IA:
        // IA perd: -1000 + profondeur => Perdre est le pire, mais perdre le plus tard
        //                                (depth eleve) est legerement moins mal => retarder la perte
        // IA gagne: 1000 - profondeur => Gagner est le meilleur, mais gagner le plus vite
        //                                (depth faible) vaut davantage => chercher a finir vite
        // Exemple: IA perd
        // Tout de suite => -1000 + 0 = -1000
        // Apres 5 coups => -1000 + 5 = -995 (defaite retardee)
        // Exemple: IA gagne
        // Tout de suite => 1000 - 0 = 1000
        // Apres 5 coups => 1000 - 5 = 995 (moins bien)
        // Justification: Pendant la selection, IA ne chosira jamais (0, 0) si il existe
        // un autre coup possible, car ils vaudront toujours >= -999, tandis que (0, 0) vaudra
        // toujours -1000. Donc IA ne perdra jamais tout de suite.
        if (niveau.finalNiveau()) {
            return IAtour ? -1000 + profondeur  // L'IA perd
                            : 1000 - profondeur; // L'IA gagne
        }

        if (profondeur == 0) {
            return heuristique();
        }

        boolean aCoup = false;

        int best = IAtour ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < niveau.getLignes(); i++) {
            for (int j = 0; j < niveau.getColonnes(); j++) {
                if (niveau.get(i, j) != Niveau.VIDE && (i!= 0 || j != 0)) {
                    aCoup = true;
                    int[][] clone = cloneEtat();
                    mangerVirtuel(i, j);
                    
                    int score = minimax(!IAtour, profondeur - 1);
                    restoreEtat(clone);

                    best = IAtour ? Math.max(best, score)
                                : Math.min(best, score);
                }
            }
        }
        return aCoup ? best : (IAtour ? -1000 + profondeur : 1000 - profondeur);
    }

    /**
     * heuristique
     * 
     * Stratégie d'évaluation: compte le nombre de cases restantes
     * 
     * Cette heuristique est simple, plus il y a de #, plus la partie 
     * dure longtemps, donc plus l'IA retarde sa defaite. 
     *       
     * @return int - Le score de l'état actuel
     */
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
