package algos;


import java.util.Arrays;

/**
 * @author Tabary
 * Structure inspirée de la page https://fr.wikipedia.org/w/index.php?title=Union-find&oldid=171197463
 */
public class UnionFind {
    private final int[] modele;
    private final int[] parents;
    private final int[] rang;

    /**
     * Créer un tableau listant les parents des éléments
     * Créer un tableau indiquant le rang (la hauteur dans l'arbre) des éléments
     * @param N nombre d'éléments qu'on aura
     */
    public UnionFind(int N){
        parents = new int[N];
        modele = new int[N];
        rang = new int[N];
        Arrays.fill(rang, 0);
        for(int i = 0; i < N; i++) modele[i] = i;
        for(int i = 0; i < N; i++) parents[i] = i;
        //System.out.println(Arrays.toString(parents));
    }

    /**
     * Find
     * @param x l'élément dont on veut la racine
     * @return si x est la racine : x
     *         sinon find(parent[x])
     */
    public int find(int x){
        int parent = parents[x];
        if(parent == x){
            return x;
        }
        return find(parent);
    }

    /**
     * Union
     * Fait l'union de deux ensembles s'ils ne sont pas dans le même arbre
     * Greffe l'arbre le plus petit sous l'arbre le plus grand
     * Si les deux arbres fusionnés ont le même rang, on incrémente le rang de la racine
     * @param x premier ensemble
     * @param y second ensemble
     */
    public void union(int x, int y){
        System.out.println("Union " + x + "->" + y);
        int racineX = find(x);
        int racineY = find(y);
        if(racineX != racineY){
            if(rang[racineX] < rang[racineY]){
                parents[racineX] = racineY;
            } else {
                parents[racineY] = racineX;
                if(rang[racineX] == rang[racineY]){
                    rang[racineX] ++;
                }
            }
        }
        System.out.println(Arrays.toString(modele));
        System.out.println(Arrays.toString(parents)+'\n');
    }
}
