package com.raepertum;

import com.raepertum.auxiliar.TextDimension;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FieldImageGenerator {

    public File generateImage(){

        Font font = new Font("Arial", Font.BOLD, 20);
        String[] text = new String[]{"Hello, world!", " penecio"};

        TextDimension textDimensions = getTextDimensions(font, text);

        int width = textDimensions.width();
        int height = textDimensions.height();
        BufferedImage bufferedImage = new BufferedImage(width, height*(text.length), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, width, height*text.length);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);

        for(int i=0; i<text.length; i++){
            g2d.drawString(text[i], 0, textDimensions.ascent()*(i+1));
        }

        g2d.dispose();
        File file = new File("HelloWorld.png");
        try {
            ImageIO.write(bufferedImage, "PNG", file);
            System.out.println("Image saved as " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private TextDimension getTextDimensions(Font font, String[] text){

        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setFont(font);
        FontMetrics fontMetrics = g2d.getFontMetrics();

        int width = 0;
        for (String line : text) {
            width = Math.max(width, fontMetrics.stringWidth(line));
        }
        g2d.dispose();
        return new TextDimension(width, fontMetrics.getHeight(), fontMetrics.getAscent());
    }
}
