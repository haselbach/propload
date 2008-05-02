package mrcode.propload.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mrcode.propload.PropConf;
import mrcode.propload.PropValue;

/**
 * Simple default implemenation for PropConf.
 * Note: This implementation does not have cycle detection. I.e., if you try to
 * resolve a key with cyclic references, the getConf() method will not
 * terminate.
 */
public class PropConfImpl implements PropConf {
    
    private PropConf parent;
    
    private final Map<String, PropValue> confMap;
    
    public PropConfImpl() {
        this(null);
    }
    
    public PropConfImpl(PropConf parent) {
        this(parent, new HashMap<String, PropValue>());
    }
    
    public PropConfImpl(PropConf parent, Map<String, PropValue> confMap) {
        this.parent = parent;
        this.confMap = confMap;
    }
    
    public void putPropValue(String key, PropValue propValue) {
        confMap.put(key, propValue);
    }
    
    public String getConf(String key) {
        PropConf conf = this;
        while (conf != null) {
            PropValue value = conf.getPropValue(key);
            if (value != null) {
                return value.getValue(this);
            }
            conf = conf.getParent();
        }
        return null;
    }
    
    public PropValue getPropValue(String key) {
        return confMap.get(key);
    }

    public PropConf getParent() {
        return parent;
    }

    public Set<String> getKeys() {
        Set<String> keys = parent == null ? new HashSet<String>() : parent.getKeys();
        keys.addAll(confMap.keySet());
        return keys;
    }


}
