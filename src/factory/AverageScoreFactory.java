package factory;

import strategy.AverageScoreStrategy;
import strategy.BabyAverageScoreStrategy;
import strategy.KidAverageScoreStrategy;
import strategy.TeenAverageScoreStrategy;

/**
 * This class is used to generate an AverageScoreStrategy object
 * (implemented as singleton)
 */
public final class AverageScoreFactory {
    private static AverageScoreFactory factory;

    private AverageScoreFactory() { }

    /**
     * Method that checks if an instance of
     * the current object exists, and if not
     * it creates one
     *
     * @return AverageScoreFactory instance
     */
    public static AverageScoreFactory getInstance() {
        if (factory == null) {
            factory = new AverageScoreFactory();
        }
        return factory;
    }

    /**
     * Method that creates a strategy based on what type is needed
     * @param strategyType String that specifies what strategy needs to be returned
     * @return an AverageScoreStrategy object
     */
    public AverageScoreStrategy createStrategy(final String strategyType) {
        return switch (strategyType) {
            case "BABY" -> new BabyAverageScoreStrategy();
            case "KID" -> new KidAverageScoreStrategy();
            case "TEEN" -> new TeenAverageScoreStrategy();
            default -> null;
        };
    }
}
