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

public class InputLoader {
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

        ArrayList<ChildInput> children = null;
        ArrayList<GiftInput> gifts = null;
        ArrayList<AnnualChangeInput> annualChanges = new ArrayList<>();

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
                new InitialDataInput(children, gifts), annualChanges);
    }

    public ArrayList<ChildInput> readChildren(final JSONArray jsonChildren) {
        ArrayList<ChildInput> children = new ArrayList<>();

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
                        Utils.JSONArrayToCategoryList((JSONArray) ((JSONObject) jsonChild)
                                .get(Constants.GIFTS_PREFERENCES))
                ));
            }
        } else {
            System.out.println("No child on the Santa's list");
        }

        return children;
    }

    public ArrayList<GiftInput> readGifts(final JSONArray jsonGiftsList) {
        ArrayList<GiftInput> gifts = new ArrayList<>();

        if (jsonGiftsList != null) {
            for (Object jsonGift : jsonGiftsList) {
                gifts.add(new GiftInput(
                        (String) ((JSONObject) jsonGift).get(Constants.PRODUCT_NAME),
                        Double.parseDouble(((JSONObject) jsonGift)
                                .get(Constants.PRICE).toString()),
                        Utils.stringToCategory((String) ((JSONObject) jsonGift)
                                .get(Constants.CATEGORY))
                ));
            }
        } else {
            System.out.println("No gifts on the list:(");
        }

        return gifts;
    }

    public ArrayList<AnnualChangeInput> readAnnualChanges(final JSONArray jsonAnnualChanges) {
        ArrayList<AnnualChangeInput> annualChanges = new ArrayList<>();

        if (jsonAnnualChanges != null) {
            for (Object jsonAnnualChange : jsonAnnualChanges) {
                annualChanges.add(new AnnualChangeInput(
                        Double.parseDouble(((JSONObject) jsonAnnualChange)
                                .get(Constants.NEW_BUDGET).toString()),
                        readGifts((JSONArray) ((JSONObject) jsonAnnualChange)
                                .get(Constants.NEW_GIFTS)),
                        readChildren((JSONArray) ((JSONObject) jsonAnnualChange)
                                .get(Constants.NEW_CHILDREN)),
                        readChildUpdates((JSONArray) ((JSONObject) jsonAnnualChange)
                                .get(Constants.CHILDREN_UPDATES))
                ));
            }
        } else {
            System.out.println("No annual changes");
        }

        return annualChanges;
    }

    public ArrayList<ChildUpdateInput> readChildUpdates(final JSONArray jsonChildUpdates) {
        ArrayList<ChildUpdateInput> childUpdates = new ArrayList<>();

        if (jsonChildUpdates != null) {
            for (Object jsonChildUpdate : jsonChildUpdates) {
                childUpdates.add(new ChildUpdateInput(
                        Integer.parseInt(((JSONObject) jsonChildUpdate)
                                .get(Constants.ID).toString()),
                        ((JSONObject) jsonChildUpdate).get(Constants.NICE_SCORE) == null
                                ? null
                                : Double.parseDouble(((JSONObject) jsonChildUpdate)
                                .get(Constants.NICE_SCORE).toString()),
                        Utils.JSONArrayToCategoryList((JSONArray) ((JSONObject) jsonChildUpdate)
                                .get(Constants.GIFTS_PREFERENCES))
                ));
            }
        } else {
            System.out.println("No children updates");
        }

        return childUpdates;
    }
}
