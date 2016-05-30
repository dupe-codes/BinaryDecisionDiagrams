package cs166.BDD;

import java.io.*;

import cs166.BDD.*;
import cs166.BDD.parser.*;
import cs166.BDD.parser.expressions.*;

public final class Tester {

  public static final void main(String args[]) {
    // DO stuff to test here
    String test = "(a | b) & !x";
    Lexer lex = new Lexer(new ByteArrayInputStream(test.getBytes()));
    Parser parser = new Parser(lex);
    ExpressionTree tree = parser.buildExprTree();
    System.out.println("no crashes lol");
  }
}
