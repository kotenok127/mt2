import java.text.ParseException;

public class Parser {
    private static LexicalAnalyzer lex;

    public Parser(LexicalAnalyzer la) throws ParseException {
        this.lex = la;
    }

    static Tree E() throws ParseException {
        //System.out.println("E : " + lex.curToken());
        switch (lex.curToken()) {
            case NOT:
            case NAME:
            case LPAREN: {
                Tree t = T();
                Tree ep = EPrime();
                return new Tree("E", t, ep);
            }
            default: {
                throw new ParseException(" expected | or 'name' or ( or end of string" +
                        " at position ", lex.curPos());
            }
        }
    }

    static Tree EPrime() throws ParseException {
        //System.out.println("E' : " + lex.curToken());
        switch (lex.curToken()) {
            case OR:
            case XOR: {
                String tokenString = "";
                if (lex.curToken() == Token.OR) {
                    tokenString = "|";
                } else if (lex.curToken() == Token.XOR) {
                    tokenString = "^";
                } else throw new ParseException(" expected | or ^ " +
                        " at position ", lex.curPos());
                lex.nextToken();
                Tree t = T();
                Tree ep = EPrime();
                return new Tree("E'", new Tree(tokenString), t, ep);
            }
            case RPAREN:
            case END: {
                return new Tree("E'", new Tree("eps"));
            }
            default: {
                throw new ParseException(" expected operation" +
                        " at position ", lex.curPos());
                //break;
            }
        }
    }

    static Tree T() throws ParseException {
        //System.out.println("T : " + lex.curToken());
        switch (lex.curToken()) {
            case NOT:
            case NAME:
            case LPAREN: {
                Tree a = N();
                Tree tp = TPrime();
                return new Tree("T", a, tp);
            }
            default: {
                throw new ParseException(" expected | or 'name' or ( or end of string" +
                        " at position ", lex.curPos());
            }
        }
    }

    static Tree TPrime() throws ParseException {
        // System.out.println("T' : " + lex.curToken());
        switch (lex.curToken()) {
            case AND: {
                String tokenString = "";
                if (lex.curToken() == Token.AND) {
                    tokenString = "&";
                } else throw new ParseException(" expected " + tokenString +
                        " at position ", lex.curPos());

                lex.nextToken();
                Tree a = N();
                Tree tp = TPrime();
                return new Tree("T'", new Tree("&"), a, tp);
            }
            case OR:
            case XOR:
            case RPAREN:
            case END: {
                return new Tree("T'", new Tree("eps"));
            }
            default: {
                throw new ParseException(" expected operation " +
                        " at position ", lex.curPos());
            }
        }
    }

    static Tree N() throws ParseException {
        //System.out.println("A : " + lex.curToken());
        switch (lex.curToken()) {
            case NOT: {
                String tokenString = "";
                if (lex.curToken() == Token.NOT) {
                    tokenString = "!";
                } else throw new AssertionError();
                lex.nextToken();
                Tree a = A();
                return new Tree("A", new Tree(tokenString), a);
            }
            case NAME:
            case LPAREN: {
                Tree a = A();
                return new Tree("N", new Tree("!"), a);
            }
            default: {
                throw new ParseException(" expected name or negation or expression in brackets " +
                        " at position ", lex.curPos());
            }
        }
    }

    static Tree A() throws ParseException {
        //System.out.println("A : " + lex.curToken());
        switch (lex.curToken()) {
            case NAME: {
                String tokenString = "";
                if (lex.curToken() == Token.NAME) {
                    tokenString += lex.curName();
                } else throw new AssertionError();
                //System.out.println(tokenString);
                lex.nextToken();
                return new Tree("A", new Tree(tokenString));
            }
            case LPAREN: {
                lex.nextToken();
                Tree e = E();
                //lex.nextToken();
                if (lex.curToken() != Token.RPAREN) {
                    throw new ParseException(" expected ) " +
                            " at position ", lex.curPos());
                }
                lex.nextToken();
                return new Tree("A", new Tree("("), e, new Tree(")"));
            }
            default: {
                throw new ParseException(" expected name or negation or expression in brackets " +
                        " at position ", lex.curPos());
            }
        }
    }

    public static Tree parse() throws ParseException {
        // lex = new LexicalAnalyzer(is);
        lex.nextToken();
        Tree parseTree = E();
        if (lex.curToken() != Token.END) {
            throw new ParseException(" extra ) " +
                    " at position ", lex.curPos());
        }
        // System.out.println(lex.analyzed);
        // System.out.println(lex.curToken());
        return parseTree;
    }
}