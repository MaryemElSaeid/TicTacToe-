package tictactoe_2_2;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import static java.awt.SystemColor.text;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class newGameBase extends AnchorPane implements Runnable{

    boolean aniflag=true;
    boolean finish=false;
    String turn;
    Integer play_count = 0;
    Integer player1_score = 0;
    Integer player2_score = 0;
    Socket plyr;
    DataInputStream dis;
    PrintStream ps;
    Thread th;
    String myTurn; // To switch between players in multi mood
    // For AI create player and opponent && Move Method
    static String player = "X", opponent = "O";
    static class Move { int row, col; };
    
    Button[][] btns = new Button[3][3];
    String[][] virtualBoard =
            {
                {
                    "", "", ""
                },
                {
                    "", "", ""
                },
                {
                    "", "", ""
                }
            };
    protected String mode;
    protected final ImageView imageView;
    protected final Label gameId;
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
    protected final Label label0;
    protected final Label playAgain;
    protected final Label Score1;
    protected final Label Score2;

    public newGameBase(Stage primaryStage, String gameMode) {

        mode=gameMode;
        imageView = new ImageView();
        gameId = new Label();
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
        label0 = new Label();
        playAgain = new Label();
        Score1 = new Label();
        Score2 = new Label();
        
      init();  
        Back.setOnMouseClicked((mouseEvent) -> {
            if(finish) {
               ps.println("finish"); 
            }
            
            gameModeBase root = new gameModeBase(primaryStage);
            primaryStage.setScene(new Scene(root));
            new SlideInLeft(root).play();
        });
        
        playAgain.setOnMouseClicked((mouseEvent) -> {
            if(finish) {
               ps.println("finish"); 
            }
            
            play_count = 0;                
            clearBoard();
            clearVirtualBoard();
            activateBoard();
            gridPane.getChildren().remove(gridPane.lookup("#txt2"));
            gridPane.getChildren().removeAll(gridPane.lookup("#txt"));
            gridPane.getChildren().remove(gridPane.lookup("#image"));
            gridPane.getChildren().remove(gridPane.lookup("#txt3"));

            turn = "X";
            if (mode.equalsIgnoreCase("online")) 
            {
                    checkMyTurn();
            }
        });

        turn="X";
        if (mode.equalsIgnoreCase("single"))
        {
            clearBoard(); 
            activateBoard();
            turn="X";
            
            
            
            th = new Thread(newGameBase.this);
            
            
            try {
                plyr = new Socket(InetAddress.getLocalHost(), 2000);
                dis = new DataInputStream(plyr.getInputStream());
                ps = new PrintStream(plyr.getOutputStream());
                ps.println("one");
                System.out.println("Senddd one-interface");
                th.start();
                System.out.println("Thread Start for single mode");
            } catch (IOException ex) {
                Alert serverAlert = new Alert(Alert.AlertType.INFORMATION);
                serverAlert.setTitle("Server maybe sleeping");
                serverAlert.setHeaderText("");
                serverAlert.setContentText("Server is down, Plz try again later");
                serverAlert.showAndWait();
                System.exit(0);
            }
                
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Button btn = btns[i][j];
                    final int x = i;
                    final int y = j;
                    btn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                           if (btn.getText().equalsIgnoreCase("")){
                            if (turn.equalsIgnoreCase("X") && btn.getText().equalsIgnoreCase("")) {
                                virtualBoard[x][y] = "X";
                                ps.println("X");
                                ps.println(btn.getId());
                                //btn.setStyle("-fx-text-fill: blue");
                                //btn.setText("X");
                                play_count++;
                                
                                //isWin();
                                //isDraw(); 
                                
                            }
                            
                            if (virtualWinCheck() == 0) {
                                
                                String board[][] =
                                {
                                        {
                                            virtualBoard[0][0], virtualBoard[0][1], virtualBoard[0][2]
                                        },
                                        {
                                            virtualBoard[1][0], virtualBoard[1][1], virtualBoard[1][2]
                                        },
                                        {
                                            virtualBoard[2][0], virtualBoard[2][1], virtualBoard[2][2]
                                        }
                                };

                                newGameBase.Move bestMove = findBestMove(board);
                                
                                if (play_count < 9)
                                {
                                    virtualBoard[bestMove.row][bestMove.col] = "O";
                                    ps.println("O");
                                    ps.println(btns[bestMove.row][bestMove.col].getId());
                                    play_count++;
                                    
                                    //isWin();
                                    //isDraw();
                                }  
                            }
                        }
                        }});
                }
            }
            
            
        }
        else
        {
            if (mode.equalsIgnoreCase("offline"))
            {
                
                System.out.println("2player Mode");
                clearBoard(); 
                activateBoard();

                th = new Thread(newGameBase.this);
                
                try {
                    plyr = new Socket(InetAddress.getLocalHost(), 2000);
                    dis = new DataInputStream(plyr.getInputStream());
                    ps = new PrintStream(plyr.getOutputStream());
                    ps.println("one");
                    System.out.println("Senddd one-interface");
                } catch (IOException ex) {
                    Alert serverAlert = new Alert(Alert.AlertType.INFORMATION);
                    serverAlert.setTitle("Server maybe sleeping");
                    serverAlert.setHeaderText("");
                    serverAlert.setContentText("Server is down, Plz try again later");
                    serverAlert.showAndWait();
                    System.exit(0);
                }
                for (int i=0; i<3; i++) {
                    for (int j=0; j<3; j++) {
                        Button btn = btns[i][j];
                        btn.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (btn.getText().equalsIgnoreCase("")){
                                    ps.println(turn); 
                                    ps.println(btn.getId());
                                    System.out.println("Send" + turn +" "+ btn.getId());
                                }
                            }
                        });
                    }    
                } 
                th.start();
                System.out.println("Thread Start");
            }
            else
            {
                if (mode.equalsIgnoreCase("online"))
                {
                    
                    clearBoard(); 
                    activateBoard();

                    th = new Thread(newGameBase.this);
                    
                    try {
                        plyr = new Socket(InetAddress.getLocalHost(), 2000);
                        dis = new DataInputStream(plyr.getInputStream());
                        ps = new PrintStream(plyr.getOutputStream());
                        ps.println("two");
                        System.out.println("Send two-interfaces");
                        myTurn = dis.readLine();                       
                        if (myTurn.equalsIgnoreCase(turn)) {
                            activateBoard();
                        } else {
                            deactivateBoard();
                        }
                    } catch (IOException ex) {
                        Alert serverAlert = new Alert(Alert.AlertType.INFORMATION);
                        serverAlert.setTitle("Server maybe sleeping");
                        serverAlert.setHeaderText("");
                        serverAlert.setContentText("Server is down, Plz try again later");
                        serverAlert.showAndWait();
                        System.exit(0);
                    }
                    for (int i=0; i<3; i++) {
                        for (int j=0; j<3; j++) {
                            Button btn = btns[i][j];
                            btn.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    if (btn.getText().equalsIgnoreCase("")){
                                        ps.println(turn); 
                                        ps.println(btn.getId());
                                        System.out.println("Send: " + turn +" "+ btn.getId()); 
                                        
                                    }
                                }
                            });
                        }    
                    } 
                    th.start();
                    System.out.println("Thread Start");
                }
            }
        }//end of if condition
        
        
        primaryStage.setOnHiding(event -> {
            System.exit(0);
        });
        
    }//end of constructor
    @Override
    public void run() {
        System.out.println("RUN");
        while (true) {
            try {
                System.out.println("New Thread Wait");
                String turner = dis.readLine();
                String loc = dis.readLine();
                System.out.println("Recieve: " + turner + " " + loc);
                // Switch to the application Thread to make changes to the interface
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        PrintOnboard(loc, turner);
                    }
                });                               
            } catch (IOException ex) {
                try {
                    plyr.close();
                    dis.close();
                    ps.close();
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
                    System.out.println("ex1");
                }    
            }     
        }
    }
    
    
    synchronized void PrintOnboard(String loc ,String turner)
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                Button btn = btns[i][j];
                if (loc.equalsIgnoreCase(btn.getId()))
                {
                    btn.setText(turner);
                    isWin();
                    isDraw();
                }
            }
        }
    }
    private void playerTurn(){
        if (turn.equalsIgnoreCase("X")) { turn ="O"; }
        else turn ="X";
    }
    void isWin() {
        if (winCheck() == 1) {
            if (turn.equalsIgnoreCase("X")) {
                System.out.println("Player 1 increment");
                player1_score++;
                Score1.setText(player1_score.toString());
                 if (mode.equalsIgnoreCase("online"))
                 {
                    if (myTurn.equalsIgnoreCase("O")){
                        ps.println(turn); ps.println("stop");
                    }
                 }
            } else {
                System.out.println("Player i2 ncrement");
                player2_score++;
                Score2.setText(player2_score.toString());               
            }
            /*
            Alert win = new Alert(Alert.AlertType.INFORMATION);
            win.setTitle("We have a Winner");
            win.setContentText(turn+" wins");
            win.showAndWait();
            */
            if(mode.equalsIgnoreCase("online")){
                if (myTurn.equalsIgnoreCase(turn)){
                    playAnimationWin(myTurn," Wins");
                }
                else
                {
                    playAnimationLose();
                }
            }
            else{
                playAnimationWin(turn," Wins");
            }
            deactivateBoard();
        } else {
            playerTurn();
            if (mode.equalsIgnoreCase("online")) {
                checkMyTurn();
            } 
        }
    }
    
    void checkMyTurn(){
        if (myTurn.equalsIgnoreCase(turn)) {
                     activateBoard();
                 } else {
                     deactivateBoard();
                 } 
    }
    
    int winCheck () {
        for (int i=0; i<3; i++) {
            if (!btns[i][0].getText().equals("") && btns[i][0].getText().equalsIgnoreCase(btns[i][1].getText()) && btns[i][1].getText().equalsIgnoreCase(btns[i][2].getText())) {
                finish = true;
                return 1;
            }
            if (!btns[0][i].getText().equals("") && btns[0][i].getText().equalsIgnoreCase(btns[1][i].getText()) && btns[1][i].getText().equalsIgnoreCase(btns[2][i].getText())) {
                finish = true;
                return 1;
            }
        }
        if (!btns[0][0].getText().equals("") &&btns[0][0].getText().equalsIgnoreCase(btns[1][1].getText()) && btns[1][1].getText().equalsIgnoreCase(btns[2][2].getText())) {
            finish = true;    
            return 1;
        }
        if (!btns[2][0].getText().equals("") &&btns[2][0].getText().equalsIgnoreCase(btns[1][1].getText()) && btns[1][1].getText().equalsIgnoreCase(btns[0][2].getText())) {
            finish = true;    
            return 1;
        }
        return 0;
    }
    
    int virtualWinCheck () {
        for (int i=0; i<3; i++) {
            if (!virtualBoard[i][0].equals("") && virtualBoard[i][0].equalsIgnoreCase(virtualBoard[i][1]) && virtualBoard[i][1].equalsIgnoreCase(virtualBoard[i][2])) {
                return 1;
            }
            if (!virtualBoard[0][i].equals("") && virtualBoard[0][i].equalsIgnoreCase(virtualBoard[1][i]) && virtualBoard[1][i].equalsIgnoreCase(virtualBoard[2][i])) {
                return 1;
            }
        }
        if (!virtualBoard[0][0].equals("") &&virtualBoard[0][0].equalsIgnoreCase(virtualBoard[1][1]) && virtualBoard[1][1].equalsIgnoreCase(virtualBoard[2][2])) {
                return 1;
        }
        if (!virtualBoard[2][0].equals("") &&virtualBoard[2][0].equalsIgnoreCase(virtualBoard[1][1]) && virtualBoard[1][1].equalsIgnoreCase(virtualBoard[0][2])) {
                return 1;
        }
        return 0;
    }
    
    private void isDraw() {
        if (drawCheck()== 1) {
             playAnimationDraw();
        }
    }
    private int drawCheck() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = btns[i][j];
                if (btn.getText().equals("") || winCheck()==1) {  
                    return 0;
                }
            }
        }
        finish = true;
        return 1;
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
    
    private void clearVirtualBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                virtualBoard[i][j] = "";
                
            }
        }
    }
    
    private void deactivateBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = btns[i][j];
                btn.setDisable(true);
            }
        }
    }
    
    private void activateBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = btns[i][j];
                btn.setDisable(false);
            }
        }
    }
    
    // -----------------------------------------------------------------------
    //                               AI Code
    //------------------------------------------------------------------------
    static Boolean isMovesLeft(String board[][]) {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j].equals("")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static int evaluate(String b[][]) {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++){
            if (b[row][0].equalsIgnoreCase(b[row][1]) && b[row][1].equalsIgnoreCase(b[row][2])){
                if (b[row][0].equalsIgnoreCase(player)){
                    return +10;
                } else if (b[row][0].equalsIgnoreCase(opponent)){
                    return -10;
                }
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++){
            if (b[0][col].equalsIgnoreCase(b[1][col]) && b[1][col].equalsIgnoreCase(b[2][col])){
                if (b[0][col].equalsIgnoreCase(player)){
                    return +10;
                } else if (b[0][col].equalsIgnoreCase(opponent)){
                    return -10;
                }
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0].equalsIgnoreCase(b[1][1]) && b[1][1].equalsIgnoreCase(b[2][2])){
            if (b[0][0].equalsIgnoreCase(player)){
                return +10;
            } else if (b[0][0].equalsIgnoreCase(opponent)){
                return -10;
            }
        }

        if (b[0][2].equalsIgnoreCase(b[1][1]) && b[1][1].equalsIgnoreCase(b[2][0])){
            if (b[0][2].equalsIgnoreCase(player)){
                return +10;
            } else if (b[0][2].equalsIgnoreCase(opponent)){
                return -10;
            }
        }
        // Else if none of them have won then return 0
        return 0;
    }
    
    static int minimax(String board[][], int depth, Boolean isMax){
        int score = evaluate(board);
        // If Maximizer has won the game 
        // return his/her evaluated score
        if (score == 10){
            return score;
        }
        // If Minimizer has won the game 
        // return his/her evaluated score
        if (score == -10){
            return score;
        }
        // If there are no more moves and 
        // no winner then it is a tie
        if (isMovesLeft(board) == false){
            return 0;
        }
        // If this maximizer's move
        if (isMax){
            int best = -1000;
            // Traverse all cells
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j].equals("")){
                        // Make the move
                        board[i][j] = player;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(board,
                                depth + 1, !isMax));

                        // Undo the move
                        board[i][j] = "";
                    }
                }
            }
            return best;
        } // If this minimizer's move
        else{
            int best = 1000;
            // Traverse all cells
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j].equals("")){
                        // Make the move
                        board[i][j] = opponent;
                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(board,
                                depth + 1, !isMax));
                        // Undo the move
                        board[i][j] = "";
                    }
                }
            }
            return best;
        }
    }
    
    static newGameBase.Move findBestMove(String board[][]) {
        int bestVal = -1000;
        newGameBase.Move bestMove = new newGameBase.Move();
        bestMove.row = -1;
        bestMove.col = -1;
        // Traverse all cells, evaluate minimax function 
        // for all empty cells. And return the cell 
        // with optimal value.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (board[i][j].equals(""))
                {
                    // Make the move
                    board[i][j] = player;

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, 0, false);

                    // Undo the move
                    board[i][j] = "";

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        //System.out.printf("The value of the best Move " + "is : %d\n\n", bestVal);
        return bestMove;
    }

 
    void playAnimationWin(String winner, String state ){
        
        System.out.println("asd");
        Text text = new Text(winner+ state);

        text.setId("txt");
        text.setFont(new Font("Didot", 30));
        text.setFill(javafx.scene.paint.Color.BLACK);
        text.setStroke(javafx.scene.paint.Color.BLACK);
        
        gridPane.setMargin(text, new javafx.geometry.Insets(120));
        
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(javafx.util.Duration.seconds(0.8));
        
        translateTransition.setNode(text);
        translateTransition.setByY(150);
        translateTransition.setCycleCount(-1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
        
        
        try {
           Image image1 =  image1 = new Image(new FileInputStream("./src/tictactoe_2_2/6ob.gif"));
           ImageView imageview1 = new ImageView();
            //Setting image to the image view
            imageview1.setImage(image1);
            imageview1.setId("image");
            gridPane.getChildren().addAll(text,imageview1);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(newGameBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        

       System.out.println("asd");
    }
    
    void playAnimationDraw() {
         Text text2 = new Text("It's a Draw!");
        
        text2.setFont(new Font("Tahoma", 30));
        text2.setFill(Color.BLACK);
        text2.setStroke(Color.BLACK);
        text2.setId("txt2");
        text2.setRotate(30);
        
        gridPane.setMargin(text2, new Insets(100));
        
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(1));
        translateTransition.setNode(text2);
        translateTransition.setByY(230);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.seconds(0.5));
        rotateTransition.setNode(text2);
        rotateTransition.setByAngle(-30);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        
        SequentialTransition sequentialTransition = new SequentialTransition(text2, translateTransition, rotateTransition ); 
        gridPane.getChildren().addAll(text2);
      
        sequentialTransition.play(); 


    }
    
    void playAnimationLose(){
     Text text = new Text("You Lose!");
        
        text.setFont(new Font("Tahoma", 18));
        text.setFill(Color.BLACK);
        text.setStroke(Color.BLACK);
        text.setId("txt3");

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.seconds(1));
        gridPane.setMargin(text, new javafx.geometry.Insets(120));

        scaleTransition.setNode(text);
        scaleTransition.setByY(1.5);
        scaleTransition.setByX(1.5);    
        scaleTransition.setCycleCount(-1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        gridPane.getChildren().addAll(text);

    }
    
    public void init(){
        
          
        btns[0][0] = btn1;
        btns[0][1] = btn2;
        btns[0][2] = btn3;
        btns[1][0] = btn4;
        btns[1][1] = btn5;
        btns[1][2] = btn6;
        btns[2][0] = btn7;
        btns[2][1] = btn8;
        btns[2][2] = btn9;
        
        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        imageView.setFitHeight(408.0);
        imageView.setFitWidth(631.0);
        imageView.setLayoutY(-5.0);
        imageView.setOpacity(0.67);
        imageView.setImage(new Image(getClass().getResource("file.gif").toExternalForm()));

        gameId.setAlignment(javafx.geometry.Pos.CENTER);
        gameId.setLayoutX(263.0);
        gameId.setLayoutY(30.0);
        gameId.setText("Game ID: 0");
        gameId.setFont(new Font("Arial Rounded MT Bold", 20.0));

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

        btn1.setId("btn1");
        btn1.setMnemonicParsing(false);
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
        GridPane.setRowIndex(btn5, 1);
        btn5.setId("btn5");
        btn5.setMnemonicParsing(false);
        btn5.setPrefHeight(79.0);
        btn5.setPrefWidth(110.0);
        btn5.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn5.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn5.setFont(new Font("Comic Sans MS", 40.0));

        GridPane.setColumnIndex(btn6, 2);
        GridPane.setRowIndex(btn6, 1);
        btn6.setId("btn6");
        btn6.setMnemonicParsing(false);
        btn6.setPrefHeight(89.0);
        btn6.setPrefWidth(143.0);
        btn6.setStyle("-fx-background-radius: 10px; -fx-background-color: #0099cc; -fx-border-radius: 10px; -fx-border-color: black;");
        btn6.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        btn6.setFont(new Font("Comic Sans MS", 40.0));

        GridPane.setRowIndex(btn7, 2);
        btn7.setMnemonicParsing(false);
        btn7.setId("btn7");
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

        label0.setAlignment(javafx.geometry.Pos.CENTER);
        label0.setLayoutX(486.0);
        label0.setLayoutY(345.0);
        label0.setOpacity(0.5);
        label0.setPrefHeight(36.0);
        label0.setPrefWidth(130.0);
        label0.setStyle("-fx-background-color: grey; -fx-background-radius: 50px;");

        playAgain.setAlignment(javafx.geometry.Pos.CENTER);
        playAgain.setLayoutX(486.0);
        playAgain.setLayoutY(345.0);
        playAgain.setPrefHeight(36.0);
        playAgain.setPrefWidth(130.0);
        playAgain.setText("Play Again");
        playAgain.setFont(new Font("Arial Rounded MT Bold", 20.0));

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

        getChildren().add(imageView);
        getChildren().add(gameId);
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
        getChildren().add(label0);
        getChildren().add(playAgain);
        getChildren().add(Score1);
        getChildren().add(Score2);
        
        Back.setStyle("-fx-cursor:hand;");
        playAgain.setStyle("-fx-cursor:hand;");
      
        
        
        
    }
    
}




