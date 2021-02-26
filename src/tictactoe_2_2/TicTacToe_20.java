package tictactoe_2_2;

import animatefx.animation.BounceInUp;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class TicTacToe_20 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
       // XO_PlayerBase root = new XO_PlayerBase(primaryStage);
        mainMenuBase newRoot= new mainMenuBase(primaryStage);
        
        Scene scene = new Scene(newRoot, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toString());
        primaryStage.setTitle("Tic Tac Toe!");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        new BounceInUp(newRoot).play();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
