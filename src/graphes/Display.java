package graphes;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {
    private JLabel jlabelImage;
    private JTextArea jlabelText;
    boolean visible;

    /**
     * Créer un affichage du graphe (pour débugger)
     */
    public  Display() {
        super("Image");       // Titre de la fenêtre
        setPreferredSize(new Dimension(400, 500));  // largeur, hauteur
        jlabelImage = new JLabel();
        jlabelText = new JTextArea();
        visible = false;
        this.add(jlabelText, BorderLayout.NORTH);
        this.add(jlabelImage, BorderLayout.CENTER);
        this.pack();
    }

    public void setImage(Image blop) {
        if (!visible) {
            visible = true;
            this.setVisible(true);
	    }
	    jlabelImage.setIcon(new ImageIcon(blop));
    }

    /**
     * La fenêtre n'est plus visible
     */
    public void close() {
        this.dispose();
    }

    public void setJlabelText(String text){
        jlabelText.setText(text);
    }

} 
