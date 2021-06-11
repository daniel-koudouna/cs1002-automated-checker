import org.junit.Test;

import cs1002.FileTest;
import cs1002.FileTestRunner;

public class AppTests {
  @Test
  public void testPass() {
    FileTest.run(App::main, "hello");
  }

  @Test
  public void testFail() {
    FileTest.run(App::main, "test");
  }

  @Test
  public void testException() {
    FileTest.run(App::main, "42");
  }

  @Test
  public void testSystemExit() {
    FileTest.run(App::main, "exit");
  }

  @Test
  public void testScanners() {
    FileTest.run(App::main, "eat");
  }

  public static void main(String[] args) {
    FileTestRunner.run(AppTests.class, args);
  }
}
