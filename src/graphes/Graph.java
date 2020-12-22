package graphes;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.util.List;

public class Graph{
	/**
	 * Tableau de liste d'adjacences des sommets
	 * Pour chaque sommet v on a une List d'arête qui y sont rattaché.
	 */
	private final List<Edge>[] adj;
	/**
	 * Tableau de coordonnées en x des sommets
	 */
	private final int[] coordX;
	/**
	 * Tableau des coordonnées en y des sommets
	 */
	private final int[] coordY;
	/**
	 * Nombre de sommets du graphe
	 */
	private final int V;
	/**
	 * Nombre d'arête du graphe
	 */
	private int E;

	/**
	 * Créer un graphe à N sommets
	 * @param N nombre de sommets du graphe
	 */
	@SuppressWarnings("unchecked")
	public Graph(int N){
		this.V = N;
		this.E = 0;
		adj = (ArrayList<Edge>[]) new ArrayList[N];
		for (int v= 0; v < N; v++) {
			adj[v] = new ArrayList<>();
		}
		coordX = new int[N];
		coordY = new int[N];
		for (int v= 0; v < N; v++) {
			coordX[v] = 0;
		}
		for (int v= 0; v < N; v++) {
			coordY[v] = 0;
		}
	}

	/**
	 * Retourne le nombre de sommets
	 * @return V le nombre de sommets du graphe
	 */
	public int vertices(){
		return V;
	}

	/**
	 * Fixe les coordonnées (x, y) d'un sommet i
	 * @param i : numéro du sommet
	 * @param x : coordonnées en abscisse du sommet
	 * @param y : coordonnées en ordonnée du sommet
	 */
	public void setCoordinate(int i, int x, int y){
		coordX[i] = x;
		coordY[i] = y;
	}

	/**
	 * Ajoute une arête au graphe, met à jours les listes d'adjacences des sommets connectés
	 * @param e : l'arête ajoutée
	 */
	public void addEdge(Edge e){
		int v = e.from;
		int w = e.to;
		adj[v].add(e);
		adj[w].add(e);
	}

	/**
	 * Retourne une copie de la liste d'adjacence d'un sommet
	 * @param v : le sommet qui nous interesse
	 * @return  La liste des arêtes qui sont connectées à ce sommet
	 */
	public List<Edge> adj(int v){
		return new ArrayList<>(adj[v]);
	}

	/**
	 * Retourne la liste de toutes les arêtes du graphe
	 * @return La liste de toutes les arêtes
	 */
	public List<Edge> edges(){
		ArrayList<Edge> list = new ArrayList<Edge>();
		for (int v = 0; v < V; v++) {
			for (Edge e : adj(v)) {
				if (e.from == v) {
					list.add(e);
				}
			}
		}
		return list;
	}

	/**
	 * Retourne le graphe G1 donné sur le sujet
	 * @return graphe d'exemple avec 4 sommets et 5 arêtes
	 */
	public static Graph example(){
		Graph g = new Graph(4);
		g.setCoordinate(0, 100,100);
		g.setCoordinate(1, 300,300);
		g.setCoordinate(2, 300,100);
		g.setCoordinate(3, 100,300);
		g.addEdge(new Edge(0,1));
		g.addEdge(new Edge(0,2));
		g.addEdge(new Edge(0,3));
		g.addEdge(new Edge(1,2));
		g.addEdge(new Edge(1,3));
		return g;
	}

	/**
	 * Créer un graphe de n * n sommets
	 * Positionne les sommets sur une grille carrée de sorte à ce que les sommets soient connecté à max 4 autres sommets (les 4 voisins)
	 * @param n taille de la grille
	 * @return Graphe de taille n * n
	 */
	public static Graph Grid(int n){
		Graph g = new Graph(n*n);
		int i,j;
		for (i = 0 ; i < n; i ++){
			for (j = 0 ; j < n; j ++){
				g.setCoordinate(n*i+j, 50+(300*i)/n,50+(300*j)/n);
			}
		}

		for (i = 0 ; i < n; i ++) {
			for (j = 0; j < n; j++) {
				if (i < n - 1) {
					g.addEdge(new Edge(n * i + j, n * (i + 1) + j));
				}
				if (j < n - 1) {
					g.addEdge(new Edge(n * i + j, n * i + j + 1));
				}
			}
		}
		return g;
	}

	public BufferedImage toImage(){
		BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.fillRect(0, 0, 400, 400);
		g2d.setColor(Color.BLACK);
		BasicStroke bs = new BasicStroke(2);
		g2d.setStroke(bs);
		// dessine les arêtes
		for (Edge e: edges()){
			int i = e.from;
			int j = e.to;
			if (e.used) {
				g2d.setColor(Color.RED);
			} else {
				g2d.setColor(Color.GRAY);
			}
			g2d.drawLine(coordX[i], coordY[i], coordX[j], coordY[j]);
		}
		// dessine les sommets
		for (int i = 0; i < V; i++){
			g2d.setColor(Color.WHITE);
			g2d.fillOval(coordX[i]-15, coordY[i]-15,30,30);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(coordX[i]-15, coordY[i]-15,30,30);
			g2d.drawString(Integer.toString(i), coordX[i], coordY[i]);
		}

		return image;
	}

	public void writeFile(String s) {
		// Visualisable sur le site https://dreampuf.github.io/GraphvizOnline/
		try {
			PrintWriter writer = new PrintWriter(s, StandardCharsets.UTF_8);
			writer.println("digraph G{");
			for (Edge e: edges()){
				writer.println(e.from + "->" + e.to+";");
			}
			writer.println("}");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getCoordX(int i) {
		return coordX[i];
	}

	public int getCoordY(int i) {
		return coordY[i];
	}

	public void sort(){
		for(List<Edge> list : adj){
			Collections.sort(list);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Graph graph = (Graph) o;
		return V == graph.V && E == graph.E && Arrays.equals(adj, graph.adj) && Arrays.equals(coordX, graph.coordX) && Arrays.equals(coordY, graph.coordY);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(V, E);
		result = 31 * result + Arrays.hashCode(adj);
		result = 31 * result + Arrays.hashCode(coordX);
		result = 31 * result + Arrays.hashCode(coordY);
		return result;
	}
}
