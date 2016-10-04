package me.ysobj.sqlparser.parser;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.model.ASTree;
import me.ysobj.sqlparser.model.Token;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class KeywordParser implements Parser {
	private String keyword;

	public KeywordParser(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public ASTree parse(Tokenizer tokenizer) throws ParseException {
		Token token = tokenizer.peek();
		if (token.getType() == Token.TokenType.KEYWORD
				&& keyword.equals(token.getOriginal())) {
			tokenizer.next();
			return new ASTree();
		}
		throw new ParseException();
	}
}
