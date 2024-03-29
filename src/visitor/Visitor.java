package visitor;

import entities.Child;
import entities.Santa;

public interface Visitor {
    /**
     * Method that applies an algorithm on a Child
     * @param child Child object
     */
    void visit(Child child);
    /**
     * Method that applies an algorithm on a Santa
     * @param santa Santa object
     */
    void visit(Santa santa);
}

