package labyrinthe;

import graphes.Display;
import graphes.Graph;
import graphes.Test;

import javax.swing.*;

/**
 * @author Tabary
 * M'a permis de comprendre les postitions des sommets dans le fichier tex et dans le graphe
 */
public class TestGrid {
    public static void main(String[] args) {
        Graph graph = Graph.Grid(5);
        Display display = new Display();
        display.setImage(graph.toImage());
        display.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Test.printLaby(graph, 5, "out/toto.tex");
    }
}
