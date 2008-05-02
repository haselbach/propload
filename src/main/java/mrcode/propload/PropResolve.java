package mrcode.propload;


/**
 * A property resolver.
 */
public interface PropResolve {

    /**
     * Gets the configuration value for a key.
     * @param key The key (must be literal).
     * @return    The configuration value.
     */
    String getConf(String key);

}
