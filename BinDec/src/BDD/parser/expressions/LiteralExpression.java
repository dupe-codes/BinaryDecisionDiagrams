package BDD.parser.expressions;

import BDD.parser.Token;
import java.util.Map;

public final class LiteralExpression extends ExpressionTree {
  private boolean value;

  public LiteralExpression(Token type) {
    value = type == Token.TRUE;
  }

  public boolean evaluate(Map<String, Boolean> assignments) {
    return value;
  }
}
