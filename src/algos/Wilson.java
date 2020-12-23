package algos;

import graphes.Edge;
import graphes.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static graphes.Edge.areteFromSommet;
import static graphes.Edge.sommetFromArete;

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
    public Graph getArbreCouvrant(Graph g) {
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
            marche.sort(null);
            System.out.println("marche :  "+marche);
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
                if(!res.edges().contains(edge) && g.edges().contains(edge)) {
                    res.addEdge(edge);
                    edge.setUsed(true);
                }
            }
        }

        res.sort();
        return res;
    }

    private List<Edge> nettoieCycles(List<Edge> marche) {
        List<Integer> sommetsRepetes = new ArrayList<>();
        List<Integer> cptSommetsRepetes = new ArrayList<>();

        // On compte le nombre d'apparition de chaque sommet
        int from, to, indice;
        for (Edge edge : marche){
            from = edge.getFrom();
            to = edge.getTo();

            // On ajoute les sommets si on ne les as jamais vu
            if(!sommetsRepetes.contains(from)){
                sommetsRepetes.add(from);
                cptSommetsRepetes.add(1);
            } else {
                // Si on a déjà vu le sommet, on incrémente le compteur
                indice = sommetsRepetes.indexOf(from);
                cptSommetsRepetes.set(indice, cptSommetsRepetes.get(indice)+1);
            }

            // Idem qu'avant mais en regardant l'autre partie de l'arête
            if(!sommetsRepetes.contains(to)){
                sommetsRepetes.add(to);
                cptSommetsRepetes.add(1);
            } else {
                indice = sommetsRepetes.indexOf(to);
                cptSommetsRepetes.set(indice, cptSommetsRepetes.get(indice)+1);
            }
        }

        // On trouve le premier sommet parcouru deux fois par la marche
        int premierSommetRepete = 0;
        int nbRepetition = 0;
        for(Integer cpt : cptSommetsRepetes){
            if(cpt > nbRepetition) {
                nbRepetition = cpt;
                premierSommetRepete = sommetsRepetes.get(cptSommetsRepetes.indexOf(cpt));
            }
        }

        // S'il n'y a pas de répétition, on n'a pas besoin de nettoyer plus
        if(nbRepetition == 1) return marche;

        // On trouve le premier et dernier indice du sommet étant apparu plus d'une fois
        List<Integer> marcheInteger = sommetFromArete(marche);

        Integer premierIndice = marcheInteger.indexOf(premierSommetRepete);
        int nbElemASuppr = marcheInteger.lastIndexOf(premierSommetRepete) - premierIndice;

        for(int i = 0; i < nbElemASuppr; i++){
            marcheInteger.remove(premierIndice);
        }
        System.out.println("sommets : "+marcheInteger);

        // On retire les arêtes partant du premier sommet répété
        List<Edge> edges = areteFromSommet(marcheInteger);
        System.out.println("arêtes :  "+edges+"\n");
        return edges;
    }

    private List<Edge> marcheAleatoire(Integer pointDeDepart){
        List<Edge> marche = new ArrayList<>();
        boolean stop = false;

        List<Edge> adj;
        Edge edge;

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
            if(!marche.contains(edge)) {
                marche.add(edge);
            }

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
