package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.ui.gui.components.ClickableSVG;
import pt.isec.pa.javalife.ui.gui.components.SideBar;
import pt.isec.pa.javalife.ui.gui.components.SideBarNavbar;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Scale;
import javafx.scene.shape.Polygon;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class MainScene extends Scene
{
    Ecosystem model;
    Canvas canvas;
    Button btninspecionar, btnConfigurar;
    Stage primaryStage;

    HBox topPanel; 
    VBox mainpanel;//sidebar;


    Button btnPlay,btnSnapShot,btnRewind;


    public MainScene(Stage primaryStage__,Ecosystem ecosystem)
    {
        super(new VBox());
        primaryStage =  primaryStage__;
        model = ecosystem;
        createView(primaryStage);
        registerHandlers();
    }

    private void createView(Stage primaryStage)
    {
        getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        VBox root = (VBox) this.getRoot();
        root.getStyleClass().add("secondary-background");

        primaryStage.setTitle("JavaLife - Ecossistema");

        //Ícones SVG
        ClickableSVG svgPlay = new ClickableSVG();
        svgPlay.setContent("M20.7227 10.4813L3.53516 0.320197C2.13867 -0.504998 0 0.295783 0 2.3368V22.6542C0 24.4852 1.9873 25.5888 3.53516 24.6708L20.7227 14.5145C22.2559 13.6112 22.2607 11.3846 20.7227 10.4813Z");
        svgPlay.getStyleClass().add("icon");
        Tooltip.install(svgPlay,new Tooltip("Pausar/Continuar simulação"));

        ClickableSVG svgSnapShot = new ClickableSVG();
        svgSnapShot.setContent("M3.21814 0.5625C1.92419 0.5625 0.87439 1.6123 0.87439 2.90625V20.0938C0.87439 21.3877 1.92419 22.4375 3.21814 22.4375H23.5306C24.8246 22.4375 25.8744 21.3877 25.8744 20.0938V2.90625C25.8744 1.6123 24.8246 0.5625 23.5306 0.5625H3.21814ZM3.21814 2.125H8.39392C8.55505 2.125 8.68689 2.25684 8.68689 2.41797V3.39453C8.68689 3.55566 8.55505 3.6875 8.39392 3.6875H2.72986C2.56873 3.6875 2.43689 3.55566 2.43689 3.39453V2.90625C2.43689 2.47656 2.78845 2.125 3.21814 2.125ZM24.0189 6.8125H2.72986C2.56873 6.8125 2.43689 6.68066 2.43689 6.51953V4.76172C2.43689 4.60059 2.56873 4.46875 2.72986 4.46875H9.46814L10.9427 2.25684C10.9965 2.17383 11.0892 2.125 11.1869 2.125H23.5306C23.9603 2.125 24.3119 2.47656 24.3119 2.90625V6.51953C24.3119 6.68066 24.1801 6.8125 24.0189 6.8125ZM13.3744 19.7031C10.142 19.7031 7.51501 17.0762 7.51501 13.8438C7.51501 10.6113 10.142 7.98438 13.3744 7.98438C16.6068 7.98438 19.2338 10.6113 19.2338 13.8438C19.2338 17.0762 16.6068 19.7031 13.3744 19.7031ZM13.3744 9.54688C11.0062 9.54688 9.07751 11.4756 9.07751 13.8438C9.07751 16.2119 11.0062 18.1406 13.3744 18.1406C15.7426 18.1406 17.6713 16.2119 17.6713 13.8438C17.6713 11.4756 15.7426 9.54688 13.3744 9.54688ZM11.0306 14.625C10.601 14.625 10.2494 14.2734 10.2494 13.8438C10.2494 12.1201 11.6508 10.7188 13.3744 10.7188C13.8041 10.7188 14.1556 11.0703 14.1556 11.5C14.1556 11.9297 13.8041 12.2812 13.3744 12.2812C12.515 12.2812 11.8119 12.9844 11.8119 13.8438C11.8119 14.2734 11.4603 14.625 11.0306 14.625Z");
        svgSnapShot.getStyleClass().add("icon");
        Tooltip.install(svgSnapShot,new Tooltip("Gravar snapshot\nPermite registar o momento atual da simulação.\nEsta informação nunca é gravada para ficheiro."));
        
        ClickableSVG svgRewind = new ClickableSVG();
        svgRewind.setContent("M9.29148 0.209423C8.4151 0.418084 8.33163 1.54485 9.08281 2.58816C9.91746 3.71493 11.0442 3.00548 11.0442 1.33619C11.0442 -0.0827022 10.8356 -0.207899 9.29148 0.209423ZM13.2142 1.41967C13.3812 3.38109 14.0906 3.67321 14.8001 2.00392C15.3843 0.585028 15.3843 0.543296 14.0489 0.125974C13.1308 -0.12442 13.0891 -0.0409555 13.2142 1.41967ZM4.78434 2.33776L3.90796 3.04721L4.90953 4.00705C5.78591 4.84169 6.07803 4.92516 6.78748 4.4661C7.49693 4.04878 7.53866 3.75665 7.2048 2.75508C6.74575 1.41965 6.07803 1.29445 4.78434 2.33776ZM16.9702 2.83855C16.3859 4.50783 17.0536 4.88342 18.3891 3.63146C19.3489 2.71335 19.3906 2.62988 18.7229 2.1291C17.6379 1.33619 17.4292 1.46138 16.9702 2.83855ZM2.15523 4.96689C-1.05815 9.84956 -0.640827 15.8173 3.1568 20.1574C6.16152 23.5377 11.7536 24.7062 16.5111 22.87L18.6812 22.0771L19.3906 22.9952C19.808 23.496 20.2253 23.8298 20.3505 23.7047C20.7261 23.3291 22.7709 17.3196 22.604 17.1527C22.4788 17.0692 20.7678 17.0275 18.7647 17.111L15.1339 17.2362L16.0103 18.3629L16.9284 19.4897L15.6765 20.1574C14.967 20.4913 13.3812 20.7834 11.9623 20.7834C8.70719 20.7834 6.28672 19.5314 4.49224 16.8606C3.282 15.1078 3.11507 14.5236 3.11507 12.0614C3.11507 10.0582 3.36547 8.84799 3.99145 7.67949L4.86783 6.05193L3.82452 5.00862L2.78121 4.00705L2.15523 4.96689ZM20.2671 5.50943C19.0151 6.84486 19.3072 7.22045 21.0183 6.511C22.1868 6.01021 22.2702 5.88502 21.8112 5.2173C21.2687 4.50785 21.1852 4.50785 20.2671 5.50943ZM22.0197 9.0984C20.726 9.64092 20.8095 9.93304 22.3119 9.93304C23.1465 9.93304 23.5638 9.72438 23.5638 9.30706C23.5638 8.59761 23.3552 8.55588 22.0197 9.0984Z");
        svgRewind.getStyleClass().add("icon");
        Tooltip.install(svgRewind,new Tooltip("Restaurar snapshot\nPermite retomar a simulação num ponto salvaguardado anteriormente."));

        ClickableSVG svgApplyStrength = new ClickableSVG();
        svgApplyStrength.setContent("M15.1988 23H13.882C13.8019 22.9886 13.7103 22.9545 13.6301 22.9545C12.7942 22.8634 11.9469 22.8179 11.1225 22.6813C10.1034 22.522 9.11867 22.6358 8.16828 23H7.71027C7.68737 22.9772 7.67592 22.9431 7.65302 22.9317C5.48889 21.6343 3.86294 19.8362 2.92401 17.5032C1.79042 14.6467 1.18355 11.6764 1.00034 8.61504C0.977443 8.15982 0.920191 7.7046 0.87439 7.26076V5.34884C0.89729 5.22365 0.931641 5.09847 0.943092 4.97328C1.09195 3.69866 1.57286 2.59476 2.52324 1.6957C3.29042 0.967343 4.1034 0.295893 5.15683 0H5.94691C6.19882 0.102425 6.47363 0.170707 6.69118 0.318654C7.32096 0.773874 7.95073 1.22909 8.52325 1.74122C8.7866 1.96883 8.94691 2.34438 9.06141 2.6858C9.15302 2.95893 9.16447 3.27759 8.83241 3.45967C8.7866 3.48243 8.7637 3.5621 8.7637 3.619C8.695 4.06284 8.47744 4.35873 8.07668 4.39287C7.95073 4.6091 7.88202 4.81395 7.74462 4.91638C7.48126 5.12123 7.18355 5.25779 6.90874 5.49678C6.48508 5.87234 5.54615 5.65611 5.20263 5.20089C5.09958 5.23503 5.00798 5.30331 4.91637 5.34884C4.81332 5.40574 4.69882 5.43988 4.58431 5.4854C4.33241 5.57645 4.25225 5.74715 4.34386 6.00891C4.92783 7.7046 5.695 9.28649 7.04615 10.5383C8.14538 11.5626 8.96981 12.7917 9.55378 14.1687C9.64538 14.385 9.73699 14.5898 9.85149 14.8516C10.6187 10.9139 12.966 9.24097 16.9049 9.77585C16.1148 7.38595 17.2713 5.38298 18.84 4.42702C20.4202 3.47105 22.1492 3.2093 23.9355 3.55072C25.8591 3.91489 26.9698 5.21227 27.5423 7.01039C27.7026 7.49975 27.7713 8.02326 27.8744 8.53538V9.91242C27.7942 10.2538 27.7484 10.5952 27.6454 10.9253C27.05 12.9283 25.6645 14.237 23.8324 15.1361C23.6263 15.2385 23.5232 15.3637 23.4545 15.5685C22.9736 16.9456 22.3095 18.2316 21.4278 19.3924C20.1683 21.0426 18.611 22.2716 16.5385 22.761C16.1148 22.8748 15.6568 22.9203 15.1988 23Z");
        svgApplyStrength.getStyleClass().add("icon");
        svgApplyStrength.getTransforms().add(new Scale(-1, 1, 15, 0));
        Tooltip.install(svgApplyStrength,new Tooltip("Injetar Força\nAplica-se apenas a elementos do tipo fauna, permitindo aumentar a força do elemento selecionado em 50 unidades."));

        ClickableSVG svgHerb = new ClickableSVG();
        svgHerb.setContent("M18.2521 1.24359C17.9604 1.53531 17.752 2.61885 17.752 3.66071C17.752 5.9528 18.2938 6.57792 20.1275 6.57792C20.8776 6.57792 21.5027 6.74462 21.5027 6.99466C21.5027 7.20304 21.9611 7.41141 22.5446 7.41141C23.128 7.41141 23.5864 7.20304 23.5864 6.99466C23.5864 6.74462 24.2116 6.57792 24.9617 6.57792C25.7118 6.57792 26.5453 6.36955 26.837 6.07783C27.5038 5.41103 27.5038 1.91038 26.837 1.24359C26.5036 0.910196 24.92 0.743498 22.5446 0.743498C20.1691 0.743498 18.5855 0.910196 18.2521 1.24359ZM25.5868 3.53569C25.7118 4.45252 25.5868 4.4942 22.5862 4.4942C19.7941 4.4942 19.419 4.41085 19.419 3.74406C19.419 2.5355 19.8357 2.3688 22.7529 2.49382C25.2117 2.61885 25.4618 2.7022 25.5868 3.53569ZM1.12389 2.16041C0.748817 2.57715 0.790491 2.8272 1.29058 3.24394C2.0824 3.91073 2.99924 3.20227 2.62417 2.24375C2.29077 1.41027 1.749 1.41027 1.12389 2.16041ZM4.95796 2.24375C4.58289 3.20227 5.49973 3.91073 6.29154 3.24394C6.79163 2.8272 6.83331 2.57715 6.45824 2.16041C5.83312 1.41027 5.29136 1.41027 4.95796 2.24375ZM9.04198 2.16041C8.66691 2.57715 8.70858 2.8272 9.20868 3.24394C10.0005 3.91073 10.9173 3.20227 10.5423 2.24375C10.2089 1.41027 9.66709 1.41027 9.04198 2.16041ZM3.20765 5.07762C2.62421 5.78608 2.91593 6.5779 3.7911 6.5779C4.70793 6.5779 4.99966 5.74441 4.33286 5.07762C3.7911 4.53585 3.66607 4.53585 3.20765 5.07762ZM7.04164 5.16097C6.70825 6.03613 7.58341 6.9113 8.20853 6.28618C8.7503 5.74441 8.4169 4.49418 7.75011 4.49418C7.50006 4.49418 7.20834 4.7859 7.04164 5.16097ZM5.29134 7.82812C4.58288 8.24487 4.99962 9.4951 5.83311 9.4951C6.12483 9.4951 6.4999 9.24506 6.62492 8.91166C6.95832 8.11985 5.99981 7.36971 5.29134 7.82812ZM16.085 9.49513C15.7933 9.99522 15.71 13.5375 15.7516 19.1636L15.8767 28.0403H22.5446H29.2125L29.3375 18.9136C29.4209 12.5374 29.2958 9.62015 28.9624 9.24508C28.5874 8.78666 27.0871 8.66164 22.5029 8.66164C17.0019 8.66164 16.5018 8.74499 16.085 9.49513ZM19.7524 17.955C19.8774 25.0814 19.5857 26.79 18.3771 26.3316C17.8354 26.1232 17.752 24.7896 17.752 18.3718V10.7037L18.7105 10.8287C19.6274 10.9537 19.6274 11.0371 19.7524 17.955ZM4.24944 11.0371C3.8327 12.0373 3.95772 14.4961 4.41614 14.4961C4.66619 14.4961 4.83288 15.7463 4.83288 17.4133C4.83288 19.0802 4.66619 20.3305 4.41614 20.3305C4.20777 20.3305 3.9994 20.7889 3.9994 21.3723C3.9994 22.3309 4.16609 22.4142 5.87474 22.4142C7.5834 22.4142 7.75009 22.3309 7.75009 21.3723C7.75009 20.7889 7.5834 20.3305 7.33335 20.3305C7.0833 20.3305 6.91661 19.0802 6.91661 17.4133C6.91661 15.7463 7.0833 14.4961 7.33335 14.4961C7.87512 14.4961 7.87512 12.7874 7.37502 11.4538C6.91661 10.2869 4.66619 9.95354 4.24944 11.0371ZM12.2927 11.2871C10.9174 12.329 10.6674 13.5792 10.6674 19.4553C10.6674 24.9147 10.6257 25.3314 9.79221 25.9148C8.54198 26.7483 7.16673 26.1649 6.91668 24.6646C6.74998 23.8311 6.41659 23.4144 5.74979 23.331C4.29119 23.1227 4.45789 25.4564 6.04152 27.0401C7.62514 28.582 9.70887 28.6654 11.3758 27.2484L12.5427 26.2482L12.6678 19.7054C12.7928 13.2875 12.8344 13.2041 13.8346 12.7457C15.1265 12.1623 15.1682 10.7453 13.918 10.7453C13.3762 10.7453 12.6678 10.9954 12.2927 11.2871Z");
        svgHerb.getStyleClass().add("icon");
        Tooltip.install(svgHerb,new Tooltip("Aplicar Herbicida\nAplica-se apenas a elementos do tipo flora, matando o elemento selecionado."));

        ClickableSVG svgSun = new ClickableSVG();
        svgSun.setContent("M13 3.25C13 4.75 13.25 6 13.5 6C13.8 6 14 4.75 14 3.25C14 1.75 13.8 0.5 13.5 0.5C13.25 0.5 13 1.75 13 3.25ZM3.5 4.89999C3.5 5.59999 6.95 8.69999 7.35 8.34999C7.5 8.14999 6.9 7.19999 6 6.24999C4.35 4.54999 3.5 4.09999 3.5 4.89999ZM21 6.24999C20.1 7.19999 19.5 8.14999 19.65 8.34999C20.05 8.69999 23.5 5.59999 23.5 4.89999C23.5 4.09999 22.65 4.54999 21 6.24999ZM9.80002 8.45C5.35002 11.4 5.35002 17.6 9.80002 20.55C10.7 21.1 12.15 21.5 13.5 21.5C20.6 21.5 23.15 12.4 17.2 8.45C15.3 7.2 11.7 7.2 9.80002 8.45ZM0 14.5C0 14.75 1.15 15 2.5 15C3.9 15 5 14.75 5 14.5C5 14.2 3.9 14 2.5 14C1.15 14 0 14.2 0 14.5ZM22 14.5C22 14.75 23.15 15 24.5 15C25.9 15 27 14.75 27 14.5C27 14.2 25.9 14 24.5 14C23.15 14 22 14.2 22 14.5ZM5.00005 22.25C4.10005 23.2 3.50005 24.15 3.65005 24.35C4.05005 24.7 7.50005 21.6 7.50005 20.9C7.50005 20.1 6.65005 20.55 5.00005 22.25ZM19.5 20.9C19.5 21.6 22.95 24.7 23.35 24.35C23.5 24.15 22.9 23.2 22 22.25C20.35 20.55 19.5 20.1 19.5 20.9ZM13 25.75C13 27.25 13.25 28.5 13.5 28.5C13.8 28.5 14 27.25 14 25.75C14 24.25 13.8 23 13.5 23C13.25 23 13 24.25 13 25.75Z");
        svgSun.setFill(Color.web("#5A508C"));
        svgSun.getStyleClass().add("icon");
        Tooltip.install(svgSun,new Tooltip("Aplicar Sol\nDurante 10 unidades de tempo a flora ganha força ao dobro da velocidade e a fauna desloca-se a metade da velocidade."));

        Region spacer = new Region();
        spacer.setMinWidth(0);
        spacer.setMaxWidth(0);

        Rectangle separateBar = new Rectangle(2, 35, Color.web("#5A508C"));

        topPanel = new HBox();
        topPanel.setMinHeight(35);
        topPanel.setSpacing(10);
        topPanel.getStyleClass().add("primary-background");
        topPanel.setAlignment(Pos.CENTER_LEFT);
        topPanel.getChildren().addAll(spacer,svgPlay,svgSnapShot,svgRewind,separateBar,svgApplyStrength,svgHerb,svgSun);

        canvas = new Canvas(model.getWidth() * 2 , model.getHeight() * 2);

        HBox content = new HBox();
        HBox ecosystemPanel = new HBox();
        //ecosystemPanel.getStyleClass().add("primary-background");
        ecosystemPanel.getChildren().addAll(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Desenhar um retângulo vermelho no canvas
        gc.setFill(javafx.scene.paint.Color.RED);

        // Preencher todo o canvas com a cor definida
        gc.fillRect(0, 0, 50,50);

        HBox.setHgrow(ecosystemPanel, Priority.ALWAYS);
        VBox.setVgrow(ecosystemPanel, Priority.ALWAYS);
        HBox.setMargin(ecosystemPanel, new Insets(10));


        SideBar sidebar = new SideBar();

        content.getChildren().addAll(ecosystemPanel,sidebar);

       

        //root.setPrefWidth(Region.USE_COMPUTED_SIZE);
        //root.setPrefHeight(Region.USE_COMPUTED_SIZE);

        root.getChildren().addAll(topPanel,content);


        HBox.setHgrow(sidebar, Priority.ALWAYS);
       
        System.out.printf("ecosystem width : %d , height : %d\n",model.getWidth(),model.getHeight()); 

        //sidebar margin , sidebar width +  content padding + ecosystem width
        int newWidth = 18 +  (int)200 + 10*2 + model.getWidth() * 2;
        //10 + windowsBar + IconsBar + padding + ecosystem height
        int newHeight = 10 + 30 + 35 + 10 *2 + model.getHeight()*2;
        primaryStage.setWidth(newWidth);
        primaryStage.setHeight(newHeight);
        System.out.printf("newWidth %d %d\n",(int)newWidth,(int)newHeight);


        new AnimationTimer() {
                private static final long ONE_SECOND_NANO = 1_000_000_000L;
                private static final long FRAME_DURATION = ONE_SECOND_NANO / 60; // 60 fps
                private long lastTick = 0;

                @Override
                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        onUpdate(gc);
                        return;
                    }

                    if (now - lastTick > FRAME_DURATION) {
                        lastTick = now;
                        onUpdate(gc);
                    }
                }

        }.start();
    }


    double teste = 0;

    private void onUpdate(GraphicsContext gc)
    {
        //clean background
        gc.setFill(Color.web("#373054"));
        gc.fillRect(0, 0,model.getWidth()*2,model.getHeight()*2);


           gc.setFill(Color.RED);
        gc.fillRect(0, (int)teste, 50,50);
        teste += 0.1;


    }

    private void registerHandlers()
    {
       
    }
}
