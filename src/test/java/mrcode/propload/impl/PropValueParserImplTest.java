package mrcode.propload.impl;

import java.util.List;

import junit.framework.TestCase;
import mrcode.propload.PropValue;
import mrcode.propload.PropValueParser;

public class PropValueParserImplTest extends TestCase {
    
    public void testLexer() {
        PropValueParserImpl parser = new PropValueParserImpl();
        {
            List<PropValueParserImpl.Token> tokens = parser.lexer("foo".toCharArray());
            assertEquals(1, tokens.size());
            assertEquals("foo", tokens.get(0).getStringValue());
        }
        {
            List<PropValueParserImpl.Token> tokens = parser.lexer("${foo}".toCharArray());
            assertEquals(3, tokens.size());
            assertEquals(PropValueParserImpl.TokenType.PAREN_START, tokens.get(0).getType());
            assertEquals("foo", tokens.get(1).getStringValue());
            assertEquals(PropValueParserImpl.TokenType.PAREN_END, tokens.get(2).getType());
        }
        {
            List<PropValueParserImpl.Token> tokens = parser.lexer("foo${foo}".toCharArray());
            assertEquals(4, tokens.size());
            assertEquals("foo", tokens.get(0).getStringValue());
            assertEquals(PropValueParserImpl.TokenType.PAREN_START, tokens.get(1).getType());
            assertEquals("foo", tokens.get(2).getStringValue());
            assertEquals(PropValueParserImpl.TokenType.PAREN_END, tokens.get(3).getType());
        }
        
    }
    
    public void testParser() {
        PropValue literalFoo = new PropValueLiteral("foo");
        PropValue referenceFoo = new PropValueReference(literalFoo);
        PropValue complexFoo = new PropValueComplex(new PropValue[] {literalFoo, referenceFoo, literalFoo});
        PropValue complexFoo2 = new PropValueComplex(new PropValue[] {literalFoo, referenceFoo});
        PropValue interComplexFoo = new PropValueComplex(new PropValue[] {literalFoo, new PropValueReference(complexFoo)});
        PropValue interComplexFoo2 = new PropValueComplex(new PropValue[] {literalFoo, new PropValueReference(complexFoo2)});
        PropValueParser parser = new PropValueParserImpl();
        assertEquals(literalFoo, parser.parsePropValue("foo"));
        assertEquals(referenceFoo, parser.parsePropValue("${foo}"));
        assertEquals(complexFoo, parser.parsePropValue("foo${foo}foo"));
        assertEquals(complexFoo2, parser.parsePropValue("foo${foo}"));
        assertEquals(interComplexFoo, parser.parsePropValue("foo${foo${foo}foo}"));
        assertEquals(interComplexFoo2, parser.parsePropValue("foo${foo${foo}}"));
    }

}
