package com.raepertum;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ImageSelector {

    public File askForImage(File[] images){
        if (images.length == 0) {
            return askForImageGeneration();
        }
        return getImageFromSelector(images);
    }

    private File askForImageGeneration() {
        System.out.println("No se han encontrado imágenes ¿Quieres generar una?");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        List<String> affirmativeAnswers = List.of("YES", "Y", "SÍ", "SI", "S");
        List<String> negativeAnswers = List.of("NO", "N");
        boolean isPositiveAnswer = affirmativeAnswers.contains(answer.toUpperCase());
        boolean isNegativeAnswer = negativeAnswers.contains(answer.toUpperCase());

        if (!isPositiveAnswer && !isNegativeAnswer) {
            System.out.println("Perdona, no te he entendido");
            System.out.println("Respuestas posibles: YES, Y, SÍ, SI, S, NO, N mayúsculas o minúsculas");
            return askForImageGeneration();
        }
        if(isPositiveAnswer){
             return generateBlackImage();
        }
        return null;
    }

    private File generateBlackImage(){
        int width = 1920, height = 1080;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, 0x000000);
            }
        }
        File outputFile = new File("black_background.jpg");
        try {
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Imagen generada con éxito");
        return outputFile;
    }

    private File getImageFromSelector(File[] images){

        System.out.println("Se encontró más de una imagen, " +
                "por favor introduce el número de la imagen que quieres usar como fondo");
        HashMap<Integer, File> options = new HashMap<>();
        for(int i = 0; i<images.length; i++){
            int spaceSize = 4 - (""+i).length();
            System.out.print(i+1+")"+String.format("%"+spaceSize+"s", ""));
            System.out.println(images[i].getName());
            options.put(i+1, images[i]);
        }
        int selectedImage = getInputForImageSelector(images.length);
        return options.get(selectedImage);
    }

    private int getInputForImageSelector(int numOptions){
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Por favor introduce un número del 1 al "+numOptions);
            scanner.next();
        }
        int selectedOption = scanner.nextInt();
        if(selectedOption<=0){
            System.out.println("La opción seleccionada no puede ser igual o menor que 0");
            return getInputForImageSelector(numOptions);
        }
        if(selectedOption>numOptions){
            System.out.println("La opción seleccionada no puede ser mayor que "+numOptions);
            return getInputForImageSelector(numOptions);
        }
        return  selectedOption;
    }
}
