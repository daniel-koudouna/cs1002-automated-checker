import org.junit.Test;
import cs1002.FileTest;

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
}
