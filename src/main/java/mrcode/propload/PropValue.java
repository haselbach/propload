package mrcode.propload;

public interface PropValue {
    
    /**
     * Gets a value using a property resolver.
     * @param propResolve The property resolver.
     * @return            The String value of this property value.
     */
    String getValue(PropResolve propResolve);

}
