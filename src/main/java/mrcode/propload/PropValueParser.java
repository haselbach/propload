package mrcode.propload;

public interface PropValueParser {

    /**
     * Parses a property value represented as a string.
     * @param input  A string representation of a property.
     * @return       A PropValue instance for the input string.
     */
    PropValue parsePropValue(String input);
}
