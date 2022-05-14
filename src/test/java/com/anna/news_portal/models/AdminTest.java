package com.anna.news_portal.models;

import com.anna.news_portal.parameter_resolvers.AdminParameterResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AdminParameterResolver.class)
class AdminTest {
  @Test
  @DisplayName("Test that an Admin class instance is instantiated correctly")
  public void newAdmin_instantiatesCorrectly_true(Admin admin) {
    assertNotNull(admin);
  }
}