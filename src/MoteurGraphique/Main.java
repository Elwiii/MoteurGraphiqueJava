/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author nikolai
 */
public class Main {

    public static void main(String[] args) {
        try {
            
            
            Model model = new Model("obj/testA.obj", "obj/african_head_diffuse.jpg", "obj/african_head_nm.jpg");
//            Model model = new Model("obj/african_head.obj", "obj/african_head_diffuse.jpg", "obj/african_head_nm.jpg");

            MoteurGraphique mg = new MoteurGraphique(model);
            Parameter parametre = new Parameter();
            parametre.plain = false;
            parametre.scale = 1;
            mg.setParametre(parametre);
            Vecteur light = new Vecteur (0,0,1);
            mg.setLight(light);
            

            Image bi = mg.draw();
//            GUI gui = new GUI();            
//            gui.paint(bi);
            File outputfile = new File("saved.png");
            ImageIO.write((RenderedImage) bi, "png", outputfile);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
