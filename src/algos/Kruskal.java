package algos;

import graphes.Display;
import graphes.Edge;
import graphes.Graph;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Tabary
 */
public class Kruskal implements Algorithme{
    @Override
    public Graph getArbreCouvrant(Graph g) {
        ArrayList<Edge> edges = g.edges();
        Collections.shuffle(edges);

        Graph res = new Graph(g.vertices());
        for (int i = 0; i < res.vertices(); i++) {
            res.setCoordinate(i, g.getCoordX(i), g.getCoordY(i));
        }

        UnionFind unionFind = new UnionFind(res.vertices());
        int from, to;
        for(Edge edge : edges){
            Display display = new Display();
            from = edge.getFrom();
            to = edge.getTo();
            display.setTitle("Edge : " + from + "->" + to);
            if(unionFind.find(from) != unionFind.find(to)){
                res.addEdge(edge);
                unionFind.union(to, from);
                edge.setUsed(true);
            }
            display.setImage(res.toImage());
        }
        return res;
    }

}
