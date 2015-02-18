package test;

import com.hannesdorfmann.annotatedrest.annotation.Api;
import com.hannesdorfmann.annotatedrest.annotation.method.Get;
import com.hannesdorfmann.annotatedrest.processor.ProcessingException;
import com.hannesdorfmann.annotatedrest.processor.Validator;
import java.lang.annotation.Annotation;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hannes Dorfmann
 */
public class ValidatorTest {

  @Test public void testVersion() throws ProcessingException {

    versionShouldNotFail("v1");
    versionShouldNotFail("1");
    versionShouldNotFail("123_baqwe");
    versionShouldNotFail("");

    versionShouldFail(null);
    versionShouldFail("v.1");
    versionShouldFail("v 1");
    versionShouldFail("*");
    versionShouldFail("ä");
    versionShouldFail("?");
    versionShouldFail("!");
    versionShouldFail("^");
    versionShouldFail("/");
    versionShouldFail("&");
    versionShouldFail("+");
    versionShouldFail("#");
  }

  @Test public void testBasePath() throws ProcessingException {

    basePathShouldNotFail("");
    basePathShouldNotFail("/");
    basePathShouldNotFail("/asd");
    basePathShouldNotFail("/asd/_/123");
    basePathShouldNotFail("/QWE");

    basePathShouldFail(null);
    basePathShouldFail("//");
    basePathShouldFail("/123/");
    basePathShouldFail("/qwe/{id}/asd");
    basePathShouldFail("/qwe/");
    basePathShouldFail("/asd_123/");
    basePathShouldFail("/ä");
    basePathShouldFail("/!");
    basePathShouldFail("/§");
  }

  @Test public void testServletClassName() throws ProcessingException {

    classNameShouldNotFail("com.hannesdorfmann.annotatedrest.AnnotatedServlet");
    classNameShouldNotFail("AnnotatedServlet");
    classNameShouldNotFail("com.a123.AnnotatedServlet");

    classNameShouldNotFail(null);
    classNameShouldFail("");
    classNameShouldFail(".ClassFor");
    classNameShouldFail("com.123.ClassFor");
  }

  @Test public void testMethodPath() throws ProcessingException {


    methodPathShouldNotFail("/");
    methodPathShouldNotFail("/foo");
    methodPathShouldNotFail("/foo/{id}");
    methodPathShouldNotFail("/foo/{id}/asd");
    methodPathShouldNotFail("/foo/{id}/asd/{other}");
    methodPathShouldNotFail("/foo/{id}/_123/{other}/_");

    methodPathShouldFail(null);
    methodPathShouldFail("//");
    methodPathShouldFail("foo");
    methodPathShouldFail("/ä");
    methodPathShouldFail("/ö");
    methodPathShouldFail("/!");
    methodPathShouldFail("/§");


  }

  private void versionShouldNotFail(String v) throws ProcessingException {
    Api api = newApiVersion(v);
    Validator.checkValidVersion(null, api);
    Assert.assertTrue(true);
  }

  private void versionShouldFail(String v) {
    try {
      Api api = newApiVersion(v);
      Validator.checkValidVersion(null, api);
      Assert.fail("Version " + v + " should have failed, but haven't");
    } catch (ProcessingException e) {
      Assert.assertTrue(true);
    }
  }

  private Api newApiVersion(String version) {
    return newApi(version, "", "");
  }

  private Api newApiBasePath(String basePath) {
    return newApi(null, basePath, null);
  }

  private Api newApiServletClass(String className) {
    return newApi(null, null, className);
  }

  private void basePathShouldNotFail(String b) throws ProcessingException {
    Api api = newApiBasePath(b);
    Validator.checkValidBasePath(null, api);
    Assert.assertTrue(true);
  }

  private void basePathShouldFail(String b) {
    try {
      Api api = newApiBasePath(b);
      Validator.checkValidBasePath(null, api);
      Assert.fail("Base Path " + b + " should have failed, but haven't");
    } catch (ProcessingException e) {
      Assert.assertTrue(true);
    }
  }

  private void classNameShouldNotFail(String name) throws ProcessingException {
    Api api = newApiServletClass(name);
    Validator.checkValidServletClassName(null, api);
    Assert.assertTrue(true);
  }

  private void classNameShouldFail(String name) {
    try {
      Api api = newApiServletClass(name);
      Validator.checkValidServletClassName(null, api);
      Assert.fail("ClassName " + name + " should have failed, but haven't");
    } catch (ProcessingException e) {
      Assert.assertTrue(true);
    }
  }

  private void methodPathShouldFail(String path) {
    try {
      Validator.checkValidMethodPath(null, path, Get.class);
      Assert.fail("Path " + path + " should have failed, but haven't");
    } catch (ProcessingException e) {
      Assert.assertTrue(true);
    }
  }

  private void methodPathShouldNotFail(String path) throws ProcessingException {
    Validator.checkValidMethodPath(null, path, Get.class);
    Assert.assertTrue(true);
  }

  private Api newApi(final String version, final String basePath, final String servletClassName) {
    return new Api() {
      @Override public String version() {
        return version;
      }

      @Override public String basePath() {
        return basePath;
      }

      @Override public String servletClassName() {
        return servletClassName;
      }

      @Override public Class<? extends Annotation> annotationType() {
        return Api.class;
      }
    };
  }

}
