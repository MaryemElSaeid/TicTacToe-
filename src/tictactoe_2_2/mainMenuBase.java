package tictactoe_2_2;

import animatefx.animation.SlideInRight;
import animatefx.animation.SlideOutRight;
import java.net.InetAddress;
import java.net.Socket;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class mainMenuBase extends AnchorPane {

    protected final ImageView imageView;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final Label loadGame;
    protected final Label newGame;
    protected final Label Exit;

    public mainMenuBase(Stage primaryStage) {
   

        imageView = new ImageView();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        loadGame = new Label();
        newGame = new Label();
        Exit = new Label();

       init();

        newGame.setOnMouseClicked((mouseEvent) -> {
        gameModeBase root = new gameModeBase(primaryStage);
        primaryStage.setScene(new Scene(root));
        new SlideInRight(root).play();
        
    });
        loadGame.setOnMouseClicked((mouseEvent) -> {
        ReplaysBase root = new ReplaysBase(primaryStage);
        primaryStage.setScene(new Scene(root));
        new SlideInRight(root).play();
    });
        Exit.setOnMouseClicked((mouseEvent) -> {
             System.exit(0);
    });
    }
    
    
    public void init(){
        
         setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(601.0);

        imageView.setFitHeight(400.0);
        imageView.setFitWidth(602.0);
        imageView.setLayoutY(-1.0);
        imageView.setImage(new Image(getClass().getResource("ezgif-7-b81bb51dd75b.gif").toExternalForm()));

        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(234.0);
        label.setLayoutY(105.0);
        label.setOpacity(0.5);
        label.setPrefHeight(45.0);
        label.setPrefWidth(133.0);
        label.setStyle("-fx-background-radius: 60px; -fx-background-color: grey; -fx-background-repeat: stretch;");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label.setFont(new Font(20.0));

        label0.setAlignment(javafx.geometry.Pos.CENTER);
        label0.setLayoutX(234.0);
        label0.setLayoutY(165.0);
        label0.setOpacity(0.5);
        label0.setPrefHeight(45.0);
        label0.setPrefWidth(133.0);
        label0.setStyle("-fx-background-color: grey; -fx-background-radius: 50px;");
        label0.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label0.setFont(new Font(20.0));

        label1.setAlignment(javafx.geometry.Pos.CENTER);
        label1.setLayoutX(234.0);
        label1.setLayoutY(225.0);
        label1.setOpacity(0.5);
        label1.setPrefHeight(45.0);
        label1.setPrefWidth(133.0);
        label1.setStyle("-fx-background-color: grey; -fx-background-radius: 50px;");
        label1.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label1.setFont(new Font(20.0));

        loadGame.setAlignment(javafx.geometry.Pos.CENTER);
        loadGame.setLayoutX(234.0);
        loadGame.setLayoutY(165.0);
        loadGame.setPrefHeight(45.0);
        loadGame.setPrefWidth(133.0);
        loadGame.setText("Load Game");
        loadGame.setFont(new Font("Arial Rounded MT Bold",20.0));

        newGame.setAlignment(javafx.geometry.Pos.CENTER);
        newGame.setLayoutX(234.0);
        newGame.setLayoutY(105.0);
        newGame.setPrefHeight(45.0);
        newGame.setPrefWidth(133.0);
        newGame.setText("New Game");
        newGame.setFont(new Font("Arial Rounded MT Bold",20.0));

        Exit.setAlignment(javafx.geometry.Pos.CENTER);
        Exit.setLayoutX(234.0);
        Exit.setLayoutY(225.0);
        Exit.setPrefHeight(45.0);
        Exit.setPrefWidth(133.0);
        Exit.setText("Exit");
        Exit.setFont(new Font("Arial Rounded MT Bold",20.0));

        getChildren().add(imageView);
        getChildren().add(label);
        getChildren().add(label0);
        getChildren().add(label1);
        getChildren().add(loadGame);
        getChildren().add(newGame);
        getChildren().add(Exit);
        
        newGame.setStyle("-fx-cursor:hand;");
        loadGame.setStyle("-fx-cursor:hand;");
        Exit.setStyle("-fx-cursor:hand;");

        
        
    }
}
