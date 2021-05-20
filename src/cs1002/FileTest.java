package cs1002;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test class which runs an instance of a java application,
 * and compares the actual output with the expected output.
 * 
 * The class uses the conventions:
 *   - Files are inside the "tests" directory.
 *   - Pairs of files of test "NAME" are called "NAME.in" and "NAME.out".
 *   - A main method must be supplied to FileTest::run . This can allow for tests
 *     to be written for advanced tasks.
 * 
 * Some other notes:
 *   - The class runs from the JUnit test runner, and replaces stdin and stdout.
 *     This means all logging will be redirected appropriately
 *   - The class outputs a log of each line side-by-side, and adds an asterisk
 *     on lines that are detected as different. There is also a message at the end
 *     indicating if the test passed.
 *   - The output is best viewed from the VS Code "Java Test Report" view. This is
 *     opened with Ctrl+Shift+P -> 'show test report'
 *   - If an exception is thrown, the stack trace relating to the exception is printed
 *     in the JUnit window.
 */
public class FileTest {

  public static void run(MainRunner main, String filename) {
    run(main, Paths.get("tests", filename + ".in"), Paths.get("tests", filename + ".out"));
  }

  public static void run(MainRunner main, Path inFile, Path outFile) {
    InputStream fin;
    try {
      fin = Files.newInputStream(inFile);
      System.setIn(fin);
    } catch (IOException e1) {
      e1.printStackTrace();
      fail("Failed to read from input file: " + inFile.getFileName() + 
      " Ensure the file exists in the 'tests' folder of your project.");
    }

    String expected = "";
    try {
      expected = Files.readString(outFile);
    } catch (IOException e) {
      e.printStackTrace();
      fail("Failed to read from output file: " + outFile.getFileName() + 
      " Ensure the file exists in the 'tests' folder of your project.");
    }

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    System.setOut(new PrintStream(buffer));

    try {
      main.apply(null);
    } catch (Exception e) {
      System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
      String strace = e.toString();
      for (int i = 0; i < e.getStackTrace().length; i++) {
        StackTraceElement el = e.getStackTrace()[i];
        if (el.getClassName().equals(FileTest.class.getName())){
          break;
        }
        else {
          strace += "\n\t at " + el.toString();
        }
      }

      fail("There was an exception in your java program. See the stack trace below: \n"
       + strace + "\n\nThe rest of the stack trace is related to testing, and you may ignore it.\n");
    }


    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    String content = buffer.toString();

    int maxWidth = expected.lines()
        .map(String::length)
        .max(Integer::compare)
        .orElse(0);

    maxWidth = Integer.max(maxWidth, "Expected output".length());

    List<String> c = content.lines().collect(Collectors.toList());
    List<String> ex = expected.lines().collect(Collectors.toList());

    if (ex.size() > 0 && ex.get(ex.size() -1).equals("")) {
      ex.remove(ex.size() - 1);
    }

    int maxHeight = c.size() > ex.size() ? c.size() : ex.size();

    System.out.println("Running test for file: " + inFile.getFileName());
    System.out.println("  |     | " + String.format("%-" + (maxWidth + 2) + "s","Expected output") + " | " + "Actual output");
    System.out.println("--|-----|-" + "-".repeat(maxWidth+2) + "-|-" + "-".repeat(maxWidth+2));

    boolean passed = true;
    int lineNum = 0;

    for (int i = 0; i < maxHeight; i++) {
      String exp = "===End of Output===";
      String ac = "===End of Output===";
      if (i < ex.size()) {
        exp = ex.get(i);
      }
      if (i < c.size()) {
        ac = c.get(i);
      }
      String symbol = " ";
      if (!exp.equals(ac)) {
        symbol = "*";
        if (passed) {
          passed = false;
          lineNum = i+1;
        }

      }

      String line = symbol + " | " + String.format("%3d", (i+1)) + " | " + String.format("%-" + (maxWidth + 2) + "s",exp) + " | " + ac;
      System.out.println(line);
    }

    if (passed) {
      System.out.println("Test passed!");
    } else {
      System.out.println("Test failed! First difference found in line " + lineNum);
    }

    System.out.println("");

    assertLinesMatch(ex.stream(), content.lines());

  }
}
