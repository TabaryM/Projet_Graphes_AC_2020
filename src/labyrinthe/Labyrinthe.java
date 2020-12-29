package labyrinthe;

import algos.Algorithme;
import exception.EdgeException;
import graphes.Graph;

/**
 * @author Tabary
 */
public class Labyrinthe {
    protected final Algorithme algorithme;
    private final Graph laby;

    public Labyrinthe(Algorithme algorithme, int taille) throws EdgeException {
        this.algorithme = algorithme;
        laby = algorithme.getArbreCouvrant(Graph.Grid(taille));
    }

    public Graph getLaby() {
        return laby;
    }

    /**
     * Compte le nombre de sommet ayant un degré égal à 1 et retourne ce nombre.
     * @return le nombre de cul-de-sac dans le labyrinthe.
     */
    public int getCulDeSac(){
        int res = 0;
        for(int i = 0; i < laby.vertices(); i++){
            if(laby.adj(i).size() == 1) {
                res = res + 1;
            }
        }
        return res;
    }

    // TODO : algo recherche de chemin de l'entrée à la sortie
}
