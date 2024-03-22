package com.raepertum;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class WeightPaper {

    private final FilenameUtils filenameUtils = new FilenameUtils();
    private final ImageSelector imageSelector = new ImageSelector();

    private final FieldImageGenerator fieldImageGenerator = new FieldImageGenerator();

    public WeightPaper(){
        generateImage();
        setWallpaper();
    }

    public interface User32 extends Library {
        User32 INSTANCE = Native.load("user32",User32.class, W32APIOptions.DEFAULT_OPTIONS);
        boolean SystemParametersInfo (int one, int two, String s ,int three);
    }

    private void setWallpaper(){
        File foundWallPaperFile = getWallpaperFile();
        if(foundWallPaperFile!=null) {
            User32.INSTANCE.SystemParametersInfo(
                    0x0014, 0, foundWallPaperFile.getAbsolutePath(), 1);
        }
    }

    private File generateImage(){
        return fieldImageGenerator.generateImage();
    }

    private File getWallpaperFile() {
        String currentDirectory = System.getProperty("user.dir");
        File dir = new File(currentDirectory+"/img");
        File[] filesAndDirs = dir.listFiles();
        var images = getImgs(filesAndDirs);
        Arrays.stream(images).forEach(System.out::println);

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




