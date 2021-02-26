package tictactoe_2_2;

import com.sun.webkit.ThemeClient;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class PlayerHandler1 extends Thread {

    DataInputStream dis;
    PrintStream ps;
    // Variables for database
    DataBaseCon db;
    String sqnce = "";
    String queryString;
    int id;

    public PlayerHandler1(Socket player, int id) {
        this.id = id;
        System.out.println("Enter Player Handler for One");
        db = new DataBaseCon();
        db.insertID(id);
        try {
            System.out.println("Player is ready");
            dis = new DataInputStream(player.getInputStream());
            ps = new PrintStream(player.getOutputStream());
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
                System.out.println("New turn");
                turn = dis.readLine();
                if (turn.equalsIgnoreCase("finish")) {
                    id = db.loadidFromDB(id);
                    id++;
                    sqnce = "";
                    db.insertID(id);
                    continue;
                }
                location = dis.readLine();
                System.out.println(turn + " " + location);
                ps.println(turn);
                ps.println(location);
                addPositionAndChartoDatabase(location, turn);
            } catch (IOException ex) {
                System.out.println(ex);
                break;
            }
        }
    }

    void addPositionAndChartoDatabase(String btnId, String O_X) {
        sqnce = sqnce + btnId + "," + O_X + ",";
        db.updateSequence(sqnce, id);
    }

}
