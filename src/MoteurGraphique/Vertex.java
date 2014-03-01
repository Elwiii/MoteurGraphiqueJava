/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author nikolai
 */
public class Vertex implements Drawable {

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

    public static Vertex interpolation(Vertex v1, Vertex v2, double alpha) {
        Vertex res = new Vertex();
        res.v = new Vecteur();
        res.v_proj = Vecteur.interpolationXY(v1.v_proj, v2.v_proj, alpha);
        res.vt = Vecteur.interpolationXY(v1.vt, v2.vt, alpha); // la coordonnée Z est égale à zéro anyway
        res.vn = Vecteur.interpolationXYZ(v1.vn, v2.vn, alpha);
        return res;
    }

    @Override
    public String toString() {
        return "" + v_proj/*+"\nvt : "+vt+"\nvn : "+vn+"\n"*/;
    }

    @Override
    public void draw(BufferedImage image, Model model, Parameter parameters) {
        Color myWhite = new Color(255, 0, 255);
//        System.out.println("x : "+v_proj.x);
//        System.out.println("y : "+v_proj.y);
//        System.out.println("model.w "+model.width);
//        System.out.println("model.h "+model.hight);
        image.setRGB((int) v_proj.x, model.hight - 1 - (int) v_proj.y /* (int) v_proj.y */, myWhite.getRGB());
    }

    @Override
    public Vertex clone() {
//        System.out.println(""+v);
        Vertex res = new Vertex(v.clone(), vt.clone(), vn.clone());
        res.v_proj = v_proj.clone();
        return res;
    }

}
