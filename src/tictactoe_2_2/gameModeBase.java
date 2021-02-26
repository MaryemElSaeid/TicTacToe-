package tictactoe_2_2;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class gameModeBase extends AnchorPane {

    protected final ImageView imageView;
    protected final Label label;
    protected final Label singlePlayer;
    protected final Label label1;
    protected final Label label2;
    protected final Label label3;
    protected final Label Offline;
    protected final Label Online;        //these are only refernces
    protected final Label Back;

    public gameModeBase(Stage primaryStage) {

        imageView = new ImageView();          //now refernece is pointing to an object
        label = new Label();
        singlePlayer = new Label();
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        Offline = new Label();
        Online = new Label();
        Back = new Label();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        imageView.setFitHeight(400.0);
        imageView.setFitWidth(601.0);
        imageView.setLayoutY(-1.0);
        imageView.setImage(new Image(getClass().getResource("ezgif-7-b81bb51dd75b.gif").toExternalForm()));

        label.setLayoutX(210.0);
        label.setLayoutY(90.0);
        label.setOpacity(0.5);
        label.setPrefHeight(45.0);
        label.setPrefWidth(197.0);
        label.setStyle("-fx-background-color: grey; -fx-background-radius: 50px;");

        singlePlayer.setAlignment(javafx.geometry.Pos.CENTER);
        singlePlayer.setLayoutX(216.0);
        singlePlayer.setLayoutY(90.0);
        singlePlayer.setPrefHeight(45.0);
        singlePlayer.setPrefWidth(190.0);
        singlePlayer.setText("Single Player (VS A.I.)");
        singlePlayer.setFont(new Font("Arial Rounded MT Bold", 17.5));

        label1.setLayoutX(212.0);
        label1.setLayoutY(150.0);
        label1.setOpacity(0.5);
        label1.setPrefHeight(45.0);
        label1.setPrefWidth(197.0);
        label1.setStyle("-fx-background-color: grey; -fx-background-radius: 50px;");

        label2.setLayoutX(210.0);
        label2.setLayoutY(210.0);
        label2.setOpacity(0.5);
        label2.setPrefHeight(45.0);
        label2.setPrefWidth(197.0);
        label2.setStyle("-fx-background-color: grey; -fx-background-radius: 50px;");

        label3.setLayoutX(210.0);
        label3.setLayoutY(300.0);
        label3.setOpacity(0.5);
        label3.setPrefHeight(45.0);
        label3.setPrefWidth(197.0);
        label3.setStyle("-fx-background-color: grey; -fx-background-radius: 50px;");

        Offline.setAlignment(javafx.geometry.Pos.CENTER);
        Offline.setLayoutX(214.0);
        Offline.setLayoutY(150.0);
        Offline.setPrefHeight(45.0);
        Offline.setPrefWidth(190.0);
        Offline.setText("Multiplayer (Offline)");
        Offline.setFont(new Font("Arial Rounded MT Bold", 18.0));

        Online.setAlignment(javafx.geometry.Pos.CENTER);
        Online.setLayoutX(214.0);
        Online.setLayoutY(210.0);
        Online.setPrefHeight(45.0);
        Online.setPrefWidth(190.0);
        Online.setText("Multiplayer (Online)");
        Online.setFont(new Font("Arial Rounded MT Bold", 18.0));

        Back.setAlignment(javafx.geometry.Pos.CENTER);
        Back.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        Back.setLayoutX(216.0);
        Back.setLayoutY(300.0);
        Back.setPrefHeight(45.0);
        Back.setPrefWidth(190.0);
        Back.setText("Back");
        Back.setFont(new Font("Arial Rounded MT Bold", 18.0));

        getChildren().add(imageView);
        getChildren().add(label);
        getChildren().add(singlePlayer);
        getChildren().add(label1);
        getChildren().add(label2);
        getChildren().add(label3);
        getChildren().add(Offline);
        getChildren().add(Online);
        getChildren().add(Back);
        
        singlePlayer.setStyle("-fx-cursor:hand;");
        Online.setStyle("-fx-cursor:hand;");
        Offline.setStyle("-fx-cursor:hand;");
        Back.setStyle("-fx-cursor:hand;");
        
        singlePlayer.setOnMouseClicked((mouseEvent) -> {
        newGameBase root = new newGameBase(primaryStage, "single");
        primaryStage.setScene(new Scene(root));
        new SlideInRight(root).play();
    });
        
        Online.setOnMouseClicked((mouseEvent) -> {
        newGameBase root = new newGameBase(primaryStage, "online");
        primaryStage.setScene(new Scene(root));
        new SlideInRight(root).play();
        
    });
        Offline.setOnMouseClicked((mouseEvent) -> {
        newGameBase root = new newGameBase(primaryStage, "offline");
        primaryStage.setScene(new Scene(root));
        new SlideInRight(root).play();
        
    });
        Back.setOnMouseClicked((mouseEvent) -> {
        mainMenuBase newRoot= new mainMenuBase(primaryStage);
        primaryStage.setScene(new Scene(newRoot));
        new SlideInLeft(newRoot).play();
        
    });

    }
}
