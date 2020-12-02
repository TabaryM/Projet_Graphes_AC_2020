import algos.Algorithme;
import algos.Kruskal;
import graphes.Display;
import graphes.Graph;

import javax.swing.*;
import java.util.Scanner;

/**
 * @author Tabary
 */
public class Main {
    public static void main(String[] args) {
        Display d = new Display();
        Graph graph = Graph.example();

        Algorithme algorithme = new Kruskal();

        Graph arbreCouvrant = algorithme.getArbreCouvrant(graph);
        arbreCouvrant.setCoordinate(0, 100,100);
        arbreCouvrant.setCoordinate(1, 300,300);
        arbreCouvrant.setCoordinate(2, 300,100);
        arbreCouvrant.setCoordinate(3, 100,300);
        d.setImage(arbreCouvrant.toImage());
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
