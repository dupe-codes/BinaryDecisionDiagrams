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
    private HashMap<Node, Node> existingNodes;


    private final class Node {
      private String name;
      private int index;
      private Node low;
      private Node high;

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
        return toString().hashCode();
      }

      @Override
      public String toString() {
        return name + index + low.getIndex() + high.getIndex();
      }

      // TODO: Finish this...
      @Override
      public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node)o;
        return true;
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
     */
    public BDD of(BoolExpression expr) {
      return null;
    }

    private Node makeNode(int index, Node low, Node high) {
      if (low.equals(high)) return low;
      return null;
    }

    // evaluate(x)

    // getSmallestSolution()

    // getNumSolutions()

    // getRandomSolution()

    // getAllSolutions()
}
