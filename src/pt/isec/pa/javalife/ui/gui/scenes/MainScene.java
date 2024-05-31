package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.EcosystemManager;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import pt.isec.pa.javalife.ui.gui.FaunaImagesManager;
import pt.isec.pa.javalife.ui.gui.components.ClickableSVG;
import pt.isec.pa.javalife.ui.gui.components.SideBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


import java.io.File;
import java.io.IOException;


public class MainScene extends Scene
{
    EcosystemManager model;
    
    Canvas canvas;
    Button btninspecionar, btnConfigurar;
    Stage primaryStage;

    HBox topPanel; 
    VBox mainpanel;//sidebar;
    SideBar sidebar;

    Button btnSair;


    ClickableSVG svgPlay;
    GraphicsContext gc;

    ClickableSVG svgApplyStrength;
    ClickableSVG svgApplyHerb;
    ClickableSVG svgApplySun;

    ClickableSVG svgImportCsv,svgExportCsv;
    ClickableSVG svgLoadGame,svgSaveGame;
    ClickableSVG svgSnapShot,svgRewind;


    private final String svgStopContent = "M7.03125 21.875H2.34375C1.0498 21.875 0 20.8252 0 19.5312V2.34375C0 1.0498 1.0498 0 2.34375 0H7.03125C8.3252 0 9.375 1.0498 9.375 2.34375V19.5312C9.375 20.8252 8.3252 21.875 7.03125 21.875ZM21.875 19.5312V2.34375C21.875 1.0498 20.8252 0 19.5312 0H14.8438C13.5498 0 12.5 1.0498 12.5 2.34375V19.5312C12.5 20.8252 13.5498 21.875 14.8438 21.875H19.5312C20.8252 21.875 21.875 20.8252 21.875 19.5312Z";
    private final String svgPlayContent = "M20.7227 10.4813L3.53516 0.320197C2.13867 -0.504998 0 0.295783 0 2.3368V22.6542C0 24.4852 1.9873 25.5888 3.53516 24.6708L20.7227 14.5145C22.2559 13.6112 22.2607 11.3846 20.7227 10.4813Z";
    

