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

	@Test
	public void testSeaquenceParser() {
		Parser p1 = new KeywordParser("select");
		Parser p2 = new KeywordParser("update");
		Parser p3 = new KeywordParser("delete");
		Parser parser = new SequenceParser(p1, p2, p3);
		assertThat(parser.parse(new Tokenizer("select update delete")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("select delete update")), is(nullValue()));
	}

	@Test
	public void testCombination() {
		// (A|B)CD
		Parser ab = new ChoiseParser(new KeywordParser("A"), new KeywordParser("B"));
		Parser parser = new SequenceParser(ab, new KeywordParser("C"), new KeywordParser("D"));
		assertThat(parser.parse(new Tokenizer("A C D")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("B C D")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("A B C D")), is(nullValue()));
		assertThat(parser.parse(new Tokenizer("C D")), is(nullValue()));
	}
}
