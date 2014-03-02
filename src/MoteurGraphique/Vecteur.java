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
        res.x = (double) (1 - alpha) * (double) v1.x + (double) alpha * (double) v2.x;
        res.y = (double) (1 - alpha) * (double) v1.y + (double) alpha * (double) v2.y;
        res.z = (double) (1 - alpha) * (double) v1.z + (double) alpha * (double) v2.z;
        return res;//todo
    }

    public static Vecteur interpolationXY(Vecteur v1, Vecteur v2, double alpha) {
        // (D) = v1 * (1-t) + v2 * t
        Vecteur res = new Vecteur();
        res.x = (double) (1 - (double)alpha) * (double) v1.x + (double) alpha * (double) v2.x;
        res.y = (double) (1 - (double)alpha) * (double) v1.y + (double) alpha * (double) v2.y;
        return res;
    }

    public static Vecteur interpolationXYZEntiere(Vecteur v1, Vecteur v2, double alpha) {
        // (D) = v1 * (1-t) + v2 * t
        Vecteur res = new Vecteur();
        res.x = Math.round((double) (1 - alpha) * (double) v1.x + (double) alpha * (double) v2.x);
        res.y = Math.round((double) (1 - alpha) * (double) v1.y + (double) alpha * (double) v2.y);
        res.z = Math.round((double) (1 - alpha) * (double) v1.y + (double) alpha * (double) v2.y);
        return res;
    }

    public void normalise() {
        double denominateur = Math.sqrt(Math.pow(x, 2.) + Math.pow(y, 2.) + Math.pow(z, 2.));
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

    public static Vecteur createVector3DFromPoint(Vecteur v1, Vecteur v2) {
        Vecteur res = new Vecteur();
        res.x = (v1.x - v2.x);
        res.y = (v1.y - v2.y);
        res.z = (v1.z - v2.z);
        return res;
    }

    public static double produitScalaire(Vecteur v1, Vecteur v2) {
        double res;
        res = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
        return res;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    public Vecteur clone() {
        Vecteur v = new Vecteur(x, y, z);
        return v;
    }
}
