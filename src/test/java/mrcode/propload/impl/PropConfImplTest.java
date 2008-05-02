package mrcode.propload.impl;

import java.util.HashMap;
import java.util.Map;

import mrcode.propload.PropConf;
import mrcode.propload.PropValue;
import mrcode.propload.PropValueParser;
import junit.framework.TestCase;

public class PropConfImplTest extends TestCase {
    
    public void testDirectSimplePropConf() {
        Map<String, PropValue> confMap = new HashMap<String, PropValue>();
        confMap.put("a", new PropValueLiteral("1"));
        confMap.put("a.e", new PropValueLiteral("2"));
        confMap.put("a.f", new PropValueLiteral("3"));
        confMap.put("r", new PropValueLiteral("e"));
        confMap.put("s", new PropValueLiteral("f"));
        confMap.put("b", new PropValueReference(new PropValueLiteral("a")));
        confMap.put("c", new PropValueReference(new PropValueComplex(new PropValue[] {
                new PropValueLiteral("a."),
                new PropValueReference(new PropValueLiteral("r"))})));
        confMap.put("d", new PropValueReference(new PropValueComplex(new PropValue[] {
                new PropValueLiteral("a."),
                new PropValueReference(new PropValueLiteral("s"))})));
        PropConf propConf = new PropConfImpl(null, confMap);
        assertEquals("1", propConf.getConf("a"));
        assertEquals("2", propConf.getConf("a.e"));
        assertEquals("3", propConf.getConf("a.f"));
        assertEquals("1", propConf.getConf("b"));
        assertEquals("2", propConf.getConf("c"));
        assertEquals("3", propConf.getConf("d"));
    }
    
    public void testPropConfWithParent() {
        PropValueParser parser = new PropValueParserImpl();
        Map<String, PropValue> confMapParent = new HashMap<String, PropValue>();
        confMapParent.put("foo", parser.parsePropValue("1"));
        confMapParent.put("bar", parser.parsePropValue("2${foo}"));
        Map<String, PropValue> confMap = new HashMap<String, PropValue>();
        confMap.put("foo", parser.parsePropValue("3"));
        PropConf propConfParent = new PropConfImpl(null, confMapParent);
        PropConf propConf = new PropConfImpl(propConfParent, confMap);
        assertEquals("21", propConfParent.getConf("bar"));
        assertEquals("23", propConf.getConf("bar"));
    }
    
}
