import java.util.Scanner;

/**
 * This is an example application using the testing framework.
 * NB: The .vscode folder is /necessary/ in order to run the test 
 * framework jar, as it sets the current project path and thus 
 * allows for the use of .in and .out files. 
 */
public class App {
    public static void main(String[] args) throws Exception {
      System.out.println("Please enter your name:");
      Scanner stdin = new Scanner(System.in);
      String name = stdin.nextLine();

      if (name.equals("42")) {
        stdin.close();
        throw new RuntimeException("Something went wrong!");
      }

      if (name.equals("exit")) {
        System.out.println("Hello, exit");
        System.exit(2);
      }

      if(name.equals("eat")) {
        stdin.nextLine();
        stdin.nextLine();
        stdin.nextLine();
        stdin.nextLine();
      }

      if (name.equals("multi")) {
        Scanner otherScanner = new Scanner(System.in);
        stdin.nextLine();
        otherScanner.nextLine();
      }

      System.out.println("Hello, " + name);

    }
}
