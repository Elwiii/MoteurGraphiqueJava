/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.Image;
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

//                        Model model = new Model("obj/testA.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");

            Model model = new Model("obj/african_head.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
//            Model model = new Model("obj/anchor.obj", "obj/anchor.jpg", "obj/anchor.jpg");

            MoteurGraphique mg = new MoteurGraphique(model);
            Parameter parametre = new Parameter();
//            parametre.rendu = Parameter.FIL_DE_FER;
//            parametre.rendu = Parameter.FIL_DE_FER_ET_PLAIN;
            parametre.rendu = Parameter.PLAIN;
//            parametre.shadow = Parameter.NO_SHADE;
//            parametre.shadow = Parameter.PHONG_SHADE;
            parametre.shadow = Parameter.VN_COMPUTED_SHADE;
            parametre.scale = 2;
//            parametre.texture = false;
            parametre.texture = true;
//            parametre.use_buffer = false;
            parametre.use_buffer = true;
            mg.setParametre(parametre);
            Image bi;
//            File outputfile;
            Vecteur light;
            
            GUI gui;
            
            light = new Vecteur(0, 0, -1);
            mg.setLight(light);
            bi = mg.draw();
            gui = new GUI();
            gui.paint(bi);
            bi = mg.drawZBuffer();
            gui = new GUI();
            gui.paint(bi);
            light = new Vecteur(1, 0, 0);
            mg.setLight(light);
            bi = mg.draw();
            gui = new GUI();
            gui.paint(bi);
            
                        
            File outputfile ;

//            for (int i = -1; i <= 1; i++) {
//                for (int j = -1; j <= 1; j++) {
//                    for (int k = -1; k <= 1; k++) {
//                        light = new Vecteur(i, j, k);
//                        System.out.println("drawing : "+light);
//                        mg.setLight(light);
//                        
//                        bi = mg.draw();
//                        outputfile = new File("saved" + i + "" + j + "" + k + ".png");
//                        ImageIO.write((RenderedImage) bi, "png", outputfile);
//                    }
//                }
//            }

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
