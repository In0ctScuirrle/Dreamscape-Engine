package lu.embellished_duck.dreamscape_engine.util;

/**
 * Holder class for different kinds of common error codes and formats
 *
 * @since 0.5.0
 *
 * @author Will Blanchard
 */
public class ErrorFormats {

    /**
     * Helper method which can identify an error at a specific index in the tile library. Formerly known as formatted error.
     *
     * @param index The index at which the error has occurred
     * @return Formatted error text
     *
     * @since 0.2.0
     *
     * @author Will Blanchard
     */
    public static String textureLoadingError(int index) {

        return """
                    File IO error at index %s: One of the errors couldn't be found due to either reason |
                    
                    1. The image file name isn't spelled correctly
                    
                    2. There is a type in the file path, most likely a missing / in front of the assets path
                    
                    3. The file extension is incorrect, it needs to be a .png format image
                    """.formatted(index);

    }//End of Helper Method

}//End of Class