/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

/**
 *
 * @author nikolai
 */
public class Vecteur {

    double x;
    double y;
    double z;

    public Vecteur(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vecteur() {

    }

    public static Vecteur interpolationXYZ(Vecteur v1, Vecteur v2, double alpha) {
        // (D) = v1 * (1-t) + v2 * t
        Vecteur res = new Vecteur();
        res.x = (double)(1 - alpha) * (double)v1.x + (double)alpha * (double)v2.x;
        res.y = (double)(1 - alpha) * (double)v1.y + (double)alpha * (double)v2.y;
        res.z = (double)(1 - alpha) * (double)v1.z + (double)alpha * (double)v2.z;
//        System.out.println("alpha : "+alpha);
//        System.out.println("v1 : "+v1);
//        System.out.println("v2 : "+v2);
//        System.out.println("res : "+res);
        return res;//todo
    }

    public static Vecteur interpolationXY(Vecteur v1, Vecteur v2, double alpha) {
        // (D) = v1 * (1-t) + v2 * t
        Vecteur res = new Vecteur();
        res.x = (double)(1 - alpha) * (double)v1.x + (double)alpha * (double)v2.x;
        res.y = (double)(1 - alpha) * (double)v1.y + (double)alpha * (double)v2.y;
        return res;
    }

    public void normalise() {
        double denominateur = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        x /= denominateur;
        y /= denominateur;
        z /= denominateur;
    }

    public double getNorme() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public static Vecteur cross(Vecteur v1, Vecteur v2) {
        Vecteur res = new Vecteur();
        res.x = (v1.y * v2.z - v1.z * v2.y);
        res.y = (v1.z * v2.x - v1.x * v2.z);
        res.z = (v1.x * v2.y - v2.x * v1.y);
        return res;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
    
    public Vecteur clone(){
        Vecteur v = new Vecteur(x,y,z);
        return v;
    }
}
