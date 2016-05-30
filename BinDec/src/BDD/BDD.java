package BDD;

/*
 * A framework for using Reduced Order Binary Decision Diagrams
 * (henceforth called Binary Decision Diagrams or BDDs for short) to solve
 * problems representable as boolean expressions.
 *
 * This file implements the base BDD class, from which all operations of generating
 * and working with BDDs can be launched.
 */

public final class BDD {

    private BDD() {
      // Private constructor so that BDD instances cannot be directly created.
      // Clients must use the getInstance method to create BDD objects
    }

    public BDD getInstance() {
      return new BDD();
    }

    // evaluate(x)

    // getSmallestSolution()

    // getNumSolutions()

    // getRandomSolution()

    // getAllSolutions()
}
