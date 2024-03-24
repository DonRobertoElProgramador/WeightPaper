package com.raepertum;

import com.raepertum.auxiliar.FieldsPlace;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class WeightPaper {

    private final FilenameUtils filenameUtils = new FilenameUtils();
    private final ImageOverlapper imageOverlapper;
    private final FieldImageGenerator fieldImageGenerator;
    private final ImageSelector imageSelector;


    public WeightPaper(ImageSelector imageSelector, FieldImageGenerator fieldImageGenerator,
                       ImageOverlapper imageOverlapper){

        this.fieldImageGenerator = fieldImageGenerator;
        this.imageSelector = imageSelector;
        this.imageOverlapper = imageOverlapper;

        String[] fieldValues = getFieldValues();
        BufferedImage fieldsImage = generateFieldsImage(fieldValues);
        File backgroundImage = getWallpaperFile();
        File finalWallpaperFile = overlapImageToFile(backgroundImage, fieldsImage);
        setWallpaper(finalWallpaperFile);
    }

    private BufferedImage generateFieldsImage(String[] fieldValues){
        return fieldImageGenerator.generateFieldsImage(fieldValues);
    }

    private String[] getFieldValues(){
        return new String[]{"Esto es una prueba", "De dos líneas", "Ahora de tres",
        "Ahora de cuatro", "Ahora de cinco", "Ahora de seis"};
    }

    private File overlapImageToFile(File backgroundImage, BufferedImage fieldsImage){
        return imageOverlapper.getCompositeImage(backgroundImage, fieldsImage,
                FieldsPlace.UPPER_RIGHT);
    }

    public interface User32 extends Library {
        User32 INSTANCE = Native.load("user32",User32.class, W32APIOptions.DEFAULT_OPTIONS);
        boolean SystemParametersInfo (int one, int two, String s ,int three);
    }

    private void setWallpaper(File finalWallpaperFile){
        if(finalWallpaperFile!=null) {
            User32.INSTANCE.SystemParametersInfo(
                    0x0014, 0, finalWallpaperFile.getAbsolutePath(), 1);
        }
    }

    private File getWallpaperFile() {
        String currentDirectory = System.getProperty("user.dir");
        File dir = new File(currentDirectory+"/img");
        File[] filesAndDirs = dir.listFiles();
        var images = getImgs(filesAndDirs);
        return images.length==1 ? images[0] : imageSelector.askForImage(images);
    }

    private File[] getImgs(File[] files){
        return Arrays.stream(files)
                .filter(this::hasImageExtension)
                .toArray(File[]::new);
    }

    private boolean hasImageExtension(File file){
        List<String> extensions = List.of("jpg", "png", "bmp", "gif", "jpeg");
        return extensions.contains(filenameUtils.getExtension(file.getName()).toLowerCase());
    }
}




