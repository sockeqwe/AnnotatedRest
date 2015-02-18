package com.hannesdorfmann.annotatedrest.processor;

import com.google.auto.service.AutoService;
import com.hannesdorfmann.annotatedrest.annotation.Api;
import com.hannesdorfmann.annotatedrest.annotation.method.Delete;
import com.hannesdorfmann.annotatedrest.annotation.method.Get;
import com.hannesdorfmann.annotatedrest.annotation.method.Head;
import com.hannesdorfmann.annotatedrest.annotation.method.Post;
import com.hannesdorfmann.annotatedrest.annotation.method.Put;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hannes Dorfmann
 */
@AutoService(Processor.class) public class AnnotatedRestProcessor extends AbstractProcessor {

  private Types typeUtils;
  private Elements elementUtils;
  private Filer filer;
  private Messager messager;
  private Map<String, ApiEndpoint> apiEndpoints = new HashMap<String, ApiEndpoint>();

  @Override public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    typeUtils = processingEnv.getTypeUtils();
    elementUtils = processingEnv.getElementUtils();
    filer = processingEnv.getFiler();
    messager = processingEnv.getMessager();
  }

  @Override public Set<String> getSupportedAnnotationTypes() {
    Set<String> annotataions = new LinkedHashSet<String>();

    annotataions.add(Get.class.getCanonicalName());
    annotataions.add(Post.class.getCanonicalName());
    annotataions.add(Delete.class.getCanonicalName());
    annotataions.add(Put.class.getCanonicalName());
    annotataions.add(Head.class.getCanonicalName());

    return annotataions;
  }

  @Override public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    try {
      // @Get
      for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Get.class)) {
        Get annotation = annotatedElement.getAnnotation(Get.class);
        processHttpAnnotation(annotatedElement, Get.class, annotation.value());
      }

      // @Post
      for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Post.class)) {
        Post annotation = annotatedElement.getAnnotation(Post.class);
        processHttpAnnotation(annotatedElement, Post.class, annotation.value());
      }

      // @Put
      for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Put.class)) {
        Put annotation = annotatedElement.getAnnotation(Put.class);
        processHttpAnnotation(annotatedElement, Put.class, annotation.value());
      }

      // @Delete
      for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Delete.class)) {
        Delete annotation = annotatedElement.getAnnotation(Delete.class);
        processHttpAnnotation(annotatedElement, Delete.class, annotation.value());
      }

      // @Head
      for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Head.class)) {
        Head annotation = annotatedElement.getAnnotation(Head.class);
        processHttpAnnotation(annotatedElement, Head.class, annotation.value());
      }
    } catch (ProcessingException e) {
      error(e);
    }

    return true;
  }

  /**
   * Process the http method annotations
   *
   * @param annotatedElement The annotated element, should be a method
   * @throws ProcessingException
   */
  private void processHttpAnnotation(Element annotatedElement, Class<?> annotationClass,
      String annotationPath) throws ProcessingException {

    // check if a  method is annotated
    if (annotatedElement.getKind() != ElementKind.METHOD) {
      throw new ProcessingException(annotatedElement, "Only methods can be annotated with @%s",
          annotationClass.getSimpleName());
    }

    // Method must be public
    if (!annotatedElement.getModifiers().contains(Modifier.PUBLIC)) {
      throw new ProcessingException(annotatedElement, "Method annotated with @%s must be public",
          annotationClass.getSimpleName());
    }

    // method can not be static
    if (annotatedElement.getModifiers().contains(Modifier.STATIC)) {
      throw new ProcessingException(annotatedElement,
          "Method annotated with @%s can not be static!", annotationClass.getSimpleName());
    }

    // Check if surrounding element is a class
    Element enclosingClassElement = annotatedElement.getEnclosingElement();
    if (enclosingClassElement.getKind() != ElementKind.CLASS) {
      throw new ProcessingException(annotatedElement,
          "Only methods in a public class can be annotated with  @%s",
          annotationClass.getSimpleName());
    }

    // class must be public
    TypeElement classElement = (TypeElement) enclosingClassElement;
    if (!enclosingClassElement.getModifiers().contains(Modifier.PUBLIC)) {
      throw new ProcessingException(enclosingClassElement,
          "The class must be public, but %s isn't public",
          classElement.getQualifiedName().toString());
    }

    // @Api annotation must be set
    Api apiAnnotation = classElement.getAnnotation(Api.class);
    if (apiAnnotation == null) {
      throw new ProcessingException(classElement,
          "The class must be annotated with @%s " + "because it contains http annotated methods",
          Api.class.getSimpleName());
    }

    // Check if at least on path is set
    if (StringUtils.isEmpty(apiAnnotation.basePath()) && StringUtils.isEmpty(annotationPath)) {
      throw new ProcessingException(annotatedElement,
          "The @%s(basePath) or the @%s(path) must specify. Both are empty which is not allowed!",
          Api.class.getSimpleName(), annotationClass.getSimpleName());
    }
  }

  private void error(Element e, String msg, Object... args) {
    messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
  }

  private void error(ProcessingException e) {
    error(e.getCauseElement(), e.getMessage());
  }
}
