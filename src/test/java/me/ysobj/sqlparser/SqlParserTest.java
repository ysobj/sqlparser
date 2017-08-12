package me.ysobj.sqlparser;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public class SqlParserTest {

	@Test
	public void test() throws Exception {
		SqlParser parser = new SqlParser();
		assertThat(parser.parse(new Tokenizer("select A from fuga")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("select A,B,C from fuga")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("select A,B,C from fuga as hoge")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("select * from fuga")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("select * from hoge, fuga")), not(nullValue()));
		assertThat(parser.parse(new Tokenizer("select * from hoge as X, fuga as Y")), not(nullValue()));
	}

	@Test(expected = ParseException.class)
	public void test2() throws Exception {
		SqlParser parser = new SqlParser();
		assertThat(parser.parse(new Tokenizer("select from fuga")), not(nullValue()));
	}

	@Test(expected = ParseException.class)
	public void test3() throws Exception {
		SqlParser parser = new SqlParser();
		assertThat(parser.parse(new Tokenizer("select * from")), not(nullValue()));
	}
}
