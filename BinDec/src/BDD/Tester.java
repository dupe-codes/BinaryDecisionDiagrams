package BDD;

import java.io.*;

import BDD.*;
import BDD.parser.*;
import BDD.parser.expressions.*;

import java.util.HashMap;

public final class Tester {

  public static final void main(String args[]) {
    String test = "a & b";
    BoolExpression expr = new BoolExpression(test);

    HashMap<String, Boolean> assignments = new HashMap<String, Boolean>();
    assignments.put("a", true);
    assignments.put("b", false);
    System.out.println(expr.evaluate(assignments));
    System.out.println(expr.getVariables());
    BDD testBdd = BDD.of(expr);

    System.out.println("no crashes lol");
  }
}
