package com.raepertum;

import com.raepertum.configuration.ConfigurationLoader;
import com.raepertum.configuration.MainConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido a Weightpaper!");
        ConfigurationLoader configurationLoader = new ConfigurationLoader();
        configurationLoader.loadConfiguration();
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MainConfiguration.class);
        WeightPaper weightPaper = context.getBean(WeightPaper.class);
    }
}