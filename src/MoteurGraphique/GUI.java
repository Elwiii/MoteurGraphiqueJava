/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nikolai
 */
public class GUI extends JFrame {

    JPanel jpanel;
    JLabel jlabel;

    public GUI() {
        super("Moteur graphique");
        try {
//            JFrame parametres = new JFrame("parametres");
            
            /*parametres.*/add(new JPanelParametre(this),BorderLayout.EAST);
//            parametres.setPreferredSize(new Dimension(454, 685));
//            parametres.setLocationRelativeTo(null);
//            parametres.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            parametres.setVisible(true);
//            parametres.pack();
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        setPreferredSize(new Dimension(650, 750));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public void paint(Image image) {
        if (jpanel != null) {
            remove(jpanel);
        }
        jlabel = new JLabel(new ImageIcon(image));
        jpanel = new JPanel();
        jpanel.add(jlabel);
        add(jpanel, BorderLayout.CENTER);
        jpanel.repaint();
        repaint();
        this.pack();
    }

}
