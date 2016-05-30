package cs166.BDD.parser;

import cs166.BDD.parser.expressions.*;

public final class Parser {
  private Lexer lexer;

  public Parser(Lexer lexer) {
    this.lexer = lexer;
  }

  public ExpressionTree buildExprTree() {
    return readExpr(0);
  }

  private ExpressionTree readExpr(int precedence) {
    ExpressionTree expr = readToken();
    while (true) {
      Token token = lexer.nextToken();
      int newPrecedence = getPrecedence(token);
      if (newPrecedence <= precedence) break;
      ExpressionTree rightSide = readExpr(newPrecedence);
      expr = (token == Token.AND) ? new AndExpression(expr, rightSide) : new OrExpression(expr, rightSide);
    }
  }
}
