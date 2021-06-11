package cs1002;

import java.util.function.Function;

public class ExecutionResult {
  private OutputComparison comp; 
  private Class<? extends Exception> exceptionClass;
  private String stackTrace;
  private boolean exitEarly;

  private String description;
  private boolean failed;

  public ExecutionResult(
    OutputComparison comp, 
    String stackTrace, 
    Class<? extends Exception> exceptionClass, 
    boolean exitEarly
  ) {
    this.comp = comp;
    this.exceptionClass = exceptionClass;
    this.stackTrace = stackTrace;
    this.exitEarly = exitEarly;

    this.description = "\n";
    this.failed = false;
  }

  public boolean hasFailed() {
    return this.failed;
  }

  public String getDescription() {
    return this.description;
  }

  public void checkOutputMatches() {
    if (comp.firstDifference != 0) {
      failed = true;
      description += comp.output;
      description += "- Your output did not match the expected output.\n";
      description += "\tFirst difference found on line " + comp.firstDifference + "\n";
      description += "\tExpected: \"" + comp.expectedLine + "\"\n";
      description += "\tFound   : \"" + comp.actualLine + "\"\n";
      description += "  Ensure spaces, capitalization, punctuation etc. match the expected output exactly.\n";
      description += "  For larger projects, you will have to look at the visual output above,\n";
      description += "  with asterisks (*) indicating lines which are different.\n";
    }
  }

  public void checkExceptions() {
    if (stackTrace != null) {
      failed = true;
      description += "- There was an exception in your java program. See the stack trace below: \n"
      + stackTrace + "\n";
      description += "  To diagnose the problem, find the first line which originates from your program.\n";
      description += "  Lines follow the format  Class.methodname(Filename.java::linenumber)\n";
    }
  }

  public void checkEarlyExit() {
    if (exitEarly) {
      failed = true;
      description += "- Your program terminated early using System::exit or analogous method call.\n";
    }
  }
}
