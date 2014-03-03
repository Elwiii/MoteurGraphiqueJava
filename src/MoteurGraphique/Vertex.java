/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.Color;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;

/**
 *
 * @author nikolai
 */
public class Vertex {

    Vecteur vt;
    Vecteur vn;
    Vecteur v;
    Vecteur v_proj;

    public Vertex(Vecteur v, Vecteur vt, Vecteur vn) {
        this.v = v;
        this.vt = vt;
        this.vn = vn;
    }

    public Vertex() {

    }

    public static Vertex interpolationPlanImage(Vertex v1, Vertex v2, double alpha) {
        assert(alpha <=1 && alpha >=0):"alpha incorrect : "+alpha;
        Vertex res = new Vertex();
        res.v = new Vecteur();
        res.v_proj = Vecteur.interpolationXYZEntiere(v1.v_proj, v2.v_proj, alpha);
        res.vt = Vecteur.interpolationXY(v1.vt, v2.vt, alpha); // la coordonnée Z est égale à zéro anyway
        res.vn = Vecteur.interpolationXYZ(v1.vn, v2.vn, alpha);
        return res;
    }

    @Override
    public String toString() {
        return "" + v_proj/*+"\nvt : "+vt+"\nvn : "+vn+"\n"*/;
    }

    public void draw(BufferedImage image, Model model, Parameter parameters, Face face, MoteurGraphique mg) {
        int red, green, blue;
        // texture
//        if (parameters.texture) {
//            BufferedImage bi = model.getImageDiffuse();
//            int x_diffu = (int) Math.round(vt.x * (double) bi.getWidth());
//            int y_diffu = (int) Math.round((double) bi.getHeight() - vt.y * (double) bi.getHeight());
//            // TODO SPA NORMAL CA BORDEL DE MERDE
//            if (y_diffu == 0 || y_diffu == 1023) {
//                System.out.println("y_diffu : " + y_diffu);
//            }
//            int rgb = bi.getRGB(x_diffu, y_diffu);
//            red = (rgb >> 16) & 0xFF;
//            green = (rgb >> 8) & 0xFF;
//            blue = rgb & 0xFF;
//        } else {
//            red = 255;
//            green = 0;
//            blue = 255;
//        } // TODO decommenter ça si ça bug remmetre le cast en int dans projectionEnZ
        red = 255;
        green = 0;
        blue = 255;
        // normal mapping
        
        Vecteur vecteur_normal = null;
        switch (parameters.shadow) {
            case Parameter.NO_SHADE:
                // nothing
                break;
            case Parameter.VN_COMPUTED_SHADE:
                Vecteur vectorTriangle1 = Vecteur.createVector3DFromPoint(face.v1.v_proj, face.v2.v_proj);
                Vecteur vectorTriangle2 = Vecteur.createVector3DFromPoint(face.v3.v_proj, face.v2.v_proj);
                Vecteur n = Vecteur.cross(vectorTriangle1, vectorTriangle2);
                vecteur_normal = n;
                break;
            case Parameter.GOURAUD_SHADE:
                // todo besoin de alpha beta et gamma
                break;
            case Parameter.PHONG_SHADE:
                vecteur_normal = vn;
                break;
            case Parameter.NORMAL_MAPPING_SHADE:
                break;
        }
        double eclairage = 1;
        if (parameters.shadow != Parameter.NO_SHADE) {
            vecteur_normal.normalise();
            // cosnl est la valeur du cosinus lors du produit scalaire de la normal par la lumière 
            // pour avoir de la lumière ce cosinus doit être négatif
            // les deux vecteurs doivent être normalisé
            double cosnl = Vecteur.produitScalaire(vecteur_normal, mg.getLight());
            if(cosnl <0){
                eclairage = - cosnl;
            }else{
                // la face est caché, il n'y a pas de lumière
                eclairage = 0;
            }
        }
//        System.out.println("coeff : "+coeffGris);
//        System.out.println("red : "+red+" green : "+green+" blue : "+blue);
        red = (int) ((double) red * (double) eclairage);
        green = (int) ((double) green * (double) eclairage);
        blue = (int) ((double) blue * (double) eclairage);
//System.out.println("red : "+red+" green : "+green+" blue : "+blue);
        // on dessine le point si il n'y a pas plus proche dans le buffer
        Color c = new Color(red, green, blue);
//        System.out.println(""+c.toString());
        if (parameters.use_buffer) {

            if (v_proj.z > model.zbufferAt((int) v_proj.x, (int) v_proj.y)) {
                model.majZBuffer((int) v_proj.x, (int) v_proj.y, v_proj.z);
                image.setRGB((int) v_proj.x, model.hight - 1 - (int) v_proj.y, c.getRGB());
            } else {
//                System.out.println("dawn it");
            }
        } else {
            image.setRGB((int) v_proj.x, model.hight - 1 - (int) v_proj.y, c.getRGB());

        }
    }

    public void drawWhite(BufferedImage image, Model model, Parameter parameters) {
        Color c = new Color(255, 255, 255);
        image.setRGB((int) v_proj.x, model.hight - 1 - (int) v_proj.y, c.getRGB());
    }

    @Override
    public Vertex clone() {
        Vertex res = new Vertex(v.clone(), vt.clone(), vn.clone());
        res.v_proj = v_proj.clone();
        return res;
    }

}
