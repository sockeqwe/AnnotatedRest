package com.hannesdorfmann.annotatedrest.processor;

import com.hannesdorfmann.annotatedrest.annotation.Api;
import java.lang.annotation.Annotation;
import java.util.regex.Pattern;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hannes Dorfmann
 */
public class Validator {

  private static Pattern versionPattern = Pattern.compile("\\w+");
  private static Pattern basePathPattern = Pattern.compile("(\\w|/)+");
  private static Pattern servletClassNamePattern =
      Pattern.compile("([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*");

  private static Pattern subPathWithVariable = Pattern.compile("\\{\\w+\\}");

  /**
   * Checks if the basePath is valid
   *
   * @throws ProcessingException
   */
  public static void checkValidBasePath(Element classElement, Api api) throws ProcessingException {

    String basePath = api.basePath();
    if (StringUtils.isNotEmpty(basePath)) {

      if (basePath.charAt(0) != '/') {
        throw new ProcessingException(classElement, "basePath must start with '/' in @%s",
            api.getClass().getSimpleName());
      }

      if (basePath.length() > 1 && basePath.endsWith("/")) {
        throw new ProcessingException(classElement, "basePath can not end with '/' in @%s",
            api.getClass().getSimpleName());
      }

      if (!basePathPattern.matcher(basePath).matches()) {
        throw new ProcessingException(classElement,
            "The basePath in @%s must match the following regex: %s",
            api.getClass().getSimpleName(), basePathPattern.pattern());
      }
    }
  }

  /**
   * Checks if the version of @Api is a valid one
   *
   * @throws ProcessingException
   */
  public static void checkValidVersion(Element classElement, Api api) throws ProcessingException {
    String version = api.version();
    if (StringUtils.isNotEmpty(version)) {

      if (!versionPattern.matcher(version).matches()) {
        throw new ProcessingException(classElement,
            "The specified version in @%s is not a valid version string. It must match the following regex: %s",
            api.getClass().getSimpleName(), versionPattern.pattern());
      }
    }
  }

  /**
   * Checks if the servletClassName is a valid java
   *
   * @throws ProcessingException
   */
  public static void checkValidServletClassName(Element classElement, Api api)
      throws ProcessingException {

    if (!servletClassNamePattern.matcher(api.servletClassName()).matches()) {
      throw new ProcessingException(classElement,
          "The servletClassName specified in @%s must be a valid full qualified java class name (optional package name + class name)",
          api.getClass().getSimpleName());
    }
  }

  /**
   * Checks if a method path is a valid one
   *
   * @param method The method element
   * @param methodPath The path from the annotation
   * @param annotationClass The annotation class
   * @throws ProcessingException
   */
  public static void checkValidMethodPath(ExecutableElement method, String methodPath,
      Class<? extends Annotation> annotationClass) throws ProcessingException {

    if (StringUtils.isNotEmpty(methodPath)) {

      if (methodPath.charAt(0) != '/') {
        throw new ProcessingException(method, "The @%s method path must start with '/' %s",
            annotationClass.getSimpleName(), method == null ? "" : "in " + method.toString());
      }

      String[] subPaths = methodPath.split("/");
      for (String p : subPaths) {

        if (p.length() > 0 && !basePathPattern.matcher(p).matches() && !subPathWithVariable.matcher(
            p).matches()) {
          // TODO better preciser error message
          throw new ProcessingException(method,
              "The @%s method contains a not valid subpath \"%s\" %s ",
              annotationClass.getSimpleName(), p, method == null ? "" : "for " + method.toString());
        }
      }
    }
  }
}
