package utils;

import common.Constants;
import enums.AgeCategory;
import enums.Category;
import enums.Cities;
import enums.CityStrategyEnum;
import enums.ElvesType;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains methods that help to parse the input.
 */
public final class Utils {
    /**
     * Moves the elements of a JSONArray into a list of Category enum values
     * @param array JSONArray
     * @return a list of Category enum types
     */
    public static List<Category> jsonArrayToCategoryList(final JSONArray array) {
        List<Category> arrayAsList = new ArrayList<>();
        for (Object object : array) {
            arrayAsList.add(stringToCategory((String) object));
        }
        return arrayAsList;
    }

    /**
     * Transforms a string into an enum
     * @param category String
     * @return a Category enum
     */
    public static Category stringToCategory(final String category) {
        return switch (category) {
            case "Board Games" -> Category.BOARD_GAMES;
            case "Books" -> Category.BOOKS;
            case "Clothes" -> Category.CLOTHES;
            case "Sweets" -> Category.SWEETS;
            case "Technology" -> Category.TECHNOLOGY;
            case "Toys" -> Category.TOYS;
            default -> null;
        };
    }

    /**
     * Transform a string into an enum
     * @param city String
     * @return a Cities enum
     */
    public static Cities stringToCity(final String city) {
        return switch (city) {
            case "Bucuresti" -> Cities.BUCURESTI;
            case "Constanta" -> Cities.CONSTANTA;
            case "Buzau" -> Cities.BUZAU;
            case "Timisoara" -> Cities.TIMISOARA;
            case "Cluj-Napoca" -> Cities.CLUJ;
            case "Iasi" -> Cities.IASI;
            case "Craiova" -> Cities.CRAIOVA;
            case "Brasov" -> Cities.BRASOV;
            case "Braila" -> Cities.BRAILA;
            case "Oradea" -> Cities.ORADEA;
            default -> null;
        };
    }

    /**
     * Transforms an int into an enum
     * @param age integer
     * @return an AgeCategory enum
     */
    public static AgeCategory ageToAgeCategory(final int age) {
        return (age <= Constants.BABY_MAX_AGE) ? AgeCategory.BABY
                : (age <= Constants.KID_MAX_AGE) ? AgeCategory.KID
                : (age <= Constants.TEEN_MAX_AGE) ? AgeCategory.TEEN
                : AgeCategory.YOUNG_ADULT;
    }

    /**
     * Transform a string into an enum
     * @param cityStrategy String
     * @return a CityStrategyEnum enum
     */
    public static CityStrategyEnum stringToCityStrategy(final String cityStrategy) {
        return switch (cityStrategy) {
            case "niceScoreCity" -> CityStrategyEnum.NICE_SCORE_CITY;
            case "id" -> CityStrategyEnum.ID;
            case "niceScore" -> CityStrategyEnum.NICE_SCORE;
            default -> null;
        };
    }

    /**
     * Transform a string into an enum
     * @param elfType String
     * @return a CityStrategyEnum enum
     */
    public static ElvesType stringToElfType(final String elfType) {
        return switch (elfType) {
            case "yellow" -> ElvesType.YELLOW;
            case "black" -> ElvesType.BLACK;
            case "pink" -> ElvesType.PINK;
            case "white" -> ElvesType.WHITE;
            default -> null;
        };
    }

    private Utils() { }
}