    public MainScene(Stage primaryStage__,EcosystemManager manager_)
    {
        super(new VBox());
        primaryStage =  primaryStage__;
        model = manager_;
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
        svgPlay = new ClickableSVG();
        svgPlay.setContent(svgStopContent);
        svgPlay.getStyleClass().add("icon");
        Tooltip.install(svgPlay,new Tooltip("Pausar/Continuar simulação"));

        svgSnapShot = new ClickableSVG();
        svgSnapShot.setContent("M3.21814 0.5625C1.92419 0.5625 0.87439 1.6123 0.87439 2.90625V20.0938C0.87439 21.3877 1.92419 22.4375 3.21814 22.4375H23.5306C24.8246 22.4375 25.8744 21.3877 25.8744 20.0938V2.90625C25.8744 1.6123 24.8246 0.5625 23.5306 0.5625H3.21814ZM3.21814 2.125H8.39392C8.55505 2.125 8.68689 2.25684 8.68689 2.41797V3.39453C8.68689 3.55566 8.55505 3.6875 8.39392 3.6875H2.72986C2.56873 3.6875 2.43689 3.55566 2.43689 3.39453V2.90625C2.43689 2.47656 2.78845 2.125 3.21814 2.125ZM24.0189 6.8125H2.72986C2.56873 6.8125 2.43689 6.68066 2.43689 6.51953V4.76172C2.43689 4.60059 2.56873 4.46875 2.72986 4.46875H9.46814L10.9427 2.25684C10.9965 2.17383 11.0892 2.125 11.1869 2.125H23.5306C23.9603 2.125 24.3119 2.47656 24.3119 2.90625V6.51953C24.3119 6.68066 24.1801 6.8125 24.0189 6.8125ZM13.3744 19.7031C10.142 19.7031 7.51501 17.0762 7.51501 13.8438C7.51501 10.6113 10.142 7.98438 13.3744 7.98438C16.6068 7.98438 19.2338 10.6113 19.2338 13.8438C19.2338 17.0762 16.6068 19.7031 13.3744 19.7031ZM13.3744 9.54688C11.0062 9.54688 9.07751 11.4756 9.07751 13.8438C9.07751 16.2119 11.0062 18.1406 13.3744 18.1406C15.7426 18.1406 17.6713 16.2119 17.6713 13.8438C17.6713 11.4756 15.7426 9.54688 13.3744 9.54688ZM11.0306 14.625C10.601 14.625 10.2494 14.2734 10.2494 13.8438C10.2494 12.1201 11.6508 10.7188 13.3744 10.7188C13.8041 10.7188 14.1556 11.0703 14.1556 11.5C14.1556 11.9297 13.8041 12.2812 13.3744 12.2812C12.515 12.2812 11.8119 12.9844 11.8119 13.8438C11.8119 14.2734 11.4603 14.625 11.0306 14.625Z");
        svgSnapShot.getStyleClass().add("icon");
        Tooltip.install(svgSnapShot,new Tooltip("Gravar snapshot\nPermite registar o momento atual da simulação.\nEsta informação nunca é gravada para ficheiro."));
        
        svgRewind = new ClickableSVG();
        svgRewind.setContent("M9.29148 0.209423C8.4151 0.418084 8.33163 1.54485 9.08281 2.58816C9.91746 3.71493 11.0442 3.00548 11.0442 1.33619C11.0442 -0.0827022 10.8356 -0.207899 9.29148 0.209423ZM13.2142 1.41967C13.3812 3.38109 14.0906 3.67321 14.8001 2.00392C15.3843 0.585028 15.3843 0.543296 14.0489 0.125974C13.1308 -0.12442 13.0891 -0.0409555 13.2142 1.41967ZM4.78434 2.33776L3.90796 3.04721L4.90953 4.00705C5.78591 4.84169 6.07803 4.92516 6.78748 4.4661C7.49693 4.04878 7.53866 3.75665 7.2048 2.75508C6.74575 1.41965 6.07803 1.29445 4.78434 2.33776ZM16.9702 2.83855C16.3859 4.50783 17.0536 4.88342 18.3891 3.63146C19.3489 2.71335 19.3906 2.62988 18.7229 2.1291C17.6379 1.33619 17.4292 1.46138 16.9702 2.83855ZM2.15523 4.96689C-1.05815 9.84956 -0.640827 15.8173 3.1568 20.1574C6.16152 23.5377 11.7536 24.7062 16.5111 22.87L18.6812 22.0771L19.3906 22.9952C19.808 23.496 20.2253 23.8298 20.3505 23.7047C20.7261 23.3291 22.7709 17.3196 22.604 17.1527C22.4788 17.0692 20.7678 17.0275 18.7647 17.111L15.1339 17.2362L16.0103 18.3629L16.9284 19.4897L15.6765 20.1574C14.967 20.4913 13.3812 20.7834 11.9623 20.7834C8.70719 20.7834 6.28672 19.5314 4.49224 16.8606C3.282 15.1078 3.11507 14.5236 3.11507 12.0614C3.11507 10.0582 3.36547 8.84799 3.99145 7.67949L4.86783 6.05193L3.82452 5.00862L2.78121 4.00705L2.15523 4.96689ZM20.2671 5.50943C19.0151 6.84486 19.3072 7.22045 21.0183 6.511C22.1868 6.01021 22.2702 5.88502 21.8112 5.2173C21.2687 4.50785 21.1852 4.50785 20.2671 5.50943ZM22.0197 9.0984C20.726 9.64092 20.8095 9.93304 22.3119 9.93304C23.1465 9.93304 23.5638 9.72438 23.5638 9.30706C23.5638 8.59761 23.3552 8.55588 22.0197 9.0984Z");
        svgRewind.getStyleClass().add("icon");
        Tooltip.install(svgRewind,new Tooltip("Restaurar snapshot\nPermite retomar a simulação num ponto salvaguardado anteriormente."));

        svgApplyStrength = new ClickableSVG();
        svgApplyStrength.setContent("M15.1988 23H13.882C13.8019 22.9886 13.7103 22.9545 13.6301 22.9545C12.7942 22.8634 11.9469 22.8179 11.1225 22.6813C10.1034 22.522 9.11867 22.6358 8.16828 23H7.71027C7.68737 22.9772 7.67592 22.9431 7.65302 22.9317C5.48889 21.6343 3.86294 19.8362 2.92401 17.5032C1.79042 14.6467 1.18355 11.6764 1.00034 8.61504C0.977443 8.15982 0.920191 7.7046 0.87439 7.26076V5.34884C0.89729 5.22365 0.931641 5.09847 0.943092 4.97328C1.09195 3.69866 1.57286 2.59476 2.52324 1.6957C3.29042 0.967343 4.1034 0.295893 5.15683 0H5.94691C6.19882 0.102425 6.47363 0.170707 6.69118 0.318654C7.32096 0.773874 7.95073 1.22909 8.52325 1.74122C8.7866 1.96883 8.94691 2.34438 9.06141 2.6858C9.15302 2.95893 9.16447 3.27759 8.83241 3.45967C8.7866 3.48243 8.7637 3.5621 8.7637 3.619C8.695 4.06284 8.47744 4.35873 8.07668 4.39287C7.95073 4.6091 7.88202 4.81395 7.74462 4.91638C7.48126 5.12123 7.18355 5.25779 6.90874 5.49678C6.48508 5.87234 5.54615 5.65611 5.20263 5.20089C5.09958 5.23503 5.00798 5.30331 4.91637 5.34884C4.81332 5.40574 4.69882 5.43988 4.58431 5.4854C4.33241 5.57645 4.25225 5.74715 4.34386 6.00891C4.92783 7.7046 5.695 9.28649 7.04615 10.5383C8.14538 11.5626 8.96981 12.7917 9.55378 14.1687C9.64538 14.385 9.73699 14.5898 9.85149 14.8516C10.6187 10.9139 12.966 9.24097 16.9049 9.77585C16.1148 7.38595 17.2713 5.38298 18.84 4.42702C20.4202 3.47105 22.1492 3.2093 23.9355 3.55072C25.8591 3.91489 26.9698 5.21227 27.5423 7.01039C27.7026 7.49975 27.7713 8.02326 27.8744 8.53538V9.91242C27.7942 10.2538 27.7484 10.5952 27.6454 10.9253C27.05 12.9283 25.6645 14.237 23.8324 15.1361C23.6263 15.2385 23.5232 15.3637 23.4545 15.5685C22.9736 16.9456 22.3095 18.2316 21.4278 19.3924C20.1683 21.0426 18.611 22.2716 16.5385 22.761C16.1148 22.8748 15.6568 22.9203 15.1988 23Z");
        svgApplyStrength.getStyleClass().add("icon");
        svgApplyStrength.getTransforms().add(new Scale(-1, 1, 15, 0));
        Tooltip.install(svgApplyStrength,new Tooltip("Injetar Força\nAplica-se apenas a elementos do tipo fauna, permitindo aumentar a força do elemento selecionado em 50 unidades."));

        svgApplyHerb = new ClickableSVG();
        svgApplyHerb.setContent("M18.2521 1.24359C17.9604 1.53531 17.752 2.61885 17.752 3.66071C17.752 5.9528 18.2938 6.57792 20.1275 6.57792C20.8776 6.57792 21.5027 6.74462 21.5027 6.99466C21.5027 7.20304 21.9611 7.41141 22.5446 7.41141C23.128 7.41141 23.5864 7.20304 23.5864 6.99466C23.5864 6.74462 24.2116 6.57792 24.9617 6.57792C25.7118 6.57792 26.5453 6.36955 26.837 6.07783C27.5038 5.41103 27.5038 1.91038 26.837 1.24359C26.5036 0.910196 24.92 0.743498 22.5446 0.743498C20.1691 0.743498 18.5855 0.910196 18.2521 1.24359ZM25.5868 3.53569C25.7118 4.45252 25.5868 4.4942 22.5862 4.4942C19.7941 4.4942 19.419 4.41085 19.419 3.74406C19.419 2.5355 19.8357 2.3688 22.7529 2.49382C25.2117 2.61885 25.4618 2.7022 25.5868 3.53569ZM1.12389 2.16041C0.748817 2.57715 0.790491 2.8272 1.29058 3.24394C2.0824 3.91073 2.99924 3.20227 2.62417 2.24375C2.29077 1.41027 1.749 1.41027 1.12389 2.16041ZM4.95796 2.24375C4.58289 3.20227 5.49973 3.91073 6.29154 3.24394C6.79163 2.8272 6.83331 2.57715 6.45824 2.16041C5.83312 1.41027 5.29136 1.41027 4.95796 2.24375ZM9.04198 2.16041C8.66691 2.57715 8.70858 2.8272 9.20868 3.24394C10.0005 3.91073 10.9173 3.20227 10.5423 2.24375C10.2089 1.41027 9.66709 1.41027 9.04198 2.16041ZM3.20765 5.07762C2.62421 5.78608 2.91593 6.5779 3.7911 6.5779C4.70793 6.5779 4.99966 5.74441 4.33286 5.07762C3.7911 4.53585 3.66607 4.53585 3.20765 5.07762ZM7.04164 5.16097C6.70825 6.03613 7.58341 6.9113 8.20853 6.28618C8.7503 5.74441 8.4169 4.49418 7.75011 4.49418C7.50006 4.49418 7.20834 4.7859 7.04164 5.16097ZM5.29134 7.82812C4.58288 8.24487 4.99962 9.4951 5.83311 9.4951C6.12483 9.4951 6.4999 9.24506 6.62492 8.91166C6.95832 8.11985 5.99981 7.36971 5.29134 7.82812ZM16.085 9.49513C15.7933 9.99522 15.71 13.5375 15.7516 19.1636L15.8767 28.0403H22.5446H29.2125L29.3375 18.9136C29.4209 12.5374 29.2958 9.62015 28.9624 9.24508C28.5874 8.78666 27.0871 8.66164 22.5029 8.66164C17.0019 8.66164 16.5018 8.74499 16.085 9.49513ZM19.7524 17.955C19.8774 25.0814 19.5857 26.79 18.3771 26.3316C17.8354 26.1232 17.752 24.7896 17.752 18.3718V10.7037L18.7105 10.8287C19.6274 10.9537 19.6274 11.0371 19.7524 17.955ZM4.24944 11.0371C3.8327 12.0373 3.95772 14.4961 4.41614 14.4961C4.66619 14.4961 4.83288 15.7463 4.83288 17.4133C4.83288 19.0802 4.66619 20.3305 4.41614 20.3305C4.20777 20.3305 3.9994 20.7889 3.9994 21.3723C3.9994 22.3309 4.16609 22.4142 5.87474 22.4142C7.5834 22.4142 7.75009 22.3309 7.75009 21.3723C7.75009 20.7889 7.5834 20.3305 7.33335 20.3305C7.0833 20.3305 6.91661 19.0802 6.91661 17.4133C6.91661 15.7463 7.0833 14.4961 7.33335 14.4961C7.87512 14.4961 7.87512 12.7874 7.37502 11.4538C6.91661 10.2869 4.66619 9.95354 4.24944 11.0371ZM12.2927 11.2871C10.9174 12.329 10.6674 13.5792 10.6674 19.4553C10.6674 24.9147 10.6257 25.3314 9.79221 25.9148C8.54198 26.7483 7.16673 26.1649 6.91668 24.6646C6.74998 23.8311 6.41659 23.4144 5.74979 23.331C4.29119 23.1227 4.45789 25.4564 6.04152 27.0401C7.62514 28.582 9.70887 28.6654 11.3758 27.2484L12.5427 26.2482L12.6678 19.7054C12.7928 13.2875 12.8344 13.2041 13.8346 12.7457C15.1265 12.1623 15.1682 10.7453 13.918 10.7453C13.3762 10.7453 12.6678 10.9954 12.2927 11.2871Z");
        svgApplyHerb.getStyleClass().add("icon");
        Tooltip.install(svgApplyHerb,new Tooltip("Aplicar Herbicida\nAplica-se apenas a elementos do tipo flora, matando o elemento selecionado."));

        svgApplySun = new ClickableSVG();
        svgApplySun.setContent("M13 3.25C13 4.75 13.25 6 13.5 6C13.8 6 14 4.75 14 3.25C14 1.75 13.8 0.5 13.5 0.5C13.25 0.5 13 1.75 13 3.25ZM3.5 4.89999C3.5 5.59999 6.95 8.69999 7.35 8.34999C7.5 8.14999 6.9 7.19999 6 6.24999C4.35 4.54999 3.5 4.09999 3.5 4.89999ZM21 6.24999C20.1 7.19999 19.5 8.14999 19.65 8.34999C20.05 8.69999 23.5 5.59999 23.5 4.89999C23.5 4.09999 22.65 4.54999 21 6.24999ZM9.80002 8.45C5.35002 11.4 5.35002 17.6 9.80002 20.55C10.7 21.1 12.15 21.5 13.5 21.5C20.6 21.5 23.15 12.4 17.2 8.45C15.3 7.2 11.7 7.2 9.80002 8.45ZM0 14.5C0 14.75 1.15 15 2.5 15C3.9 15 5 14.75 5 14.5C5 14.2 3.9 14 2.5 14C1.15 14 0 14.2 0 14.5ZM22 14.5C22 14.75 23.15 15 24.5 15C25.9 15 27 14.75 27 14.5C27 14.2 25.9 14 24.5 14C23.15 14 22 14.2 22 14.5ZM5.00005 22.25C4.10005 23.2 3.50005 24.15 3.65005 24.35C4.05005 24.7 7.50005 21.6 7.50005 20.9C7.50005 20.1 6.65005 20.55 5.00005 22.25ZM19.5 20.9C19.5 21.6 22.95 24.7 23.35 24.35C23.5 24.15 22.9 23.2 22 22.25C20.35 20.55 19.5 20.1 19.5 20.9ZM13 25.75C13 27.25 13.25 28.5 13.5 28.5C13.8 28.5 14 27.25 14 25.75C14 24.25 13.8 23 13.5 23C13.25 23 13 24.25 13 25.75Z");
        svgApplySun.setFill(Color.web("#5A508C"));
        svgApplySun.getStyleClass().add("icon");
        Tooltip.install(svgApplySun,new Tooltip("Aplicar Sol\nDurante 10 unidades de tempo a flora ganha força ao dobro da velocidade e a fauna desloca-se a metade da velocidade."));

        svgExportCsv = new ClickableSVG();
        svgExportCsv.setContent("M12.1548 7.17188V0H1.7381C1.04539 0 0.488098 0.564258 0.488098 1.26562V25.7344C0.488098 26.4357 1.04539 27 1.7381 27H19.2381C19.9308 27 20.4881 26.4357 20.4881 25.7344V8.4375H13.4048C12.7173 8.4375 12.1548 7.86797 12.1548 7.17188ZM7.15476 14.7656C7.15476 14.9987 6.96831 15.1875 6.7381 15.1875H6.32143C5.86101 15.1875 5.4881 15.5651 5.4881 16.0312V17.7188C5.4881 18.1849 5.86101 18.5625 6.32143 18.5625H6.7381C6.96831 18.5625 7.15476 18.7513 7.15476 18.9844V19.8281C7.15476 20.0612 6.96831 20.25 6.7381 20.25H6.32143C4.9407 20.25 3.82143 19.1167 3.82143 17.7188V16.0312C3.82143 14.6333 4.9407 13.5 6.32143 13.5H6.7381C6.96831 13.5 7.15476 13.6888 7.15476 13.9219V14.7656ZM9.46049 20.25H8.82143C8.59122 20.25 8.40476 20.0612 8.40476 19.8281V18.9844C8.40476 18.7513 8.59122 18.5625 8.82143 18.5625H9.46049C9.77039 18.5625 10.0027 18.3779 10.0027 18.2134C10.0027 18.1448 9.96362 18.0731 9.89226 18.0109L8.75216 17.0211C8.31101 16.6403 8.05789 16.0993 8.05789 15.5371C8.05789 14.4139 9.04851 13.5005 10.2667 13.5005H10.9048C11.135 13.5005 11.3214 13.6893 11.3214 13.9224V14.7662C11.3214 14.9992 11.135 15.188 10.9048 15.188H10.2657C9.95581 15.188 9.72351 15.3726 9.72351 15.5371C9.72351 15.6057 9.76258 15.6774 9.83393 15.7396L10.974 16.7295C11.4152 17.1102 11.6683 17.6513 11.6683 18.2134C11.6688 19.3361 10.6787 20.25 9.46049 20.25ZM13.8214 13.9219V15.0187C13.8214 16.0877 14.1183 17.1371 14.6548 18.0183C15.1912 17.1376 15.4881 16.0877 15.4881 15.0187V13.9219C15.4881 13.6888 15.6746 13.5 15.9048 13.5H16.7381C16.9683 13.5 17.1548 13.6888 17.1548 13.9219V15.0187C17.1548 16.8898 16.4839 18.6516 15.2652 19.9805C15.1079 20.1519 14.8865 20.25 14.6548 20.25C14.423 20.25 14.2016 20.1519 14.0443 19.9805C12.8256 18.6516 12.1548 16.8898 12.1548 15.0187V13.9219C12.1548 13.6888 12.3412 13.5 12.5714 13.5H13.4048C13.635 13.5 13.8214 13.6888 13.8214 13.9219ZM20.1235 5.53711L15.0246 0.369141C14.7902 0.131836 14.4725 0 14.1391 0H13.8214V6.75H20.4881V6.42832C20.4881 6.09609 20.3579 5.77441 20.1235 5.53711Z");
        svgExportCsv.setFill(Color.web("#5A508C"));
        svgExportCsv.getStyleClass().add("icon");
        Tooltip.install(svgExportCsv,new Tooltip("Exportar para csv\nGuarda os atuais elementos da simulação"));

        svgImportCsv = new ClickableSVG();
        svgImportCsv.setContent("M1.3006 14.0625C0.853723 14.0625 0.488098 14.4141 0.488098 14.8438V16.4062C0.488098 16.8359 0.853723 17.1875 1.3006 17.1875H6.9881V14.0625H1.3006ZM26.1326 5.12695L21.1611 0.341797C20.9326 0.12207 20.6229 0 20.2979 0H19.9881V6.25H26.4881V5.95215C26.4881 5.64453 26.3611 5.34668 26.1326 5.12695ZM18.3631 6.64062V0H8.20685C7.53146 0 6.9881 0.522461 6.9881 1.17188V14.0625H13.4881V10.8789C13.4881 10.1807 14.3666 9.83398 14.8795 10.3271L19.7342 15.0391C20.0693 15.3662 20.0693 15.8887 19.7342 16.2109L14.8744 20.918C14.3615 21.4111 13.483 21.0645 13.483 20.3662V17.1875H6.9881V23.8281C6.9881 24.4775 7.53146 25 8.20685 25H25.2693C25.9447 25 26.4881 24.4775 26.4881 23.8281V7.8125H19.5818C18.9115 7.8125 18.3631 7.28516 18.3631 6.64062Z");
        svgImportCsv.setFill(Color.web("#5A508C"));
        svgImportCsv.getStyleClass().add("icon");
        Tooltip.install(svgImportCsv,new Tooltip("Importar para csv\nGuarda os atuais elementos da simulação"));

        svgSaveGame = new ClickableSVG();
        svgSaveGame.setContent("M1.15493 0.640287C0.990502 0.715108 0.771271 0.91151 0.67079 1.07985C0.488098 1.38849 0.488098 1.51942 0.488098 13.5C0.488098 25.3496 0.488098 25.6209 0.67079 25.9108C1.03618 26.5374 0.497232 26.5 9.9881 26.5C19.479 26.5 18.94 26.5374 19.3054 25.9108C19.479 25.6209 19.4881 25.3777 19.4881 17.1288V8.64604L15.8891 8.61798L12.29 8.58993L12.0434 8.35611C11.5501 7.89784 11.541 7.80432 11.541 4.00719V0.5H6.49868C2.36983 0.509353 1.39243 0.528057 1.15493 0.640287ZM5.51214 13.9676V15.5576H5.14675H4.78137V14.3885V13.2194L4.54387 13.3129C4.16021 13.4719 4.0506 13.4252 4.0506 13.1353C4.0506 12.8921 4.10541 12.8453 4.67175 12.6302C5.00973 12.4993 5.33858 12.387 5.40252 12.387C5.48473 12.3777 5.51214 12.7518 5.51214 13.9676ZM8.17944 12.6489C8.48089 12.9201 8.48089 12.9388 8.50829 13.8554C8.54483 14.9216 8.43521 15.2676 7.96021 15.5201C7.56743 15.7259 7.01935 15.6417 6.70877 15.3331C6.48954 15.1086 6.47127 15.0151 6.44387 14.1453C6.41646 13.5468 6.453 13.1072 6.51694 12.9295C6.76358 12.3309 7.66791 12.1813 8.17944 12.6489ZM10.4448 13.9676V15.5576H10.0794H9.71406V14.3885V13.2194L9.47656 13.3129C9.09291 13.4719 8.98329 13.4252 8.98329 13.1353C8.98329 12.8921 9.0381 12.8453 9.60444 12.6302C9.94243 12.4993 10.2713 12.387 10.3352 12.387C10.4174 12.3777 10.4448 12.7518 10.4448 13.9676ZM12.9112 13.9676V15.5576H12.5458H12.1804V14.3885V13.2194L11.9429 13.3129C11.5593 13.4719 11.4496 13.4252 11.4496 13.1353C11.4496 12.8921 11.5044 12.8453 12.0708 12.6302C12.4088 12.4993 12.7376 12.387 12.8016 12.387C12.8838 12.3777 12.9112 12.7518 12.9112 13.9676ZM15.4597 12.5273C15.5785 12.6115 15.7246 12.8266 15.7977 12.995C15.9621 13.4065 15.9621 14.6317 15.7977 15.0432C15.5328 15.6885 14.6102 15.8381 14.1078 15.3331C13.8886 15.1086 13.8703 15.0151 13.8429 14.1453C13.8064 13.0885 13.8977 12.7237 14.2722 12.518C14.5919 12.3403 15.1948 12.3403 15.4597 12.5273ZM5.7131 17.8863C6.01454 18.1576 6.01454 18.1763 6.04194 19.0928C6.07848 20.1496 5.96887 20.505 5.51214 20.7482C5.17416 20.9259 4.84531 20.9259 4.50733 20.7482C4.08714 20.5237 3.95925 20.1683 3.95925 19.2518C3.95925 17.9986 4.20589 17.6338 5.028 17.6245C5.33858 17.6151 5.48473 17.6712 5.7131 17.8863ZM7.97848 19.205V20.795H7.6131H7.24771V19.6259V18.4568L7.01021 18.5504C6.62656 18.7094 6.51694 18.6626 6.51694 18.3727C6.51694 18.1295 6.57175 18.0827 7.1381 17.8676C7.47608 17.7367 7.80493 17.6245 7.86887 17.6245C7.95108 17.6151 7.97848 17.9892 7.97848 19.205ZM10.6458 17.8863C10.9381 18.1576 10.9472 18.1856 10.9746 19.0367C11.0112 20.0935 10.8833 20.5144 10.4357 20.7482C10.0338 20.9633 9.48569 20.8791 9.17512 20.5705C8.95589 20.346 8.93762 20.2525 8.91021 19.3827C8.88281 18.7842 8.91935 18.3446 8.98329 18.1669C9.22993 17.5683 10.1343 17.4187 10.6458 17.8863ZM12.9934 17.7647C13.1121 17.8489 13.2583 18.064 13.3314 18.2324C13.4958 18.6439 13.4958 19.8691 13.3314 20.2806C13.0665 20.9259 12.1439 21.0755 11.6415 20.5705C11.4222 20.346 11.404 20.2525 11.3766 19.3827C11.34 18.3259 11.4314 17.9612 11.8059 17.7554C12.1256 17.5777 12.7285 17.5777 12.9934 17.7647ZM15.3775 19.205V20.795H15.0121H14.6468V19.6259V18.4568L14.4093 18.5504C14.0165 18.7094 13.916 18.6626 13.916 18.3727C13.916 18.1295 13.9708 18.0734 14.4001 17.9144C14.6559 17.8115 14.9391 17.7086 15.0121 17.6806C15.3684 17.5403 15.3775 17.5683 15.3775 19.205ZM7.21119 13.1446C7.04676 13.3878 7.01023 14.3791 7.15638 14.7626C7.22946 14.959 7.30253 15.0058 7.49436 14.9777C7.70446 14.959 7.75926 14.8842 7.82321 14.5755C7.86888 14.3698 7.87801 13.977 7.85061 13.7058C7.78667 12.9576 7.50349 12.7144 7.21119 13.1446ZM14.6468 13.0324C14.5554 13.0885 14.5006 13.3316 14.4823 13.8554C14.4367 14.7439 14.5463 15.0245 14.9025 14.9777C15.1492 14.9496 15.1492 14.9496 15.1766 14.0705C15.204 13.0324 15.067 12.7611 14.6468 13.0324ZM4.74485 18.382C4.64437 18.5223 4.5987 18.7842 4.60783 19.1583C4.61697 20 4.69004 20.1871 5.00975 20.1871C5.22899 20.1871 5.28379 20.1403 5.35687 19.8597C5.45735 19.4576 5.38427 18.4943 5.23812 18.3166C5.0737 18.1202 4.90927 18.1389 4.74485 18.382ZM9.6775 18.382C9.51307 18.6252 9.47653 19.6165 9.62269 20C9.69576 20.1964 9.76884 20.2432 9.96067 20.2151C10.1708 20.1964 10.2256 20.1216 10.2895 19.8129C10.3809 19.3734 10.2986 18.4755 10.1525 18.2978C10.0063 18.1201 9.83278 18.1482 9.6775 18.382ZM12.1439 18.382C12.0434 18.5223 11.9977 18.8029 11.9977 19.2518C11.9977 20.0093 12.1256 20.2712 12.4545 20.2151C12.7285 20.1777 12.8564 19.6727 12.7742 18.9338C12.7011 18.1856 12.4362 17.9518 12.1439 18.382ZM13.1852 3.77339V7.04677H16.3367H19.4881V6.77555C19.4881 6.6259 19.4425 6.38274 19.3876 6.2331C19.278 5.93382 14.3636 0.836696 13.9891 0.630941C13.8521 0.556121 13.6146 0.500005 13.4593 0.500005H13.1852V3.77339Z");
        svgSaveGame.setFill(Color.web("#5A508C"));
        svgSaveGame.getStyleClass().add("icon");
        Tooltip.install(svgSaveGame,new Tooltip("Gravar\nPermite gravar o estado atual da simulação, indicando o nome do ficheiro"));

        svgLoadGame = new ClickableSVG();
        svgLoadGame.setContent("M1.3006 14.0625C0.853723 14.0625 0.488098 14.4141 0.488098 14.8438V16.4062C0.488098 16.8359 0.853723 17.1875 1.3006 17.1875H6.9881V14.0625H1.3006ZM26.1326 5.12695L21.1611 0.341797C20.9326 0.12207 20.6229 0 20.2979 0H19.9881V6.25H26.4881V5.95215C26.4881 5.64453 26.3611 5.34668 26.1326 5.12695ZM18.3631 6.64062V0H8.20685C7.53146 0 6.9881 0.522461 6.9881 1.17188V14.0625H13.4881V10.8789C13.4881 10.1807 14.3666 9.83398 14.8795 10.3271L19.7342 15.0391C20.0693 15.3662 20.0693 15.8887 19.7342 16.2109L14.8744 20.918C14.3615 21.4111 13.483 21.0645 13.483 20.3662V17.1875H6.9881V23.8281C6.9881 24.4775 7.53146 25 8.20685 25H25.2693C25.9447 25 26.4881 24.4775 26.4881 23.8281V7.8125H19.5818C18.9115 7.8125 18.3631 7.28516 18.3631 6.64062Z");
        svgLoadGame.setFill(Color.web("#5A508C"));
        svgLoadGame.getStyleClass().add("icon");
        Tooltip.install(svgLoadGame,new Tooltip("Abrir\nPermite abrir e continuar uma simulação previamente gravada"));

        Region spacer = new Region();
        spacer.setMinWidth(0);
        spacer.setMaxWidth(0);

        Rectangle separateBar = new Rectangle(2, 35, Color.web("#5A508C"));
        Rectangle separateBar2 = new Rectangle(2, 35, Color.web("#5A508C"));

        btnSair = new Button("Sair");
        btnSair.getStyleClass().add("btn-primary");
        btnSair.getStyleClass().add("text-bold");
        btnSair.setMinHeight(35);

        Region spacerExit = new Region();
        HBox.setHgrow(spacerExit, Priority.ALWAYS);

        topPanel = new HBox();
        topPanel.setMinHeight(35);
        topPanel.setSpacing(10);
        topPanel.getStyleClass().add("primary-background");
        topPanel.setAlignment(Pos.CENTER_LEFT);
        topPanel.getChildren().addAll(spacer,svgPlay,svgSnapShot,svgRewind,separateBar,svgApplyStrength,svgApplyHerb,svgApplySun,separateBar2,svgImportCsv,svgExportCsv,svgLoadGame,svgSaveGame,spacerExit,btnSair);

        canvas = new Canvas(model.getWidth(), model.getHeight());
        gc = canvas.getGraphicsContext2D();

        HBox content = new HBox();
        HBox ecosystemPanel = new HBox();
        ecosystemPanel.getChildren().addAll(canvas);

        HBox.setHgrow(ecosystemPanel, Priority.ALWAYS);
        VBox.setVgrow(ecosystemPanel, Priority.ALWAYS);
        HBox.setMargin(ecosystemPanel, new Insets(10));

        sidebar = new SideBar(model);

        content.getChildren().addAll(ecosystemPanel,sidebar);
        root.getChildren().addAll(topPanel,content);

        HBox.setHgrow(sidebar, Priority.ALWAYS);

        //sidebar margin , sidebar width +  content padding + ecosystem width
        int newWidth = 18 +  (int)200 + 10*2 + model.getWidth();
        //10 + windowsBar + IconsBar + padding + ecosystem height
        int newHeight = 10 + 30 + 35 + 10 *2 + model.getHeight();
        primaryStage.setWidth(newWidth);
        primaryStage.setHeight(newHeight);

    }

