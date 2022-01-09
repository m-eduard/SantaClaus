package command;

import java.util.LinkedList;

/**
 * The invoker that executes the commands
 * (implemented as a singleton, to be accessible from
 * anywhere within the program (so the history will store
 * all the executed commands))
 */
public final class Invoker {
    private static Invoker invokerInstance = null;
    /**
     * List of commands, useful if the redo operation
     * will be needed at some point in the implementation
     */
    private final LinkedList<Command> history = new LinkedList<>();

    private Invoker() { }

    /**
     * Clears the history
     */
    public void restart() {
        history.clear();
    }

    /**
     *  Get the instance of the invoker
     * @return Invoker instance
     */
    public static Invoker getInstance() {
        if (invokerInstance == null) {
            invokerInstance = new Invoker();
        }
        return invokerInstance;
    }

    /**
     * Executes a given command
     * @param command a Command object
     */
    public void execute(final Command command) {
        history.add(command);
        command.execute();
    }
}
