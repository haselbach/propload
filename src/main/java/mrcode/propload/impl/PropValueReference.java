package mrcode.propload.impl;

import mrcode.propload.PropResolve;
import mrcode.propload.PropValue;

/**
 * A value that is a reference to another value.
 */
public class PropValueReference implements PropValue {
    
    private final PropValue keyPropValue;
    
    public PropValueReference(final PropValue keyPropValue) {
        this.keyPropValue = keyPropValue;
    }

    public String getValue(PropResolve propResolve) {
        return propResolve.getConf(keyPropValue.getValue(propResolve));
    }
    
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other instanceof PropValueComplex) {
            return other.equals(this);
        }
        if (!(other instanceof PropValueReference)) {
            return false;
        }
        final PropValueReference otherReference = (PropValueReference) other;
        return this.keyPropValue.equals(otherReference.keyPropValue);
    }
    
    public String toString() {
        return "<R[" + keyPropValue.toString() + "]>";
    }

}
