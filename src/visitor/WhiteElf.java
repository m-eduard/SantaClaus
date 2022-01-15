package visitor;

import entities.Child;

public class WhiteElf implements Visitor {
    @Override
    public void visit(Child child) {
        /* This elf doesn't modify anything */
    }
}
