import algos.Algorithme;
import algos.Kruskal;
import graphes.Display;
import graphes.Graph;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Tabary
 */
public class Main {
    public static void main(String[] args) {
        Graph graph = Graph.example();

        Algorithme algorithme = new Kruskal();
        HashMap<Graph, Integer> graphs = new HashMap<>();
        ArrayList<Graph> graphList = new ArrayList<>();
        ArrayList<Integer> cptGraph = new ArrayList<>();

        // TODO comprendre pourquoi deux graphs ne sont pas repérés comme égaux alors qu'ils ont les mêmes arrêtes
        for (int i = 0; i < 1000000; i++) {
            Graph tmp = algorithme.getArbreCouvrant(graph);
            if(graphList.contains(tmp)){
                int indice = graphList.indexOf(tmp);
                if(cptGraph.size() < indice){
                    cptGraph.add(1);
                } else {
                    int val = cptGraph.get(indice);
                    cptGraph.set(indice, val+1);
                }
            } else {
                graphList.add(tmp);
                cptGraph.add(1);
            }

        }

        for(int i = 0; i < graphList.size(); i++){
            Display display = new Display();
            display.setTitle(cptGraph.get(i)+"");
            display.setImage(graphList.get(i).toImage());
            display.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        }
    }
}
