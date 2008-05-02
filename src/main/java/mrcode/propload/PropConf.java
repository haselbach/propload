package mrcode.propload;

import java.util.Set;

/**
 * 
 */
public interface PropConf extends PropResolve {
   
    /**
     * @return The parent property configuration. 
     */
    PropConf getParent();
    
    /**
     * @return All keys (including the parent's keys) of the property configuration.
     */
    Set<String> getKeys();
    
    /**
     * @param key The key.
     * @return    The PropValue object associated with the key in this configuration. The parent is not searched.
     */
    PropValue getPropValue(String key);
    
}
