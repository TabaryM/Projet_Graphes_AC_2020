import algos.Algorithme;
import algos.Kruskal;
import algos.Wilson;
import exception.EdgeException;
import graphes.Graph;
import labyrinthe.Labyrinthe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Tabary
 */
public class MainQuestion8 {
    public static void main(String[] args) throws EdgeException {
        Algorithme algorithme;
        Labyrinthe labyrinthe;
        Random random = new Random(System.currentTimeMillis());
        float nbMoyenCDS;
        float distanceMoyenne;
        int taille = 20;
        int nbLaby = 1000;
        long debut, fin;


        nbMoyenCDS = 0;
        distanceMoyenne = 0;
        algorithme = new Kruskal();
        System.out.println("Algorithme de Kruskal : ");
        debut = System.currentTimeMillis();
        System.out.println(debut);
        for(int i = 0; i < nbLaby; i++){
            labyrinthe = new Labyrinthe(algorithme, taille);
            nbMoyenCDS += labyrinthe.getCulDeSac();
            distanceMoyenne += labyrinthe.getDistanceFromEntree(taille*taille-1);
        }
        nbMoyenCDS = nbMoyenCDS / nbLaby;
        distanceMoyenne = distanceMoyenne / nbLaby;
        System.out.println("\tle nombre moyen de cul-de-sacs est de : "+nbMoyenCDS);
        System.out.println("\tla distance moyenne entre l'entrée et la sortie est de : "+distanceMoyenne);
        fin = System.currentTimeMillis();
        System.out.println(fin);
        System.out.println("Durée totale : "+(fin-debut));


        nbMoyenCDS = 0;
        distanceMoyenne = 0;
        algorithme = new Wilson(random);
        System.out.println("\nAlgorithme de Wilson : ");
        debut = System.currentTimeMillis();
        System.out.println(debut);
        for(int i = 0; i < nbLaby; i++){
            labyrinthe = new Labyrinthe(algorithme, taille);
            nbMoyenCDS += labyrinthe.getCulDeSac();
            distanceMoyenne += labyrinthe.getDistanceFromEntree(taille*taille-1);
        }
        nbMoyenCDS = nbMoyenCDS / nbLaby;
        distanceMoyenne = distanceMoyenne / nbLaby;
        System.out.println("\tle nombre moyen de cul-de-sacs est de : "+nbMoyenCDS);
        System.out.println("\tla distance moyenne entre l'entrée et la sortie est de : "+distanceMoyenne);
        fin = System.currentTimeMillis();
        System.out.println(fin);
        System.out.println("Durée totale : "+(fin-debut));
    }
}
