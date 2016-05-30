package BDD;

/*
 * This file implements a class for representing boolean expressions
 */

import BDD.parser.*;
import BDD.parser.expressions.ExpressionTree;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.ArrayList;

public final class BoolExpression {
  private ExpressionTree expr;
  private ArrayList<String> variables;

  /*
   * Construct a BoolExpression from the given string
   *
   * TODO(nick): Change RuntimeException to a different type of exception.
   *             maybe ParseException?
   */
  public BoolExpression(String expr) {
    Lexer lexer = new Lexer(new ByteArrayInputStream(expr.getBytes()));
    try {
      Parser parser = new Parser(lexer);
      this.expr = parser.buildExprTree();
      variables = parser.getVariables();
    } catch (RuntimeException e) {
      System.out.println("An error occurred building the expression tree");
      System.out.println(e);
    }
  }

  public boolean evaluate(Map<String, Boolean> assignments) {
    return expr.evaluate(assignments);
  }

}
