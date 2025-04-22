package Modele;

public class IAExpert extends IA {

    public IAExpert(Niveau n) {
        this.niveau = n;
    }

    @Override
    public int[] run() {
        System.out.println("IA Expert pas encore implémentée");
        return new int[] { 0, 0 };
    }
}