package command;

import entities.Child;
import enums.Category;
import update.ChildUpdate;

import java.util.List;

public class UpdateChild implements Command {
    /**
     * The Child object that receives the command
     */
    private final Child child;
    /**
     * The changes implemented by the command
     */
    private final ChildUpdate childUpdate;

    public UpdateChild(final Child child,
                       final ChildUpdate childUpdate) {
        this.child = child;
        this.childUpdate = childUpdate;
    }

    /**
     * Updates the child's info with new data, gathered from the last year.
     */
    @Override
    public void execute() {
        List<Category> newPreferences = childUpdate.getGiftsPreferences();
        List<Category> giftsPreferences = child.getGiftsPreferences();

        /* Insert the new gifts preferences into the list,
         * pushing consecutively the least important ones
         * on the first position, so in the end, the list will
         * store the preferences decreasingly after priority
         * */
        for (int i = newPreferences.size() - 1; i >= 0; --i) {
            /* Remove the preference, if it already exists,
             * in order to insert it again, at the beginning
             * of the list (with higher priority)
             * */
            giftsPreferences.remove(newPreferences.get(i));
            giftsPreferences.add(0, newPreferences.get(i));
        }

        if (childUpdate.getNiceScore() != null) {
            child.getNiceScoreHistory().add(childUpdate.getNiceScore());
        }
    }
}
