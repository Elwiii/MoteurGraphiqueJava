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
public class Parameter {
    /* ombres */

    public static final int NO_SHADE = 0;
    // calcul de l'angle entre la lumière et le vn de la facette
    public static final int VN_COMPUTED_SHADE = 1;
    // vn sommet par sommet du fichier obj
    public static final int GOURAUD_SHADE = 2;
    // vn point par point grace à interpolation des vn du fichier obj
    public static final int PHONG_SHADE = 3;
    // normal mapping
    public static final int NORMAL_MAPPING_SHADE = 4;
    
    /* rendu */
    public static final int FIL_DE_FER = 0;
    public static final int PLAIN = 1;
    public static final int FIL_DE_FER_ET_PLAIN = 2;

    int rendu;
    double scale;
    int shadow;
    boolean texture;
    boolean use_buffer;

    public Parameter() {

    }

    //todo factory
}
