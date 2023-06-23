package entity;

import utils.Bus;
import utils.GetUser;

import java.sql.*;
import java.util.HashMap;

public class Msg {
    private int uid;
    private int from;
    private String contents;
    private String timeStamp;

    public Msg(int uid, int from, String contents, String timeStamp) {
        this.uid = uid;
        this.from = from;
        this.contents = contents;
        this.timeStamp = timeStamp;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    static public void setupDatabase() {
        String url = "jdbc:sqlite:db/msg.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS messages (\n"
                + " uid INTEGER,\n"
                + " sender TEXT,\n"
                + " contents TEXT,\n"
                + " timestamp TEXT\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    static public void storeMessage(Msg message) {
        String url = "jdbc:sqlite:db/msg.db";

        String sql = "INSERT INTO messages(uid,sender, contents, timestamp) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,message.getUid());
            stmt.setInt(2, message.getFrom());
            stmt.setString(3, message.getContents());
            stmt.setString(4, message.getTimeStamp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public HashMap<Integer, String> getMessages() {
        HashMap<Integer, String> recordMap = new HashMap<>();
        String url = "jdbc:sqlite:db/msg.db";
        String sql = "SELECT uid, sender, contents, timestamp FROM messages";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                int uid= rs.getInt("uid");
                if(uid== Bus.Uid){
                    int from = rs.getInt("sender");
                    String contents = rs.getString("contents");
                    String timeStamp = rs.getString("timestamp");

                    String msg = recordMap.get(from);
                    if (msg == null) {
                        msg = GetUser.getName(from)+" "+timeStamp+"\n"+contents+"\n\n";
                    }else{
                        msg =msg+'\n'+GetUser.getName(from)+" "+timeStamp+"\n"+contents+"\n\n";
                    }

                    recordMap.put(from, msg);
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return recordMap;
    }




}
