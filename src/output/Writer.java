package output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

/**
 * Class that writes the output in files
 */
public final class Writer {
    private Writer() { }

    /**
     * Redirects the output of simulation as JSON to a file
     * @param outputPath path of the file where the output will be written
     * @param mapper ObjectMapper used to write an ObjectNode in a file
     * @param childrenJsonArray array of JSONs
     * @throws IOException
     */
    public static void writeJsonToFile(final String outputPath,
                                       final ObjectMapper mapper,
                                       final ArrayNode childrenJsonArray) throws IOException {
        ObjectNode annualChildren = mapper.createObjectNode()
                .putPOJO("annualChildren", childrenJsonArray);
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(outputPath), annualChildren);
    }
}
