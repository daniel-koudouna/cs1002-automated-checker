import org.junit.Test;

import cs1002.checker.FileTest;
import cs1002.junit.FileTestRunner;

public class AppTests {
  @Test
  public void testPass() {
    FileTest.run(App::main, "hello");
  }

 @Test
 public void testPartial() {
   FileTest.run(App::main, "partial");
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
  public void testReadingInput() {
    FileTest.run(App::main, "eat");
  }


  @Test
  public void testMultipleScanners() {
    FileTest.run(App::main, "multi");
  }

  public static void main(String[] args) {
    FileTestRunner.run(AppTests.class, args);
  }
}
