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

    public static List<Integer> sommetFromArete(List<Edge> aretes){
        if(aretes.size() < 1) {
            throw new EdgeException("Nombre d'arête non propice à la décomposition en sommets.\nAttendu : >= 1");
        }
        List<Integer> res = new ArrayList<>();
        Edge arete;
        if(aretes.size() == 1){ // Cas simple où il n'y a qu'une arête
            arete = aretes.get(0);
            res.add(arete.from);
            res.add(arete.to);
        } else { // Cas plus complexe où il y a au moins 2 arêtes
/*
            res.add(aretes.get(0).getFrom());
            int sommet;
            for (int i = 0; i < aretes.size(); i++) {
                arete = aretes.get(i);
                res.add(arete.getTo());
            }
*/
        }
        return res;
    }

    public static List<Edge> areteFromSommet(List<Integer> sommets){
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
    final int other(int v){
        if(v != from && v != to){
            throw new EdgeException("Sommet non reconnu");
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
        return (from == edge.from && to == edge.to) || (from == edge.to && to == edge.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "("+from+", "+to+")";
    }
}
