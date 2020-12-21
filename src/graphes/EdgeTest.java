package graphes;

import org.junit.jupiter.api.Test;

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
}