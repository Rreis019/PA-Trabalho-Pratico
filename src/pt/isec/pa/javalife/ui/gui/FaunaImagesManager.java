package pt.isec.pa.javalife.ui.gui;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;

public class FaunaImagesManager { //classe Multiton
    private static final HashMap <String, Image> imagesFaunaMap = new HashMap<>();

    private FaunaImagesManager() {
    }

    public static Image getImage(String specie) {
        Image image = imagesFaunaMap.get(specie);
        if (image == null) {
            String imagePath = "/images/fauna/";

            switch (specie) {
                case "Lobo":
                   // image = loadImage(imagePath + "lobo.png"); ---> Exemplo de como fazer
                    break;
                case "Leão":
                    // image = loadImage(imagePath + "leao.png");
                    break;
                default:
                    break;
            }
            imagesFaunaMap.put(specie, image);
        }
        return image;
    }

    private static Image loadImage(String path) {
       try{
           InputStream is = FaunaImagesManager.class.getResourceAsStream(path);
           if (is != null){
               return new Image(is);
           } else {
               System.out.println("Erro para encontrar imagem");
               return null;
           }
       }catch (Exception e){
           System.out.println("Erro para carregar imagem");
           return null;
       }
    }
}
