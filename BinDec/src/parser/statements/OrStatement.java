package cs166.BDD.parser.statements;

import java.util.Map;

public final class OrStatement extends ExpressionTree {
  private ExpressionTree left;
  private ExpressionTree right;

  public OrStatement(ExpressionTree left, ExpressionTree right) {
    this.left = left;
    this.right = right;
  }

  public boolean evaluate(Map<String, Boolean> assignments) {
    return true;
  }
}
