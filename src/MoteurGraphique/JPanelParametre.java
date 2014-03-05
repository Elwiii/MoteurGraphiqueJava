/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nikolai
 */
public class JPanelParametre extends JPanel {

    private final Parameter parameter;
    private final JButton zbuffer;
    private BufferedImage bi;
    private final MoteurGraphique mg;

//    private final GUI gui;
    public JPanelParametre(final GUI gui) throws IOException {
        super(new GridLayout(4, 1));
        add(new JLabel("ok"));
//        this.gui = gui;
        parameter = new Parameter();
        parameter.rendu = Parameter.PLAIN;
        parameter.shadow = Parameter.NORMAL_MAPPING_SHADE;
        parameter.scale = 1;
        parameter.texture = true;
        parameter.use_buffer = false;
        Vecteur light = new Vecteur(0, 0, -1);
        Vecteur camera = new Vecteur(0, 0, -1);
        Model model = new Model("obj/african_head.obj", "obj/african_head_diffuse.png", "obj/african_head_nm.png");
        mg = new MoteurGraphique(model);
        mg.setCamera(camera);
        mg.setLight(light);
        mg.setParametre(parameter);
        zbuffer = new JButton("Avec ZBuffer");
        add(zbuffer);
        zbuffer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                parameter.use_buffer = !parameter.use_buffer;
                zbuffer.setText(!parameter.use_buffer ? "Avec ZBuffer" : "Sans ZBuffer");
                try {
                    gui.paint(mg.draw());
                } catch (IOException ex) {
                    Logger.getLogger(JPanelParametre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
