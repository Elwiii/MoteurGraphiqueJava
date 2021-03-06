/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import Jama.Matrix;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.List;

/**
 *
 * @author nikolai
 */
public class MoteurGraphique {

//    private Image image;
    private Vecteur light;

    private Vecteur camera;

    private Model model;

    private int width;

    private int height;

    private double zmax;

    private double zmin;

    private Parameter parametre;
    
    private Matrix eyeview;
    
    private Matrix eyeviewInverse;

    // TODO le transformer en singleton
    public MoteurGraphique(Model model) throws IOException {
        this.model = model;
        model.parse();
    }

    public Image draw() throws IOException {
//        graphicsPipeline();
        projectionEnZ();
        System.out.println("width : "+width);
        System.out.println("height: "+height);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //TODO REMOVE pour débuguer
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                image.setRGB(x, y, new Color(255, 0, 0).getRGB());
            }
        }
        int i = 0;
        for (Face face : model.getFaces()) {
//            System.out.println("face : "+face);
            face.draw(image, model, parametre, this);
            i++;
        }
        return image;
    
    }
    

    public Image drawZBuffer() throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        double denominateur =  abs(zmax - zmin);
        System.out.println("denominateur : "+denominateur);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = model.zbufferAt(x, y);
                assert(value <= zmax):"zbufferAt(x, y) > zmax  : "+value+" > "+zmax;
//                System.out.println("(abs(zmin) + abs(value)) : "+(abs(zmin) + abs(value)));
                double rate = value == -Double.MAX_VALUE ? 0 : (abs(zmin) + abs(value)) / (double)denominateur;
                int intensite = (int) (rate * 255.);
                assert(rate <=1 && rate >=0):"rate incorrect : "+rate;
                Color c = new Color(intensite,intensite,intensite);
