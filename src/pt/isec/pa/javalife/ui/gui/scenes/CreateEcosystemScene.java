package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

class CreateEcosystemScene extends Scene
{
    private Scene scene;
    private VBox root;
    public CreateEcosystemScene()
    {
        super(new VBox());
        createView();
        registerHandlers();
    }

    private void createView()
    {
        // Implementação da criação da interface gráfica
    }

    private void registerHandlers()
    {
        // Implementação do registro de event handlers
    }

    public Scene getScene()
    {
        return this.scene;
    }

}
