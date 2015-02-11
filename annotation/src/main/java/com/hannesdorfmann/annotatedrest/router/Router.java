package com.hannesdorfmann.annotatedrest.router;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Hannes Dorfmann
 */
public class Router {

  private List<ApiMethodInvoker> invokers = new ArrayList<ApiMethodInvoker>();

  public void addInvoker(ApiMethodInvoker route) {
    this.invokers.add(route);
  }

  public ApiMethodInvoker getInvoker(HttpServletRequest request) {

    String url = request.getPathTranslated();
    if (url == null || url.equals("")) {
      return null;
    }

    for (ApiMethodInvoker r : invokers) {
      if (r.matches(url)) {
        return r;
      }
    }

    return null;
  }

}
