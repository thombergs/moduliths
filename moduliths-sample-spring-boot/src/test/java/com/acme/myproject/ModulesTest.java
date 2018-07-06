package com.acme.myproject;

import de.olivergierke.moduliths.model.Modules;
import org.junit.Test;

public class ModulesTest {

  @Test
  public void verifyModules() {
    Modules.of(Application.class).verify();
  }

}
