import algos.AldousBroder;
import algos.Algorithme;
import algos.Kruskal;
import algos.Wilson;
import exception.EdgeException;
import graphes.Display;
import graphes.Graph;
import graphes.Test;
import labyrinthe.Labyrinthe;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;


public class Main {
    public static void main(String[] args) throws EdgeException {
        if(args.length != 1){
            System.out.println("Veuillez ajouter le numéro de la question à tester (sous la forme \"QX\" avec X le numéro de la question)");
            System.out.println("Questions testables : Q3, Q5, Q6, Q7, Q8");
            return;
        }
        Algorithme algorithme = null;
        switch (args[0]){
            case "Q3":
                algorithme = new Kruskal();
                break;
            case "Q5":
                algorithme = new AldousBroder(new Random(System.currentTimeMillis()));
                break;
            case "Q6":
                algorithme = new Wilson(new Random(System.currentTimeMillis()));
                break;
            case "Q7":
                testCreateLaby();
                break;
            case "Q8":
                testLabyKruskalVSWilsonVSAldousBroder();
                break;
        }
        if(algorithme != null) {
            testStats(algorithme);
        }
    }

    /**
     * Permet les tests pour les question 3, 5 et 6
     * @param algorithme l'algorithme de recherche d'arbre couvrant que l'on test
     * @throws EdgeException Si il y a un gros problème, envoyez nous un mail,
     *                       normalement j'ai fait suffisement de tests pour pas qu'il y ai cette erreur
     */
    public static void testStats(Algorithme algorithme) throws EdgeException {
        Graph graph;
        ArrayList<Graph> graphList = new ArrayList<>();
        ArrayList<Integer> cptGraph = new ArrayList<>();
        int indice;
        int val;

        // On créer 1 million d'arbres couvrant du graphe d'exemple
        for (int i = 0; i < 1000000; i++) {
            graph = Graph.example();
            Graph tmp = algorithme.getArbreCouvrant(graph);
            // Si on a déjà créer cet arbre couvrant, on incrémente le compteur de cet arbre
            if(graphList.contains(tmp)){
                indice = graphList.indexOf(tmp);
                val = cptGraph.get(indice);
                cptGraph.set(indice, val+1);
                // On a pas encore créer cet arbre, on l'ajoute donc a la liste des arbres couvrants possibles
            } else {
                graphList.add(tmp);
                // On initialise son compteur à 1
                cptGraph.add(1);
            }

        }

        for(int i = 0; i < graphList.size(); i++){
            Display display = new Display();
            display.setTitle(cptGraph.get(i)+"");
            display.setJlabelText("Nombre d'occurence : "+cptGraph.get(i)+"\nProbabilité d'apparition : "+cptGraph.get(i)*100/ 1_000_000.0);
            display.setImage(graphList.get(i).toImage());
            display.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    /**
     * Test la création de labyrinthe avec chaque algo et met la description dans un fichier latex dans le repertoire out avec comme titre
     * laby[Algorithme].tex
     */
    public static void testCreateLaby() throws EdgeException {
        Algorithme algorithme;
        Labyrinthe labyrinthe;
        Graph graph;
        Random random = new Random(System.currentTimeMillis());

        algorithme = new Kruskal();
        labyrinthe = new Labyrinthe(algorithme, 20);
        graph = labyrinthe.getLaby();
        Test.printLaby(graph, 20, "out/labyKruskal.tex");

        algorithme = new AldousBroder(random);
        labyrinthe = new Labyrinthe(algorithme, 20);
        graph = labyrinthe.getLaby();
        Test.printLaby(graph, 20, "out/labyAldousBroder.tex");

        algorithme = new Wilson(random);
        labyrinthe = new Labyrinthe(algorithme, 20);
        graph = labyrinthe.getLaby();
        Test.printLaby(graph, 20, "out/labyWilson.tex");
    }

    /**
     * Test 1000 labyrinthe créer avec la méthode Kruskal et compte le nombre moyen de cul de sac et la distance moyenne de l'entrée à la sortie
     * Fait la même chose avec la méthode Wilson
     * Affiche le nombre moyen de cul de sac et la distance moyenne de l'entrée à la sortie ainsi que le temps d'exécution en secondes
     */
    public static void testLabyKruskalVSWilsonVSAldousBroder() throws EdgeException {
        Algorithme algorithme;
        Labyrinthe labyrinthe;
        Random random = new Random(System.currentTimeMillis());
        // Nombre moyen de cul de sac
        float nbMoyenCDS;
        // Taille moyenne du chemin entre l'entrée et la sortie
        float distanceMoyenne;
        // Taille des labyrinthes
        int taille = 20;
        // Nombre de labyrinthe à produire
        int nbLaby = 1000;
        long debut, fin;

        nbMoyenCDS = 0;
        distanceMoyenne = 0;
        algorithme = new Kruskal();
        System.out.println("Algorithme de Kruskal : ");
        debut = System.currentTimeMillis();
        for(int i = 0; i < nbLaby; i++){
            labyrinthe = new Labyrinthe(algorithme, taille);
            nbMoyenCDS += labyrinthe.getCulDeSac();
            distanceMoyenne += labyrinthe.getDistanceFromEntree((taille*taille)-1);
        }
        nbMoyenCDS = nbMoyenCDS / nbLaby;
        distanceMoyenne = distanceMoyenne / nbLaby;
        System.out.println("\tle nombre moyen de cul-de-sacs est de : "+nbMoyenCDS);
        System.out.println("\tla distance moyenne entre l'entrée et la sortie est de : "+distanceMoyenne);
        fin = System.currentTimeMillis();
        System.out.println("\tTemps d'execution : "+((fin-debut)/1000f)+" secondes");

        nbMoyenCDS = 0;
        distanceMoyenne = 0;
        algorithme = new Wilson(random);
        System.out.println("\nAlgorithme de Wilson : ");
        debut = System.currentTimeMillis();
        for(int i = 0; i < nbLaby; i++){
            labyrinthe = new Labyrinthe(algorithme, taille);
            nbMoyenCDS += labyrinthe.getCulDeSac();
            distanceMoyenne += labyrinthe.getDistanceFromEntree(taille*taille-1);
        }
        nbMoyenCDS = nbMoyenCDS / nbLaby;
        distanceMoyenne = distanceMoyenne / nbLaby;
        System.out.println("\tle nombre moyen de cul-de-sacs est de : "+nbMoyenCDS);
        System.out.println("\tla distance moyenne entre l'entrée et la sortie est de : "+distanceMoyenne);
        fin = System.currentTimeMillis();
        System.out.println("\tTemps d'execution : "+((fin-debut)/1000f)+" secondes");

        nbMoyenCDS = 0;
        distanceMoyenne = 0;
        algorithme = new AldousBroder(random);
        System.out.println("\nAlgorithme de Aldous et Broder : ");
        debut = System.currentTimeMillis();
        for(int i = 0; i < nbLaby; i++){
            labyrinthe = new Labyrinthe(algorithme, taille);
            nbMoyenCDS += labyrinthe.getCulDeSac();
            distanceMoyenne += labyrinthe.getDistanceFromEntree(taille*taille-1);
        }
        nbMoyenCDS = nbMoyenCDS / nbLaby;
        distanceMoyenne = distanceMoyenne / nbLaby;
        System.out.println("\tle nombre moyen de cul-de-sacs est de : "+nbMoyenCDS);
        System.out.println("\tla distance moyenne entre l'entrée et la sortie est de : "+distanceMoyenne);
        fin = System.currentTimeMillis();
        System.out.println("\tTemps d'execution : "+((fin-debut)/1000f)+" secondes");
    }
}
