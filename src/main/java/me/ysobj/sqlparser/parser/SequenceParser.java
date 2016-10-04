package me.ysobj.sqlparser.parser;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.model.ASTree;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class SequenceParser implements Parser {
	private Parser[] parsers;

	public SequenceParser(Parser... parsers) {
		this.parsers = parsers;
	}

	@Override
	public ASTree parse(Tokenizer tokenizer) throws ParseException {
		for (Parser parser : parsers) {
			ASTree tmp = parser.parse(tokenizer);
			if (tmp == null) {
				return null;
			}
		}
		return new ASTree();
	}
}
