package de.barop.demos.jsr269.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;

import static javax.tools.Diagnostic.Kind.ERROR;

/**
 * Dumb annotation processor which generates always the same files.
 *
 * In a real world example you would generate source code on the on the basis of information you get
 * from the annotation and the annotated elements.
 *
 * @author Johannes Barop <jb@barop.de>
 */
@SupportedAnnotationTypes({"de.barop.demos.jsr269.annotation.Generate"})
public class GenerateAnnotationProcessor extends AbstractProcessor {

  private static final String CLASS_NAME = "de.barop.demos.jsr269.HelloWorldImpl";
  private static final String CLASS_SOURCE = "" +
      "package de.barop.demos.jsr269;\n" +
      "\n" +
      "// Generated \n" +
      "public class HelloWorldImpl {\n" +
      "\n" +
      "  public String helloWorld() {\n" +
      "    String unused = null;\n" +
      "    return \"Hello World!\";\n" +
      "  }\n" +
      "\n" +
      "}";

  public boolean process(Set<? extends TypeElement> annotations,
                         RoundEnvironment env) {
    final Messager messager = processingEnv.getMessager();

    // A processor must gracefully handle an empty set of annotations.
    if (annotations.size() == 0) {
      return false;
    }

    assert annotations.size() == 1 : "This processor expects only one annotation";

    for (TypeElement annotationElement : annotations) {
      final Set<? extends Element> annotatedElements = env.getElementsAnnotatedWith(annotationElement);
      for (Element annotatedElement : annotatedElements) {

        // Generate a Java source
        try {
          final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(CLASS_NAME);
          final Writer sourceWriter = sourceFile.openWriter();
          sourceWriter.write(CLASS_SOURCE);
          sourceWriter.close();
        } catch (Exception ex) {
          messager.printMessage(ERROR, "Error writing source" + ex.getMessage());
        }

        // Generate a Java class
        try {
          final JavaFileObject classFile = processingEnv.getFiler().createClassFile(CLASS_NAME + "2");
          final OutputStream classStream = classFile.openOutputStream();
          ClassPool pool = ClassPool.getDefault();
          CtClass cc = pool.makeClass(CLASS_NAME + "2");
          classStream.write(cc.toBytecode());
          classStream.close();
        } catch (Exception ex) {
          messager.printMessage(ERROR, "Error writing class" + ex.getMessage());
        }
      }
    }

    return true;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

}
