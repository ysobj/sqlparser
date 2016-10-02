package me.ysobj.sqlparser.parser;

import me.ysobj.sqlparser.model.ASTree;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class ChoiseParser implements Parser {
	private Parser[] parsers;

	public ChoiseParser(Parser... parsers) {
		this.parsers = parsers;
	}

	@Override
	public ASTree parse(Tokenizer tokenizer) {
		for (Parser parser : parsers) {
			ASTree tmp = parser.parse(tokenizer);
			if (tmp != null) {
				return tmp;
			}
		}
		return null;
	}

}
