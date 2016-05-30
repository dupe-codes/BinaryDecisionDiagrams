package BDD.parser.expressions;

import java.util.Map;

public final class VariableExpression extends ExpressionTree {
  private String name;

  public VariableExpression(String name) {
    this.name = name;
  }

  public boolean evaluate(Map<String, Boolean> assignments) {
    Boolean result = assignments.get(name);
    if (!result) {
      throw new RuntimeException("Variable " + name + " not in given assignments");
    }
    return result;
  }
}
