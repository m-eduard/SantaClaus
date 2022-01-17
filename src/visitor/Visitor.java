package visitor;

import entities.Child;

public interface Visitor {
    /**
     * Method that applies an algorithm on a Child
     * @param child Child object
     */
    void visit(Child child);
}