    void drawCornerBox(GraphicsContext gc,double x, double y, double w, double h)
    {
        gc.fillRect(x, y, 1, h / 4);// left up
        gc.fillRect(x, (y + h) - (h / 4), 1, h / 4);//left bottom

        gc.fillRect(x + w, y, 1, h / 4);// right up
        gc.fillRect(x + w, (y + h) - (h / 4), 1, h / 4);//right bottom

        gc.fillRect(x, y, w / 4, 1);// ---top left
        gc.fillRect(x + w - (w / 4), y, w / 4, 1);// ---top right

        gc.fillRect(x, y + h, w / 4, 1);// ---bot left
        gc.fillRect(x + w - (w / 4) + 1, y + h, w / 4, 1);// ---bot right
    }

    private void onRender(GraphicsContext gc)
    {
        //clean background
        gc.setFill(Color.web("#373054"));
        gc.fillRect(0, 0,model.getWidth(),model.getHeight());

        ConcurrentMap<Integer, IElement> elements = model.getElements();

        IElement currentElement = null;

        for (IElement element : elements.values()) {
            if (element.getType() == Element.FLORA) {
                // Calcula a cor da flora segundo a sua força (inversamente proporcional)
                Flora fl = (Flora) element;
                double strength = fl.getStrength();
                double alpha = strength / 100.0; // Calcula o nível de transparência
                Color floraColor = Color.GREENYELLOW.deriveColor(0, 1, 1, alpha);

                gc.setFill(floraColor);
                Area area = element.getArea();
                gc.fillRect(area.left(), area.top(), area.right() - area.left(), area.bottom() - area.top());
            } else if (element.getType() == Element.INANIMATE) {
                gc.setFill(Color.GRAY);
            }
            
            if (element.getType() != Element.FAUNA) {
                Area area = element.getArea();
                gc.fillRect(area.left(),
                            area.top(),
                            area.right() - area.left(),
                            area.bottom() - area.top());
            }
        }

        for (IElement element : elements.values()) {
            if(element.getId() == model.getInspectTargetId()){currentElement = element;}
            if (element.getType() == Element.FAUNA) {
                Fauna f = (Fauna) element;
                gc.drawImage(FaunaImagesManager.getImage(f.getImage()),
                             f.getArea().left(),
                             f.getArea().top(),
                             f.getArea().right() - f.getArea().left(),
                             f.getArea().bottom() - f.getArea().top()
                );
            }
        }

        if(currentElement != null)
        {
            Area area = currentElement.getArea();
            gc.setFill(Color.WHITE);

            drawCornerBox(gc,
                area.left() - 2 , //-2 PARA TER ESPAÇAMENTO da entidade e cornerbox 
                area.top()  - 2 ,
                area.right() - area.left() + 3,
                area.bottom() - area.top() + 3); 


            if(model.getCurrentState() == GameEngineState.RUNNING)
            {
                
                sidebar.getTxtX().setText(String.valueOf((int)area.left()));
                sidebar.getTxtY().setText(String.valueOf((int)area.top()));

                sidebar.getTxtEsq().setText(String.valueOf((int)area.left()));
                sidebar.getTxtDir().setText(String.valueOf((int)area.right()));
                sidebar.getTxtCima().setText(String.valueOf((int)area.top()));
                sidebar.getTxtBaixo().setText(String.valueOf((int)area.bottom()));

                if(currentElement.getType() == Element.FAUNA){
                    sidebar.getStrenghtSlider().setValue(((Fauna)currentElement).getStrength());
                }
                else if(currentElement.getType() == Element.FLORA){
                    sidebar.getStrenghtSlider().setValue(((Flora)currentElement).getStrength());
                }

            }
        }
    }

