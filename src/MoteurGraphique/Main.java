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
public class Main {

    public static void main(String[] args) {
            new GUI();
    }
}
////            testNormalMapping();
////            test2();
////testAll();
//            Parameter parameter = new Parameter();
//            parameter.rendu = Parameter.PLAIN;
//            parameter.shadow = Parameter.NORMAL_MAPPING_SHADE;
//            parameter.scale = 1;
//            parameter.texture = true;
//            parameter.use_buffer = false;
//            Vecteur light = new Vecteur(0, 0, -1);
//            Vecteur camera = new Vecteur(0, 0, -1);
//
//             parameter.shadow = Parameter.NO_SHADE;
//            test(parameter, light,camera);
//            parameter.shadow = Parameter.VN_COMPUTED_SHADE;
//            test(parameter, light,camera);
//            parameter.shadow = Parameter.GOURAUD_SHADE;
//            test(parameter, light,camera);
//            parameter.shadow = Parameter.PHONG_SHADE;
//            test(parameter, light,camera);
//            parameter.shadow = Parameter.NORMAL_MAPPING_SHADE;
//            test(parameter, light,camera);
//            testLigne(parameter, light, camera);
//            
//            parametre.shadow = Parameter.GOURAUD_SHADE;
////
//
//            
//            parametre.use_buffer = true;
//            System.out.println("triangle A");
//            testA(parametre, light,camera);
//            System.out.println("\n\ntriangle B");
//            testB(parametre, light,camera);
//            System.out.println("\n\ntriangle C");
//            testC(parametre, light,camera);
//            parametre.scale = 3;
//            test(parametre, light,camera);
//            parametre.scale = 11;
//            testt(parametre, light,camera);
//            parametre.scale = 2;
//            parametre.shadow = Parameter.NORMAL_MAPPING_SHADE;
//            parametre.use_buffer = true;
//            parametre.texture = true;
//            parametre.rendu = Parameter.FIL_DE_FER_ET_PLAIN;
//            test(parametre, light,camera);
//            parametre = new Parameter();
//            parametre.rendu = Parameter.PLAIN;
//            parametre.shadow = Parameter.VN_COMPUTED_SHADE;
//            parametre.scale = 2;
//            parametre.texture = false;
//            parametre.use_buffer = false;
//            test(parametre, light,camera);
//            parametre = new Parameter();
//            parametre.rendu = Parameter.PLAIN;
//            parametre.shadow = Parameter.VN_COMPUTED_SHADE;
//            parametre.scale = 2;
//            parametre.texture = false;
//            parametre.use_buffer = true;
//            test(parametre, light,camera);
//            parametre = new Parameter();
//            parametre.rendu = Parameter.PLAIN;
//            parametre.shadow = Parameter.PHONG_SHADE;
//            parametre.scale = 2;
//            parametre.texture = false;
//            parametre.use_buffer = true;
//            test(parametre, light,camera);
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

