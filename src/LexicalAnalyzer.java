import java.io.*;
import java.text.ParseException;

import static java.lang.Character.isLowerCase;

public class LexicalAnalyzer {
    InputStream is;
    String analyzed = "";
    int curChar;
    int curPos;
    char curName;
    Token curToken;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private boolean isLetter(int c) {
        return ((c <= 'z')&&(c >= 'a')|| ((c >= 'A')&& (c <= 'Z')));
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
            analyzed+=(char) curChar;
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        if (isLetter(curChar)) {
            if (!isLowerCase(curChar)) {
                throw new ParseException("Incorrect symbol in name of variable \"" + (char) curChar + "\"", curPos);
            }
            curName = (char) curChar;
            nextChar();
            if (isLetter(curChar)) {
                throw new ParseException("Incorrect name of variable \"" + (char) curChar + "\"", curPos);
            }
            curToken = Token.NAME;
        }
        else {
            switch (curChar) {
                case '&':
                    nextChar();
                    curToken = Token.AND;
                    break;
                case '|':
                    nextChar();
                    curToken = Token.OR;
                    break;
                case '^':
                    nextChar();
                    curToken = Token.XOR;
                    break;
                case '!':
                    nextChar();
                    curToken = Token.NOT;
                    break;
                case '(':
                    nextChar();
                    curToken = Token.LPAREN;
                    break;
                case ')':
                    nextChar();
                    curToken = Token.RPAREN;
                    break;
                case -1:
                    curToken = Token.END;
                    break;
                default:
                    throw new ParseException("Illegal character \"" + (char) curChar + "\"", curPos);
            }
        }
    }

    public Token curToken() {
        return curToken;
    }
    public char curName() {
        return  curName;
    }

    public int curPos() {
        return curPos;
    }
}