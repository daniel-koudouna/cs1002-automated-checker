package cs1002.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.runner.JUnitCore;

public class FileTestRunner {
  public static void run(Class testClass, String[] args) {

    List<String> argv = Arrays.asList(args);
    boolean useColors = argv.contains("--color");

    JUnitCore junit = new JUnitCore();
    CustomRunListener listener = new CustomRunListener();
    junit.addListener(listener);
    junit.run(testClass);

    Map<String, TestSuiteDetails> results = listener.getMap();
    results.forEach( (s, d) -> {
      String status = d.getTestStatus();
      boolean success = status.equals("Passed");
      String padding = ".".repeat(80 - s.length() - status.length());

      String header = s + padding + status;
      if (useColors) {
        if (success) {
          System.out.println("\u001B[32m"+ header + "\u001B[0m");
        } else {
          System.out.println("\u001B[31m"+ header + "\u001B[0m");
        }

      } else {
        System.out.println(header);
      }

      if (!success) {
        System.out.println(d.getTestDescription());
      }
    });

  } 
}
