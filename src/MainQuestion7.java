import algos.AldousBroder;
import algos.Algorithme;
import algos.Kruskal;
import algos.Wilson;
import exception.EdgeException;
import graphes.Graph;
import graphes.Test;
import labyrinthe.LabyrintheCreator;

import java.util.Random;


/**
 * @author Tabary
 */
public class MainQuestion7 {
    public static void main(String[] args) throws EdgeException {
        Algorithme algorithme;
        LabyrintheCreator labyrintheCreator;
        Graph graph;
        Random random = new Random(System.currentTimeMillis());

        algorithme = new Kruskal();
        labyrintheCreator = new LabyrintheCreator(algorithme);
        graph = labyrintheCreator.generateLaby(20);
        Test.printLaby(graph, 20, "out/labyKruskal.tex");

        algorithme = new AldousBroder(random);
        labyrintheCreator = new LabyrintheCreator(algorithme);
        graph = labyrintheCreator.generateLaby(20);
        Test.printLaby(graph, 20, "out/labyAldousBroder.tex");

        algorithme = new Wilson(random);
        labyrintheCreator = new LabyrintheCreator(algorithme);
        graph = labyrintheCreator.generateLaby(20);
        Test.printLaby(graph, 20, "out/labyWilson.tex");
    }
}
