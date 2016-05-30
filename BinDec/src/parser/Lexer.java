package cs166.BDD.parser;

import java.io.*;

public final class Lexer {
  private StreamTokenizer input;

  public Lexer(InputStream source) {
    BufferedReader rd = new BufferedReader(new InputStreamReader(source));
    input = new StreamTokenizer(rd);

    input.ordinaryChar('(');
    input.ordinaryChar(')');
    input.ordinaryChar('&');
    input.ordinaryChar('|');
    input.ordinaryChar('!');
  }

  public Token nextToken() {
    Token result = Token.NONE;
    return result;
  }

  //public void close() {
    // TODO: is this needed?
  //}
}
