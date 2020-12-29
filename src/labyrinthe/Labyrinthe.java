package labyrinthe;

import algos.Algorithme;
import exception.EdgeException;
import graphes.Edge;
import graphes.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
            parent[i] = -1;
        }
        calculDistances(0);
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

        Integer courrant, succ;

        listeSommets.add(sommetDebut);
        distance[sommetDebut] = 0;
        parent[sommetDebut] = sommetDebut;

        while(!listeSommets.isEmpty()){ // On parcourera tous les sommets
            courrant = listeSommets.get(0);

            sommetsParcourus.add(courrant);
            listeSommets.remove(0);

            for(Edge edge : laby.adj(courrant)){        // Pour voisin...
                if(edge.isUsed()){                      // On ne regarde que les arêtes de l'arbre couvrant (i.e : les couloirs du labyrinthe)
                    succ = edge.other(courrant);
                    if(!sommetsParcourus.contains(succ) && !listeSommets.contains(succ)) {
                        listeSommets.add(succ);
                    }
                    if(distance[succ] > distance[courrant]+1){
                        parent[succ] = courrant;
                        distance[succ] = distance[courrant]+1;
                    }
                }
            }
        }
    }

    public int getDistanceFromEntree(int salle){
        return distance[salle];
    }

    List<Edge> edges(){
        return laby.edges();
    }

}
