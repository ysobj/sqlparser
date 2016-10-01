package me.ysobj.sqlparser.tokenizer;

import static org.junit.Assert.*;

import static org.hamcrest.core.Is.*;
import org.junit.Test;

public class TokenizerTest {

	@Test
	public void testSimpleCase() {
		Tokenizer tokenizer = new Tokenizer("select * from hoge");
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("select"));
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("*"));
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("from"));
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("hoge"));
		assertThat(tokenizer.hasNext(), is(false));
	}

	@Test
	public void testWithQuote() {
		Tokenizer tokenizer = new Tokenizer("select 'ab cd' from hoge");
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("select"));
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("'ab cd'"));
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("from"));
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("hoge"));
		assertThat(tokenizer.hasNext(), is(false));
	}

	@Test
	public void testWithQuote2() {
		Tokenizer tokenizer = new Tokenizer("select 'ab''cd' from hoge");
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("select"));
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("'ab''cd'"));
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("from"));
		assertThat(tokenizer.hasNext(), is(true));
		assertThat(tokenizer.next().getOriginal(), is("hoge"));
		assertThat(tokenizer.hasNext(), is(false));
	}
}
