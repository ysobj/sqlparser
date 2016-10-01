package me.ysobj.sqlparser.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import me.ysobj.sqlparser.model.Token;

public class Tokenizer {
	private Reader reader;
	private Token preRead;

	public Tokenizer(String string) {
		this.reader = new StringReader(string);
	}

	public Tokenizer(Reader reader) {
		this.reader = reader;
	}

	public boolean hasNext() {
		if( preRead == Token.EOF){
			return false;
		}
		if (preRead != null) {
			return true;
		}
		if (this.reader == null) {
			return false;
		}
		Token t = next();
		if(t == Token.EOF){
			return false;
		}
		this.preRead = t;
		return true;
	}

	public Token next() {
		if (preRead != null) {
			Token tmp = this.preRead;
			this.preRead = null;
			return tmp;
		}
		StringBuilder sb = new StringBuilder();
		while (true) {
			try {
				int r = reader.read();
				if (r == -1) {
					if(sb.length() > 0){
						return new Token(sb.toString());
					}
					this.preRead = Token.EOF;
					return this.preRead;
				}
				if (r == 32) {
					break;
				}
				sb.append((char) r);
			} catch (IOException e) {
				try {
					if (this.reader != null) {
						this.reader.close();
					}
				} catch (IOException e1) {
				}
				this.reader = null;
			}
		}
		return new Token(sb.toString());
	}

}
