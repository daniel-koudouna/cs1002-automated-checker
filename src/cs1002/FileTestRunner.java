package cs1002;

import java.util.Map;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class FileTestRunner {
  public static void run(Class testClass, String[] args) {

    JUnitCore junit = new JUnitCore();
    CustomRunListener listener = new CustomRunListener();
    junit.addListener(listener);
    Result result = junit.run(testClass);

    Map<String, TestSuiteDetails> results = listener.getMap();
    results.forEach( (s, d) -> {
      System.out.println(s);
      System.out.println(d.getTestStatus());

      System.out.println(d.getTestDescription());
    });

  } 
}
