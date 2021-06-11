package cs1002.checker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class AdviceCheck {
  public final Function<Exception,Boolean> checkerFn;
  public final String title;
  public final String advice;

  public AdviceCheck(Function<Exception,Boolean> checkerFn, String title, String advice) {
    this.checkerFn = checkerFn;
    this.title = title;
    this.advice = advice;
  }
}

public class ExecutionResult {
  private OutputComparison comp; 
  private String stackTrace;
  private Exception exception;

  private String description;
  private boolean failed;

  private List<AdviceCheck> exceptionAdvice;

  public ExecutionResult(
    OutputComparison comp, 
    String stackTrace, 
    Exception exception
  ) {
    this.comp = comp;
    this.stackTrace = stackTrace;
    this.exception = exception;

    this.description = "";
    this.failed = false;


    this.exceptionAdvice = new ArrayList<>();
  }

  public void addAdvice(Class<? extends Exception> exceptionClass, String title, String advice) {
    this.exceptionAdvice.add(new AdviceCheck(e -> e.getClass() == exceptionClass, title, advice));
  }

  public void addAdvice(Function<Exception,Boolean> checkerFn, String title, String advice) {
    this.exceptionAdvice.add(new AdviceCheck(checkerFn, title, advice));
  }

  public boolean hasFailed() {
    return this.failed;
  }

  public String getDescription() {
    return this.description;
  }

  private void addError(String title, String details, String advice) {
    Settings settings = Settings.get();
    this.failed = true;
    this.description += "- " + settings.yellow(title) + "\n";
    for (String l : details.split("\n")) {
      this.description +="\t" + l + "\n";
    }
    for (String l : advice.split("\n")) {
      this.description +="  " + settings.cyan(l) + "\n";
    }
  }

  public void checkOutputMatches() {
    if (comp.firstDifference != 0) {
      addError("Your output did not match the expected output.", 
      comp.output + 
      "\nFirst difference found on line " + comp.firstDifference + ":" + 
      "\nExpected: \"" + comp.expectedLine + "\"" + 
      "\nFound   : \"" + comp.actualLine + "\"", 
      "Ensure spaces, capitalization, punctuation etc. match the expected output exactly." +
      "\nFor larger projects, you will have to look at the visual output above," + 
      "\nwith asterisks (*) indicating lines which are different.");
    }
  }

  public void checkExceptions() {
    Settings settings = Settings.get();
    String hints = "";


    if (stackTrace != null) {
      if (settings.showLinks) {
        String name = exception.getClass().getName();
        if (!name.startsWith("cs1002")) {
          hints = "\nYou might be able to learn more about the exception by using a search engine:"
          +"\n\n" + "https://duckduckgo.com/?q=site%3Astackoverflow.com+" + name; 
        }
      }

      for (AdviceCheck adviceCheck : exceptionAdvice) {
        if (adviceCheck.checkerFn.apply(this.exception)) {
          addError(adviceCheck.title
          +"\n  The exception and stack trace is shown below:", stackTrace, adviceCheck.advice + hints);
          return;
        }
      }
      addError("There was an exception in your java program. See the stack trace below", 
      stackTrace, 
      "To diagnose the problem, find the first line which originates from your program." + 
      "\nLines follow the format  Class.methodname(Filename.java::linenumber)" + hints);
    }
  }

}
