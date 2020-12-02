package algos;

import graphes.Graph;

/**
 * @author Tabary
 */
public interface Algorithme {
    /**
     * Retourne un arbre couvrant du graphe g
     * @param g le graphe dont on veut un arbre couvrant.
     * @return Graph : un arbre couvrant de g.
     */
    Graph getArbreCouvrant(Graph g);
}
