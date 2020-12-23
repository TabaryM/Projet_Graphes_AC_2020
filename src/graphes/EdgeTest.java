package graphes;

import exception.EdgeException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    // On vérifie que l'exception n'est pas lancée si ne doit pas l'être
    @Test
    void compareToClassCastExceptionRight() {
        Edge x = new Edge(1, 2);
        Edge y = new Edge(2, 3);
        assertDoesNotThrow(() -> x.compareTo(y));
    }

    // On vérifie que l'exception est lancé quand on compare deux objets de type différents
    @Test
    void compareToClassCastException() {
        Edge x = new Edge(1, 2);
        Graph graph = Graph.example();
        assertThrows(ClassCastException.class, () -> x.compareTo(graph));
    }

    @Test
    void compareToSgn(){
        Edge x = new Edge(1, 2);
        Edge y = new Edge(2, 3);
        int comparaison = x.compareTo(y);
        int comparaisonInverse = - y.compareTo(x);
        assertEquals(comparaison, comparaisonInverse);

        x = new Edge(2, 3);
        y = new Edge(1, 2);
        comparaison = x.compareTo(y);
        comparaisonInverse = - y.compareTo(x);
        assertEquals(comparaison, comparaisonInverse);
    }

    @Test
    void compareToTransitif(){
        Edge x = new Edge(5, 4);
        Edge y = new Edge(4, 3);
        Edge z = new Edge(3, 7);
        int xSupY = x.compareTo(y);
        int ySupZ = y.compareTo(z);
        int xSupZ = x.compareTo(z);
        if(xSupY > 0 && ySupZ > 0){
            assertTrue(xSupZ > 0);
        }

        x = new Edge(4, 3);
        y = new Edge(3, 7);
        z = new Edge(5, 4);
        xSupY = x.compareTo(y);
        ySupZ = y.compareTo(z);
        xSupZ = x.compareTo(z);
        if(xSupY > 0 && ySupZ > 0){
            assertTrue(xSupZ > 0);
        }

        x = new Edge(3, 7);
        y = new Edge(5, 4);
        z = new Edge(4, 3);
        xSupY = x.compareTo(y);
        ySupZ = y.compareTo(z);
        xSupZ = x.compareTo(z);
        if(xSupY > 0 && ySupZ > 0){
            assertTrue(xSupZ > 0);
        }

        x = new Edge(3, 7);
        y = new Edge(4, 3);
        z = new Edge(5, 4);
        xSupY = x.compareTo(y);
        ySupZ = y.compareTo(z);
        xSupZ = x.compareTo(z);
        if(xSupY > 0 && ySupZ > 0){
            assertTrue(xSupZ > 0);
        }
    }

    @Test
    void compareToTransitifInverse(){
        Edge x = new Edge(5, 4);
        Edge y = new Edge(4, 3);
        Edge z = new Edge(3, 7);
        int xSupY = x.compareTo(y);
        int ySupZ = y.compareTo(z);
        int xSupZ = x.compareTo(z);
        if(xSupY == 0){
            assertEquals(ySupZ, xSupZ);
        }

        x = new Edge(4, 3);
        y = new Edge(3, 7);
        z = new Edge(5, 4);
        xSupY = x.compareTo(y);
        ySupZ = y.compareTo(z);
        xSupZ = x.compareTo(z);
        if(xSupY == 0){
            assertEquals(ySupZ, xSupZ);
        }

        x = new Edge(3, 7);
        y = new Edge(5, 4);
        z = new Edge(4, 3);
        xSupY = x.compareTo(y);
        ySupZ = y.compareTo(z);
        xSupZ = x.compareTo(z);
        if(xSupY == 0){
            assertEquals(ySupZ, xSupZ);
        }

        x = new Edge(3, 7);
        y = new Edge(4, 3);
        z = new Edge(5, 4);
        xSupY = x.compareTo(y);
        ySupZ = y.compareTo(z);
        xSupZ = x.compareTo(z);
        if(xSupY == 0){
            assertEquals(ySupZ, xSupZ);
        }
    }

    @Test
    void equalsRight(){
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);
        assertEquals(edge2, edge1);
    }

    @Test
    void sommetFromArete(){
        Edge edge1 = new Edge(0, 2);
        Edge edge2 = new Edge(1, 2);
        Edge edge3 = new Edge(1, 3);
        List<Edge> edges = new ArrayList<>();
        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);

        List<Integer> sommets = new ArrayList<>();
        sommets.add(0);
        sommets.add(2);
        sommets.add(1);
        sommets.add(3);
        assertEquals(sommets, Edge.sommetFromArete(edges));
    }

    @Test
    void areteFromSommet(){
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(4, 6);
        List<Edge> edges = new ArrayList<>();
        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);
        edges.add(edge4);

        List<Integer> sommets = new ArrayList<>();
        sommets.add(1);
        sommets.add(2);
        sommets.add(3);
        sommets.add(4);
        sommets.add(6);
        assertEquals(edges, Edge.areteFromSommet(sommets));
    }

    @Test
    void otherRight(){
        Edge edge1 = new Edge(1, 2);
        assertEquals(1, edge1.other(2));
        assertEquals(2, edge1.other(1));
    }

    @Test
    void otherError(){
        Edge edge1 = new Edge(1, 2);
        assertThrows(EdgeException.class, () -> edge1.other(3));
    }
}