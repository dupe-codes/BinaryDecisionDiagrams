package BDD;

import java.io.*;

import BDD.*;
import BDD.parser.*;
import BDD.parser.expressions.*;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

public final class Tester {

  public static final void main(String args[]) {
    //String test = "(a & b) | (y & !(g | z))";
    String test = "a | b";
    BoolExpression expr = new BoolExpression(test);

    System.out.println(expr.getVariables());

    /*ArrayList<String> varOrder = new ArrayList<String>(
      Arrays.asList("z", "g", "y", "a", "b")
    );*/
    BDD testBdd = BDD.of(expr);
    testBdd.outputGraph("test2.dot");
    Map<String, Boolean> ans = testBdd.anySat();
    System.out.println(ans);
    System.out.println(testBdd.getNumSolutions());
    System.out.println(testBdd.getAllSolutions());

    System.out.println("");
    String test2 = "(a | b) & (!a | !b)";
    BDD secondBDD = BDD.of(new BoolExpression(test2));
    System.out.println(secondBDD.getNumSolutions());
    System.out.println(secondBDD.getAllSolutions());

    //secondBDD.outputGraph();
    System.out.println("\nno crashes lol");
  }
}
