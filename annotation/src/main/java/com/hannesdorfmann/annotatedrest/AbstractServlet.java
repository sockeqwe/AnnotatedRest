package com.hannesdorfmann.annotatedrest;

import com.hannesdorfmann.annotatedrest.config.Configuration;
import com.hannesdorfmann.annotatedrest.config.DefaultConfig;
import com.hannesdorfmann.annotatedrest.router.ApiMethodInvoker;
import com.hannesdorfmann.annotatedrest.router.Router;
import com.hannesdorfmann.annotatedrest.serializer.Serializer;
import com.hannesdorfmann.exception.HttpException;
import com.hannesdorfmann.exception.NotFoundException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.output.CountingOutputStream;

/**
 * The base class for annotated servlets. This will be used for
 *
 * @author Hannes Dorfmann
 */
public abstract class AbstractServlet extends HttpServlet {

  private final static String CONFIG_INIT_PARAM = "com.hannesdorfmann.annotatedrest.config";

  /**
   * The router for routing http get calls
   */
  protected Router getRouter = new Router();

  /**
   * The router for routing http post calls
   */
  protected Router postRouter = new Router();

  /**
   * The router for routing http delete calls
   */
  protected Router deleteRouter = new Router();

  /**
   * The router for routing http put calls
   */
  protected Router putRouter = new Router();

  /**
   * The serializer, used for serializing and deserializing
   */
  private Serializer serializer;

  public AbstractServlet()
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {

    Configuration configuration = getConfig();
    serializer = configuration.getSerializer();
  }

  /**
   * Get the {@link Configuration} by checking servlets init parameter
   *
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  protected Configuration getConfig()
      throws ClassNotFoundException, InstantiationException, IllegalAccessException {

    Configuration config = null;
    String confClassName = getInitParameter(CONFIG_INIT_PARAM);
    if (confClassName == null || confClassName.equals("")) {
      return new DefaultConfig();
    } else {
      Object oConf = Class.forName(confClassName).newInstance();
      if (oConf instanceof Configuration) {
        return (Configuration) oConf;
      } else {
        throw new ClassCastException(confClassName
            + " does not implement "
            + Configuration.class.getCanonicalName()
            + " which is required!");
      }
    }
  }

  /**
   * Searches for a route an dispatches the request by calling the corresponding method invoker
   *
   * @throws Exception
   */
  private void dispatchRoute(HttpServletRequest req, HttpServletResponse res, Router router)
      throws Exception {

    try {
      ApiMethodInvoker route = router.getInvoker(req);
      if (route == null) {
        throw new NotFoundException();
      }

      Class<?> requestBodyType = route.getRequestBodyType();
      Object requestBody = null;
      if (requestBodyType != null) {
        requestBody = serializer.deserialize(req.getInputStream(), requestBodyType,
            route.isRequestBodyTypeList());
      }

      Object responseBody = route.dispatch(req, res, requestBody);
      if (responseBody != null) {
        serializeResponse(res, responseBody);
      }
    } catch (HttpException httpException) {
      serializeResponse(res, httpException);
    }
  }

  /**
   * Writes the return value serialized to the response outputstream. it also sets the content
   * length http header and the content type http header
   *
   * @throws Exception
   */
  private void serializeResponse(HttpServletResponse res, Object toSerialize) throws Exception {
    if (toSerialize != null) {
      CountingOutputStream outputStream = new CountingOutputStream(res.getOutputStream());
      serializer.serialize(toSerialize, outputStream);
      res.setContentLength(outputStream.getCount());
      res.setContentType(serializer.getContentType());
    }
  }

  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      dispatchRoute(req, resp, getRouter);
    } catch (Exception e) {
      new ServletException(e);
    }
  }

  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      dispatchRoute(req, resp, postRouter);
    } catch (Exception e) {
      new ServletException(e);
    }
  }

  @Override protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      dispatchRoute(req, resp, deleteRouter);
    } catch (Exception e) {
      new ServletException(e);
    }
  }

  @Override protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      dispatchRoute(req, resp, putRouter);
    } catch (Exception e) {
      new ServletException(e);
    }
  }
}