//                System.out.println("c : "+c);
                image.setRGB(x, height-1- y, c.getRGB());
            }

        }
        return image;
    }
    
    /**
     * object coordinate -> world coordoniate
     */
    private Matrix objectToWorldCoordinate(){
        // nothing to do
        return Geometry.createIdentity() ;
    }
    
    /**
     * teta1 représente l'angle necessaire pour tourner la camera sur le plan yz
     * teta2 represente l'angle necessaire pour placer la camera du plan yz sur l'axe z
     * zc correspond à l'éloignement de la camera par rapport au modèle
     * 
     * 1) rotation de degré teta1 autour de l'axe X 
     * 2) rotation de degré teta2 autour de l'axe Y
     * @return 
     */
    private Matrix worldToEyeCoordinate(){
        double teta1 = computeTeta1(camera);
        Matrix rotateX = Geometry.createRotateXAxeMatrix(teta1);
        Matrix cameraXZm = rotateX.times(camera.toMatrix());
        Vecteur cameraXZ = new Vecteur(cameraXZm);
        double teta2 = computeTeta2(cameraXZ);
        Matrix rotateY = Geometry.createRotateYAxeMatrix(teta2);
        return rotateY.times(rotateX.times(objectToWorldCoordinate()));
    }
    
     /**
     * 
     * @return 
     */
    private double computeTeta1(Vecteur v){
        // on doit calculé v1 :rotation de pi/2 du vecteur camera
        Vecteur v1 = new Vecteur(v.y,v.x,v.z);
        // on calcul l'angle entre v1 et k = (0,0,1) et ça nous donne teta1
        v1.normalise();
        double cosv1k = Vecteur.produitScalaire(v1, Vecteur.k);
        return Math.acos(cosv1k);
    }
    
    public double computeTeta2(Vecteur v){
        double cosvk = Vecteur.produitScalaire(v, Vecteur.k);
        return Math.acos(cosvk);
    }

    /**
     * projection centrale de centre zc 
     * @return 
     */
    private Matrix eyeToClipCoordinate(){
        Matrix worldToEyeCoordinate = worldToEyeCoordinate();
        double zc = parametre.scale * 1000; // todo à voir ici
        Matrix projectionCentrale = Geometry.createCentralProjectionZMatrix(zc);
        return projectionCentrale.times(worldToEyeCoordinate);
    }
    
    /**
     * homogène -> cartésien
     * @return 
     */
    private Matrix clipToNormalDeviceCoordinate(Matrix v){
        double a = v.get(0, 3);
        double[][] w = {{v.get(0,0)/a,v.get(1,0)/a,v.get(2,0)/a}};
        return new Matrix(w,3,1);
    }
    
    private Matrix normalDeviceToWindowCoordinate(){
        // translation+homoethétie
        return null;
    }
    
    private void compute(){
        eyeview = normalDeviceToWindowCoordinate();
        eyeviewInverse = eyeview.inverse();//TODO IMPORTANT pas sur que ce soit juste l(inverse pr les vns
         for(Vertex v : model.getVertexs()){
            Matrix vm = v.v.toMatrix();
            Matrix m = eyeview.times(vm);
            Matrix mcart = clipToNormalDeviceCoordinate(m);
            v.v_proj = new Vecteur(mcart);
            // puis on projette les normals avec l'inverse de eyeview
            //TODO IMPORTANT ici on applique la transfo eyeviewinverse aux vn que si gouraud ou phong
        }
    }
    
    
    private void graphicsPipeline(){
        compute();
        Matrix normalDeviceToWindowCoordinate = normalDeviceToWindowCoordinate();
        for(Vertex v : model.getVertexs()){
            v.v_proj = new Vecteur(normalDeviceToWindowCoordinate.times(v.v_proj.toMatrix()));
            // puis on projette les normals avec l'inverse de eyeview
            //TODO IMPORTANT ici on applique la transfo eyeviewinverse aux vn que si gouraud ou phong
        }
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
        height = hauteurRepere2d +1 ;
        width = largeurRepere2d + 1;
        model.width = width;
        model.hight = height;
        // le point en bas à gauche (minX,minY) de la projection du cube englobant sur le plan xy, son opposé représente le vecteur de tranlation
        double[] min = new double[2];
        min[0] = (-minX);
        min[1] = (-minY);
        double maxprofondeur = -Double.MAX_VALUE;
        double minprofondeur = Double.MAX_VALUE;
        for (int i = 0; i < vertexs.size(); i++) {
            Vertex vertex = vertexs.get(i);
            double x_proj;
            double y_proj;
            // translation 
            x_proj = vertex.v.x + min[0];
            y_proj = vertex.v.y + min[1];
            // homothétie
            double positionRelativeX = abs(x_proj) /  (abs(maxX - minX));
            double positionRelativeY = abs(y_proj) /  (abs(maxY - minY));
            x_proj = largeurRepere2d * positionRelativeX;
            y_proj = hauteurRepere2d * positionRelativeY;
            // on a besoin de la profondeur pour zbuffer
            double z_proj = vertex.v.z;
//            if(z_proj > maxprofondeur){
//                System.out.println("z_proj : "+z_proj);
//            }
            maxprofondeur =  (z_proj > maxprofondeur ? z_proj : maxprofondeur);
            minprofondeur =  (z_proj < minprofondeur ? z_proj : minprofondeur);

            Vecteur v_proj = new Vecteur();
            v_proj.x = x_proj;
            v_proj.y = y_proj;
            v_proj.z = z_proj;
            
//            if(z_proj > 115){
//                System.out.println("pv_proj.z : "+pv_proj.z);
//            }
            vertex.v_proj = v_proj;
        }
        zmax = maxprofondeur;
        zmin = minprofondeur;
        assert(zmax>=zmin):"zmax < zmin : ";
        model.initializeZBuffer(zmin,zmax); // juste pr debug on add zmin et zmax
        
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
        light.normalise();
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
        camera.normalise();
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

    /**
     * @return the model
     */
    public Model getModel() {
        return model;
    }

    /**
     * @param model the model to set
     * @throws java.io.IOException
     */
    public void setModel(Model model) throws IOException {
        this.model = model;
        model.parse();
    }
}
