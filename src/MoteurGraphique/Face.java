/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.image.BufferedImage;

/**
 *
 * @author nikolai
 */
public class Face {

    Vertex v1;
    Vertex v2;
    Vertex v3;

//    private int longueur;
//    private int hauteur;
//    private boolean steep;
    public Face(Vertex v1, Vertex v2, Vertex v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    /**
     * swap les vextexs afin que v1 correspond au point 2d le plus haut, v2
     * celui du milieu et v3 le plus bas
     *
     */
    private void swapEnHauteur() {
        Vertex vtemp;
        if (v2.v_proj.y > v1.v_proj.y) {
            vtemp = v1;
            v1 = v2;
            v2 = vtemp;
        }
        if (v3.v_proj.y > v1.v_proj.y) {
            vtemp = v1;
            v1 = v3;
            v3 = vtemp;
        }
        if (v3.v_proj.y > v2.v_proj.y) {
            vtemp = v3;
            v3 = v2;
            v2 = vtemp;
        }
    }

    public void draw(BufferedImage image, Model model, Parameter parameter, MoteurGraphique mg) {
        swapEnHauteur(); //todo on peut faire le swap une fois pour toute dès qu'on a compute les v_proj
        // on récupère la hauteur du triangle
        int h = (int) (v1.v_proj.y - v3.v_proj.y);
        h = h == 0 ? h = 1 : h; // si c'est une ligne ou un point
        int htop = (int) (v1.v_proj.y - v2.v_proj.y);
        int hbot = (int) (v2.v_proj.y - v3.v_proj.y);
        // on dessine une ligne par pixel en parcourant la hauteur
        for (int i = 0; i <= h; i++) {
            // on dessine le top ou le bottom du triangle ?
            boolean bottom = i > htop;
            // position relative des pointeurs p1 et p2 sur leur droite respective
            double alpha = (double) i / (double) (h);
            double beta = bottom ? (hbot == 0 ? 0 : (i - htop) / (double) (hbot)) : (htop == 0 ? 0 : i / (double) (htop));
            Vertex p1 = Vertex.interpolationPlanImage(v1, v3, alpha);
            Vertex p2 = Vertex.interpolationPlanImage((bottom ? v2 : v1), (bottom ? v3 : v2), beta);
            // p2 doit être le point le plus à droite
            if (p1.v_proj.x > p2.v_proj.x) {
                Vertex vtemp = p1;
                p1 = p2;
                p2 = vtemp;
            }

            // rendu de la ligne
            if (parameter.rendu == Parameter.FIL_DE_FER || parameter.rendu == Parameter.FIL_DE_FER_ET_PLAIN) {
                p1.drawWhite(image, model, parameter);
                p2.drawWhite(image, model, parameter);
            }else{
                p1.draw(image, model, parameter, this, mg);
                p2.draw(image, model, parameter, this, mg);
            }
            if (parameter.rendu == Parameter.PLAIN || parameter.rendu == Parameter.FIL_DE_FER_ET_PLAIN) {
                int l = (int) p2.v_proj.x - (int) p1.v_proj.x;
                l = (l < 1) ? l = 0 : l;
                if (l != 0 && l != 1) {
                    // on dessine la ligne qui va de p1 exclu à p2 exclu
                    for (int j = 1; j < l; j++) {
                        double gamma = (double) j / (double) (l);
                        Vertex p3 = Vertex.interpolationPlanImage(p1, p2, gamma);
                        p3.draw(image, model, parameter, this, mg);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "[" + v1 + ", " + v2 + ", " + v3 + "]";
    }

    @Override
    public Face clone() {
        return new Face(v1.clone(), v2.clone(), v3.clone());
    }

}