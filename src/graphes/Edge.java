package graphes;

import java.util.Objects;

public class Edge {
    int from;
    int to;
    boolean used;

    /**
     * Créer une arête entre les sommets x et y.
     * @param x un sommet
     * @param y un autre
     */
    Edge(int x, int y){
        this.from = x;
        this.to = y;
        this.used = false;
    }

    /**
     * Permet de connaître le sommet à l'autre extrémité de l'arête
     * @param v le sommet connu
     * @return le sommet inconnu
     */
    final int other(int v){
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return from == edge.from &&
                to == edge.to;
    }

}
