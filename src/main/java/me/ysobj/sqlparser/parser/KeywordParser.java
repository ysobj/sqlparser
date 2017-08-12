package me.ysobj.sqlparser.parser;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.model.ASTNode;
import me.ysobj.sqlparser.model.Token;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class KeywordParser implements Parser {
	private String keyword;

	public KeywordParser(String keyword) {
		this.keyword = keyword;
	}

	public KeywordParser() {
	}

	@Override
	public ASTNode parse(Tokenizer tokenizer) throws ParseException {
		Token token = tokenizer.peek();
		if (token == Token.EOF) {
			throw new ParseException();
		}
		if (keyword == null || keyword.equals(token.getOriginal())) {
			tokenizer.next();
			return new ASTNode();
		}
		throw new ParseException();
	}

	@Override
	public String toString() {
		return keyword != null ? keyword : "KEYWORD";
	}

}
