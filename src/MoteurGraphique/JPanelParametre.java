/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author nikolai
 */
public class JPanelParametre extends JPanel {

    private final JButton zbufferPopup;
    private final Parameter parameter;
    private final JButton zbuffer;
    private final JButton transparence;
    private final JButton specular;
    private final JButton debug;
    private final JButton texture;
    private BufferedImage bi;
    private MoteurGraphique mg;
    private Model model;
    private Vecteur light = new Vecteur(0, 0, -1);
    private Vecteur camera = new Vecteur(0, 0, -1);
//    private final JButton compute;
//    private final GUI gui;

    public JPanelParametre(final GUI gui) throws IOException {
        super(new GridLayout(8, 1));
        parameter = new Parameter();
        parameter.rendu = Parameter.PLAIN;//Parameter.PLAIN;
        parameter.shadow = Parameter.VN_COMPUTED_SHADE;//.PHONG_SHADE;
        parameter.scale = 2;
        parameter.texture = true;
        parameter.use_buffer = true;
        parameter.debuggage_sale = true;
        parameter.specular = false;
        parameter.transparence = false;
        light = new Vecteur(0, 0, -1);
        camera = new Vecteur(0, 0, -1);
        model = new Model("obj/african_head.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png", "obj/african_head_spec.png", "obj/african_head_SSS.png");
        mg = new MoteurGraphique(model);
        mg.setCamera(camera);
        mg.setLight(light);
        mg.setParametre(parameter);
        Image i = mg.draw();
        gui.paint(i);
        File outputfile = new File(parameter + ".png");
        ImageIO.write((RenderedImage) i, "png", outputfile);
        
        transparence = new JButton("Sans transparence");
        add(transparence);
        transparence.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.transparence = !parameter.transparence;
                transparence.setText(!parameter.transparence ? "Avec transparence" : "Sans transparence");
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        zbufferPopup = new JButton("Show ZBuffer");
        add(zbufferPopup);
        zbufferPopup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    gui.popZBuffer(mg.drawZBuffer());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        specular = new JButton("Sans specular");
        add(specular);
        specular.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.specular = !parameter.specular;
                specular.setText(!parameter.specular ? "Avec specular" : "Sans specular");
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        zbuffer = new JButton("Sans ZBuffer");
        add(zbuffer);
        zbuffer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.use_buffer = !parameter.use_buffer;
                zbuffer.setText(!parameter.use_buffer ? "Avec ZBuffer" : "Sans ZBuffer");
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        texture = new JButton("Sans texture");
        add(texture);
        texture.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.texture = !parameter.texture;
                texture.setText(!parameter.texture ? "Avec texture" : "Sans texture");
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        debug = new JButton("Avec Debug sale");
        add(debug);
        debug.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.debuggage_sale = !parameter.debuggage_sale;
                debug.setText(!parameter.debuggage_sale ? "Avec Debug sale" : "Sans debug sale");
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        //Create the radio buttons.
        JRadioButton birdButton = new JRadioButton("fil de fer");
        birdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.rendu = Parameter.FIL_DE_FER;
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JRadioButton catButton = new JRadioButton("plain");
        catButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.rendu = Parameter.PLAIN;
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        catButton.setSelected(true);
        JRadioButton dogButton = new JRadioButton("fil de fer et plain");
        dogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.rendu = Parameter.FIL_DE_FER_ET_PLAIN;
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(birdButton);
        group.add(catButton);
        group.add(dogButton);

        //Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(birdButton);
        radioPanel.add(catButton);
        radioPanel.add(dogButton);

        add(radioPanel);

        //Create the radio buttons.
        JRadioButton noshade = new JRadioButton("no shade");
        noshade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.shadow = Parameter.NO_SHADE;
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JRadioButton vnCalc = new JRadioButton("vn calculés");
        vnCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.shadow = Parameter.PLAIN;
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JRadioButton gouraud = new JRadioButton("gouraud");
        gouraud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.shadow = Parameter.GOURAUD_SHADE;
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JRadioButton phong = new JRadioButton("phong");
        phong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.shadow = Parameter.PHONG_SHADE;
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JRadioButton mapping = new JRadioButton("mapping vn");
        mapping.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.shadow = Parameter.NORMAL_MAPPING_SHADE;
                try {
                    init();
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        mapping.setSelected(true);

        //Group the radio buttons.
        ButtonGroup group2 = new ButtonGroup();
        group2.add(noshade);
        group2.add(vnCalc);
        group2.add(gouraud);
        group2.add(phong);
        group2.add(mapping);

        //Put the radio buttons in a column in a panel.
        JPanel radioPanel2 = new JPanel(new GridLayout(0, 1));
        radioPanel2.add(noshade);
        radioPanel2.add(vnCalc);
        radioPanel2.add(gouraud);
        radioPanel2.add(phong);
        radioPanel2.add(mapping);
        add(radioPanel2);

//        compute = new JButton("compute");
//        add(compute);
//        compute.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                try {
//                    init();
//                    gui.paint(mg.draw());
//                } catch (IOException ex) {
//                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
    }

    // c'est sale on devrait pas avoir à refaire un modèle et parser, TODO OPTIMISATION revoir ça si j'ai le temps
    private void init() throws IOException {
        model = new Model("obj/african_head.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png", "obj/african_head_spec.png", "obj/african_head_SSS.png");
        mg = new MoteurGraphique(model);
        mg.setCamera(camera);
        mg.setLight(light);
        mg.setParametre(parameter);

    }
}
