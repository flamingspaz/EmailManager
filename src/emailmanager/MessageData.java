package emailmanager;

// Skeleton version of MessageData.java that links to Java Derby database.
// NOTE: You should not have to make any changes to the other
// Java GUI classes for this to work, if you complete it correctly.
// Indeed these classes shouldn't even need to be recompiled

import java.sql.*;
import java.io.*;
import static java.lang.System.*;
import javax.swing.*;
import org.apache.derby.drda.NetworkServerControl;

public class MessageData {

    private static Connection connection;
    private static Statement stmt;

    static {
        // standard code to open a connection and statement to Java Derby database
        try {
            NetworkServerControl server = new NetworkServerControl();
            server.start(null);
            // Load JDBC driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //Establish a connection
            String sourceURL = "jdbc:derby://localhost:1527/"
                    + new File("EmailDB").getAbsolutePath() + ";";
            connection = DriverManager.getConnection(sourceURL, "student", "student");
            stmt = connection.createStatement();
        } // The following exceptions must be caught
        catch (ClassNotFoundException cnfe) {
            out.println(cnfe);
        } catch (SQLException sqle) {
            out.println(sqle);
            JOptionPane.showMessageDialog(null, "An database error has occurred:\n" + sqle);
        } catch (Exception e) {
            System.out.println(e);
        }
    }    
    
    public static void reset() {
        String[] defaultMessages =  {
            "'Kate@redwich.ac.uk','Chris@redwich.ac.uk', 'Hello', '> How is the course going?', '', 0",
            "'Chris@redwich.ac.uk', 'Kate@redwich.ac.uk', 'Re: Hello', '> How is the course going?\n\nBrilliant, thanks. The students are all fantastic and are going to get top marks in their coursework.', '', 0"
        };
        try {
            stmt.executeUpdate("TRUNCATE TABLE STUDENT.MESSAGES");
            System.out.println("Reset table, adding default values");
            for (int i = 0; i < defaultMessages.length; i++) {
                String sqlString = "INSERT INTO STUDENT.MESSAGES(TOO, SENDER, SUBJECT, MESSAGE, LABEL, PRIORITY) VALUES(" + defaultMessages[i] + ")";
                System.out.println(sqlString);
                stmt.executeUpdate(sqlString);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private static String pad(String string, int width) {
        if (string == null) {
            string = "";
        }
        width -= string.length();
        for (int i = 0; i < width; ++i) {
            string += " ";
        }
        return string;
    }

    private static String formatListEntry(ResultSet res) throws SQLException {
        return res.getString("ID") + " " + pad(stars(res.getInt("Priority")), 8) + " " + pad(res.getString("Sender"), 30) + " " + pad(res.getString("Label"), 6) + " " + res.getString("Subject") + "\n";
    }

    private static String listHeader() {
        String output = "Id Priority From                           Label  Subject\n";
        output += "== ======== ====                           =====  =======\n";
        return output;
    }

    static String listLabelled(String label) {
        String output = listHeader();
        try {
            ResultSet res = stmt.executeQuery("SELECT * from STUDENT.MESSAGES WHERE Label = '" + label + "'");
            while (res.next()) { // there is a result
                output += formatListEntry(res);
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return output;
    }

    static String getLabel(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * from STUDENT.MESSAGES WHERE ID = '" + id + "'");
            if (res.next()) { // there is a result
                return res.getString("Label");
            } else {
                return null; // null means no such item
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static String setLabel(String id, String label) {
        // SQL UPDATE statement required. For instance if label is "todo" and id is "04" then updateStr is
        // UPDATE Libary SET Label = 'todo' WHERE id = '04'
        String updateStr = "UPDATE STUDENT.MESSAGES SET Label = '" + label + "' WHERE ID = " + id + "";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
        return listLabelled(label);
    }    
    
        public static void sendMessage(String to, String sender, String subject, String message) {
        String updateStr = "INSERT INTO STUDENT.MESSAGES(TOO, SENDER, SUBJECT, MESSAGE, LABEL, PRIORITY) VALUES('" + to + "', '" + sender +  "', '" + subject + "', '" + message + "', '', 0" + ")";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }   
    
    static String getMessage(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * from STUDENT.MESSAGES WHERE ID = " + id + "");
            if (res.next()) { // there is a result
                return res.getString("Message");
            } else {
                return null; // null means no such item
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static void setMessage(String id, String message) {
        // SQL UPDATE statement required. For instance if message is "Hello, how are you?" and id is "04" then updateStr is
        // UPDATE Libary SET Message = 'Hello, how are you?' WHERE id = '04'
        String updateStr = "UPDATE STUDENT.MESSAGES SET Message = '" + message + "' WHERE ID = " + id + "";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void deleteMessage(String id) {
        // SQL UPDATE statement required. For instance if message is "Hello, how are you?" and id is "04" then updateStr is
        // UPDATE Libary SET Message = 'Hello, how are you?' WHERE id = '04'
        String updateStr = "DELETE FROM STUDENT.MESSAGES WHERE ID = " + id + "";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static int getPriority(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * from STUDENT.MESSAGES WHERE ID = " + id + "");
            if (res.next()) { // there is a result
                return res.getInt("PRIORITY");
            } else {
                return -1;
            }
        } catch (Exception e) {
            System.out.println(e);
            return -1; // -1 means no such item
        }
    }

    public static void setPriority(String id, int priority) {
        // SQL UPDATE statement required. For instance if priority is 5 and id is "04" then updateStr is
        // UPDATE Libary SET Priority = 5 WHERE id = '04'
        String updateStr = "UPDATE STUDENT.MESSAGES SET Priority = " + priority + " WHERE ID = " + id + "";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static String getRecipient(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * FROM STUDENT.MESSAGES WHERE ID = " + id + "");
            if (res.next()) { // there is a result
			//To is a reserved word in Derby SQL, Too has been used instead
                return res.getString("Too");
            } else {
                return null; // null means no such item
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static void setRecipient(String id, String to) {
        // complete this method yourself - similar to setMessage()
        String updateStr = "UPDATE STUDENT.MESSAGES SET Too = '" + to + "' WHERE ID = '" + id + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getSender(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * FROM STUDENT.MESSAGES WHERE ID = " + id + "");
            if (res.next()) { // there is a result
                return res.getString("Sender");
            } else {
                return null; // null means no such item
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static void setSender(String id, String from) {
        // complete this method yourself - similar to setMessage()
        String updateStr = "UPDATE STUDENT.MESSAGES SET Sender = '" + from + "' WHERE ID = '" + id + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getSubject(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * FROM STUDENT.MESSAGES WHERE ID = " + id + "");
            if (res.next()) { // there is a result
                return res.getString("Subject");
            } else {
                return null; // null means no such item
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static void setSubject(String id, String subject) {
        // complete this method yourself - similar to setMessage()
        String updateStr = "UPDATE STUDENT.MESSAGES SET Subject = '" + subject + "' WHERE ID = '" + id + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String stars(int priority) {
        String stars = "";
        for (int i = 0; i < priority; ++i) {
            stars += "*";
        }
        return stars;
    }

    // close the database
    public static void close() {
        try {
            connection.close();
        } catch (Exception e) {
            // this shouldn't happen
            System.out.println(e);
        }
    }
    
    public static ResultSetTable listAllRS() {
        try {
            ResultSet rset = stmt.executeQuery("SELECT * from STUDENT.MESSAGES");
            ResultSetTable rst = new ResultSetTable(rset);
            return rst;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
