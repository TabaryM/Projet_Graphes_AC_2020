package labyrinthe;

import algos.Algorithme;
import exception.EdgeException;
import graphes.Graph;

/**
 * @author Tabary
 */
public class LabyrintheCreator {
    protected final Algorithme algorithme;

    public LabyrintheCreator(Algorithme algorithme) {
        this.algorithme = algorithme;
    }

    public Graph generateLaby(int taille) throws EdgeException {
        Graph res = Graph.Grid(taille);
        res = algorithme.getArbreCouvrant(res);
        return res;
    }

    // TODO : compteur de cul-de-sac
    // TODO : algo recherche de chemin de l'entrée à la sortie
}
