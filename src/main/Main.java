package main;

import checker.Checker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import common.Constants;
import input.Input;
import input.InputLoader;
import output.Writer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }
    /**
     * This method is used to apply the simulation for every test,
     * and then call the checker which calculates the score
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) throws IOException {
        if (!Files.exists(Path.of(Constants.OUTPUT_DIR))) {
            Files.createDirectories(Path.of(Constants.OUTPUT_DIR));
        }

        File outputDir = new File(Constants.OUTPUT_DIR);
        for (File file : outputDir.listFiles()) {
            file.delete();
        }

        /**
         * For every test's input data, run the simulation
         */
        for (int i = 1; i <= Constants.TESTS_NUMBER; ++i) {
            String inputPath = Constants.INPUT_PATH + i + ".json";
            String outputPath = Constants.OUTPUT_PATH + i + ".json";

            runSimulation(inputPath, outputPath);
        }

        Checker.calculateScore();
    }

    /**
     * Method that runs the simulation for a dataset corresponding to a test
     * @param inputPath the path of the test, as String
     * @param outputPath the path where the output will be written, as String
     * @throws IOException
     */
    public static void runSimulation(final String inputPath,
                              final String outputPath) throws IOException {
        InputLoader inputLoader = new InputLoader(inputPath);
        Input input = inputLoader.readData();

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode childrenJsonArray = mapper.createArrayNode();

        /**
         * Entry point to the main logic
         */
        Simulation simulator = Simulation.getInstance(input);
        simulator.simulateAllYears(mapper, childrenJsonArray);

        Writer.writeJsonToFile(outputPath, mapper, childrenJsonArray);
    }
}
