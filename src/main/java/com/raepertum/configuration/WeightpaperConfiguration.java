package com.raepertum.configuration;

import com.raepertum.auxiliar.FieldsPlace;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@NoArgsConstructor
public class WeightpaperConfiguration {


    private FieldBox fieldBox = new FieldBox();
    private BackgroundImage backgroundImage = new BackgroundImage();
    private SO so = new SO();

    @Data
    @NoArgsConstructor
    public static class FieldBox {

        private FieldBox fieldBox;
        private BackgroundImage backgroundImage;
        private SO so;

        private int fontSize = 20;
        private FieldsPlace fieldsPlace = FieldsPlace.UPPER_RIGHT;
        private Color color = Color.BLACK;
        private Color backgroundColor = Color.LIGHT_GRAY;
        private int widthPadding = 10;
        private int heightPadding = 10;
    }

    @Data
    @NoArgsConstructor
    public static class BackgroundImage {
        private String defaultImageLocation = "";
        private String[] otherBackgroundImageLocations = new String[]{"/img", "/bckgr"};
    }

    @Data
    @NoArgsConstructor
    public static class SO {
        private int windowsBarHeight = 40;
    }
}
