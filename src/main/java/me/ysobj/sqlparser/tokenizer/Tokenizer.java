package me.ysobj.sqlparser.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import me.ysobj.sqlparser.model.Token;

public class Tokenizer {
	private Reader reader;
	private Token preRead;
	private static final int EOS = -1;
	private static final int SPACE = (int)' ';
	private static final int QUOTE = (int)'\'';

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
		boolean isOpen = false;
		while (true) {
			try {
				int r = reader.read();
				switch(r){
				case QUOTE:
					isOpen = !isOpen;
					break;
				case EOS:
					if(sb.length() > 0){
						return new Token(sb.toString());
					}
					this.preRead = Token.EOF;
					return this.preRead;
				case SPACE:
					if(!isOpen){
						return new Token(sb.toString());
					}
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
	}

}
