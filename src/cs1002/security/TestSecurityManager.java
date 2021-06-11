package cs1002.security;

import java.security.Permission;

public class TestSecurityManager extends SecurityManager{
  public final SecurityManager manager;
  public TestSecurityManager(SecurityManager manager) {
    this.manager = manager;
  }

   @Override public void checkExit(int status) {
    throw new EarlyExitException();
  }

  @Override public void checkPermission(Permission perm) {
    if (this.manager != null) {
      this.manager.checkPermission(perm);
    }
  }
}