    private void registerHandlers()
    {
        sidebar.getBtnCreateEco().setOnAction(event -> {
            CreateEcosystemScene createEcoSystemScene = new CreateEcosystemScene(primaryStage,model);
            primaryStage.setScene(createEcoSystemScene);
        });

        btnSair.setOnAction(event -> {
            if(model.hasUnsavedChanges()) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Saída");
                alert.setHeaderText("Existem alterações por gravar");
                alert.setContentText("Deseja guardar as alterações antes de sair?");

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));


                ButtonType buttonTypeSave = new ButtonType("Guardar", ButtonBar.ButtonData.YES);
                ButtonType buttonTypeDontSave = new ButtonType("Não Guardar", ButtonBar.ButtonData.NO);
                ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeDontSave, buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == buttonTypeSave) {
                    saveSimulation();
                    model.stopGame();
                    primaryStage.close();
                } else if (result.isPresent() && result.get() == buttonTypeDontSave) {
                    model.stopGame();
                    primaryStage.close();
                } else if (result.isPresent() && result.get() == buttonTypeCancel) {
                    alert.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Saída");
                alert.setHeaderText("Não há alterações por gravar");
                alert.setContentText("Deseja sair da simulação?");

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));

                ButtonType buttonTypeYes = new ButtonType("Sim", ButtonBar.ButtonData.YES);
                ButtonType buttonTypeNo = new ButtonType("Não", ButtonBar.ButtonData.NO);

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == buttonTypeYes) {
                    model.stopGame();
                    primaryStage.close();
                } else if (result.isPresent() && result.get() == buttonTypeNo) {
                    alert.close();
                }
            }
        });

        model.addPropertyChangeListener(EcosystemManager.PROP_DATA, evt -> { sidebar.update(); });

        svgPlay.setOnMouseClicked((MouseEvent event) -> {
            if(model.getCurrentState() == GameEngineState.RUNNING){
                model.pauseGame();
                sidebar.enable();
                svgPlay.setContent(svgPlayContent);
            }
            else if(model.getCurrentState() == GameEngineState.PAUSED){
                model.resumeGame();
                sidebar.disable();
                svgPlay.setContent(svgStopContent);
            }
        }); 

        svgSnapShot.setOnMouseClicked((MouseEvent event) -> {
            try {
                model.saveSnapshot();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }); 

        svgRewind.setOnMouseClicked((MouseEvent event) -> {
            try {
                model.restoreSnapshot();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        svgApplyStrength.setOnMouseClicked((MouseEvent event) -> {
            IElement element = model.getElement(model.getInspectTargetId());
            if(element != null){model.applyStrengthEvent(element);}
        }); 

        svgApplyHerb.setOnMouseClicked((MouseEvent event) -> {
            IElement element = model.getElement(model.getInspectTargetId());
            if(element != null){model.applyHerbicideEvent(element);}
        }); 

        svgApplySun.setOnMouseClicked((MouseEvent event) -> {
            IElement element = model.getElement(model.getInspectTargetId());
            if(element != null){model.applySunEvent(element); }
            System.out.println("SunEvent\n");

        }); 

        svgImportCsv.setOnMouseClicked((MouseEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importar Arquivo CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                if(model.importGame(selectedFile.getAbsolutePath())){
            
                    showAlert(AlertType.INFORMATION, "Sucesso", "O arquivo foi importado com sucesso.");
                }else{
                    showAlert(AlertType.ERROR, "Erro", "Ocorreu um erro ao importar o arquivo.");
                }
            }
        }); 

        svgExportCsv.setOnMouseClicked((MouseEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar Arquivo CSV");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivos de simulação", "*.csv")
            );

            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                String filePath = file.getAbsolutePath(); // Obtém o caminho absoluto do arquivo como String
                if(model.exportGame(filePath)){
                    showAlert(AlertType.INFORMATION, "Sucesso", "O arquivo foi exportado com sucesso.");
                }
                else{
                    showAlert(AlertType.ERROR, "Erro", "Ocorreu um erro ao exportar o arquivo.");
                }
            }
        });

        svgSaveGame.setOnMouseClicked((MouseEvent event) -> {
            saveSimulation();
        });

        svgLoadGame.setOnMouseClicked((MouseEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Carregar Simulação");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivos de simulação", "*.dat")
            );

            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                String filePath = file.getAbsolutePath(); // Obtém o caminho absoluto do arquivo como String
                if(model.load(filePath)){
                    showAlert(AlertType.INFORMATION, "Sucesso", "O arquivo foi carregado com sucesso.");
                }
                else{
                    showAlert(AlertType.ERROR, "Erro", "Ocorreu um erro ao carregar o arquivo.");
                }
            }
        });
        
        model.addPropertyChangeListener(EcosystemManager.PROP_STATE, evt -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    onRender(gc);
                }
            });
        });

        sidebar.getBtnCreateEco().setOnAction(event -> {
            CreateEcosystemScene createEcoSystemScene = new CreateEcosystemScene(primaryStage,model);
            primaryStage.setScene(createEcoSystemScene);
        });

        canvas.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) { 
                double mouseX = event.getX();
                double mouseY = event.getY();

                ConcurrentMap<Integer, IElement> elements = model.getElements();
                for (IElement element : elements.values()) {
                    if (mouseX >= element.getArea().left() && mouseX <= element.getArea().right() &&
                            mouseY >= element.getArea().top() && mouseY <= element.getArea().bottom()) {

                        model.setInspectTarget(element.getId());
                        sidebar.update();
                        sidebar.showInspectTab();

                        break;
                    }
                }
            }
        });

    }

    private void saveSimulation(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Gravar Simulação");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivos de simulação", "*.dat")
        );

        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            String filePath = file.getAbsolutePath(); // Obtém o caminho absoluto do file como String
            if(model.save(filePath)){
                showAlert(AlertType.INFORMATION, "Sucesso", "O ficheiro foi guardado com sucesso.");
            }
            else{
                showAlert(AlertType.ERROR, "Erro", "Ocorreu um erro ao guardar o ficheiro.");
            }
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
