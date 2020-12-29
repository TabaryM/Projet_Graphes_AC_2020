import algos.AldousBroder;
import algos.Algorithme;
import algos.Kruskal;
import algos.Wilson;
import exception.EdgeException;
import graphes.Graph;
import graphes.Test;
import labyrinthe.Labyrinthe;

import java.util.Random;


/**
 * @author Tabary
 */
public class MainQuestion7 {
    public static void main(String[] args) throws EdgeException {
        Algorithme algorithme;
        Labyrinthe labyrinthe;
        Graph graph;
        Random random = new Random(System.currentTimeMillis());

        algorithme = new Kruskal();
        labyrinthe = new Labyrinthe(algorithme, 20);
        graph = labyrinthe.getLaby();
        Test.printLaby(graph, 20, "out/labyKruskal.tex");

        algorithme = new AldousBroder(random);
        labyrinthe = new Labyrinthe(algorithme, 20);
        graph = labyrinthe.getLaby();
        Test.printLaby(graph, 20, "out/labyAldousBroder.tex");

        algorithme = new Wilson(random);
        labyrinthe = new Labyrinthe(algorithme, 20);
        graph = labyrinthe.getLaby();
        Test.printLaby(graph, 20, "out/labyWilson.tex");
    }
}
