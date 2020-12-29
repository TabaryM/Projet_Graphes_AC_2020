package labyrinthe;

import algos.Algorithme;
import exception.EdgeException;
import graphes.Edge;
import graphes.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tabary
 */
public class Labyrinthe {
    protected final Algorithme algorithme;
    private final Graph laby;
    private final int[] distance;
    private final int[] parent;

    public Labyrinthe(Algorithme algorithme, int taille) throws EdgeException {
        this.algorithme = algorithme;
        laby = algorithme.getArbreCouvrant(Graph.Grid(taille));
        distance = new int[laby.vertices()];
        parent = new int[laby.vertices()];
        for (int i = 0; i < laby.vertices(); i++) {
            distance[i] = Integer.MAX_VALUE;
            parent[i] = i;
        }
        calculDistances(taille-1);
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
        int degre;
        for(int i = 0; i < laby.vertices(); i++){
            degre = 0;
            for(Edge edge : laby.adj(i)){
                if(edge.isUsed()) {
                    degre++;
                }
            }
            if(degre == 1) {
                res++;
            }
        }
        return res;
    }

    // TODO : algo recherche de chemin de l'entrée à la sortie
    // On peut connaitre la distance à l'entrée en effectuant un parcours en largeur depuis l'entrée
    // Entrée : taille - 1
    // Sortie : taille² -1
    public void calculDistances(int sommetDebut) throws EdgeException {
        List<Integer> listeSommets = new ArrayList<>();
        List<Integer> sommetsParcourus = new ArrayList<>();
        listeSommets.add(sommetDebut);
        int courrant, succ;
        int profondeur = 1;
        while(listeSommets.size() != 0){ // On parcourera tous les sommets
            courrant = listeSommets.get(0);
            listeSommets.remove(0);
            sommetsParcourus.add(courrant);
            for(Edge edge : laby.adj(courrant)){
                if(edge.isUsed()){                      // On ne regarde que les arêtes de l'arbre couvrant (i.e : les couloirs du labyrinthe)
                    succ = edge.other(courrant);
                    if(!sommetsParcourus.contains(succ) && !listeSommets.contains(succ)) {
                        listeSommets.add(succ);
                    }
                    if(parent[succ] == succ){           // On ne met à jour que si c'est la première fois qu'on observe ce sommet
                        parent[succ] = courrant;
                        distance[succ] = profondeur;
                    }
                }
            }
            profondeur = profondeur + 1;
        }
    }

    public int getDistanceFromEntree(int salle){
        return distance[salle];
    }
}
