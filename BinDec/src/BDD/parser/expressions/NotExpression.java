package BDD.parser.expressions;

import java.util.Map;

public final class NotExpression extends ExpressionTree {
  private ExpressionTree expr;

  public NotExpression(ExpressionTree expr) {
    this.expr = expr;
  }

  public boolean evaluate(Map<String, Boolean> assignments) {
    return true;
  }
}
