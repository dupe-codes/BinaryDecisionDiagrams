package BDD;

/*
 * A framework for using Reduced Order Binary Decision Diagrams
 * (henceforth called Binary Decision Diagrams or BDDs for short) to solve
 * problems representable as boolean expressions.
 *
 * This file implements the base BDD class, from which all operations of generating
 * and working with BDDs can be launched.
 */

import java.util.HashMap;

public final class BDD {
    private Node root;

    private static final class Node {
      private String name;
      private int index;
      private Node low;
      private Node high;

      private static final Node TRUE_NODE = new Node("true", -1);
      private static final Node FALSE_NODE = new Node("false", -1);

      private String stringified = null;

      public Node(String name, int index) {
        this.name = name;
        this.index = index;
        low = null;
        high = null;
      }

      public Node(String name, int index, Node low, Node high) {
        this.name = name;
        this.index = index;
        this.low = low;
        this.high = high;
      }

      public int getIndex() {
        return index;
      }

      @Override
      public int hashCode() {
        // TODO: Come up with a better hash code!
        return toString().hashCode();
      }

      @Override
      public String toString() {
        if (stringified == null) {
          stringified = String.format(
            "(BDD Node %0$d: (%1s) low: %2s high %3s)",
            index,
            name,
            (low != null) ? low.toString() : "null",
            (high != null) ? high.toString() : "null"
          );
        }
        return stringified;
      }

      // TODO: Improve this expensive recursion
      //       note - actually might be okay due to string caching added above
      @Override
      public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node)o;
        if (node.name != name || node.index != index) return false;
        if (!node.low.equals(low) || !node.high.equals(high)) return false;
        return true;
      }

      public static Node getTrueNode() {
        return TRUE_NODE;
      }

      public static Node getFalseNode() {
        return FALSE_NODE;
      }

    }

    private BDD() {
      // Private constructor so that BDD instances cannot be directly created.
      // Clients must use the of method to create BDD objects
    }

    /*
     * Constructs a new BDD out of the given boolean expression.
     * TODO: Currently just uses arbtirary variable ordering given from the
     * BoolExpression object. This should change.
     *
     * Usage: BDD myBdd = BDD.of(new BoolExpression('(a | b) & y'));
     */
    public static BDD of(BoolExpression expr) {
      BDD result = new BDD();
      HashMap<String, Boolean> assignments = new HashMap<String, Boolean>();
      HashMap<Node, Node> existingNodes = new HashMap<Node, Node>();
      result.root = build(0, expr, assignments, existingNodes);

      System.out.println(result.root);
      return result;
    }

    private static Node makeNode(HashMap<Node,Node> existingNodes, String name,
            int index, Node low, Node high) {

      if (low.equals(high)) return low;
      Node newNode = new Node(name, index, low, high);
      Node existing = existingNodes.get(newNode);
      if (existing != null) return existing;
      existingNodes.put(newNode, newNode);
      return newNode;

    }

    private static Node build(int index, BoolExpression expr,
          HashMap<String, Boolean> assignments, HashMap<Node, Node> existingNodes) {

      if (index >= expr.getVariables().size()) {
          return (expr.evaluate(assignments)) ? Node.getTrueNode() : Node.getFalseNode();
      }
      String curr = expr.getVariables().get(index);
      System.out.println("Index "  + index + ": " + curr);
      assignments.put(curr, false);
      Node low = build(index + 1, expr, assignments, existingNodes);
      assignments.put(curr, true);
      Node high = build(index + 1, expr, assignments, existingNodes);
      return makeNode(existingNodes, curr, index, low, high);
    }

    // evaluate(x)

    // getSmallestSolution()

    // getNumSolutions()

    // getRandomSolution()

    // getAllSolutions()
}
