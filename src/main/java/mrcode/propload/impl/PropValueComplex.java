package mrcode.propload.impl;

import mrcode.propload.PropResolve;
import mrcode.propload.PropValue;

/**
 * A complex value consisting of a sequence of other values.
 */
public class PropValueComplex implements PropValue {
    
    final private PropValue[] propValues;
    
    public PropValueComplex(PropValue[] propValues) {
        this.propValues = propValues;
    }

    public String getValue(PropResolve propConf) {
        StringBuilder refBuilder = new StringBuilder();
        for (PropValue propValue : propValues) {
            refBuilder.append(propValue.getValue(propConf));
        }
        return refBuilder.toString();
    }
    
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (propValues.length == 1) {
            return propValues[0].equals(other);
        }
        if (!(other instanceof PropValueComplex)) {
            return false;
        }
        PropValue[] otherPropValues = ((PropValueComplex) other).propValues;
        if (propValues.length != otherPropValues.length) {
            return false;
        }
        for (int i = 0; i < propValues.length; i++) {
            if (!propValues[i].equals(otherPropValues[i])) {
                return false;
            }
        }
        return true;
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder("<C");
        for (PropValue propValue : propValues) {
            builder.append("[").append(propValue.toString()).append("]");
        }
        return builder.append(">").toString();
    }

}
