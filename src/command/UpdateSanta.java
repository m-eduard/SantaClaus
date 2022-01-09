package command;

import entities.Child;
import entities.Gift;
import entities.Santa;
import enums.AgeCategory;
import update.ChildUpdate;

import java.util.ArrayList;
import java.util.List;

public class UpdateSanta implements Command {
    /**
     * The Santa object that receives the command
     */
    private final Santa santa;
    private final double newBudget;
    private final List<Gift> newGifts;
    private final List<Child> newChildren;
    /**
     * List of updates for the current year
     */
    private final List<ChildUpdate> childrenUpdate;

    public UpdateSanta(final Santa santa,
                       final double newBudget,
                       final List<Gift> newGifts,
                       final List<Child> newChildren,
                       final List<ChildUpdate> childrenUpdate) {
        this.santa = santa;
        this.newBudget = newBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdate = childrenUpdate;
    }

    /**
     * Updates Santa's resources for the new year
     * and also updates the info for the children
     * that are still on its list.
     */
    @Override
    public void execute() {
        santa.setBudget(newBudget);

        /* Increment the age for the children that are
         * on Santa's list
         * */
        for (int id : santa.getChildrenList().keySet()) {
            santa.getChildrenList().get(id).incrementAge();
        }

        /* Add all the new children in santa's list
         * (which is represented as Map)
         * */
        for (Child child : newChildren) {
            santa.getChildrenList().put(child.getId(), child);
        }

        /* Remove the children > 18yo, by gathering their id-s
         * and deleting the corresponding entries from the map
         * */
        List<Integer> idsToRemove = new ArrayList<>();
        for (int id : santa.getChildrenList().keySet()) {
            if (santa.getChildrenList().get(id)
                    .getAgeCategory() == AgeCategory.YOUNG_ADULT) {
                idsToRemove.add(id);
            }
        }
        idsToRemove.forEach(santa.getChildrenList()::remove);

        /* Update the info for the children that are still on Santa's list */
        for (ChildUpdate childUpdate : childrenUpdate) {
            if (santa.getChildrenList().containsKey(childUpdate.getId())) {
                Invoker invoker = Invoker.getInstance();
                invoker.execute(new UpdateChild(santa.getChildrenList().get(childUpdate.getId()),
                                                childUpdate));
            }
        }
    }
}
