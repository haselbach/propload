package mrcode.propload.impl;

import mrcode.propload.PropResolve;
import mrcode.propload.PropValue;

/**
 * A literal value.
 */
public class PropValueLiteral implements PropValue {
    
    final String value;
    
    public PropValueLiteral(String value) {
        this.value = value;
    }

    public String getValue(PropResolve propResolve) {
        return value;
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
        if (!(other instanceof PropValueLiteral)) {
            return false;
        }
        final PropValueLiteral otherLiteral = (PropValueLiteral) other;
        return this.value.equals(otherLiteral.value);
    }
    
    public String toString() {
        return "<L[" + value + "]>";
    }
}
