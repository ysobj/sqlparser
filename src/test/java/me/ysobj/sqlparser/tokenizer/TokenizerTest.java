package me.ysobj.sqlparser.tokenizer;

import static org.junit.Assert.*;

import static org.hamcrest.core.Is.*;
import static me.ysobj.sqlparser.model.Token.TokenType;
import org.junit.Test;

import me.ysobj.sqlparser.model.Token;

public class TokenizerTest {

	@Test
	public void testSimpleCase() {
		Token tmp = null;
		Tokenizer tokenizer = new Tokenizer("select * from hoge");
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("select"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("*"));
		assertThat(tmp.getType(), is(TokenType.OPERATOR));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("from"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("hoge"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(false));
	}

	@Test
	public void testWithQuote() {
		Token tmp = null;
		Tokenizer tokenizer = new Tokenizer("select 'ab cd' from hoge");
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("select"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("'ab cd'"));
		assertThat(tmp.getType(), is(TokenType.LITERAL));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("from"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("hoge"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(false));
	}

	@Test
	public void testWithQuote2() {
		Token tmp = null;
		Tokenizer tokenizer = new Tokenizer("select 'ab''cd' from hoge");
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("select"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("'ab''cd'"));
		assertThat(tmp.getType(), is(TokenType.LITERAL));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("from"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("hoge"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(false));
	}

	@Test
	public void testWithNumber() {
		Token tmp = null;
		Tokenizer tokenizer = new Tokenizer("select 123 + 35 from hoge");
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("select"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("123"));
		assertThat(tmp.getType(), is(TokenType.NUMBER));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("+"));
		assertThat(tmp.getType(), is(TokenType.OPERATOR));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("35"));
		assertThat(tmp.getType(), is(TokenType.NUMBER));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("from"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(true));
		tmp = tokenizer.next();
		assertThat(tmp.getOriginal(), is("hoge"));
		assertThat(tmp.getType(), is(TokenType.KEYWORD));
		assertThat(tokenizer.hasNext(), is(false));
	}
}