//    }
//
//    public static void test(Parameter parametre, Vecteur light, Vecteur camera) throws IOException {
//        Model model = new Model(/*"obj/testA.obj"*/"obj/african_head.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
//        MoteurGraphique mg = new MoteurGraphique(model);
//        mg.setParametre(parametre);
//        Image bi;
//        GUI gui;
//        mg.setLight(light);
//        mg.setCamera(camera);
//        bi = mg.draw();
//        gui = new GUI();
//        gui.paint(bi);
////        File outputfile = new File("saved"+".png");
////                    ImageIO.write((RenderedImage) bi, "png", outputfile);
//    }
//
//    public static void testA(Parameter parametre, Vecteur light, Vecteur camera) throws IOException {
//        Model model = new Model("obj/testA.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
//        MoteurGraphique mg = new MoteurGraphique(model);
//        mg.setParametre(parametre);
//        Image bi;
//        GUI gui;
//        mg.setLight(light);
//        mg.setCamera(camera);
//        bi = mg.draw();
//        gui = new GUI();
//        gui.paint(bi);
//        File outputfile = new File("triangleA" + ".png");
//        ImageIO.write((RenderedImage) bi, "png", outputfile);
//    }
//
//    public static void testLigne(Parameter parametre, Vecteur light, Vecteur camera) throws IOException {
//        Model model = new Model("obj/testLigne.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
//        MoteurGraphique mg = new MoteurGraphique(model);
//        mg.setParametre(parametre);
//        Image bi;
//        GUI gui;
//        mg.setLight(light);
//        mg.setCamera(camera);
//        bi = mg.draw();
////        gui = new GUI();
////        gui.paint(bi);
//        File outputfile = new File("triangleLigne" + ".png");
//        ImageIO.write((RenderedImage) bi, "png", outputfile);
//    }
//
//    public static void testB(Parameter parametre, Vecteur light, Vecteur camera) throws IOException {
//        Model model = new Model("obj/testB.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
//        MoteurGraphique mg = new MoteurGraphique(model);
//        mg.setParametre(parametre);
//        Image bi;
//        GUI gui;
//        mg.setLight(light);
//        mg.setCamera(camera);
//        bi = mg.draw();
////        gui = new GUI();
////        gui.paint(bi);
//        File outputfile = new File("triangleB" + ".png");
//        ImageIO.write((RenderedImage) bi, "png", outputfile);
//    }
//
//    public static void testC(Parameter parametre, Vecteur light, Vecteur camera) throws IOException {
//        Model model = new Model("obj/testC.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
//        MoteurGraphique mg = new MoteurGraphique(model);
//        mg.setParametre(parametre);
//        Image bi;
//        GUI gui;
//        mg.setLight(light);
//        mg.setCamera(camera);
//        bi = mg.draw();
////        gui = new GUI();
////        gui.paint(bi);
//        File outputfile = new File("triangleC" + ".png");
//        ImageIO.write((RenderedImage) bi, "png", outputfile);
//    }
//
//    public static void test0() throws IOException {
//
//        Model model = new Model("obj/african_head.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
////            Model model = new Model("obj/anchor.obj", "obj/anchor.jpg", "obj/anchor.jpg");
//        MoteurGraphique mg = new MoteurGraphique(model);
//        Parameter parametre = new Parameter();
////            parametre.rendu = Parameter.FIL_DE_FER;
////            parametre.rendu = Parameter.FIL_DE_FER_ET_PLAIN;
//        parametre.rendu = Parameter.PLAIN;
////            parametre.shadow = Parameter.NO_SHADE;
////            parametre.shadow = Parameter.PHONG_SHADE;
////            parametre.shadow = Parameter.VN_COMPUTED_SHADE;
//        parametre.shadow = Parameter.GOURAUD_SHADE;
//        parametre.scale = 1;
////            parametre.texture = false;
//        parametre.texture = true;
////            parametre.use_buffer = false;
//        parametre.use_buffer = true;
//        mg.setParametre(parametre);
//        Image bi;
////            File outputfile;
//        Vecteur light;
//
//        GUI gui;
//
//        light = new Vecteur(0, 0, -1);
//        mg.setLight(light);
//        bi = mg.draw();
//        gui = new GUI();
//        gui.paint(bi);
//        bi = mg.drawZBuffer();
//        gui = new GUI();
//        gui.paint(bi);
//        light = new Vecteur(1, 0, 0);
//        mg.setLight(light);
//        bi = mg.draw();
//        gui = new GUI();
//        gui.paint(bi);
//
//        parametre.shadow = Parameter.PHONG_SHADE;
//        mg.setParametre(parametre);
//        bi = mg.draw();
//        gui = new GUI();
//        gui.paint(bi);
//    }
//
//    public static void test1() throws IOException {
//        Model model = new Model("obj/african_head.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
//
//        MoteurGraphique mg = new MoteurGraphique(model);
//        Parameter parametre = new Parameter();
//        parametre.rendu = Parameter.PLAIN;
//        parametre.shadow = Parameter.GOURAUD_SHADE;
//        parametre.scale = 2;
//        parametre.texture = true;
//        parametre.use_buffer = true;
//        mg.setParametre(parametre);
//        Image bi;
//        Vecteur light;
//        GUI gui;
//
//        light = new Vecteur(0, 0, -1);
//        mg.setLight(light);
//        bi = mg.draw();
//        gui = new GUI();
//        gui.paint(bi);
//
//        parametre.shadow = Parameter.PHONG_SHADE;
//        mg.setParametre(parametre);
//        bi = mg.draw();
//        gui = new GUI();
//        gui.paint(bi);
//
//    }
//
//    public static void test2() throws IOException {
//        Model model = new Model("obj/african_head.obj", "obj/african_head_nm.png", "obj/african_head_nm.png");
//
//        MoteurGraphique mg = new MoteurGraphique(model);
//        Parameter parametre = new Parameter();
//        parametre.rendu = Parameter.PLAIN;
//        parametre.shadow = Parameter.NO_SHADE;
//        parametre.scale = 2;
//        parametre.texture = true;
//        parametre.use_buffer = true;
//        mg.setParametre(parametre);
//        Image bi;
//        Vecteur light;
//        GUI gui;
//
//        light = new Vecteur(0, 1, -1);
//        mg.setLight(light);
//        bi = mg.draw();
//        gui = new GUI();
//        gui.paint(bi);
//
//    }
//
//    public static void testNormalMapping() throws IOException {
//        Model model = new Model("obj/african_head.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
//        MoteurGraphique mg = new MoteurGraphique(model);
//        Parameter parametre = new Parameter();
//        parametre.rendu = Parameter.PLAIN;
//        parametre.shadow = Parameter.NORMAL_MAPPING_SHADE;
//        parametre.scale = 2;
//        parametre.texture = true;
//        parametre.use_buffer = true;
//        mg.setParametre(parametre);
//        Image bi;
//        Vecteur light;
//        GUI gui;
//
//        light = new Vecteur(0, 1, -1);
//        mg.setLight(light);
//        bi = mg.draw();
//        gui = new GUI();
//        gui.paint(bi);
//    }
//
//    public static void testAll() throws IOException {
//        File outputfile;
//        Model model = new Model("obj/african_head.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
//        MoteurGraphique mg = new MoteurGraphique(model);
//        Parameter parametre = new Parameter();
//        parametre.rendu = Parameter.PLAIN;
//        parametre.shadow = Parameter.NORMAL_MAPPING_SHADE;
//        parametre.scale = 2;
//        parametre.texture = true;
//        parametre.use_buffer = true;
//        mg.setParametre(parametre);
//        Image bi;
//        Vecteur light;
//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j <= 1; j++) {
//                for (int k = -1; k <= 1; k++) {
//                    light = new Vecteur(i, j, k);
//                    System.out.println("drawing : " + light);
//                    mg.setLight(light);
//
//                    bi = mg.draw();
//                    outputfile = new File("saved" + i + "" + j + "" + k + ".png");
//                    ImageIO.write((RenderedImage) bi, "png", outputfile);
//                }
//            }
//        }
//    }
//}
