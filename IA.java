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
    
    IA(String name) {
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

    public void run() {
        switch (lv) {
            case EASY:
                System.out.println("Running EASY AI");
                easy();
                break;
            case MEDIUM:
                System.out.println("Running MEDIUM AI");
                medium();
                break;
            case HARD:
                System.out.println("Running HARD AI");
                hard();
                break;
            case EXPERT:
                System.out.println("Running EXPERT AI");
                expert();
                break;
        }
    }

    private void easy() {
        // Easy AI logic
    }

    private void medium() {
        // Medium AI logic
    }

    private void hard() {
        // Hard AI logic
    }

    private void expert() {
        // Expert AI logic
    }
}
