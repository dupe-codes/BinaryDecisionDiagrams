package BDD;

import java.io.*;

import BDD.*;
import BDD.parser.*;
import BDD.parser.expressions.*;

import java.util.HashMap;

public final class Tester {

  public static final void main(String args[]) {
    String test = "(a | b) & !(x & (z | y))";
    Lexer lex = new Lexer(new ByteArrayInputStream(test.getBytes()));
    Parser parser = new Parser(lex);
    ExpressionTree tree = parser.buildExprTree();
    System.out.println(parser.getVariables());

    HashMap<String, Boolean> assignments = new HashMap<String, Boolean>();
    assignments.put("a", true);
    assignments.put("b", false);
    assignments.put("x", true);
    assignments.put("z", false);
    assignments.put("y", false);
    System.out.println(tree.evaluate(assignments));
    System.out.println("no crashes lol");
  }
}
