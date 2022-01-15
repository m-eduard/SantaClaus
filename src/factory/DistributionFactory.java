package factory;

import enums.CityStrategyEnum;
import strategy.ChildScoreDistributionStrategy;
import strategy.CityScoreDistributionStrategy;
import strategy.DistributionStrategy;
import strategy.IdDistributionStrategy;

/**
 * This class is used to generate a DistributionStrategy object
 * (implemented as singleton)
 */
public class DistributionFactory {
    private static DistributionFactory factory;

    private DistributionFactory() { }

    public static DistributionFactory getInstance() {
        if (factory == null) {
            factory = new DistributionFactory();
        }
        return factory;
    }

    /**
     * Method that creates a strategy based on what type is needed
     * @param strategyType enum that specifies what strategy needs to be returned
     * @return a DistributionStrategy object
     */
    public DistributionStrategy createStrategy(final CityStrategyEnum strategyType) {
        return switch (strategyType) {
            case ID -> new IdDistributionStrategy();
            case NICE_SCORE -> new ChildScoreDistributionStrategy();
            case NICE_SCORE_CITY -> new CityScoreDistributionStrategy();
            default -> null;
        };
    }
}
