package ochie.healthd;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import ochie.healthd.Controls;

public class Main {
  static String param1;
  static String param2;
  static String param3;
  static String param4;
  static String result = null;

  public static void main(String[] args) {
    System.out.print("creating control object... ");
    final Controls c = new Controls("jdbc:sqlite:healthd.db", "healthdUsername", "healthdPassword");
    System.out.print("done\n initializing instructions...");
    HashMap<String, Instruction> hm = new HashMap<String, Instruction>();
    hm.put(
        "login",
        new Instruction() {
          public void execute() {
            result = c.login(param1, param2);
          }
        });
    System.out.print("done\n creating client Socket...");
    try (Socket s = new Socket("127.0.0.1", 4000); ) {
      System.out.print("done\n creating OutputStreamWriter...");
      OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
      System.out.print("done\n creating InputStream...");
      InputStream is = s.getInputStream();
      System.out.print("done\n creating Scanner...");
      Scanner in = new Scanner(is);
      System.out.print("done\n reading Instruction...");
      while (!s.isClosed()) {
        String ins = in.nextLine();
        System.out.print("done\n reading param1...");
        if (in.hasNextLine()) param1 = in.nextLine();
        System.out.print("done\n reading param2...");
        if (in.hasNextLine()) param2 = in.nextLine();
        System.out.print("done\n reading param1 ...");
        if (in.hasNextLine()) param3 = in.nextLine();
        System.out.println("executing " + ins);
        hm.get(ins).execute();
        osw.append(result);
        osw.flush();
      }
      System.out.println("closing client");
      in.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}

interface Instruction {
  void execute();
}
