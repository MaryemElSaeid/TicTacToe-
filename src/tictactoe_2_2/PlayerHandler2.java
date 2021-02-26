/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_2_2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class PlayerHandler2 extends Thread {

    DataInputStream dis1, dis2, dis;
    PrintStream ps1, ps2, ps;
    // Variables for database
    DataBaseCon db;
    String sqnce = "";
    String queryString;
    int id;

    public PlayerHandler2(Socket player1, Socket player2, int id) {
        this.id = id;
        System.out.println("Enter Player Handler for One");
        db = new DataBaseCon();
        db.insertID(id);
        try {
            System.out.println("Players are ready");
            dis1 = new DataInputStream(player1.getInputStream());
            ps1 = new PrintStream(player1.getOutputStream());
            dis2 = new DataInputStream(player2.getInputStream());
            ps2 = new PrintStream(player2.getOutputStream());
            start();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void run() {
        String turn, location;
        while (true) {
            try {

                System.out.println("1st player turn");
                turn = dis1.readLine();
                if (turn.equalsIgnoreCase("finish")) {
                    turn = dis2.readLine();
                    id = db.loadidFromDB(id);
                    id++;
                    sqnce = "";
                    db.insertID(id);
                    continue;
                }
                location = dis1.readLine();
                System.out.println(turn + " " + location);
                ps1.println(turn);
                ps2.println(turn);
                ps1.println(location);
                ps2.println(location);
                addPositionAndChartoDatabase(location, turn);

                System.out.println("2nd player turn");
                turn = dis2.readLine();
                if (turn.equalsIgnoreCase("finish")) {
                    turn = dis1.readLine();
                    id = db.loadidFromDB(id);
                    id++;
                    sqnce = "";
                    db.insertID(id);
                    continue;
                }
                location = dis2.readLine();
                System.out.println(turn + " " + location);
                ps1.println(turn);
                ps2.println(turn);
                ps1.println(location);
                ps2.println(location);
                addPositionAndChartoDatabase(location, turn);

            } catch (IOException ex) {
                System.out.println("Two Players left the game");
                break;
            }
        }
    }

    void addPositionAndChartoDatabase(String btnId, String O_X) {
        sqnce = sqnce + btnId + "," + O_X + ",";
        db.updateSequence(sqnce, id);
    }
}
