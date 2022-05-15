package com.anna.news_portal.parameter_resolvers;

import com.anna.news_portal.models.DepartmentNews;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class DepartmentNewsParameterResolver implements ParameterResolver {
  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterContext.getParameter().getType() == DepartmentNews.class;
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return new DepartmentNews("Email 2FA authentication","The 2FA authentication adds an extra layer of security thus ensure security of accounts", 1, 1);
  }
}
