/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author nikolai
 */
public class MoteurGraphique {
//    private Image image;

    private Vecteur light;

    private Vecteur camera;

    private final Model model;

    private int width;

    private int hight;

    private int zmax;

    private int zmin;

    private Parameter parametre;

    // TODO le transformer en singleton
    public MoteurGraphique(Model model) throws IOException {
        this.model = model;
        model.parse();
    }

    public Image draw() throws IOException {
        projectionEnZ();
        
        BufferedImage image = new BufferedImage(width, hight, BufferedImage.TYPE_INT_RGB);
//        System.out.println("vertex : "+model.getVertexs());
        for (Face face : model.getFaces()) {
//            System.out.println("face : "+face);
            face.draw(image, model, parametre);
        }
        return image;
    }

    /**
     * fais une projection orthogonale en Z du modèle (remplit simplement les v_proj de chaque vertex)
     */
    private void projectionEnZ() {
        double maxX = 0;
        double minX = 0;
        double maxY = 0;
        double minY = 0;
        double maxZ = 0;
        double minZ = 0;
        int largeurRepere2d;
        int hauteurRepere2d;
        List<Vertex> vertexs = model.getVertexs();
        double x, y, z;
        for (int i = 0; i < vertexs.size(); i++) {
            x = vertexs.get(i).v.x;
            y = vertexs.get(i).v.y;
            z = vertexs.get(i).v.z;
            maxX = maxX < x ? x : maxX;
            maxY = maxY < y ? y : maxY;
            maxZ = maxZ < z ? z : maxZ;

            minX = minX > x ? x : minX;
            minY = minY > y ? y : minY;
            minZ = minZ > z ? z : minZ;
        }
        largeurRepere2d = (int) (parametre.scale * abs(maxX - minX));
        hauteurRepere2d = (int) (parametre.scale * abs(maxY - minY));
        hight = hauteurRepere2d +1 ;
        width = largeurRepere2d + 1;
        model.width = width;
        model.hight = hight;
        // le point en bas à gauche (minX,minY) de la projection du cube englobant sur le plan xy, son opposé représente le vecteur de tranlation
        double[] min = new double[2];
        min[0] = (-minX);
        min[1] = (-minY);
        int maxprofondeur = Integer.MIN_VALUE;
        int minprofondeur = Integer.MAX_VALUE;
        for (int i = 0; i < vertexs.size(); i++) {
            Vertex point = vertexs.get(i);
            double x_proj;
            double y_proj;
            // translation 
            x_proj = point.v.x + min[0];
            y_proj = point.v.y + min[1];
            // homothétie
            double positionRelativeX = abs(x_proj) / ((double) (abs(maxX - minX)));
            double positionRelativeY = abs(y_proj) / ((double) (abs(maxY - minY)));
            x_proj = largeurRepere2d * positionRelativeX;
            y_proj = hauteurRepere2d * positionRelativeY;
            // on a besoin de la profondeur pour zbuffer
            double z_proj = point.v.z;
            maxprofondeur = (int) (z_proj > maxprofondeur ? z_proj : maxprofondeur);
            minprofondeur = (int) (z_proj < minprofondeur ? z_proj : minprofondeur);
            Vecteur pv_proj = new Vecteur();

            pv_proj.x = (int)x_proj;
            pv_proj.y = (int)y_proj;
            pv_proj.z = (int)z_proj;
            point.v_proj = pv_proj;
        }
        zmax = maxprofondeur;
        zmin = minprofondeur;

    }

    /**
     * @return the light
     */
    public Vecteur getLight() {
        return light;
    }

    /**
     * @param light the light to set
     */
    public void setLight(Vecteur light) {
        this.light = light;
    }

    /**
     * @return the camera
     */
    public Vecteur getCamera() {
        return camera;
    }

    /**
     * @param camera the camera to set
     */
    public void setCamera(Vecteur camera) {
        this.camera = camera;
    }

    /**
     * @return the parametre
     */
    public Parameter getParametre() {
        return parametre;
    }

    /**
     * @param parametre the parametre to set
     */
    public void setParametre(Parameter parametre) {
        this.parametre = parametre;
    }
}
