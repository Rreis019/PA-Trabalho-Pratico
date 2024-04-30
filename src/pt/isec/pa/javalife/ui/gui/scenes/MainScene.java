package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class MainScene extends Scene
{
    Stage primaryStage;
    public MainScene(Stage primaryStage_)
    {
        super(new VBox());
        createView(primaryStage);
        registerHandlers();
        primaryStage =  primaryStage_;
    }

    private void createView(Stage primaryStage)
    {
        // Implementação da criação da interface gráfica
    }

    private void registerHandlers()
    {
        // Implementação do registro de event handlers
    }
}
