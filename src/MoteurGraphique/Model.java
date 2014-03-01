/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author nikolai
 */
public class Model {

    private final List<Face> faces;
    private final List<Vertex> vertexs;

    private final Image imageDiffuse;
    private final Image imageNormal;

    private final String fichierObject;
    
    int hight;
    int width;

    public Model(String fichierObject, String fichierDiffuse, String fichierNormal) throws MalformedURLException, IOException {
        faces = new ArrayList<>();
        vertexs = new ArrayList<>();
        this.fichierObject = fichierObject;
        imageDiffuse = ImageIO.read(new File(fichierDiffuse));
        imageNormal = ImageIO.read(new File(fichierNormal));
    }

    // testé ok
    public void parse() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(getFichierObject()));
        String line = null;
        String[] lineSplit;
        String[] vertex;
        List<Vecteur> vts = new ArrayList<>();
        List<Vecteur> vns = new ArrayList<>();
        List<Vecteur> vs = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            lineSplit = line.split(" ");
//            System.out.println("line : "+line);
            switch (lineSplit[0]) {
                case "v":
                    Vecteur v = new Vecteur(Double.parseDouble(lineSplit[2]), Double.parseDouble(lineSplit[3]), Double.parseDouble(lineSplit[4]));
//                    System.out.println("v : "+v);
                    vs.add(v);
                    break;
                case "vn":
                    vns.add(new Vecteur(Double.parseDouble(lineSplit[2]), Double.parseDouble(lineSplit[3]), Double.parseDouble(lineSplit[4])));
                    break;
                case "vt":
                    vts.add(new Vecteur(Double.parseDouble(lineSplit[2]), Double.parseDouble(lineSplit[3]), Double.parseDouble(lineSplit[4])));
                    break;
                case "f":
                    vertex = lineSplit[1].split("/");
                    Vertex v1 = new Vertex(vs.get(Integer.parseInt(vertex[0])-1), vts.get(Integer.parseInt(vertex[1])-1), vns.get(Integer.parseInt(vertex[2])-1));
                    vertex = lineSplit[2].split("/");
                    Vertex v2 = new Vertex(vs.get(Integer.parseInt(vertex[0])-1), vts.get(Integer.parseInt(vertex[1])-1), vns.get(Integer.parseInt(vertex[2])-1));
                    vertex = lineSplit[3].split("/");
                    Vertex v3 = new Vertex(vs.get(Integer.parseInt(vertex[0])-1), vts.get(Integer.parseInt(vertex[1])-1), vns.get(Integer.parseInt(vertex[2])-1));
                    faces.add(new Face(v1, v2, v3));
                    vertexs.add(v1);
                    vertexs.add(v2);
                    vertexs.add(v3);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @return the faces
     */
    public List<Face> getFaces() {
        return faces;
    }

    /**
     * @return the vertexs
     */
    public List<Vertex> getVertexs() {
        return vertexs;
    }

    /**
     * @return the imageDiffuse
     */
    public Image getImageDiffuse() {
        return imageDiffuse;
    }

    /**
     * @return the imageNormal
     */
    public Image getImageNormal() {
        return imageNormal;
    }

    /**
     * @return the fichierObject
     */
    public String getFichierObject() {
        return fichierObject;
    }
}
