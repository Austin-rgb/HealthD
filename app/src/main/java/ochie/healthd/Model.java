package ochie.healthd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import org.json.JSONObject;

public class Model {
  Connection con;
  Statement statement;

  public Model(String url, String username, String password) {
    try {
      con = DriverManager.getConnection(url, username, password);
      statement = con.createStatement();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  String confirmSession(String session) {
    try {
      ResultSet rs =
          statement.executeQuery("select * from sessions where sessionId=\"" + session + "\"");
      if (rs.getFetchSize() > 0) return rs.getString("email");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return "";
  }

  boolean matchCredentials(String email, String password) {
    try {
      ResultSet rs =
          statement.executeQuery(
              "select * from credentials where email=\""
                  + email
                  + " \" and password=\""
                  + password
                  + "\"");
      if (rs.getFetchSize() > 0) return true;
      return false;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }
  /*
  Adds a new session to the sessions table
  deactivate a previous session used by the email provided
  */
  boolean saveSession(String sessionId, String email) {
    try {
      statement.executeUpdate("insert into sessions values(" + sessionId + "," + email + ")");
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  boolean saveCredentials(String email, String password) {
    return false;
  }

  boolean updateProfile(String email, String name, String data) {
    try {
      statement.executeUpdate("update profiles set " + name + "=" + data + " where email=" + email);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  String getPatientData(String email) {

    try {
      ResultSet rs = statement.executeQuery("select * from datapool where email=" + email);
      ResultSetMetaData md = rs.getMetaData();
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      while (rs.next()) {
        JSONObject object = new JSONObject();
        for (int i = 0; i < md.getColumnCount(); i++) {
          object.put(md.getColumnName(i), rs.getString(i));
        }
        sb.append(object.toString());
        sb.append(",");
      }

      return sb.toString();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return "0";
  }
  boolean deactivateSession(String email){
    try {
      statement.executeUpdate("update sessions set status=\"inactive\" where email=" + email);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }
}
