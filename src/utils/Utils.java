package utils;

import common.Constants;
import enums.AgeCategory;
import enums.Category;
import enums.Cities;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains methods that help to parse the input.
 */
public final class Utils {
    /**
     * Moves the elements of a JSONArray into a list of strings
     * @param array JSONArray
     * @return a list of strings
     */
    public static List<String> JSONArrayToList(final JSONArray array) {
        ArrayList<String> arrayAsList = new ArrayList<>();
        for (Object object : array) {
            arrayAsList.add((String) object);
        }
        return arrayAsList;
    }

    public static List<Category> JSONArrayToCategoryList(final JSONArray array) {
        List<Category> arrayAsList = new ArrayList<>();
        for (Object object : array) {
            arrayAsList.add(stringToCategory((String) object));
        }
        return arrayAsList;
    }

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

    public static Cities stringToCity(final String city) {
        return switch (city) {
            case "Bucharest" -> Cities.BUCURESTI;
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

    public static AgeCategory ageToAgeCategory(final int age) {
        return (age <= Constants.BABY_MAX_AGE) ? AgeCategory.BABY
                : (age <= Constants.KID_MAX_AGE) ? AgeCategory.KID
                : (age <= Constants.TEEN_MAX_AGE) ? AgeCategory.TEEN
                : AgeCategory.YOUNG_ADULT;
    }
}
