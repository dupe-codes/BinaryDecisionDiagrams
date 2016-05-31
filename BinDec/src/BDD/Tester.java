package BDD;

import java.io.*;

import BDD.*;
import BDD.parser.*;
import BDD.parser.expressions.*;

import java.util.HashMap;

public final class Tester {

  public static final void main(String args[]) {
    String test = "(a & b) | (y & !(g | z))";
    BoolExpression expr = new BoolExpression(test);

    System.out.println(expr.getVariables());
    BDD testBdd = BDD.of(expr);
    testBdd.outputGraph("test2.dot");

    System.out.println("");
    String test2 = "(a | b) & (!a | !b)";
    BDD secondBDD = BDD.of(new BoolExpression(test2));

    //secondBDD.outputGraph();
    System.out.println("\nno crashes lol");
  }
}
