package ProblemSolver;

import BDD.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.regex.*;

import java.io.*;

public final class ProblemSolver {

  public static final void main(String args[]) {
    if (args.length < 1) {
      System.err.println("Two few arguments.");
      System.exit(1);
    }

    HashMap<String, String> options = parseOptions(args);
    System.out.println(options);

    if (options.containsKey("inFile")) {
      readExprFromFile(options);
    } else {
      createAndSolveBDD(options);
    }
  }

  private static void readExprFromFile(HashMap<String, String> options) {
      StringBuilder builder = new StringBuilder();
      String varOrder = "";
      try (BufferedReader br = new BufferedReader(new FileReader(options.get("inFile")))) {
        varOrder = br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
          if (!line.startsWith("//")) {
            if (builder.length() != 0) builder.append(" & ");
            builder.append(line);
          }
        }
      } catch (Exception e) {
        System.err.println("Error encountered reading file");
        System.err.println(e);
        System.exit(1);
      }

      String expr = builder.toString();
      options.put("expression", expr);

      // TODO: make this better
      if (!options.containsKey("order")) options.put("order", varOrder);
      createAndSolveBDD(options);
  }

  private static void createAndSolveBDD(HashMap<String, String> options) {
    try {
      BoolExpression expr = new BoolExpression(options.get("expression"));

      BDD bdd = null;
      if (options.containsKey("order")) {
        String order = options.get("order");
        bdd = (order.equals("random")) ? BDD.ofRandomOrder(expr) : BDD.of(expr, parseOrder(order));
      } else {
        bdd = BDD.of(expr);
      }

      List<String> vars = bdd.getVariables();
      double numSolutions = bdd.getNumSolutions();

      Map<String, Boolean> solution = (numSolutions != 0) ? bdd.anySat() : null;

      reportStats(options.get("expression"), vars, numSolutions, BDD.getNumNodes(), solution);

      if (options.containsKey("outFile")) {
        bdd.outputGraph(options.get("outFile"));
      }

    } catch (Exception e) {
      System.err.println("Error encountered while solving expression.");
      System.err.println(e);
      System.exit(1);
    }
  }

  private static void reportStats(String expr, List<String> vars, double numSolutions,
        int numNodes, Map<String, Boolean> soln) {
    System.out.println("Statistics for expression: " + expr);
    System.out.println("- - - - - - -");

    System.out.print("\nVariable Ordering: ");
    System.out.println(vars);

    System.out.print("\nNumber of BDD Nodes: ");
    System.out.println(numNodes);

    System.out.print("\nNum solutions: ");
    System.out.println(numSolutions);

    if (soln != null) {
      System.out.print("\nSatisfying solution: ");
      System.out.println(soln);
    }
  }

  // TODO: Add option to specify variable ordering
  private static HashMap<String, String> parseOptions(String args[]) {
    HashMap<String, String> result = new HashMap<String, String>();
    for (int i = 0; i < args.length; i++) {
      String arg = args[i];
      if (arg.length() > 0 && arg.charAt(0) == '-') {

        if (arg.equals("-file") || arg.equals("-f")) {
          try {
            result.put("inFile", args[i+1]);
            i++;
          } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Expecting file name after -file flag");
            System.exit(1);
          }
        }

        if (arg.equals("-o") || arg.equals("-out")) {
          try {
            result.put("outFile", args[i+1]);
            i++;
          } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Expecting file name after -out flag");
            System.exit(1);
          }
        }

        if (arg.equals("-order")) {
          try {
            result.put("order", args[i+1]);
            i++;
          } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Expecting variable ordering after -order flag");
            System.exit(1);
          }
        }
      } else {
        // Assume this argument is a boolean expression to parse;
        result.put("expression", arg);
      }
    }

    return result;
  }

  private static List<String> parseOrder(String orderStr) {
    return Arrays.asList(orderStr.split(Pattern.quote(" ")));
  }
}
