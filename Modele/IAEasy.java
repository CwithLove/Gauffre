package Modele;


public class IAEasy extends IA {
    IAEasy(Niveau n) {
        this.niveau = n;
    }

    @Override
    public int[] run() {
        int lig;
        int col;
        if (niveau.get(0, 1) == Niveau.VIDE && niveau.get(1, 0) != Niveau.VIDE) {
            return new int[] { 1, 0 };
        } else if (niveau.get(0, 1) != Niveau.VIDE && niveau.get(1, 0) == Niveau.VIDE) {
            return new int[] { 0, 1 };
        } else if (niveau.get(0, 1) == Niveau.VIDE && niveau.get(1, 0) == Niveau.VIDE) {
            return new int[] { 0, 0 };
        } else {
            do {
                lig = (int) (Math.random() * niveau.getLignes());
                col = (int) (Math.random() * niveau.getColonnes());
            } while (niveau.get(lig, col) == Niveau.VIDE ||
                    (lig == 0 && col == 0));

            return new int[] { lig, col };
        }
    }
}
