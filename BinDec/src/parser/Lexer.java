package cs166.BDD.parser;

import java.io.*;

public final class Lexer {
  private StreamTokenizer input;
  private String lastSeenVar;

  private static final String TRUE_LITERAL = "true";
  private static final String FALSE_LITERAL = "false";

  public Lexer(InputStream source) {
    BufferedReader rd = new BufferedReader(new InputStreamReader(source));
    input = new StreamTokenizer(rd);

    input.ordinaryChar('(');
    input.ordinaryChar(')');
    input.ordinaryChar('&');
    input.ordinaryChar('|');
    input.ordinaryChar('!');
  }

  public String getVariable() {
    return lastSeenVar;
  }

  // TODO: How to signal that this has exhausted the input...?
  public Token nextToken() {
    Token result = Token.NONE;
    try {
      switch (input.nextToken()) {
        case StreamTokenizer.TT_WORD: {
          if (input.sval.equalsIgnoreCase(TRUE_LITERAL)) {
            result = Token.TRUE;
          } else if (input.sval.equalsIgnoreCase(FALSE_LITERAL)) {
            result = Token.FALSE;
          } else {
            result = Token.VARIABLE;
            lastSeenVar = input.sval;
          }
          break;
        }
        case '(':
          result = Token.LEFT_PAREN;
          break;
        case ')':
          result = Token.RIGHT_PAREN;
          break;
        case '&':
          result = Token.AND;
          break;
        case '|':
          result = Token.OR;
          break;
        case '!':
          result = Token.NOT;
          break;
        default:
          result = Token.INVALID;
          break;
      }
    } catch (IOException e) {}
    return result;
  }

  //public void close() {
    // TODO: is this needed?
  //}
}
