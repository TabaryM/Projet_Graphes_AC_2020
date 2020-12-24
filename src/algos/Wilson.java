package algos;

import exception.EdgeException;
import graphes.Edge;
import graphes.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static graphes.Edge.getCheminAsArete;
import static graphes.Edge.getCheminAsSommets;

/**
 * @author Tabary
 */
public class Wilson implements Algorithme{
    private final Random random;
    private final List<Integer> sommetsVisites = new ArrayList<>();
    private Graph g;

    public Wilson(Random random) {
        this.random = random;
    }

    @Override
    public Graph getArbreCouvrant(Graph g) throws EdgeException {
        this.g = g;
        // On copie les sommets du graphe dans l'arbre couvrant
        Graph res = new Graph(g.vertices());
        for (int i = 0; i < res.vertices(); i++) {
            res.setCoordinate(i, g.getCoordX(i), g.getCoordY(i));
        }
        sommetsVisites.clear();
        sommetsVisites.add(random.nextInt(res.vertices()));

        int pointDeDepart ;
        List<Edge> marche;
        int from, to;
        while (sommetsVisites.size() < res.vertices()){
            // On trouve un point de départ utilisable
            do {
                pointDeDepart = random.nextInt(res.vertices());
            } while (sommetsVisites.contains(pointDeDepart));
            // On fait une marche aléatoire depuis ce point de départ jusqu'à un des sommets de l'arbre couvrant (contenus dans sommetVisites).
            marche = marcheAleatoire(pointDeDepart);

            // On nettoie la marche des cycles
            marche = nettoieCycles(marche);

            // On ajoute les sommets à la liste des sommets visités
            for(Edge edge : marche){
                from = edge.getFrom();
                to = edge.getTo();
                if(!sommetsVisites.contains(from)) sommetsVisites.add(from);
                if(!sommetsVisites.contains(to)) sommetsVisites.add(to);
            }

            // On ajoute les arêtes à l'arbre couvrant
            for(Edge edge : marche){
                if(!res.edges().contains(edge)) {
                    res.addEdge(edge);
                    edge.setUsed(true);
                }
            }
        }

        res.sort();
        return res;
    }

    private List<Edge> nettoieCycles(List<Edge> marche) throws EdgeException {
        List<Integer> sommetsRepetes = new ArrayList<>();
        List<Integer> cptSommetsRepetes = new ArrayList<>();

        ArrayList<Integer> marcheInteger = (ArrayList<Integer>) getCheminAsSommets(marche);
        int indice;
        // On compte le nombre d'apparition de chaque sommet
        for(Integer sommet : marcheInteger){
            if(!sommetsRepetes.contains(sommet)){
                sommetsRepetes.add(sommet);
                cptSommetsRepetes.add(1);
            } else {
                indice = sommetsRepetes.indexOf(sommet);
                cptSommetsRepetes.set(indice, cptSommetsRepetes.get(indice)+1);
            }
        }

        // On trouve le sommet avec le plus d'apparition
        int indicePremierSommetRepete = 0;
        int nbRepetition = 0;
        while(nbRepetition < 1 && indicePremierSommetRepete < cptSommetsRepetes.size()){
            nbRepetition = cptSommetsRepetes.get(indicePremierSommetRepete++);
        }
        indicePremierSommetRepete--;

        // S'il n'y a pas de répétition, on n'a pas besoin de nettoyer plus
        if(nbRepetition == 1) return marche;

        // On trouve le premier et dernier indice du sommet étant apparu plus d'une fois
        int premierSommetRepete = sommetsRepetes.get(indicePremierSommetRepete);

        // On trouve le permier et le dernier indice du premier sommet répété
        int premierIndicePremierSommetRepete = marcheInteger.indexOf(premierSommetRepete);
        int dernierIndicePremierSommetRepete = marcheInteger.lastIndexOf(premierSommetRepete);

        // On supprime tous les éléments entre ces deux indices (premier inclus, dernier exclus)
        // du genre : marcheInteger.removeRange(premierIndicePremierSommetRepete, dernierIndicePremierSommetRepete);
        if (dernierIndicePremierSommetRepete > premierIndicePremierSommetRepete) {
            marcheInteger.subList(premierIndicePremierSommetRepete, dernierIndicePremierSommetRepete).clear();
        }

        List<Edge> edges = getCheminAsArete(marcheInteger);
        return edges;
    }

    private List<Edge> marcheAleatoire(Integer pointDeDepart){
        List<Edge> marche = new ArrayList<>();
        boolean stop = false;

        List<Edge> adj;
        Edge edge, added;

        int sommetCourant = pointDeDepart, sommetDOrigine;
        //sommetCourant = random.nextInt(g.vertices());

        while (!stop){
            // On récupère la liste d'adjacence du sommet courant
            adj = g.adj(sommetCourant);
            sommetDOrigine = sommetCourant;
            edge = adj.get(random.nextInt(adj.size()));
            // Soit l'arête a pour origine le sommet courant
            if(edge.getFrom() == sommetDOrigine){
                sommetCourant = edge.getTo();
            } else {
                // Soit elle a le sommet courant pour destination
                sommetCourant = edge.getFrom();
            }
            try {
                added = new Edge(edge.other(sommetCourant), sommetCourant);
                marche.add(added);
            } catch (EdgeException e) {
                e.printStackTrace();
            }
            //System.out.println("origine : "+sommetDOrigine+"\tchemin : "+marche);

            // On vérifie si on doit s'arrêter
            for(Integer sommet : sommetsVisites) stop |= contientSommet(marche, sommet);
        }

        return marche;
    }

    private boolean contientSommet(List<Edge> marche, Integer cible){
        for(Edge edge : marche){
            if(edge.getFrom() == cible || edge.getTo() == cible)
                return true;
        }
        return false;
    }

}
