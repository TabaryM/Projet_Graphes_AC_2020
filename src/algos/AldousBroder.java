package algos;

import graphes.Edge;
import graphes.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Tabary
 */
public class AldousBroder implements Algorithme{
    final Random random;

    public AldousBroder(Random random) {
        this.random = random;
    }

    @Override
    public Graph getArbreCouvrant(Graph g) {
        // On copie les sommets du graphe dans l'arbre couvrant
        Graph res = new Graph(g.vertices());
        for (int i = 0; i < res.vertices(); i++) {
            res.setCoordinate(i, g.getCoordX(i), g.getCoordY(i));
        }

        int sommetCourant, sommetDOrigine;
        // sommetCourant = sommetDOrigine = 0;
        // On initialise de sommet courrant. Il est possible d'en prendre un aléatoirement avec :
        sommetCourant = random.nextInt(g.vertices());

        // Liste de sommets visités
        List<Integer> sommetsVisites = new ArrayList<>();
        sommetsVisites.add(sommetCourant);

        List<Edge> adj;
        Edge edge;

        // On s'arrête que si on a autant de sommets visités que de sommets dans le graphe.
        while(sommetsVisites.size() < g.vertices()){
            // On récupère la liste d'adjacence du sommet courant
            adj = g.adj(sommetCourant);
            sommetDOrigine = sommetCourant;
            edge = adj.get(random.nextInt(adj.size()));
            if(edge.getFrom() == sommetDOrigine){ // Soit l'arête a comme origine le sommet courant
                sommetCourant = edge.getTo();
            } else {                              // Soit elle a le sommet courant en destination
                sommetCourant = edge.getFrom();
            }
            if(!sommetsVisites.contains(sommetCourant)){
                edge.setUsed(true);
                sommetsVisites.add(sommetCourant);
                res.addEdge(edge);
            }
        }

        res.sort();
        return res;
    }
}