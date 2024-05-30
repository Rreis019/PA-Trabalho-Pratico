package pt.isec.pa.javalife.ui.gui;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;

/**
 * A classe FaunaImagesManager é responsável por gerenciar e fornecer imagens para diferentes espécies de fauna.
 * É implementada como um Multiton, garantindo que apenas uma instância de imagem seja mantida para cada espécie de fauna.
 */
public class FaunaImagesManager { //classe Multiton
    private static final HashMap <String, Image> imagesFaunaMap = new HashMap<>();

    private FaunaImagesManager() {}

    /**
     * Obtém a imagem associada a uma determinada espécie de fauna.
     * Se a imagem não estiver armazenada em cache, ela será carregada do diretório de imagens.
     * 
     * @param specie A espécie de fauna para a qual a imagem será recuperada.
     * @return A imagem associada à espécie de fauna especificada.
     */
    public static Image getImage(String specie) {
        Image image = imagesFaunaMap.get(specie);
        if (image == null) {
            String imagePath = "/images/fauna/";
            image = loadImage(imagePath + specie.toLowerCase() + ".png");
            imagesFaunaMap.put(specie, image);
        }
        return image;
    }

    /**
     * Carrega uma imagem a partir do caminho especificado.
     * 
     * @param path O caminho para a imagem a ser carregada.
     * @return A imagem carregada.
     */
    private static Image loadImage(String path) {
       try{
           InputStream is = FaunaImagesManager.class.getResourceAsStream(path);
           if (is != null){
               return new Image(is);
           } else {
               System.out.println("Erro para encontrar a imagem" + path);
               return null;
           }
       }catch (Exception e){
           System.out.println("Erro para carregar a imagem" + path);
           return null;
       }
    }
}
