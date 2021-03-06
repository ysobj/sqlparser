package me.ysobj.sqlparser.parser;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.model.ASTNode;
import me.ysobj.sqlparser.model.Token;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class CommaParser implements Parser {
	@Override
	public ASTNode parse(Tokenizer tokenizer) throws ParseException {
		Token token = tokenizer.peek();
		if (token.getType() == Token.TokenType.COMMA) {
			tokenizer.next();
			return new ASTNode();
		}
		throw new ParseException();
	}

	@Override
	public String toString() {
		return ",";
	}

}
