package com.raepertum;

import com.raepertum.auxiliar.FieldsPlace;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ImageOverlapper {

    private static final int WINDOWS_BAR_HEIGHT = 40;

    public File getCompositeImage(File backgroundImage, BufferedImage fieldsImage,
                                  FieldsPlace fieldsPlace) {

        BufferedImage backgroundImageFromFile;
        File outputFile = new File("combined_image.png");
        try {
            backgroundImageFromFile = ImageIO.read(backgroundImage);
            int width = backgroundImageFromFile.getWidth();
            int height = backgroundImageFromFile.getHeight();
            int fieldsWidth = fieldsImage.getWidth();
            int fieldsHeight = fieldsImage.getHeight();
            BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = combined.createGraphics();
            g.drawImage(backgroundImageFromFile, 0, 0, null);
            int[] fieldsImagePosition = getFieldsImagePosition(width, height,
                    fieldsWidth, fieldsHeight, fieldsPlace);
            g.drawImage(fieldsImage, fieldsImagePosition[0], fieldsImagePosition[1], null);
            g.dispose();            
            ImageIO.write(combined, "PNG", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputFile;
    }

    private int[] getFieldsImagePosition(int width, int height, int fieldsWidth,
                                         int fieldsHeight, FieldsPlace fieldsPlace){
        return switch (fieldsPlace) {
            case UPPER_RIGHT -> new int[]{width - fieldsWidth, 0};
            case BOTTOM_LEFT -> new int[]{0, height - fieldsHeight - WINDOWS_BAR_HEIGHT};
            case BOTTOM_RIGHT -> new int[]{width - fieldsWidth, height - fieldsHeight - WINDOWS_BAR_HEIGHT};
            default -> new int[]{0, 0};
        };
    }



}
