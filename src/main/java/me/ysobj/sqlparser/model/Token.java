package me.ysobj.sqlparser.model;

public class Token {
	private static final char QUOTE = '\'';

	public static enum TokenType {
		KEYWORD, LITERAL, NUMBER, OPERATOR, COMMA, OTHER
	}

	public static final Token EOF = new Token("", TokenType.OTHER);
	private String original;
	private TokenType type;

	private Token(String str, TokenType type) {
		this.original = str;
		this.type = type;
	}

	public String getOriginal() {
		return original;
	}

	public TokenType getType() {
		return type;
	}

	private static boolean in(String str, String... target) {
		for (String string : target) {
			if (str.equals(string)) {
				return true;
			}
		}
		return false;
	}

	public static Token create(String str) {
		char c = str.charAt(0);
		TokenType tmpType = TokenType.KEYWORD;
		if (c == QUOTE) {
			tmpType = TokenType.LITERAL;
		} else if ('0' <= c && c <= '9') {
			tmpType = TokenType.NUMBER;
		} else if (in(str, "+", "-", "/", "*", "%", "=", "<", ">", ">=", "<=", "<>", "!=")) {
			tmpType = TokenType.OPERATOR;
		} else if (c == ',') {
			tmpType = TokenType.COMMA;
		}

		return new Token(str, tmpType);
	}

	@Override
	public String toString() {
		return "Token [original=" + original + ", type=" + type + "]";
	}
	
}
