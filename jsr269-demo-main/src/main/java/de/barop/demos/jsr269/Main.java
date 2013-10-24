package de.barop.demos.jsr269;

import de.barop.demos.jsr269.annotation.Generate;

/**
 * @author Johannes Barop <jb@barop.de>
 */
@Generate // Trigger the annotation processor
public class Main {

  public static void main(String args[]) throws Exception {
    System.out.println(new HelloWorldImpl().helloWorld());
    HelloWorldImpl2 helloWorld2 = null; // fails in intellij
  }

}
