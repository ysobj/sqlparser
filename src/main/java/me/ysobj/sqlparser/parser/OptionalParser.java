package me.ysobj.sqlparser.parser;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.model.ASTNode;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class OptionalParser implements Parser {
	private Parser parser;

	public OptionalParser(Parser parser) {
		this.parser = parser;
	}

	@Override
	public ASTNode parse(Tokenizer tokenizer) throws ParseException {
		try {
			return parser.parse(tokenizer);
		} catch (ParseException e) {
		}
		return null;
	}
}
