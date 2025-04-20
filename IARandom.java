
import java.util.Random;
public class IARandom {
    private Random rand = new Random();

    IARandom() {}

    public Couple<Integer,Integer> IAcoup(Jeu jeu){
        int l = rand.nextInt(jeu.niveau.getLignes()-1);
        int c = rand.nextInt(jeu.niveau.getColonnes()-1);
        boolean peutManger = jeu.manger(l, c);
        while(!peutManger){
            l = rand.nextInt(jeu.niveau.getLignes()-1);
            c = rand.nextInt(jeu.niveau.getColonnes()-1);
            peutManger = jeu.manger(l,c);
        }
        jeu.tour++;
        System.out.print("L'IA a joué: "+l+", "+c);
        return new Couple<>(l, c);
    }


}