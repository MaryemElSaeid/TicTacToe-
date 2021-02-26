package tictactoe_2_2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

public class RetrieveHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    Thread th;
    DataBaseCon db;
    String idString = ""; // String of Ids to be sent to replays
    String GID = ""; // Recieving Game id
    String queryString;
    String scoreX = "", scoreO = "", Mode = "";
    String seq = ""; // Variables recieves for specific game
    int m = 0;
    String[] arrOfStr;
    Socket sck;

    public RetrieveHandler(Socket sck) {
        this.sck = sck;
        try {
            dis = new DataInputStream(sck.getInputStream());
            ps = new PrintStream(sck.getOutputStream());
            th = new Thread(this);
            System.out.println("New Database con from Retrieve handler");
            db = new DataBaseCon();
        } catch (IOException ex) {
            Logger.getLogger(RetrieveHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        //COMBO BOX___________________FETCH DATA INSIDE COMBOBOX___________________________________________________________________________________
        idString = db.selectAllIDs();  //id's concatenated in a string
        ps.println(idString);         
        th.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Selected by comboBox Recieved by retrieve " + GID);
                GID = dis.readLine();
                seq = db.RetrieveCurrentGameSequence(GID);
                ps.println(seq);
                System.out.println("Final Send " + seq);
            } catch (IOException ex) {
                try {
                    sck.close();
                    dis.close();
                    ps.close();
                    break;
                } catch (IOException ex1) {
                    Logger.getLogger(RetrieveHandler.class.getName()).log(Level.SEVERE, null, ex1);
                    break;

                }

            }
        }
    }
}
