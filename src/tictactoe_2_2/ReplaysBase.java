package tictactoe_2_2;

import animatefx.animation.SlideInLeft;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ReplaysBase extends AnchorPane implements Runnable {

    DataBaseCon db;
    protected final ImageView imageView;
    protected final Label Player1;
    protected final Label Player2;
    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final Button btn1;
    protected final Button btn2;
    protected final Button btn3;
    protected final Button btn4;
    protected final Button btn5;
    protected final Button btn6;
    protected final Button btn7;
    protected final Button btn8;
    protected final Button btn9;
    protected final Label label;
    protected final Label Back;
    protected final Label Score1;
    protected final Label Score2;
    protected final ComboBox comboBox;
    protected final Label Player21;
    String idValue;
    String[] arrOfStr;
    String[] arrOfIds;
    int m;

    volatile boolean flag;
    Button[][] btns;

      Thread th;
      Socket sck;
      PrintStream ps;
      DataInputStream dis;
      
    public ReplaysBase(Stage primaryStage) {
          

        imageView = new ImageView();
        Player1 = new Label();
        Player2 = new Label();
        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        btn1 = new Button();
        btn2 = new Button();
        btn3 = new Button();
        btn4 = new Button();
        btn5 = new Button();
        btn6 = new Button();
        btn7 = new Button();
        btn8 = new Button();
        btn9 = new Button();
        label = new Label();
        Back = new Label();
        Score1 = new Label();
        Score2 = new Label();
        comboBox = new ComboBox();
        Player21 = new Label();
        btns = new Button[3][3];
        db = new DataBaseCon();

        btns[0][0] = btn1;
        btns[0][1] = btn2;
        btns[0][2] = btn3;
        btns[1][0] = btn4;
        btns[1][1] = btn5;
        btns[1][2] = btn6;
        btns[2][0] = btn7;
        btns[2][1] = btn8;
        btns[2][2] = btn9;

        
        init();
        
        sck = new Socket();
        
        th = new Thread(this);
        
        try {
            sck = new Socket(InetAddress.getLocalHost(), 2000);
            dis = new DataInputStream(sck.getInputStream());            
            ps = new PrintStream(sck.getOutputStream());
            ps.println("load");

            ///////Get combobox data from retrieve class
            String idString = dis.readLine();
            arrOfIds = idString.split("\\,");
            for (int i=0; i<arrOfIds.length; i++) {
                comboBox.getItems().add(arrOfIds[i]);  //comboBox filled with Id's
            }
            System.out.println("Filling ComboBox from Replays");
            //.....................
            

            //COMBOBOX SELECTION
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    //casting object to integer
                    idValue = comboBox.getValue().toString();
                    clearBoard();
                    activateBoard();
                    flag = true;
//                    ReplaysBase r = new ReplaysBase(primaryStage);
                    ps.println(idValue);
                    System.out.println("combobox item selected " + idValue);
//                    th.start();
                }
            };
            comboBox.setOnAction(event);
                        
            /*String scoreX = dis.readLine();
            String scoreO = dis.readLine();
            String Mode = dis.readLine();*/
            
          th.start();
        } catch (IOException ex) {
            Alert serverAlert = new Alert(Alert.AlertType.INFORMATION);
                serverAlert.setTitle("Server maybe sleeping");
                serverAlert.setHeaderText("");
                serverAlert.setContentText("Server is down, Plz try again later");
                serverAlert.showAndWait();
                System.exit(0);
                
        }
        Back.setStyle("-fx-cursor:hand;");
        Back.setOnMouseClicked((mouseEvent) -> {
            mainMenuBase root = new mainMenuBase(primaryStage);
            primaryStage.setScene(new Scene(root));
            new SlideInLeft(root).play();
            
        });
        
    }

   @Override
    public void run() {   
        String seq="";
        while (true) {            
            try {
                seq = dis.readLine();
                arrOfStr = seq.split("\\,");
                System.out.println("Final Send "+seq);
            } catch (IOException ex) {
                try {
                    sck.close();
                    dis.close();
                    ps.close();
                    System.out.println("Server Closed!!!!");
                    Platform.runLater(()-> {
                        Alert closingAlert = new Alert(Alert.AlertType.ERROR);
                        closingAlert.setTitle("Oh No");
                        closingAlert.setHeaderText("");
                        closingAlert.setContentText("Oops Server has gone down, try to connect later");
                        closingAlert.setOnCloseRequest((DialogEvent event) -> {
                        Platform.exit();
                        } );
                        closingAlert.showAndWait();
                    } );
                    break;
                } catch (IOException ex1) {
                    Logger.getLogger(ReplaysBase.class.getName()).log(Level.SEVERE, null, ex1);
                }                    
                Logger.getLogger(ReplaysBase.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (flag == true) {
                System.out.println("flag is true");
                m = 0;
                while (m < arrOfStr.length) {
                    System.out.println("inloop");
                    System.out.println(arrOfStr[m]);
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            Button btn = btns[i][j];
                            System.out.println("inside while loop");
                            if (btn.getId().equals(arrOfStr[m])) {
                                Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    if (arrOfStr[m + 1].equals("X")) {
                                        btn.setStyle("-fx-text-fill: blue");                                    
                                    }
                                    if (arrOfStr[m + 1].equals("O")) {
                                        btn.setStyle("-fx-text-fill: red");                                  
                                    }
                                    btn.setText(arrOfStr[m + 1]);
                                    }
                                 });        
                            try {                                                               
                                th.sleep(1000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }                                    
                            break;
                            }                            
                        }
                    }
                 m = m + 2;
                }                
                checkForWin();
                deactivateBoard();
                flag = false;
            }
        }        
    }

    //////////////////////////ps.....set buttons 
    void init() {

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        imageView.setFitHeight(408.0);
        imageView.setFitWidth(631.0);
        imageView.setLayoutY(-5.0);
        imageView.setOpacity(0.67);
        imageView.setImage(new Image(getClass().getResource("file.gif").toExternalForm()));

        Player1.setAlignment(javafx.geometry.Pos.CENTER);
        Player1.setLayoutX(35.0);
        Player1.setLayoutY(121.0);
        Player1.setText("Player 1");
        Player1.setFont(new Font("Arial Rounded MT Bold", 20.0));

        Player2.setAlignment(javafx.geometry.Pos.CENTER);
        Player2.setLayoutX(513.0);
        Player2.setLayoutY(121.0);
        Player2.setText("Player 2");
        Player2.setFont(new Font("Arial Rounded MT Bold", 20.0));

        AnchorPane.setBottomAnchor(gridPane, 66.0);
        AnchorPane.setLeftAnchor(gridPane, 150.0);
        AnchorPane.setRightAnchor(gridPane, 150.0);
        AnchorPane.setTopAnchor(gridPane, 88.0);
        gridPane.setLayoutX(135.0);
        gridPane.setLayoutY(88.0);
        gridPane.setPrefHeight(249.0);
        gridPane.setPrefWidth(331.0);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(100.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(100.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(100.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(30.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(30.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        btn1.setMnemonicParsing(false);
        btn1.setId("btn1");
        btn1.setPrefHeight(87.0);
        btn1.setPrefWidth(204.0);
        btn1.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn1.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn1.setFont(new Font("Comic Sans MS", 40.0));

        GridPane.setColumnIndex(btn2, 1);
        btn2.setId("btn2");
        btn2.setMnemonicParsing(false);
        btn2.setPrefHeight(89.0);
        btn2.setPrefWidth(110.0);
        btn2.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn2.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn2.setFont(new Font("Comic Sans MS", 40.0));

        GridPane.setColumnIndex(btn3, 2);
        btn3.setId("btn3");
        btn3.setMnemonicParsing(false);
        btn3.setPrefHeight(81.0);
        btn3.setPrefWidth(151.0);
        btn3.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn3.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn3.setFont(new Font("Comic Sans MS", 40.0));

        GridPane.setRowIndex(btn4, 1);
        btn4.setId("btn4");
        btn4.setMnemonicParsing(false);
        btn4.setPrefHeight(89.0);
        btn4.setPrefWidth(161.0);
        btn4.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn4.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn4.setFont(new Font("Comic Sans MS", 40.0));

        GridPane.setColumnIndex(btn5, 1);
        btn5.setId("btn5");
        GridPane.setRowIndex(btn5, 1);
        btn5.setMnemonicParsing(false);
        btn5.setPrefHeight(79.0);
        btn5.setPrefWidth(110.0);
        btn5.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn5.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn5.setFont(new Font("Comic Sans MS", 40.0));

        GridPane.setColumnIndex(btn6, 2);
        btn6.setId("btn6");
        GridPane.setRowIndex(btn6, 1);
        btn6.setMnemonicParsing(false);
        btn6.setPrefHeight(89.0);
        btn6.setPrefWidth(143.0);
        btn6.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn6.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn6.setFont(new Font("Comic Sans MS", 40.0));

        GridPane.setRowIndex(btn7, 2);
        btn7.setId("btn7");
        btn7.setMnemonicParsing(false);
        btn7.setPrefHeight(82.0);
        btn7.setPrefWidth(181.0);
        btn7.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn7.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn7.setFont(new Font("Comic Sans MS", 40.0));

        GridPane.setColumnIndex(btn8, 1);
        GridPane.setRowIndex(btn8, 2);
        btn8.setId("btn8");
        btn8.setMnemonicParsing(false);
        btn8.setPrefHeight(87.0);
        btn8.setPrefWidth(174.0);
        btn8.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn8.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn8.setFont(new Font("Comic Sans MS", 40.0));

        GridPane.setColumnIndex(btn9, 2);
        GridPane.setRowIndex(btn9, 2);
        btn9.setId("btn9");
        btn9.setMnemonicParsing(false);
        btn9.setPrefHeight(89.0);
        btn9.setPrefWidth(152.0);
        btn9.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn9.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn9.setFont(new Font("Comic Sans MS", 40.0));

        label.setLayoutX(25.0);
        label.setLayoutY(345.0);
        label.setOpacity(0.5);
        label.setPrefHeight(36.0);
        label.setPrefWidth(100.0);
        label.setStyle("-fx-background-color: grey; -fx-background-radius: 50px;");

        Back.setAlignment(javafx.geometry.Pos.CENTER);
        Back.setLayoutX(25.0);
        Back.setLayoutY(344.0);
        Back.setPrefHeight(36.0);
        Back.setPrefWidth(100.0);
        Back.setText("Back");
        Back.setFont(new Font("Arial Rounded MT Bold", 20.0));

        Score1.setAlignment(javafx.geometry.Pos.CENTER);
        Score1.setLayoutX(64.0);
        Score1.setLayoutY(184.0);
        Score1.setText("0");
        Score1.setFont(new Font("Arial Rounded MT Bold", 20.0));

        Score2.setAlignment(javafx.geometry.Pos.CENTER);
        Score2.setLayoutX(548.0);
        Score2.setLayoutY(184.0);
        Score2.setText("0");
        Score2.setFont(new Font("Arial Rounded MT Bold", 20.0));

        comboBox.setLayoutX(462.0);
        comboBox.setLayoutY(34.0);
        comboBox.setPrefHeight(25.0);
        comboBox.setPrefWidth(155.0);
        comboBox.setPromptText("List of Played Games");
        comboBox.setStyle("-fx-background-color: #0099cc; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: black;");
        
        Player21.setAlignment(javafx.geometry.Pos.CENTER);
        Player21.setLayoutX(246.0);
        Player21.setLayoutY(25.0);
        Player21.setText("Replays");
        Player21.setFont(new Font("Arial Rounded MT Bold", 36.0));

        getChildren().add(imageView);
        getChildren().add(Player1);
        getChildren().add(Player2);
        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getChildren().add(btn1);
        gridPane.getChildren().add(btn2);
        gridPane.getChildren().add(btn3);
        gridPane.getChildren().add(btn4);
        gridPane.getChildren().add(btn5);
        gridPane.getChildren().add(btn6);
        gridPane.getChildren().add(btn7);
        gridPane.getChildren().add(btn8);
        gridPane.getChildren().add(btn9);
        getChildren().add(gridPane);
        getChildren().add(label);
        getChildren().add(Back);
        getChildren().add(Score1);
        getChildren().add(Score2);
        getChildren().add(comboBox);
        getChildren().add(Player21);

    }

    void checkForWin() {
        for (int i = 0; i < 3; i++) {
            if (btns[i][0].getText().equalsIgnoreCase("X") && btns[i][0].getText().equalsIgnoreCase(btns[i][1].getText()) && btns[i][1].getText().equalsIgnoreCase(btns[i][2].getText())) {
                btns[i][0].setStyle("-fx-background-color: green");
                btns[i][1].setStyle("-fx-background-color: green");
                btns[i][2].setStyle("-fx-background-color: green");

            }
            if (btns[0][i].getText().equalsIgnoreCase("X") && btns[0][i].getText().equalsIgnoreCase(btns[1][i].getText()) && btns[1][i].getText().equalsIgnoreCase(btns[2][i].getText())) {
                btns[0][i].setStyle("-fx-background-color: green");
                btns[1][i].setStyle("-fx-background-color: green");
                btns[2][i].setStyle("-fx-background-color: green");

            }

            if (btns[i][0].getText().equalsIgnoreCase("O") && btns[i][0].getText().equalsIgnoreCase(btns[i][1].getText()) && btns[i][1].getText().equalsIgnoreCase(btns[i][2].getText())) {
                btns[i][0].setStyle("-fx-background-color: green");
                btns[i][1].setStyle("-fx-background-color: green");
                btns[i][2].setStyle("-fx-background-color: green");

            }
            if (btns[0][i].getText().equalsIgnoreCase("O") && btns[0][i].getText().equalsIgnoreCase(btns[1][i].getText()) && btns[1][i].getText().equalsIgnoreCase(btns[2][i].getText())) {
                btns[0][i].setStyle("-fx-background-color: green");
                btns[1][i].setStyle("-fx-background-color: green");
                btns[2][i].setStyle("-fx-background-color: green");

            }

        }

        if (btns[0][0].getText().equalsIgnoreCase("X") && btns[0][0].getText().equalsIgnoreCase(btns[1][1].getText()) && btns[1][1].getText().equalsIgnoreCase(btns[2][2].getText())) {
            btns[0][0].setStyle("-fx-background-color: green");
            btns[1][1].setStyle("-fx-background-color: green");
            btns[2][2].setStyle("-fx-background-color: green");

        }
        if (btns[2][0].getText().equalsIgnoreCase("X") && btns[2][0].getText().equalsIgnoreCase(btns[1][1].getText()) && btns[1][1].getText().equalsIgnoreCase(btns[0][2].getText())) {
            btns[2][0].setStyle("-fx-background-color: green");
            btns[1][1].setStyle("-fx-background-color: green");
            btns[0][2].setStyle("-fx-background-color: green");

        }

        if (btns[0][0].getText().equalsIgnoreCase("O") && btns[0][0].getText().equalsIgnoreCase(btns[1][1].getText()) && btns[1][1].getText().equalsIgnoreCase(btns[2][2].getText())) {
            btns[0][0].setStyle("-fx-background-color: green");
            btns[1][1].setStyle("-fx-background-color: green");
            btns[2][2].setStyle("-fx-background-color: green");

        }
        if (btns[2][0].getText().equalsIgnoreCase("O") && btns[2][0].getText().equalsIgnoreCase(btns[1][1].getText()) && btns[1][1].getText().equalsIgnoreCase(btns[0][2].getText())) {
            btns[2][0].setStyle("-fx-background-color: green");
            btns[1][1].setStyle("-fx-background-color: green");
            btns[0][2].setStyle("-fx-background-color: green");

        }

    }

    private void deactivateBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = btns[i][j];
                btn.setDisable(true);
            }
        }
    }

    private void activateBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = btns[i][j];
                btn.setDisable(false);
            }
        }
    }

 
    private void clearBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = btns[i][j];
                btn.setText("");
                btn.setStyle("-fx-background-color: thistle");
                btn.setStyle("-fx-border-color: dimgray");
                btn.setStyle("-fx-border-width: 5px");
            }
        }
    }

}
