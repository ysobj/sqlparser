package me.ysobj.sqlparser.parser;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.model.ASTNode;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class SequenceParser implements Parser {
	private Parser[] parsers;

	public SequenceParser(Parser... parsers) {
		this.parsers = parsers;
	}

	@Override
	public ASTNode parse(Tokenizer tokenizer) throws ParseException {
		boolean accept = false;
		for (Parser parser : parsers) {
			ASTNode tmp = parser.parse(tokenizer);
			if (tmp != null) {
				accept = true;
			}
		}
		if (accept) {
			return new ASTNode();
		} else {
			return null;
		}
	}
}
