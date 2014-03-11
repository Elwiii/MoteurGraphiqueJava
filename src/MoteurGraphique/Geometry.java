/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import Jama.Matrix;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author Nikolai
 */
public class Geometry {
    
    public static Matrix createIdentity(){
        double[] l1 = {1, 0, 0, 0};
        double[] l2 = {0, 1, 0, 0};
        double[] l3 = {0, 0, 1, 0};
        double[] l4 = {0, 0, 0, 1};
        double[][] m = {l1, l2, l3, l4};
        return new Matrix(m, 4, 4);
    }

    public static Matrix createRotateXAxeMatrix(double teta) {
        double[] l1 = {1, 0, 0, 0};
        double[] l2 = {0, cos(teta), -sin(teta), 0};
        double[] l3 = {0, sin(teta), cos(teta), 0};
        double[] l4 = {0, 0, 0, 1};
        double[][] m = {l1, l2, l3, l4};
        return new Matrix(m, 4, 4);
    }
     public static Matrix createRotateYAxeMatrix(double teta) {
        double[] l1 = {cos(teta), 0, sin(teta), 0};
        double[] l2 = {0, 1, 0, 0};
        double[] l3 = {-sin(teta),0, cos(teta), 0};
        double[] l4 = {0, 0, 0, 1};
        double[][] m = {l1, l2, l3, l4};
        return new Matrix(m, 4, 4);
    }
     
     public static Matrix createCentralProjectionZMatrix(double zc) {
        assert(zc !=0.):"zc ne peut être égal à zéro";
        double[] l1 = {1,0,0,0};
        double[] l2 = {0, 1, 0, 0};
        double[] l3 = {0,0, 1, 0}; // on  garde la profondeur intacte pour le ZBuffer
        double[] l4 = {0, 0, -1./zc, 1};
        double[][] m = {l1, l2, l3, l4};
        return new Matrix(m, 4, 4);
    }
     
//     public static Matrix createRotationMatrix(double teta){
//         
//     }
    
}
