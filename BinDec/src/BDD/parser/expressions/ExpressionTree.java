package BDD.parser.expressions;

import java.util.Map;
import java.util.HashMap;

public abstract class ExpressionTree {

  public boolean evaluate() {
    return evaluate(new HashMap<String, Boolean>());
  }

  public abstract boolean evaluate(Map<String, Boolean> assignments);
}
