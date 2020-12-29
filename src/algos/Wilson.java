package algos;

import exception.EdgeException;
import graphes.Edge;
import graphes.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static graphes.Edge.getCheminAsArete;
import static graphes.Edge.getCheminAsSommets;


public class Wilson implements Algorithme{
    private final Random random;
    private final List<Integer> sommetsVisites = new ArrayList<>();
    private final List<Integer> sommetsInconnus = new ArrayList<>();
    private Graph g;

    public Wilson(Random random) {
        this.random = random;
    }

    @Override
    public Graph getArbreCouvrant(Graph g) throws EdgeException {
        this.g = g;

        sommetsVisites.clear();
        sommetsInconnus.clear();
        for(int i = 0; i < g.vertices(); i++){
            sommetsInconnus.add(i);
        }

        sommetsVisites.add(sommetsInconnus.get(0));
        sommetsInconnus.remove(0);

        int pointDeDepart ;
        List<Edge> marche;
        List<Integer> marcheSommet;
        Integer from, to;
        int indice;
        Edge areteDansGraph;

        long startTime, endTime, duration = 0;
        double compteur = 0.0;

        while (sommetsVisites.size() < g.vertices()){
            // On trouve un point de départ utilisable
            pointDeDepart = sommetsInconnus.get(0);
            sommetsInconnus.remove(0);

            // On fait une marche aléatoire depuis ce point de départ jusqu'à un des sommets de l'arbre couvrant (contenus dans sommetVisites).
            startTime = System.currentTimeMillis();
            marcheSommet = marcheAleatoire(pointDeDepart);
            endTime = System.currentTimeMillis();
            duration += endTime - startTime;
            compteur++;

            // On nettoie la marche des cycles
            nettoieCycles(marcheSommet);

            marche = Edge.getCheminAsArete(marcheSommet);

            // On ajoute les sommets à la liste des sommets visités
            for(Edge edge : marche){
                from = edge.getFrom();
                to = edge.getTo();
                if(!sommetsVisites.contains(from)) {
                    sommetsVisites.add(from);
                }
                if(!sommetsVisites.contains(to)) {
                    sommetsVisites.add(to);
                }
                sommetsInconnus.remove(from);
                sommetsInconnus.remove(to);
            }

            // On ajoute les arêtes à l'arbre couvrant
            for(Edge edge : marche){
                indice = g.edges().indexOf(edge);
                if(indice != -1){
                    areteDansGraph = g.edges().get(indice);
                    areteDansGraph.setUsed(true);
                }
            }
        }

        g.sort();
        //System.out.println("duration : " + (duration/compteur));
        return g;
    }

    private void nettoieCycles(List<Integer> marche) {
        int[] cptSommetsRep = new int[g.vertices()];
        int premierIndice;
        int dernierIndice;

        do {
            premierIndice = 0;
            dernierIndice = 0;
            for(int i = 0; i < g.vertices(); i++){
                cptSommetsRep[i] = 0;
            }

            // On trouve le premier truc
            for(Integer sommet : marche){
                cptSommetsRep[sommet]++;
                // Détection du premier cycle
                if(cptSommetsRep[sommet] > 1){
                    premierIndice = marche.indexOf(sommet);
                    dernierIndice = marche.lastIndexOf(sommet);
                    break;
                }
            }

            // On supprime le cycle detecté
            for(int i = 0; i < (dernierIndice - premierIndice); i++){
                marche.remove(premierIndice);
            }
        } while (contientOccurence(cptSommetsRep));
    }

    private boolean contientOccurence(int[] cptSommetsRep) {
        for (int j : cptSommetsRep) {
            if (j > 1) return true;
        }
        return false;
    }

    private List<Integer> marcheAleatoire(Integer pointDeDepart){
        List<Integer> res = new ArrayList<>();
        boolean stop = false;

        List<Edge> adj;
        Edge edge;

        int sommetCourant = pointDeDepart, sommetDOrigine;
        res.add(sommetCourant);

        while (!stop){
            // On récupère la liste d'adjacence du sommet courant
            sommetDOrigine = sommetCourant;
            adj = g.adj(sommetCourant);
            edge = adj.get(random.nextInt(adj.size()));
            // Soit l'arête a pour origine le sommet courant

            if(edge.getFrom() == sommetDOrigine){
                sommetCourant = edge.getTo();
            } else {
                // Soit elle a le sommet courant pour destination
                sommetCourant = edge.getFrom();
            }

            res.add(sommetCourant);
            // On vérifie si on doit s'arrêter
            for(Integer sommet : sommetsVisites){
                stop = res.contains(sommet);
                if (stop) break;
            }

        }

        return res;
    }
}
