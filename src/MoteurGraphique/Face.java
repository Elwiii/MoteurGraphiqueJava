/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 *
 * @author nikolai
 */
public class Face implements Drawable {

    Vertex v1;
    Vertex v2;
    Vertex v3;

    private int longueur;
    private int hauteur;
    private boolean steep;

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

    // todo à optimiser, le step fait qu'on draw souvent les mêmes pixels.
    @Override
    public void draw(BufferedImage image, Model model, Parameter parameter) {
        swapEnHauteur(); //todo on peut faire le swap une fois pour toute dès qu'on a compute les v_proj
        // on récupère la hauteur du triangle
        int h = (int) (v1.v_proj.y - v3.v_proj.y);
        int htop = (int) (v1.v_proj.y - v2.v_proj.y);
        int hbot = (int) (v2.v_proj.y - v3.v_proj.y);
        int ltop = (int) abs(v1.v_proj.x - v2.v_proj.x);
        int lbot = (int) abs(v3.v_proj.x - v2.v_proj.x);
        // on dessine une ligne par pixel en parcourant la hauteur
        // on descend pas la hauteur de 1 en 1 mais de step en step car 
        // le triangle peut etre plus large que haut et ça implique des trous
        double stepTop = (double) min(htop, ltop) / (double) max(htop, ltop);
        double stepBottom = (double) min(hbot, lbot) / (double) max(hbot, lbot);
//        System.out.println("min(htop, ltop) :" + min(htop, ltop));
//        System.out.println("max(htop, ltop) : " + max(htop, ltop));
//        System.out.println("stepTop : " + stepTop);
        double step = Double.NaN;
        double i = 0;
        System.out.println("this : " + this);
        step = 10;
        int itemp = (int) i;
        System.out.println("h-1 : "+(h-1));
        System.out.println("htop : "+htop);
        System.out.println("hbot : "+hbot);
        while (i <= /*27 * step*/ h - 1) {
            // on dessine le top ou le bottom du triangle ?
            boolean bottom = i >= htop-1;
            // thales
            System.out.println("i : "+i);
            double alpha = i / (double) (h - 1);
            double beta = bottom ? (hbot == 0 ? 0 : (i - htop+1) / (double) (hbot)) : (htop == 0 ? 0 : i / (double) (htop));
            System.out.println("beta : "+beta);
//            System.out.println("alpha : "+alpha);
            Vertex p1 = Vertex.interpolation(v1, v3, alpha);
            Vertex p2 = Vertex.interpolation((bottom ? v3 : v1), v2, beta);
            // p2 doit être le point le plus à droite
            if (p1.v_proj.x > p2.v_proj.x) {
                Vertex vtemp = p1;
                p1 = p2;
                p2 = vtemp;
            }
//            System.out.println("p1 : "+p1);
//            System.out.println("p2 : "+p2);
            // on dessine la ligne ou les deux pointeurs
            if (parameter.plain) {
                if (itemp == (int) i) {
                    // si on a pas avancé dans la hauteur, pas besoin de redessiner toute la ligne, juste les extrèmes
                    p1.draw(image, model, parameter);
                    p2.draw(image, model, parameter);
                } else {
                    itemp++;
                    int l = (int) (p2.v_proj.x - p1.v_proj.x);
                    if (l == 0 || l == 1) {
                        p1.draw(image, model, parameter);
                    } else {
                        for (int j = 0; j < l; j++) {
                            double gamma = (double) j / (double) (l - 1);
                            Vertex p3 = Vertex.interpolation(p1, p2, gamma);
                            p3.draw(image, model, parameter);
                        }
                    }
                }
            } else {
                p1.draw(image, model, parameter);
                p2.draw(image, model, parameter);
            }
//            System.out.println("step : "+step);
            step = bottom ? stepBottom /*/ 1.1 */ : stepTop/* / 1.1*/; // TODO on ne devrait pas diviser par 1.1  mais sans ça il manque des étapes, erreure d'arrondi ?
//            i++;//
            i += step;
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

/**
 * // * swap les vextexs afin que v1 correspond au point 2d le plus haut, v2 //
 * * celui du milieu et v3 le plus bas si le triangle est plus haut que large //
 * * sinon veut que v2 soit à gauche, v1 au milieu et v3 à droite //
 */
//    private void swap() {
////        computeHauteur();
////        computeLongueur();
//
////        steep = hauteur < longueur;
////        if (!steep) {
//            swapEnHauteur();
//    }
////        } else {
////            swapEnLongeur();
////        }
////    }
//    // todo à optimiser, le step fait qu'on draw souvent les mêmes pixels.
//    @Override
//    public void draw(BufferedImage image, Model model, Parameter parameter) {
//        swap(); //todo on peut faire le swap une fois pour toute dès qu'on a compute les v_proj
//        // on récupère la hauteur du triangle
//        int h = (int) (v1.v_proj.y - v3.v_proj.y);
//        int htop = (int) (v1.v_proj.y - v2.v_proj.y);
//        int hbot = (int) (v2.v_proj.y - v3.v_proj.y);
//        int ltop = (int) abs(v1.v_proj.x - v2.v_proj.x);
//        int lbot = (int) abs(v3.v_proj.x - v2.v_proj.x);
//        // on dessine une ligne par pixel en parcourant la hauteur
//        // on descend pas la hauteur de 1 en 1 mais de step en step car 
//        // le triangle peut etre plus large que haut et ça implique des trous
////        double stepTop = min(htop, ltop) / max(htop, ltop);
////        double stepBottom = min(hbot, lbot) / max(hbot, lbot);
////        double step = Double.NaN;
//        double i = 0;
//        System.out.println("this : " + this);
//        while (i <= h - 1) {
//            // on dessine le top ou le bottom du triangle ?
//            boolean bottom = i >= htop;
//            // thales
//            double alpha = i / (double) (h - 1);
//            double beta = bottom ? (hbot == 0 ? 0 : (i - htop) / (double) (hbot)) : (htop == 0 ? 0 : i / (double) (htop));
//            Vertex p1 = Vertex.interpolation(v1, v3, alpha);
//            Vertex p2 = Vertex.interpolation((bottom ? v3 : v1), v2, beta);
//            // p2 doit être le point le plus à droite
//            if (p1.v_proj.x > p2.v_proj.x) {
//                Vertex vtemp = p1;
//                p1 = p2;
//                p2 = vtemp;
//            }
//            // on dessine la ligne ou les deux pointeurs
//            if (parameter.plain) {
//                int l = (int) (p2.v_proj.x - p1.v_proj.x);
//                if (l == 0 || l == 1) {
//                    p1.draw(image, model, parameter);
//                } else {
//                    for (int j = 0; j < l; j++) {
//                        double gamma = (double) j / (double) (l - 1);
//                        Vertex p3 = Vertex.interpolation(p1, p2, gamma);
//                        p3.draw(image, model, parameter);
//                    }
//                }
//            } else {
//                p1.draw(image, model, parameter);
//                p2.draw(image, model, parameter);
//            }
////            step = bottom ? stepBottom /*/ 1.1 */ : stepTop/* / 1.1*/; // TODO on ne devrait pas diviser par 1.1  mais sans ça il manque des étapes, erreure d'arrondi ?
//            i++;//+= step;
//        }
//    }7
//    private void drawEnHauteur(BufferedImage image, Model model, Parameter parameter, int hauteur) {
//        int htop = (int) (v1.v_proj.y - v2.v_proj.y);
//        int hbot = (int) (v2.v_proj.y - v3.v_proj.y);
//        for (int i = 0; i < hauteur; i++) {
//            // on dessine le top ou le bottom du triangle ?
//            boolean bottom = i >= htop;
//            // thales
//            double alpha = i / (double) (hauteur - 1);
//            double beta = bottom ? (hbot == 0 ? 0 : (i - htop) / (double) (hbot)) : (htop == 0 ? 0 : i / (double) (htop));
//            Vertex p1 = Vertex.interpolation(v1, v3, alpha);
//            Vertex p2 = Vertex.interpolation((bottom ? v3 : v1), v2, beta);
//            // p2 doit être le point le plus à droite
//            if (p1.v_proj.x > p2.v_proj.x) {
//                Vertex vtemp = p1;
//                p1 = p2;
//                p2 = vtemp;
//            }
//            // on dessine la ligne ou les deux pointeurs
//            if (parameter.plain) {
//                int l = (int) (p2.v_proj.x - p1.v_proj.x);
//                if (l == 0 || l == 1) {
//                    p1.draw(image, model, parameter);
//                } else {
//                    for (int j = 0; j < l; j++) {
//                        double gamma = (double) j / (double) (l - 1);
//                        Vertex p3 = Vertex.interpolation(p1, p2, gamma);
//                        p3.draw(image, model, parameter);
//                    }
//                }
//            } else {
//                p1.draw(image, model, parameter);
//                p2.draw(image, model, parameter);
//            }
//        }
//    }
//
//    private void drawEnLongueur(BufferedImage image, Model model, Parameter parameter, int longueur) {
//        int ltop = (int) abs(v1.v_proj.x - v2.v_proj.x);
//        int lbot = (int) abs(v3.v_proj.x - v1.v_proj.x);
//        for (int i = 0; i < longueur; i++) {
//            // on dessine le top ou le bottom du triangle ?
//            
//            boolean bottom = i >= ltop;
////            System.out.println("bottom : "+bottom);
//            // thales
//            double alpha = i / (double) (longueur - 1);
//            double beta = bottom ? (lbot == 0 ? 0 : (i - ltop) / (double) (lbot)) : (ltop == 0 ? 0 : i / (double) (ltop));
////            System.out.println("beta : "+beta);
//            Vertex p1 = Vertex.interpolation(v2, v3, alpha);
//            Vertex p2 = Vertex.interpolation( (bottom ? v3 : v2),v1, bottom? 1-beta : beta);
//            // p2 doit être le point le plus en hauteur
//            if (p1.v_proj.y > p2.v_proj.y) {
//                Vertex vtemp = p1;
//                p1 = p2;
//                p2 = vtemp;
//            }
//            // on dessine la ligne ou les deux pointeurs
//            if (parameter.plain) {
//                int l = (int) (p2.v_proj.y - p1.v_proj.y);
//                if (l == 0 || l == 1) {
//                    p1.draw(image, model, parameter);
//                } else {
//                    for (int j = 0; j < l; j++) {
//                        double gamma = (double) j / (double) (l - 1);
//                        Vertex p3 = Vertex.interpolation(p1, p2, gamma);
//                        p3.draw(image, model, parameter);
//                    }
//                }
//            } else {
//                p1.draw(image, model, parameter);
//                p2.draw(image, model, parameter);
//            }
//        }
//    }
//    // todo à optimiser, le step fait qu'on draw souvent les mêmes pixels.
//    @Override
//    public void draw(BufferedImage image, Model model, Parameter parameter) {
//        swapEnHauteur();
//        double position = (double)((v1.v_proj.y - v2.v_proj.y)) / ((double)(v1.v_proj.y - v3.v_proj.y));
//        System.out.println("position : "+position);
//        Vertex vtemp = Vertex.interpolation(v1, v3, position);
//        Vertex vtemp2 = vtemp.clone();
////        Face faceTop = new Face(v1.clone(), v2.clone(), vtemp);
////        System.out.println("faceTop : "+faceTop);
////        drawTriangleBase(image, model, parameter, faceTop);
//        Face faceBot = new Face(v2.clone(), v3.clone(), vtemp2);
//        System.out.println("faceBot : "+faceBot);
//        drawTriangleBase(image, model, parameter, faceBot);
//
//    }
//    private void drawTriangleBase(BufferedImage image, Model model, Parameter parameter, Face face) {
//        
//        face.swap(); //todo on peut faire le swap une fois pour toute dès qu'on a compute les v_proj
//        System.out.println("hauteur : "+face.hauteur);
//        System.out.println("longeur : "+face.longueur);
//        // on dessine le triangle
//        // le triangle est il plus long que haut ?
//        System.out.println("steep : "+face.steep);
//        if (face.steep) {
//            // on remplit en longueur
//            face.drawEnLongueur(image, model, parameter);
//        } else {
//            // on remplit en hauteur
//            face.drawEnHauteur(image, model, parameter);
//        }
//    }
//    private void computeLongueur() {
//        double xmax = max(v1.v_proj.x, max(v2.v_proj.x, v3.v_proj.x));
//        double xmin = min(v1.v_proj.x, min(v2.v_proj.x, v3.v_proj.x));
//        longueur = (int) abs(xmax - xmin);
//    }
//    private void computeHauteur() {
//        double ymax = max(v1.v_proj.y, max(v2.v_proj.y, v3.v_proj.y));
//        double ymin = min(v1.v_proj.y, min(v2.v_proj.y, v3.v_proj.y));
//        hauteur = (int) abs(ymax - ymin);
//    }
//    private void swapEnLongeur(){
//        Vertex vtemp;
//            if (v2.v_proj.x > v1.v_proj.x) {
//                vtemp = v1;
//                v1 = v2;
//                v2 = vtemp;
//            }
//            if (v2.v_proj.x > v3.v_proj.x) {
//                vtemp = v3;
//                v3 = v2;
//                v2 = vtemp;
//            }
//            if (v1.v_proj.x > v3.v_proj.x) {
//                vtemp = v3;
//                v3 = v1;
//                v1 = vtemp;
//            }
//    }
