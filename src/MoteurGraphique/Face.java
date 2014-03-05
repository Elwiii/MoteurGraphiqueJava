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
    Vecteur vecteur_normal;

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
        assert (v1.v_proj.y >= v2.v_proj.y && v2.v_proj.y >= v3.v_proj.y) : "la fonction swapHauteur est à revoir";
    }

    public void draw(BufferedImage image, Model model, Parameter parameter, MoteurGraphique mg) {
        if (parameter.shadow == Parameter.VN_COMPUTED_SHADE || !parameter.use_buffer) {
            Vecteur vectorTriangle1 = Vecteur.createVector3DFromPoint(v2.v_proj, v1.v_proj);
            Vecteur vectorTriangle2 = Vecteur.createVector3DFromPoint(v3.v_proj, v2.v_proj);
            vecteur_normal = Vecteur.cross(vectorTriangle1, vectorTriangle2);
        }

        swapEnHauteur();

        // si jamais c'est un eclairage de gouraud , on doit récupérer l'éclairage des 3 vertexs de la facette
        if (parameter.shadow == Parameter.GOURAUD_SHADE) {
            v1.initEclairageGouraud(mg.getLight());
            v2.initEclairageGouraud(mg.getLight());
            v3.initEclairageGouraud(mg.getLight());
        }

        // on récupère la hauteur du triangle
        int h = (int) (v1.v_proj.y - v3.v_proj.y);
//        h = h == 0 ? h = 1 : h; // si c'est une ligne ou un point
        int htop = (int) (v1.v_proj.y - v2.v_proj.y);
        int hbot = (int) (v2.v_proj.y - v3.v_proj.y);
        // on dessine une ligne par pixel en parcourant la hauteur
//        System.out.println("h : "+h);
        for (int i = 0; i <=/* sans egale ça donne pas d'erreur d'assert mais les triangles de testA et testB ne sont pas complet */ h; i++) {
            // on dessine le top ou le bottom du triangle ?
            boolean bottom = i >= htop;
            int seg_height = bottom ? hbot : htop;
            // position relative des pointeurs p1 et p2 sur leur droite respective
            double alpha = (double) (i) / (double) (h);
            double beta = (seg_height != 0) ? (double) (i - (bottom ? htop : 0)) / (seg_height) : 1;
            //            System.out.println("beta : " + beta + "   alpha : " + alpha +  " (  bottom = " + bottom + " / h-1 : " + (h - 1) + " / hbot = " + hbot + " / htop = " + htop + " / i = " + i + ")");
            assert ((alpha <= 1 && alpha >= 0) && (beta <= 1 && beta >= 0)) : "\n\nbeta : " + beta + "   alpha : " + alpha + " (  bottom = " + bottom + " / h-1 : " + (h - 1) + " / hbot = " + hbot + " / htop = " + htop + " / i = " + i + ")\n\n";
            System.out.println("beta : " + beta + "   alpha : " + alpha + " (  bottom = " + bottom + " / h-1 : " + (h - 1) + " / hbot = " + hbot + " / htop = " + htop + " / i = " + i + ")");
            // on récupère p1 et p2
            Vertex p1 = Vertex.interpolationPlanImage(v1, v3, alpha);
            Vertex p2 = Vertex.interpolationPlanImage((bottom ? v2 : v1), (bottom ? v3 : v2), beta);

            // p2 doit être le point le plus à droite
            if (p1.v_proj.x > p2.v_proj.x) {
                Vertex vtemp = p1;
                p1 = p2;
                p2 = vtemp;
                assert (p1.v_proj.x <= p2.v_proj.x) : "swap incorrect";
            }
//            System.out.println("p1 : "+p1);
//            System.out.println("p2 : "+p2);
//            assert(Math.abs(p1.v_proj.y-p2.v_proj.y) < 0.01):this+"les pointeurs p1 et p2 ne sont pas sur la même ligne"+"\n\nbeta : " + beta + "   alpha : " + alpha +  " (  bottom = " + bottom + " / h-1 : " + (h - 1) + " / hbot = " + hbot + " / htop = " + htop + " / i = " + i + ")\n\n";;

            // rendu de la ligne
            if (parameter.rendu == Parameter.FIL_DE_FER || parameter.rendu == Parameter.FIL_DE_FER_ET_PLAIN) {
                p1.drawWhite(image, model, parameter);
                p2.drawWhite(image, model, parameter);
            } else {
                p1.draw(image, model, parameter, this, mg);
                p2.draw(image, model, parameter, this, mg);
//                p1.drawBlack(image, model, parameter);
//                p2.drawBlack(image, model, parameter);
            }
            if (parameter.rendu == Parameter.PLAIN || parameter.rendu == Parameter.FIL_DE_FER_ET_PLAIN) {
                double l = (double) p2.v_proj.x - (double) p1.v_proj.x;
                // on dessine la ligne qui va de p1 exclu à p2 exclu
                for (int j = 1; j < l; j++) {
                    double gamma = (double) j / l;
                    assert ((alpha <= 1 && alpha >= 0) && (beta <= 1 && beta >= 0) || (gamma <= 1 && gamma >= 0)) : "\n\nbeta : " + beta + "   alpha : " + alpha + " (  bottom = " + bottom + " / h-1 : " + (h - 1) + " / hbot = " + hbot + " / htop = " + htop + " / i = " + i + ")\n\n";
                    Vertex p3 = Vertex.interpolationPlanImage(p1, p2, gamma);
//                    System.out.println(""+p3.v_proj.x+" "+p3.v_proj.y);
                    p3.draw(image, model, parameter, this, mg);
//                    p3.drawBlack(image, model, parameter);
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
