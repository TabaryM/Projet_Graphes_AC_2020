package algos;

import graphes.Edge;
import graphes.Graph;

import java.util.Collections;
import java.util.List;


public class Kruskal implements Algorithme{
    @Override
    public Graph getArbreCouvrant(Graph g) {
        List<Edge> edges = g.edges();
        Collections.shuffle(edges);

        UnionFind unionFind = new UnionFind(g.vertices());
        int from, to;
        for(Edge edge : edges){
            from = edge.getFrom();
            to = edge.getTo();
            if(unionFind.find(from) != unionFind.find(to)){
                g.edges().get(g.edges().indexOf(edge)).setUsed(true);
                unionFind.union(to, from);
            }
        }
        g.sort();
        return g;
    }

}
