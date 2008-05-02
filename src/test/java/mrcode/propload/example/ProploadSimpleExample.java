package mrcode.propload.example;

import mrcode.propload.PropConf;
import mrcode.propload.PropLoad;
import mrcode.propload.impl.PropLoadExtensiveSearch;

/**
 * A simple example.
 * 
 * @author Christian Haselbach
 */
public class ProploadSimpleExample {

    public static void main(String[] args) {
        final PropLoad propLoad = new PropLoadExtensiveSearch("prop-example");
        final PropConf propConf = propLoad.getPropConf();
        if (propConf != null) {
            for (final String key : propConf.getKeys()) {
                System.out.println("Key: " + key
                        + ", value: " + propConf.getConf(key));
            }
        }
    }

}
