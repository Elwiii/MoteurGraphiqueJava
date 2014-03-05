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
    double eclairage_gouraud;

    public Vertex(Vecteur v, Vecteur vt, Vecteur vn) {
        this.v = v;
        this.vt = vt;
        this.vn = vn;
    }

    public Vertex() {

    }

    public void initEclairageGouraud(Vecteur light) {
        eclairage_gouraud = computeEclairage(vn, light);
    }

    public static Vertex interpolationPlanImage(Vertex v1, Vertex v2, double alpha) {
        assert (alpha <= 1 && alpha >= 0) : "alpha incorrect : " + alpha;
        Vertex res = new Vertex();
        res.v = new Vecteur();
//        res.v_proj = Vecteur.interpolationXYZEntiere(v1.v_proj, v2.v_proj, alpha);
        res.v_proj = Vecteur.interpolationXYZEntiere(v1.v_proj, v2.v_proj, alpha);
        res.vt = Vecteur.interpolationXY(v1.vt, v2.vt, alpha); // la coordonnée Z est égale à zéro anyway
        res.vn = Vecteur.interpolationXYZ(v1.vn, v2.vn, alpha);
        res.eclairage_gouraud = (1. - alpha) * v1.eclairage_gouraud + alpha * v2.eclairage_gouraud;
        return res;
    }

    /**
     * les vecteurs doivent être normalisé
     *
     * @param vecteur_normal
     * @param light
     * @return
     */
    private double computeEclairage(Vecteur vecteur_normal, Vecteur light) {
        assert (vecteur_normal.getNorme() - 1.0 < 0.01 && light.getNorme() - 1.0 < 0.01) : "les vecteurs ne sont pas normalisé : n =" + vecteur_normal.getNorme() + " l =" + light.getNorme();
        double eclairage;
        // cosnl est la valeur du cosinus lors du produit scalaire de la normal par la lumière 
        // pour avoir de la lumière ce cosinus doit être négatif
        // les deux vecteurs doivent être normalisé
        double cosnl = Vecteur.produitScalaire(vecteur_normal, light);
        if (cosnl <= 0) {
            eclairage = -cosnl;
        } else {
            // la face est caché, il n'y a pas de lumière
            eclairage = 0;// si on met cosnl ça debug VN_COMPUTED + BUFFER mais c'est un hack, le problème ne vient pas de là
        }
        return eclairage;
    }
    
    private boolean facetteVisible(Vecteur vecteur_normal,Vecteur camera){
        boolean visible ;
//        System.out.println(""+vecteur_normal);
        assert (vecteur_normal.getNorme() - 1.0 < 0.01 &&  camera.getNorme() - 1.0 < 0.01) : "les vecteurs ne sont pas normalisé : n =" + vecteur_normal.getNorme() + " c =" + camera.getNorme();
        double cosnc = Vecteur.produitScalaire(vecteur_normal, camera);
        visible = (cosnc <= 0);
        return visible;
    }

    @Override
    public String toString() {
        return "" + v_proj/*+"\nvt : "+vt+"\nvn : "+vn+"\n"*/;
    }

    public void draw(BufferedImage image, Model model, Parameter parameters, Face face, MoteurGraphique mg) {
        
        
        int red, green, blue;
        // texture
        if (parameters.texture) {
            BufferedImage bi = model.getImageDiffuse();
            int x_diffu = (int) Math.round(vt.x * (double) bi.getWidth());
            int y_diffu = (int) Math.round((double) bi.getHeight() - vt.y * (double) bi.getHeight());
            int rgb = bi.getRGB(x_diffu, y_diffu);
            red = (rgb >> 16) & 0xFF;
            green = (rgb >> 8) & 0xFF;
            blue = rgb & 0xFF;
        } else {
            red = 255;
            green = 0;
            blue = 255;
        }

        // normal mapping
        Vecteur vecteur_normal = null;
        Vecteur vecteur_normal_visibility_nm = null;
        double eclairage = 1;
        switch (parameters.shadow) {
            case Parameter.NO_SHADE:
                if (parameters.use_buffer) {
                    //nothing to do
                }else{
                    // on doit quand même avoir la normale pour cacher les face sencées ne pas être visibles
                    vecteur_normal =face.vecteur_normal;
                    vecteur_normal.normalise();
                }
                break;
            case Parameter.VN_COMPUTED_SHADE:
                vecteur_normal = face.vecteur_normal;
                vecteur_normal.normalise();
                break;
            case Parameter.GOURAUD_SHADE:
                eclairage = eclairage_gouraud;
                if(!parameters.use_buffer){
                    vecteur_normal =face.vecteur_normal;
                    vecteur_normal.normalise();
                }
                break;
            case Parameter.PHONG_SHADE:
                vecteur_normal = vn;
                vecteur_normal.normalise();
                break;
            case Parameter.NORMAL_MAPPING_SHADE:
                 //(blue (z) coordinate is perspective (deepness) coordinate and RG-xy flat coordinates on screen)
                BufferedImage bi = model.getImageNormal();
                int x_diffu = (int) Math.round(vt.x * (double) bi.getWidth());
                int y_diffu = (int) Math.round((double) bi.getHeight() - vt.y * (double) bi.getHeight());
                int rgb = bi.getRGB(x_diffu, y_diffu);
                int x_normal = (rgb >> 16) & 0xFF; //red
                int y_normal = (rgb >> 8) & 0xFF; // green
                int z_normal = rgb & 0xFF; // blue
                vecteur_normal = new Vecteur(x_normal,y_normal,z_normal);
//                System.out.println(""+vecteur_normal);
                vecteur_normal.normalise();
                //TODO IMPORTANT  applique ici la eyeviewinverse
                
                //besoin de ce hack si ya pas le zbuffer car tout les vecteurs normaux ont des composantes positives
                // on a donc bien un dégradé d'eclairage mais ça ne nous permet pas de déterminer si la facette est visible ou pas par produit scalaire
                if(!parameters.use_buffer){
                    vecteur_normal_visibility_nm =face.vecteur_normal;
                    vecteur_normal_visibility_nm.normalise();
                }
                break;
        }

        if (parameters.shadow != Parameter.NO_SHADE  && parameters.shadow != Parameter.GOURAUD_SHADE) {
            eclairage = computeEclairage(vecteur_normal, mg.getLight());
        }

        assert (eclairage >= 0 && eclairage <= 1) : "eclairage incorrect : " + eclairage;
        
        red = (int) ((double) red * (double) eclairage);
        green = (int) ((double) green * (double) eclairage);
        blue = (int) ((double) blue * (double) eclairage);
        // on dessine le point si il n'y a pas plus proche dans le buffer
        Color c = new Color(red, green, blue);
        //TODO OPTIMISATION , no need de faire tout le bordel du dessus donc faire le test avant. 
        if (parameters.use_buffer) {
            if (v_proj.z > model.zbufferAt((int) v_proj.x, (int) v_proj.y)) {
                model.majZBuffer((int) v_proj.x, (int) v_proj.y, v_proj.z);
//                System.out.println("ok");
                image.setRGB((int) v_proj.x, model.hight - 1 - (int) v_proj.y, c.getRGB());
            }
        } else {
            Vecteur v = parameters.shadow == Parameter.NORMAL_MAPPING_SHADE ? vecteur_normal_visibility_nm : vecteur_normal;
            if(facetteVisible(v, mg.getCamera())){
                image.setRGB((int) v_proj.x, model.hight - 1 - (int) v_proj.y, c.getRGB());
            }
        }
    }

    public void drawWhite(BufferedImage image, Model model, Parameter parameters) {
        Color c = new Color(255, 255, 255);
        image.setRGB((int) v_proj.x, model.hight - 1 - (int) v_proj.y, c.getRGB());
    }
    
    public void drawBlack(BufferedImage image, Model model, Parameter parameters) {
        Color c = new Color(0,0,0);
        image.setRGB((int) v_proj.x, model.hight - 1 - (int) v_proj.y, c.getRGB());
    }

    @Override
    public Vertex clone() {
        Vertex res = new Vertex(v.clone(), vt.clone(), vn.clone());
        res.v_proj = v_proj.clone();
        return res;
    }

}
