package tictactoe_2_2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XO_Server {
//  declare server socket

    ServerSocket server_socket;
    DataInputStream dis1, dis2;
    PrintStream ps1, ps2;
    Socket player1;
    Socket player2;
    int id = 0;  ///specify
    DataBaseCon db = new DataBaseCon();
    ///////////////////////////////////
    //servser constructor
    public XO_Server() {
        id =db.loadidFromDB(id);
        try {
            //instantiate server socket
            server_socket = new ServerSocket(2000);
            while (true) //work forever
            {
                System.out.println("Waiting for 1st player");
                player1 = server_socket.accept();       //retern new socket for client1
                System.out.println("Accept a socket1");
                dis1 = new DataInputStream(player1.getInputStream());
                String sd1 = dis1.readLine();                       // read string from client1
                System.out.println(sd1);
                if (sd1.equalsIgnoreCase("two")) //if string is two
                {
                    ps1 = new PrintStream(player1.getOutputStream());
                    ps1.println("X");                       // send X to client1

                    System.out.println("Waiting for 2nd player");
                    player2 = server_socket.accept();

                    dis2 = new DataInputStream(player2.getInputStream());
                    String sd2 = dis2.readLine();           // read string from client2 //O
                    System.out.println(sd2);
                    ps2 = new PrintStream(player2.getOutputStream());
                    ps2.println("O");                     // send O to client1
                    System.out.println("Start A Game");
                    id++;
                    new PlayerHandler2(player1, player2, id);
                } else if (sd1.equalsIgnoreCase("one")) {
                    System.out.println("Single Double = one && Start a game");
                    id++;
                    new PlayerHandler1(player1, id);
                } else if (sd1.equalsIgnoreCase("load")) {
                    System.out.println("Server Input = Load");
                    new RetrieveHandler(player1);
                }
            }
        } catch (IOException ex) {
            System.out.println("ex");;
        }
    }

    public static void main(String[] args) {
        new XO_Server();
    }
}
