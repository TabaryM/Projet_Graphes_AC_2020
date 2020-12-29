package labyrinthe;

import algos.Wilson;
import exception.EdgeException;
import graphes.Edge;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;


public class TestDistances {
    public static void main(String[] args) throws EdgeException {
        int taille = 20;
        long seed;
        seed = System.currentTimeMillis();
        //seed = 1609267076939L;
        //System.out.println(seed);
        Labyrinthe labyrinthe = new Labyrinthe(new Wilson(new Random(seed)), taille);
        printLaby(labyrinthe, taille, "out/labyAvecDistance.tex");
    }

    /**
     * Affiche dans le terminal imprime dans un fichier .tex la structure d'un labyrinthe défini par un graphe
     * @param laby Labyrinthe en grille de taille size * size
     * @param size taille du labyrinthe
     * @param file fichier .tex où sera écrit le labyrinthe
     */
    public static void printLaby(Labyrinthe laby, int size, String file){
		/* suppose que laby est une grille de taille size x size et
			   crée un .tex qui contient le labyrinthe correspondant */

        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println("\\documentclass{article}\n\\usepackage{tikz}\n\\usepackage[a3paper]{geometry}\n\\begin{document}");

            int numSalle;
            // Avec la distance a l'entrée
            writer.println("\\begin{tikzpicture}");

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i , j));
                    writer.println("\\draw (0.1,0.1) -- (0.4,0.1);");
                    writer.println("\\draw (0.6,0.1) -- (0.9,0.1);");
                    writer.println("\\draw (0.1,0.9) -- (0.4,0.9);");
                    writer.println("\\draw (0.6,0.9) -- (0.9,0.9);");
                    writer.println("\\draw (0.1,0.1) -- (0.1, 0.4);");
                    writer.println("\\draw (0.1,0.6) -- (0.1, 0.9);");
                    writer.println("\\draw (0.9,0.1) -- (0.9,0.4);");
                    writer.println("\\draw (0.9,0.6) -- (0.9,0.9);");
                    numSalle = (size*j)+i;
                    writer.println("\\node at (0.5, 0.5) {"+laby.getDistanceFromEntree(numSalle)+"};");
                    writer.println("\\end{scope}");
                }
            }

            /* bord */
            for (int i = 0; i < size; i++) {
                writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i , 0));
                writer.println("\\draw(0.4,0.1) -- (0.6, 0.1);");
                writer.println("\\end{scope}");
                writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i , size-1));
                writer.println("\\draw(0.4,0.9) -- (0.6, 0.9);");
                writer.println("\\end{scope}");
                if (i > 0) {
                    writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", 0 , i));
                    writer.println("\\draw(0.1,0.4) -- (0.1, 0.6);");
                    writer.println("\\end{scope}");
                }
                if (i < size - 1) {
                    writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", size -1 , i));
                    writer.println("\\draw(0.9,0.4) -- (0.9, 0.6);");
                    writer.println("\\end{scope}");
                }
                writer.println("\\draw (0,0.4) -- (0.1, 0.4);");
                writer.println("\\draw (0,0.6) -- (0.1, 0.6);");
                writer.println(String.format(Locale.US, "\\draw (%d, %d) ++ (0, 0.4)  -- ++ (-0.1, 0); ", size , size -1));
                writer.println(String.format(Locale.US, "\\draw (%d, %d) ++ (0, 0.6)  -- ++ (-0.1, 0); ", size , size -1));
            }

            for (Edge e: laby.edges()) {
                int i = e.getFrom() % size;
                int j = e.getFrom() / size;
                writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i , j));
                if (e.getTo() == e.getFrom() + size) {
                    /* arête verticale */
                    if (!e.isUsed()) {
                        writer.println("\\draw (0.4,0.9) -- (0.6,0.9);");
                        writer.println("\\draw (0.4,1.1) -- (0.6,1.1);");
                    } else {
                        writer.println("\\draw (0.4,0.9) -- (0.4,1.1);");
                        writer.println("\\draw (0.6,0.9) -- (0.6,1.1);");
                    }
                }
                else{
                    /* arête horizontale */
                    if (!e.isUsed()) {
                        writer.println("\\draw (0.9,0.4) -- (0.9,0.6);");
                        writer.println("\\draw (1.1,0.4) -- (1.1,0.6);");
                    }
                    else {
                        writer.println("\\draw (0.9,0.4) -- (1.1,0.4);");
                        writer.println("\\draw (0.9,0.6) -- (1.1,0.6);");
                    }
                }
                writer.println("\\end{scope}");
            }
            writer.println("\\end{tikzpicture}");
            writer.println("\\end{document}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
