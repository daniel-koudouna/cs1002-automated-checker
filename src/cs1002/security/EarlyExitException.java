package cs1002.security;

public class EarlyExitException extends SecurityException {
  public EarlyExitException() {
    super("Program terminated early by user code");
  }
}
