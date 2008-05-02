package mrcode.propload.impl;

import java.util.ArrayList;
import java.util.List;

import mrcode.propload.PropValue;
import mrcode.propload.PropValueParser;

public class PropValueParserImpl implements PropValueParser {
    
    static enum TokenType { PAREN_START, PAREN_END, LITERAL };
    
    static class Token {
        private final String stringValue;
        private final TokenType type;
        public Token(final String stringValue, final TokenType type) {
            this.stringValue = stringValue;
            this.type = type;
        }
        public String getStringValue() {
            return stringValue;
        }
        public TokenType getType() {
            return type;
        }
    }
    
    protected List<Token> lexer(char[] input) {
        final List<Token> tokens = new ArrayList<Token>();
        int start = 0;
        int end = 0;
        while (end < input.length) {
            if (input[end] == '$' && (end + 1) < input.length && input[end + 1] == '{') {
                if (start < end) {
                    tokens.add(new Token(new String(input, start, end - (start)), TokenType.LITERAL));
                }
                end += 2;
                tokens.add(new Token("${", TokenType.PAREN_START));
                start = end;
            } else if (input[end] == '}') {
                if (start < end) {
                    tokens.add(new Token(new String(input, start, end - (start)), TokenType.LITERAL));
                }
                tokens.add(new Token("}", TokenType.PAREN_END));
                end++;
                start = end;
            } else {
                end++;
            }
        }
        if (start < end) {
            tokens.add(new Token(new String(input, start, end - (start)), TokenType.LITERAL));
        }
        return tokens;
    }
    
    static class SubParse {
        final PropValue propValue;
        final int end;
        public SubParse(final PropValue propValue, final int end) {
            this.propValue = propValue;
            this.end = end;
        }
    }

    public PropValue parsePropValue(String input) {
        final char[] chars = input.toCharArray();
        SubParse subParse = parsePropValue(lexer(chars), 0, false);
        return subParse.propValue;
    }
    
    protected SubParse parsePropValue(final List<Token> tokens, final int offset, final boolean inner) {
        int end = offset;
        List<PropValue> propValues = new ArrayList<PropValue>();
        boolean subEndFound = false;
        while (end < tokens.size() && !subEndFound) {
            Token token = tokens.get(end);
            switch (token.getType()) {
                case PAREN_START:
                    SubParse subParse = parsePropValue(tokens, end+1, true);
                    propValues.add(new PropValueReference(subParse.propValue));
                    end = subParse.end;
                    break;
                case PAREN_END:
                    if (inner) {
                        subEndFound = true;
                    } else {
                        propValues.add(new PropValueLiteral("}"));
                    }
                    end++;
                    break;
                case LITERAL:
                    propValues.add(new PropValueLiteral(token.getStringValue()));
                    end++;
            }
        }
        PropValue propValue;
        switch (propValues.size()) {
            case 0:
                propValue = new PropValueLiteral("");
                break;
            case 1:
                propValue = propValues.get(0);
                break;
            default:
                propValue = new PropValueComplex(propValues.toArray(new PropValue[0]));
                break;
        }
        return new SubParse(propValue, end);
    }

}
