package mrcode.propload.util;

import java.util.Properties;

import mrcode.propload.PropConf;

public class PropConfUtil {
    
    public static Properties toProperties(final PropConf propConf) {
        final Properties properties = new Properties();
        for (String key : propConf.getKeys()) {
            properties.put(key, propConf.getConf(key));
        }
        return properties;
    }

}
