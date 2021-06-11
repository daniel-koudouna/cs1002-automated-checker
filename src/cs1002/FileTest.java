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

    boolean exitEarly = false;
    String strace = null;
    Class<? extends Exception> exceptionClass = null;

    SecurityManager manager = System.getSecurityManager();
    SecurityManager testManager = new TestSecurityManager();
    System.setSecurityManager(testManager);
    try {
      main.apply(null);
    } catch (SecurityException se) {
      exitEarly = true;
    } catch (Exception e) {
      exceptionClass = e.getClass();
      System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
      strace = "\t" + e.toString();
      for (int i = 0; i < e.getStackTrace().length; i++) {
        StackTraceElement el = e.getStackTrace()[i];
        if (el.getClassName().equals(FileTest.class.getName())){
          break;
        }
        else {
          strace += "\n\t\t at " + el.toString();
        }
      }
    }
    System.setSecurityManager(manager);
    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

    String actual = buffer.toString();

    OutputComparison diff = OutputComparison.from(expected, actual);

    ExecutionResult result = new ExecutionResult(diff, strace, exceptionClass, exitEarly);

    result.checkOutputMatches();
    result.checkExceptions();
    result.checkEarlyExit();


    if (result.hasFailed()) {
      fail(result.getDescription());
    }
  }
}
