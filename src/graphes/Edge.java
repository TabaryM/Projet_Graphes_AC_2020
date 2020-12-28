package graphes;

import exception.EdgeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Edge implements Comparable{
    int from;
    int to;
    boolean used;

    public static List<Integer> getCheminAsSommets(List<Edge> aretes) throws EdgeException {
        if(aretes.size() < 1) {
            throw new EdgeException("Nombre d'arête non propice à la décomposition en sommets.\nAttendu : >= 1");
        }
        trisAretePourChemin(aretes);
        if(!isChemin(aretes)){
            throw new EdgeException("Cette liste d'arête ne représente pas un chemin : "+aretes);
        }
        List<Integer> res = new ArrayList<>();
        for(Edge edge : aretes){
            //edge.sort();
            res.add(edge.from);
        }
        res.add(aretes.get(aretes.size()-1).getTo());
        return res;
    }

    /**
     * Tris les arêtes pour que le from d'une arête soit égal au to de l'arête suivante
     * @param aretes un chemin d'arête qui se suivent (deux arêtes successives ont au moins un sommet commun)
     * @throws EdgeException si deux arêtes successives n'ont pas un sommet commun.
     */
    private static void trisAretePourChemin(List<Edge> aretes) throws EdgeException {
        if(!isChemin(aretes)) {
            Edge current, successor;
            int tmp;
            for(int i = aretes.size() - 1 ; i > 0; i--){
                current = aretes.get(i-1);
                successor = aretes.get(i);
                if(current.to != successor.from){
                    tmp = current.from;
                    current.from = current.other(tmp);
                    current.to = tmp;
                }
                if(current.to != successor.from){
                    throw new EdgeException("Arêtes non successives : "+current+" et "+successor);
                }
            }
        }
    }

    private static boolean isChemin(List<Edge> aretes){
        boolean res = true;
        for(int i = 0; i < aretes.size() -1; i++){
            res &= aretes.get(i).to == aretes.get(i+1).from;
        }
        return res;
    }

    public static List<Edge> getCheminAsArete(List<Integer> sommets) throws EdgeException {
        if(sommets.size() < 2) {
            throw new EdgeException("Nombre de sommets non propice à la création d'arêtes.\nAttendu : >= 2");
        }
        List<Edge> res = new ArrayList<>();
        int fromPrec = sommets.get(0), to;
        for(int i = 1, n = sommets.size(); i < n; i++){
            to = sommets.get(i);
            res.add(new Edge(fromPrec, to));
            fromPrec = to;
        }
        return res;
    }

    /**
     * Créer une arête entre les sommets x et y.
     * @param x un sommet
     * @param y un autre
     */
    public Edge(int x, int y){
        this.from = x;
        this.to = y;
        this.used = false;
    }

    /**
     * Permet de connaître le sommet à l'autre extrémité de l'arête
     * @param v le sommet connu
     * @return le sommet inconnu
     * @throws EdgeException si le sommet n'est pas reconnu comme un des deux sommets de l'arête
     */
    public final int other(int v) throws EdgeException {
        if(v != from && v != to){
            throw new EdgeException("Sommet non reconnu (demandé : "+v+"; pour l'arete : "+this);
        }
        if (this.from == v) {
            return this.to;
        } else {
            return this.from;
        }
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

    @Override
    public int compareTo(Object o) {
        if(this.equals(o)) return 0;
        Edge y = (Edge)o;
        int res = this.from - y.from;
        if(res == 0){
            res = this.to - y.to;
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return ((from == edge.from && to == edge.to) || (from == edge.to && to == edge.from)) && (this.used == edge.used);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "("+from+", "+to+") " + used;
    }

    public void sort() {
        int tmp;
        if(to < from){
            tmp = from;
            from = to;
            to = tmp;
        }
    }
}
