package me.ysobj.sqlparser;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.model.ASTNode;
import me.ysobj.sqlparser.parser.ChoiceParser;
import me.ysobj.sqlparser.parser.CommaParser;
import me.ysobj.sqlparser.parser.KeywordParser;
import me.ysobj.sqlparser.parser.OptionalParser;
import me.ysobj.sqlparser.parser.Parser;
import me.ysobj.sqlparser.parser.RepeatParser;
import me.ysobj.sqlparser.parser.SequenceParser;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class SqlParser implements Parser {
	private Parser parser;

	public SqlParser() {
		Parser columns = new SequenceParser(new KeywordParser(),
				new OptionalParser(new RepeatParser(new SequenceParser(new CommaParser(), new KeywordParser()))));
		Parser selectExpression = new ChoiceParser(new KeywordParser("*"), columns);
		Parser selectPart = new SequenceParser(new KeywordParser("select"), selectExpression);
		Parser tableExpression = new SequenceParser(new KeywordParser(),
				new OptionalParser(new SequenceParser(new KeywordParser("as"), new KeywordParser())));
		Parser fromPart = new SequenceParser(new KeywordParser("from"), tableExpression);
		parser = new SequenceParser(selectPart, fromPart);
	}

	@Override
	public ASTNode parse(Tokenizer tokenizer) throws ParseException {
		return parser.parse(tokenizer);
	}

	// <select_query> ::= <select_part> <from_part>
	// <select_part> ::= "select" <select_expression>
	// <from_part> ::= "from" <table_expression>
	// <select_expression> ::= "*" | <name> { "," <name>}
	// <table_expression> ::= <name> "as" []
}
