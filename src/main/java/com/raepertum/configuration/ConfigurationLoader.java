package com.raepertum.configuration;

import org.springframework.context.annotation.Bean;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConfigurationLoader {

    @Bean
    public WeightpaperConfiguration loadConfiguration(){

        Yaml yaml = new Yaml();
        System.out.println("IMPRESIÓN CARGA DE YAML POR DEFECTO:");
        System.out.println("--------------------------:");
        WeightpaperConfiguration weightpaperConfiguration = new WeightpaperConfiguration();
        printConfigurationValues(weightpaperConfiguration);
        System.out.println("--------------------------:");
        try (FileInputStream inputStream = new FileInputStream("conf/Configuration.yaml")) {
            weightpaperConfiguration = yaml.loadAs(inputStream,
                    WeightpaperConfiguration.class);
            System.out.println("IMPRESIÓN DESPUÉS DE LA CARGA:");
            printConfigurationValues(weightpaperConfiguration);
            System.out.println("--------------------------:");
        } catch (IOException e) {
            System.out.println("ERROR CARGANDO ARCHIVO");
            e.printStackTrace();
        }
        return weightpaperConfiguration;
    }

    private void printConfigurationValues(WeightpaperConfiguration configuration){
        System.out.println("Font size: "+configuration.getFieldBox().getFontSize());
        System.out.println("Fields place: "+configuration.getFieldBox().getFieldsPlace());
        System.out.println("Font color: "+configuration.getFieldBox().getColor());
        System.out.println("Background color: "+configuration.getFieldBox().getBackgroundColor());
        System.out.println("Width padding: "+configuration.getFieldBox().getWidthPadding());
        System.out.println("Height padding: "+configuration.getFieldBox().getHeightPadding());
        System.out.println("Default image location: "+
                        configuration.getBackgroundImage().getDefaultImageLocation());
        System.out.println("Other background image locations: ");
        Stream.of(configuration.getBackgroundImage().getOtherBackgroundImageLocations())
                .forEach(System.out::println);
        System.out.println("Windows bar height: "+configuration.getSo().getWindowsBarHeight());
    }
}
