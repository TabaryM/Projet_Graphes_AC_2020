import algos.Algorithme;
import algos.Wilson;
import exception.EdgeException;
import graphes.Display;
import graphes.Graph;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Tabary
 */
public class MainQuestion6 {
    public static void main(String[] args) throws EdgeException {
        Graph graph = Graph.example();

        Algorithme algorithme = new Wilson(new Random(System.currentTimeMillis()));
        ArrayList<Graph> graphList = new ArrayList<>();
        ArrayList<Integer> cptGraph = new ArrayList<>();
        int indice;
        int val;

        // On créer 1 million d'arbres couvrant du graphe d'exemple
        for (int i = 0; i < 1000000; i++) {
            Graph tmp = algorithme.getArbreCouvrant(graph);
            // Si on a déjà créer cet arbre couvrant, on incrémente le compteur de cet arbre
            if(graphList.contains(tmp)){
                indice = graphList.indexOf(tmp);
                val = cptGraph.get(indice);
                cptGraph.set(indice, val+1);
                // On a pas encore créer cet arbre, on l'ajoute donc a la liste des arbres couvrants possibles
            } else {
                graphList.add(tmp);
                // On initialise son compteur à 1
                cptGraph.add(1);
            }
        }

        for(int i = 0; i < graphList.size(); i++){
            Display display = new Display();
            display.setTitle(cptGraph.get(i)+"");
            display.setJlabelText("Nombre d'occurence : "+cptGraph.get(i)+"\nProbabilité d'apparition : "+cptGraph.get(i)*100/ 1_000_000.0);
            display.setImage(graphList.get(i).toImage());
            display.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }
}
