package cs1002.junit;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.runner.JUnitCore;

import cs1002.checker.Settings;

public class FileTestRunner {
  public static void run(Class testClass, String[] args) {

    Settings settings = Settings.init(args);

    JUnitCore junit = new JUnitCore();
    CustomRunListener listener = new CustomRunListener();
    junit.addListener(listener);
    junit.run(testClass);

    Map<String, TestSuiteDetails> results = listener.getMap();

    for (Entry<String, TestSuiteDetails> e : results.entrySet()) {
      String s = e.getKey();
      TestSuiteDetails d = e.getValue();

      String status = d.getTestStatus();
      boolean success = status.equals("Passed");
      String padding = ".".repeat(80 - s.length() - status.length());

      String header = s + padding + status;
      if (settings.useColors) {
        if (success) {
          System.out.println("\u001B[32m"+ header + "\u001B[0m");
        } else {
          System.out.println("\u001B[31m"+ header + "\u001B[0m");
        }

      } else {
        System.out.println(header);
      }

      if (!success && !settings.terseOutput) {
        System.out.println(d.getTestDescription());
      }

      if (!success && settings.failFast) {

        break;
      }
    }

    int successes = 0;
    for (Entry<String, TestSuiteDetails> e : results.entrySet()) {
      if (e.getValue().getTestStatus().equals("Passed")) {
        successes++;
      }
    }


    String sum = "Tests passed: |";
    String total = "| " + successes + "/" + results.size(); 

    int paddingSize = 80 - sum.length() - total.length(); 

    int filled = (int)(paddingSize*successes/(1.0*results.size()));
    int empty = paddingSize - filled;

    String filledPadding = "█".repeat(filled);
    String emptyPadding = ".".repeat(empty);
    String footer = sum + filledPadding + emptyPadding + total;

    if (settings.useColors) {
      if (successes == 0) {
        System.out.println(settings.red(footer));
      } else if (successes == results.size()) {
        System.out.println(settings.green(footer));
      } else {
        System.out.println(settings.yellow(footer));
      }
    } else {
      System.out.println(footer);
    }

  } 
}
