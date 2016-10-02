package me.ysobj.sqlparser.parser;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class ParserTest {

	@Test
	public void testTokenParser() {
		Parser parser = new KeywordParser("select");
		assertThat(parser.parse(new Tokenizer("select")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("update")), is(nullValue()));
	}

	@Test
	public void testChoiseParser() {
		Parser p1 = new KeywordParser("select");
		Parser p2 = new KeywordParser("update");
		Parser p3 = new KeywordParser("delete");
		Parser parser = new ChoiseParser(p1, p2, p3);
		assertThat(parser.parse(new Tokenizer("select")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("update")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("delete")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("insert")), is(nullValue()));
	}

}
