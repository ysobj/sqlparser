package me.ysobj.sqlparser.parser;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class ParserTest {
	@Test
	public void testTokenParser() throws Exception {
		Parser parser = new KeywordParser("select");
		assertThat(parser.parse(new Tokenizer("select")), not(nullValue()));
	}

	@Test
	public void testTokenParser2() throws Exception {
		Parser parser = new KeywordParser();
		assertThat(parser.parse(new Tokenizer("select")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("update")), not(nullValue()));
	}

	@Test(expected = ParseException.class)
	public void testTokenParserFailed() throws Exception {
		Parser parser = new KeywordParser("select");
		assertThat(parser.parse(new Tokenizer("update")), is(nullValue()));
	}

	@Test
	public void testChoiseParser() throws Exception {
		Parser p1 = new KeywordParser("select");
		Parser p2 = new KeywordParser("update");
		Parser p3 = new KeywordParser("delete");
		Parser parser = new ChoiceParser(p1, p2, p3);
		assertThat(parser.parse(new Tokenizer("select")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("update")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("delete")), not(nullValue()));
	}

	@Test(expected = ParseException.class)
	public void testChoiseParserFailed() throws Exception {
		Parser p1 = new KeywordParser("select");
		Parser p2 = new KeywordParser("update");
		Parser p3 = new KeywordParser("delete");
		Parser parser = new ChoiceParser(p1, p2, p3);
		assertThat(parser.parse(new Tokenizer("insert")), is(nullValue()));
	}

	@Test
	public void testSeaquenceParser() throws Exception {
		Parser p1 = new KeywordParser("select");
		Parser p2 = new KeywordParser("update");
		Parser p3 = new KeywordParser("delete");
		Parser parser = new SequenceParser(p1, p2, p3);
		assertThat(parser.parse(new Tokenizer("select update delete")), not(nullValue()));
	}

	@Test(expected = ParseException.class)
	public void testSeaquenceParserFailed() throws Exception {
		Parser p1 = new KeywordParser("select");
		Parser p2 = new KeywordParser("update");
		Parser p3 = new KeywordParser("delete");
		Parser parser = new SequenceParser(p1, p2, p3);
		assertThat(parser.parse(new Tokenizer("select delete update")), is(nullValue()));
	}

	@Test
	public void testCombination() throws Exception {
		// (A|B)CD
		Parser ab = new ChoiceParser(new KeywordParser("A"), new KeywordParser("B"));
		Parser parser = new SequenceParser(ab, new KeywordParser("C"), new KeywordParser("D"));
		assertThat(parser.parse(new Tokenizer("A C D")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("B C D")), not(nullValue()));
	}

	@Test(expected = ParseException.class)
	public void testCombinationFailed1() throws Exception {
		// (A|B)CD
		Parser ab = new ChoiceParser(new KeywordParser("A"), new KeywordParser("B"));
		Parser parser = new SequenceParser(ab, new KeywordParser("C"), new KeywordParser("D"));
		assertThat(parser.parse(new Tokenizer("A B C D")), is(nullValue()));
	}

	@Test(expected = ParseException.class)
	public void testCombinationFailed2() throws Exception {
		// (A|B)CD
		Parser ab = new ChoiceParser(new KeywordParser("A"), new KeywordParser("B"));
		Parser parser = new SequenceParser(ab, new KeywordParser("C"), new KeywordParser("D"));
		assertThat(parser.parse(new Tokenizer("C D")), is(nullValue()));
	}

	@Test
	public void testOptional() throws Exception {
		// A?
		Parser parser = new OptionalParser(new KeywordParser("A"));
		assertThat(parser.parse(new Tokenizer("A")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("B")), is(nullValue()));
	}
}
