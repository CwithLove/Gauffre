/*
import java.util.*;
public class IAboss {
    private Random rand = new Random();
    ArrayList<ArrayList<Integer>> configs = new ArrayList<>();

    IAboss() {}

    public void IAcoupboss(Jeu jeu){ //pas encore fait
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
    }


}*/