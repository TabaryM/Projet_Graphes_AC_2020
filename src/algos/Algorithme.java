package algos;

import exception.EdgeException;
import graphes.Graph;


public interface Algorithme {
    /**
     * Pour un graphe donné, retourne ce même graphe avec les arêtes utilisées par l'arbre couvrant marquées comme used
     * ATTENTION : cette méthode ne retourne par une copie de g
     * @param g le graphe dont on veut un arbre couvrant.
     * @return Graph : le graphe g avec des arêtes marquées formant un arbre couvrant de g.
     */
    Graph getArbreCouvrant(Graph g) throws EdgeException;
}
