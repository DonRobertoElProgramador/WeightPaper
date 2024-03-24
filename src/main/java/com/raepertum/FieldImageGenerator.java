package com.raepertum;

import com.raepertum.auxiliar.TextDimension;
import com.raepertum.configuration.WeightpaperConfiguration;
import org.springframework.stereotype.Component;
import java.awt.*;
import java.awt.image.BufferedImage;


@Component
public class FieldImageGenerator {

    private static final int PADDING_WIDTH = 10;
    private static final int PADDING_HEIGHT = 10;

    private final WeightpaperConfiguration weightpaperConfiguration;

    public FieldImageGenerator(WeightpaperConfiguration weightpaperConfiguration){
        this.weightpaperConfiguration = weightpaperConfiguration;
    }

    public BufferedImage generateFieldsImage(String[] fields){

        int fontSize = weightpaperConfiguration.getFieldBox().getFontSize();

        Font font = new Font("Arial", Font.BOLD, fontSize);
        TextDimension textDimensions = getTextDimensions(font, fields);
        int width = textDimensions.width();
        BufferedImage bufferedImage = new BufferedImage(width + PADDING_WIDTH,
                textDimensions.ascent()*fields.length + PADDING_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, width + PADDING_WIDTH,
                textDimensions.ascent()*fields.length + PADDING_HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);

        for(int i=0; i<fields.length; i++){
            g2d.drawString(fields[i], PADDING_WIDTH/2,
                    textDimensions.ascent()*(i+1) + PADDING_HEIGHT/2);
        }
        g2d.dispose();
        return bufferedImage;
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
