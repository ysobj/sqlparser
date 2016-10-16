package me.ysobj.sqlparser.model;

public class Token {
	private static final char QUOTE = '\'';

	public static enum TokenType {
		KEYWORD, LITERAL, NUMBER, OPERATOR, COMMA, OTHER
	}

	public static final Token EOF = new Token("", TokenType.OTHER);
	private String original;
	private String normalize;
	private TokenType type;

	private Token(String str, TokenType type) {
		this.original = str;
		this.normalize = str != null ? str.toUpperCase() : str;
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
		return "Token [normalize=" + normalize + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((normalize == null) ? 0 : normalize.toUpperCase().hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	public String getNormalize() {
		return normalize;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Token)) {
			return false;
		}
		Token subject = (Token) obj;
		if (!this.getType().equals(subject.getType())) {
			return false;
		}
		return (this.getNormalize().equals(subject.getNormalize().toUpperCase()));
	}

}
