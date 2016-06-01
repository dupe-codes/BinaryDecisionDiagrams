package BDD;

/*
 * A framework for using Reduced Order Binary Decision Diagrams
 * (henceforth called Binary Decision Diagrams or BDDs for short) to solve
 * problems representable as boolean expressions.
 *
 * This file implements the base BDD class, from which all operations of generating
 * and working with BDDs can be launched.
 */

import BDD.graphWriter.GraphWriter;

import java.util.*;

// TODO: Add a "setOrdering" method
public final class BDD {
    private Node root;
    private HashMap<Node, Node> existingNodes;
    private BoolExpression expr;
    private Double numSolutions;

    private static final class Node {
      private String name;
      private int index;
      private Node low;
      private Node high;

      private static final Node TRUE_NODE = new Node("true", -1);
      private static final Node FALSE_NODE = new Node("false", -2);

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
      @Override
      public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node)o;
        if (node.name != name || node.index != index) return false;
        if (!node.low.equals(low) || !node.high.equals(high)) return false;
        return true;
      }

      public static boolean isTerminalNode(Node node) {
        return node.equals(TRUE_NODE) || node.equals(FALSE_NODE);
      }

      public static boolean isFalseNode(Node node) {
        return node.equals(FALSE_NODE);
      }

      public static boolean isTrueNode(Node node) {
        return node.equals(TRUE_NODE);
      }

      public static Node getTrueNode() {
        return TRUE_NODE;
      }

      public static Node getFalseNode() {
        return FALSE_NODE;
      }

    }

    private BDD(BoolExpression expr) {
      // Private constructor so that BDD instances cannot be directly created.
      // Clients must use the of method to create BDD objects
      this.expr = expr;
      this.existingNodes = new HashMap<Node, Node>();
    }

    public ArrayList<String> getVariables() {
      return expr.getVariables();
    }

    // TODO: this is a hack.. lol get rid of it
    private static int numNodes = 0;
    public static int getNumNodes() {
      return numNodes;
    }

    /*
     * Constructs a new BDD out of the given boolean expression.
     * TODO: Currently just uses arbtirary variable ordering given from the
     * BoolExpression object. This should change.
     *
     * Usage: BDD myBdd = BDD.of(new BoolExpression('(a | b) & y'));
     */
    public static BDD of(BoolExpression expr) {
      return of(expr, expr.getVariables());
    }

    public static Comparator<String> RandomComparator = new Comparator<String>() {
      private Random rgen = new Random();
      public int compare(String var1, String var2) {
        return (rgen.nextBoolean()) ? 1 : -1;
      }
    };

    public static BDD ofRandomOrder(BoolExpression expr) {
      List<String> vars = expr.getVariables();
      Collections.sort(vars, RandomComparator);
      return of(expr, vars);
    }

    public static BDD of(BoolExpression expr, List<String> vars) {
      if (vars.size() != expr.getVariables().size()) {
        throw new IllegalArgumentException("Variables missing from given variable ordering");
      }
      BDD result = new BDD(expr);
      HashMap<String, Boolean> assignments = new HashMap<String, Boolean>();
      result.root = build(1, expr, vars, assignments, result.existingNodes);
      numNodes += 2; // TODO: this is incorrect, but works for our BDDs for now...
      return result;
    }

    private static Node makeNode(HashMap<Node,Node> existingNodes, String name,
            int index, Node low, Node high) {

      if (low.equals(high)) return low;

      Node newNode = new Node(name, index, low, high);
      Node existing = existingNodes.get(newNode);
      if (existing != null) return existing;

      numNodes++;
      existingNodes.put(newNode, newNode);
      return newNode;

    }

    private static Node build(int index, BoolExpression expr, List<String> vars,
          HashMap<String, Boolean> assignments, HashMap<Node, Node> existingNodes) {

      if (index > vars.size()) {
        Node result = (expr.evaluate(assignments)) ? Node.getTrueNode() : Node.getFalseNode();
        existingNodes.put(result, result);
        return result;
      }
      String curr = vars.get(index-1);

      assignments.put(curr, false);
      Node low = build(index + 1, expr, vars, assignments, existingNodes);

      assignments.put(curr, true);
      Node high = build(index + 1, expr, vars, assignments, existingNodes);

      return makeNode(existingNodes, curr, index, low, high);
    }

    /*
     * Finds a satisfying assignment for the BDD.
     * Throws an exception if no satisfying assignment exists.
     *
     * Usage:
     *        try {
     *            Map<String, Boolean> ans = myBdd.anySat();
     *        } catch(IllegalArgumentException e) {
     *            // no assignment exists!
     *        }
     */
    public Map<String, Boolean> anySat() {
      HashMap<String, Boolean> result = new HashMap<String, Boolean>();
      if (!getSatisfyingAssignment(this.root, result)) {
        throw new IllegalArgumentException("No satisfying assignment exists.");
      }
      return result;
    }

    /*
     * Returns a satisfying assignment for the BDD rooted at curr.
     * The satisfying assignment, if found, will be the lexicographically smallest
     * such assignment.
     *
     * Returns false if no satisfying assignment exists.
     */
    private boolean getSatisfyingAssignment(Node curr, HashMap<String, Boolean> assignments) {
      if (Node.isFalseNode(curr)) return false;
      if (Node.isTrueNode(curr)) return true;

      if (Node.isFalseNode(curr.low)) {
        assignments.put(curr.name, true);
        return getSatisfyingAssignment(curr.high, assignments);
      }

      // Otherwise low node must lead to satisfying assignment
      assignments.put(curr.name, false);
      return getSatisfyingAssignment(curr.low, assignments);
    }

    // Outputs the BDD as a dot graph
    // TODO: Make up better way of generating unique ID for each node. hash codes
    //       will have collisions lol. Maybe a unique static counter in the Node class?
    public void outputGraph(String resultFile) {
      GraphWriter outWriter = new GraphWriter();
      outWriter.startGraph();
      outWriter.addHeader();

      // Add the nodes
      for (Node node : existingNodes.keySet()) {
        outWriter.addln(
          String.format(
            "Node%0$d [label=%1s, shape=%2s]",
            Math.abs(node.hashCode()),
            node.name,
            (Node.isTerminalNode(node)) ? "box" : "circle"
          )
        );
      }

      // Add the edges
      for (Node node : existingNodes.keySet()) {
          if (!Node.isTerminalNode(node)) {
            outWriter.addln(
              String.format("Node%1$d->Node%2$d [label=0, style=dashed]",
                Math.abs(node.hashCode()),
                Math.abs(node.low.hashCode())
              )
            );
            outWriter.addln(
              String.format("Node%1$d->Node%2$d [label=1, style=solid]",
                Math.abs(node.hashCode()),
                Math.abs(node.high.hashCode())
              )
            );
          }
      }
      outWriter.endGraph();

      if (!outWriter.writeGraphToFile(resultFile)) {
        System.err.println("Unable to write graph");
      }
    }

    // evaluate(x)

    // getSmallestSolution()

    public double getNumSolutions() {
      if (numSolutions == null) {
        numSolutions = solnCount(root);
      }
      return numSolutions;
    }

    private double solnCount(Node root) {
      if (Node.isFalseNode(root)) return 0;
      if (Node.isTrueNode(root)) return 1;
      int numVars = this.expr.getVariables().size();
      int lowIndex = (Node.isTerminalNode(root.low)) ? numVars + 1 : root.low.index;
      int highIndex = (Node.isTerminalNode(root.high)) ? numVars + 1 : root.high.index;

      return Math.pow(2, lowIndex - root.index - 1)*solnCount(root.low)
                + Math.pow(2, highIndex - root.index - 1)*solnCount(root.high);
    }

    // getRandomSolution()

    public List<Map<String, Boolean>> getAllSolutions() {
      return allSatAssignments(this.root);
    }

    private List<Map<String, Boolean>> allSatAssignments(Node root) {
        ArrayList<Map<String, Boolean>> result = new ArrayList<Map<String, Boolean>>();
        if (Node.isFalseNode(root)) return result;
        if (Node.isTrueNode(root)) {
          result.add(new HashMap<String, Boolean>());
          return result;
        }
        List<Map<String, Boolean>> lowResults = allSatAssignments(root.low);
        List<Map<String, Boolean>> highResults = allSatAssignments(root.high);
        for (Map<String, Boolean> lowSoln : lowResults) lowSoln.put(root.name, false);
        for (Map<String, Boolean> highSoln : highResults) highSoln.put(root.name, true);
        result.addAll(lowResults);
        result.addAll(highResults);
        return result;
    }
}
