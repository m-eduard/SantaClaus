package input;

import java.util.List;

/**
 * Class that contains all the information parsed from the input JSON
 */
public final class Input {
    /**
     * Number of years to run the simulation
     */
    private final int numberOfYears;
    /**
     * Initial Santa's budget
     */
    private final double santaBudget;
    /**
     * Data provided for the initial simulation
     */
    private final InitialDataInput initialData;
    /**
     * List of changes that occur in each year
     */
    private final List<AnnualChangeInput> annualChanges;

    public Input(final int numberOfYears, final double santaBudget,
                 final InitialDataInput initialData,
                 final List<AnnualChangeInput> annualChanges) {
        this.numberOfYears = numberOfYears;
        this.santaBudget = santaBudget;
        this.initialData = initialData;
        this.annualChanges = annualChanges;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public double getSantaBudget() {
        return santaBudget;
    }

    public InitialDataInput getInitialData() {
        return initialData;
    }

    public List<AnnualChangeInput> getAnnualChanges() {
        return annualChanges;
    }
}
