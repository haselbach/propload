package mrcode.propload;

import java.util.Properties;

/**
 * A properties resource loader. 
 */
public interface PropResourceLoader {

    
    /**
     * Loads properties from a location.
     * @param location  The name of the location. Syntax depends on the implementation.
     * @return          A properties instance containing the properties loaded from the location.
     */
    Properties loadProperties(String location);
    
}
