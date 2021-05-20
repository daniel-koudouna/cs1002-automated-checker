package cs1002;

/**
 * Functional interface for running tests with some input.
 * Used for syntactic sugar.
 */
@FunctionalInterface
public interface MainRunner {
   void apply(String[] args) throws Exception;
}
