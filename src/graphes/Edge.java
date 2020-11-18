package graphes;

class Edge {
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
}
