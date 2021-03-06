package me.ysobj.sqlparser.parser;

import java.util.Arrays;
import java.util.StringJoiner;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.model.ASTNode;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class ChoiceParser implements Parser {
	private Parser[] parsers;

	public ChoiceParser(Parser... parsers) {
		this.parsers = parsers;
	}

	@Override
	public ASTNode parse(Tokenizer tokenizer) throws ParseException {
		for (Parser parser : parsers) {
			ASTNode tmp = null;
			try {
				tmp = parser.parse(tokenizer);
			} catch (ParseException e) {
			}
			if (tmp != null) {
				return tmp;
			}
		}
		throw new ParseException();
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner("|", "(", ")");
		joiner.setEmptyValue("");
		Arrays.stream(parsers).forEach(p -> joiner.add(p.toString()));
		return joiner.toString();
	}

}
