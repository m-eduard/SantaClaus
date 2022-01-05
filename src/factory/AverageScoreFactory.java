package factory;

import enums.AgeCategory;
import strategy.AverageScoreStrategy;
import strategy.BabyAverageScoreStrategy;
import strategy.KidAverageScoreStrategy;
import strategy.TeenAverageScoreStrategy;

/**
 * This class is used to generate
 * an AverageScoreStrategy object
 *
 * (implemented as singleton)
 */
public final class AverageScoreFactory {
    private static AverageScoreFactory factory;

    private AverageScoreFactory() { }

    public static AverageScoreFactory getInstance() {
        if (factory == null)
            factory = new AverageScoreFactory();
        return factory;
    }

    public AverageScoreStrategy createStrategy(final AgeCategory strategyType) {
        return switch (strategyType) {
            case BABY -> new BabyAverageScoreStrategy();
            case KID -> new KidAverageScoreStrategy();
            case TEEN -> new TeenAverageScoreStrategy();
            default -> null;
        };
    }
}
