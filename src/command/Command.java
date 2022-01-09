package command;

/**
 * Interface for the commands applied on a Santa or Child object,
 * such as yearly update.
 *
 * The commands are applied on the Santa and Child.
 */
public interface Command {
    /**
     * Method that applies a given command on the receiver
     */
    void execute();
}
