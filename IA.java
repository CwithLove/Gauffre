public class IA {
    static enum AILevel {
        EASY,
        MEDIUM,
        HARD,
        EXPERT
    }

    public static AILevel lv;
    public Jeu jeu;
    public Niveau niveau;

    IA(Niveau n, String name) {
        this.niveau = n;
        if (name.equals("easy")) {
            lv = AILevel.EASY;
        } else if (name.equals("medium")) {
            lv = AILevel.MEDIUM;
        } else if (name.equals("hard")) {
            lv = AILevel.HARD;
        } else if (name.equals("expert")) {
            lv = AILevel.EXPERT;
        } else {
            throw new IllegalArgumentException("Invalid AI level: " + name);
        }
    }

    public int[] run() {
        switch (lv) {
            case EASY:
                return easy();
            case MEDIUM:
                return medium();
            case HARD:
                return hard();
            case EXPERT:
                return expert();
        }
        return new int[] { 0, 0 }; // Default case
    }

    private int[] easy() {
        int lig;
        int col;
        // Version aléatoire (sans se succéder 0 0)
        if (niveau.get(0,1) == Niveau.VIDE && niveau.get(1,0) != Niveau.VIDE) {
            return new int[] { 1, 0 };
        } else if (niveau.get(0,1) != Niveau.VIDE && niveau.get(1,0) == Niveau.VIDE) {
            return new int[] { 0, 1 };
        } else if (niveau.get(0, 1) == Niveau.VIDE && niveau.get(1, 0) == Niveau.VIDE) {
            return new int[] { 0, 0 };
        } else {
            do {
                lig = (int) (Math.random() * niveau.getLignes());
                col = (int) (Math.random() * niveau.getColonnes());
            }
            while (niveau.get(lig, col) == Niveau.VIDE ||
                   (lig == 0 && col == 0));

            return new int[] { lig, col };
        }
    }

    private int[] medium() {
        // Medium AI logic
        return new int[] { 0, 0 }; // Example move
    }

    private int[] hard() {
        // Hard AI logic
        return new int[] { 0, 0 }; // Example move
    }

    private int[] expert() {
        // Expert AI logic
        return new int[] { 0, 0 }; // Example move
    }
}
