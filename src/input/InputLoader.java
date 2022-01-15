package input;

import common.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to parse the input data from JSON files
 */
public final class InputLoader {
    /**
     * The path of the input file
     */
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * Method that reads the input data
     * @return an Input object
     */
    public Input readData() {
        int numberOfYears = 0;
        double santaBudget = 0.0;

        List<ChildInput> children = null;
        List<GiftInput> gifts = null;
        List<AnnualChangeInput> annualChanges = new ArrayList<>();

        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser())
                    .parse(new FileReader(inputPath));

            numberOfYears = Integer.parseInt(jsonObject
                    .get(Constants.YEARS).toString());
            santaBudget = Double.parseDouble(jsonObject
                    .get(Constants.BUDGET).toString());

            JSONObject jsonInitialData = (JSONObject) jsonObject
                    .get(Constants.INITIAL_DATA);
            children = readChildren((JSONArray) jsonInitialData
                    .get(Constants.CHILDREN));
            gifts = readGifts((JSONArray) jsonInitialData
                    .get(Constants.GIFTS_LIST));

            annualChanges = readAnnualChanges((JSONArray) jsonObject
                    .get(Constants.ANNUAL_CHANGES));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return new Input(numberOfYears, santaBudget,
                new InitialDataInput(children, gifts),
                annualChanges);
    }

    /**
     * Creates a list of ChildInput instances, using the parsed input data
     * @param jsonChildren array of children as JSONObject(s)
     * @return list of children (every child is represented as a ChildInput instance)
     */
    public List<ChildInput> readChildren(final JSONArray jsonChildren) {
        List<ChildInput> children = new ArrayList<>();

        if (jsonChildren != null) {
            for (Object jsonChild : jsonChildren) {
                children.add(new ChildInput(
                        Integer.parseInt(((JSONObject) jsonChild).get(Constants.ID).toString()),
                        (String) ((JSONObject) jsonChild).get(Constants.LAST_NAME),
                        (String) ((JSONObject) jsonChild).get(Constants.FIRST_NAME),
                        Integer.parseInt(((JSONObject) jsonChild).get(Constants.AGE).toString()),
                        Utils.stringToCity((String) ((JSONObject) jsonChild).get(Constants.CITY)),
                        Double.parseDouble(((JSONObject) jsonChild)
                                .get(Constants.NICE_SCORE).toString()),
                        Utils.jsonArrayToCategoryList((JSONArray) ((JSONObject) jsonChild)
                                .get(Constants.GIFTS_PREFERENCES)),
                        Integer.parseInt(((JSONObject) jsonChild).get(Constants.BONUS_SCORE).toString()),
                        Utils.stringToElfType((String) ((JSONObject) jsonChild).get(Constants.ELF))
                ));
            }
        } else {
            System.out.println("No child on the Santa's list");
        }
        return children;
    }

    /**
     * Loads the parsed input data into a list of GiftInput instances
     * @param jsonGiftsList JSONArray of gifts
     * @return list of GiftInput(s)
     */
    public List<GiftInput> readGifts(final JSONArray jsonGiftsList) {
        List<GiftInput> gifts = new ArrayList<>();

        if (jsonGiftsList != null) {
            for (Object jsonGift : jsonGiftsList) {
                gifts.add(new GiftInput(
                        (String) ((JSONObject) jsonGift)
                                .get(Constants.PRODUCT_NAME),
                        Double.parseDouble(((JSONObject) jsonGift)
                                .get(Constants.PRICE).toString()),
                        Utils.stringToCategory((String) ((JSONObject) jsonGift)
                                .get(Constants.CATEGORY)),
                        Integer.parseInt(((JSONObject) jsonGift)
                                .get(Constants.QUANTITY).toString())
                ));
            }
        } else {
            System.out.println("No gifts on the list:(");
        }
        return gifts;
    }

    /**
     * Creates a list to store every annual change
     * @param jsonAnnualChanges JSONArray of annual changes
     * @return list of annual changes (as AnnualChangeInput objects)
     */
    public List<AnnualChangeInput> readAnnualChanges(final JSONArray jsonAnnualChanges) {
        List<AnnualChangeInput> annualChanges = new ArrayList<>();

        if (jsonAnnualChanges != null) {
            for (Object jsonAnnualChange : jsonAnnualChanges) {
                annualChanges.add(new AnnualChangeInput(
                        Double.parseDouble(((JSONObject) jsonAnnualChange)
                                .get(Constants.NEW_BUDGET).toString()),
                        readGifts((JSONArray) ((JSONObject) jsonAnnualChange)
                                .get(Constants.NEW_GIFTS)),
                        readChildren((JSONArray) ((JSONObject) jsonAnnualChange)
                                .get(Constants.NEW_CHILDREN)),
                        readChildrenUpdates((JSONArray) ((JSONObject) jsonAnnualChange)
                                .get(Constants.CHILDREN_UPDATES)),
                        Utils.stringToCityStrategy((String) ((JSONObject) jsonAnnualChange)
                                .get(Constants.STRATEGY))
                ));
            }
        } else {
            System.out.println("No annual changes");
        }
        return annualChanges;
    }

    /**
     * Reads the children updates for one year
     * @param jsonChildrenUpdates JSONArray of children updates
     * @return list of updates for children
     */
    public List<ChildUpdateInput> readChildrenUpdates(final JSONArray jsonChildrenUpdates) {
        List<ChildUpdateInput> childUpdates = new ArrayList<>();

        if (jsonChildrenUpdates != null) {
            for (Object jsonChildUpdate : jsonChildrenUpdates) {
                childUpdates.add(new ChildUpdateInput(
                        Integer.parseInt(((JSONObject) jsonChildUpdate)
                                .get(Constants.ID).toString()),
                        ((JSONObject) jsonChildUpdate).get(Constants.NICE_SCORE) == null
                                ? null
                                : Double.parseDouble(((JSONObject) jsonChildUpdate)
                                .get(Constants.NICE_SCORE).toString()),
                        Utils.jsonArrayToCategoryList((JSONArray) ((JSONObject) jsonChildUpdate)
                                .get(Constants.GIFTS_PREFERENCES)),
                        Utils.stringToElfType((String) ((JSONObject) jsonChildUpdate)
                                .get(Constants.ELF))
                ));
            }
        } else {
            System.out.println("No children updates");
        }
        return childUpdates;
    }
}
