/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MoteurGraphique;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author nikolai
 */
interface Drawable {
    public void draw(BufferedImage image, Model model, Parameter parameters);
}
