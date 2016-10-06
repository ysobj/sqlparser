package me.ysobj.sqlparser.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import me.ysobj.sqlparser.model.Token;

public class Tokenizer {
	private Reader reader;

	// private Token preReadToken;
	private List<Token> preReadTokens = new ArrayList<>();

	private int preRead = -1;

	private static final int EOS = -1;

	private static final int SPACE = (int) ' ';

	private static final int QUOTE = (int) '\'';

	private static final int COMMA = (int) ',';

	public Tokenizer(String string) {
		this.reader = new StringReader(string);
	}

	public Tokenizer(Reader reader) {
		this.reader = reader;
	}

	public boolean hasNext() {
		Token preReadToken = null;
		if (preReadTokens.size() > 0) {
			preReadToken = preReadTokens.get(0);
		}
		if (preReadToken == Token.EOF) {
			return false;
		}
		if (preReadToken != null) {
			return true;
		}
		if (this.reader == null) {
			return false;
		}
		Token t = next();
		if (t == Token.EOF) {
			return false;
		}
		preReadTokens.add(t);
		return true;
	}

	private int read() throws IOException {
		int r = -1;
		if (preRead != -1) {
			r = preRead;
			preRead = -1;
		} else {
			r = reader.read();
		}
		return r;
	}

	public Token peek() {
		if (hasNext()) {
			return this.preReadTokens.get(0);
		}
		return Token.EOF;
	}

	public Token next() {
		if (preReadTokens.size() > 0) {
			return preReadTokens.remove(0);
		}
		StringBuilder sb = new StringBuilder();
		boolean isOpen = false;
		boolean isNumeric = false;
		while (true) {
			try {
				int r = read();
				switch (r) {
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
					case '0':
						if (!isNumeric && sb.length() > 0) {
							this.preRead = r;
							return Token.create(sb.toString());
						}
						isNumeric = true;
						break;
					case '+':
					case '-':
					case '*':
					case '/':
					case '%':
						if (isNumeric) {
							this.preRead = r;
							return Token.create(sb.toString());
						}
						break;
					case COMMA:
						if (sb.length() > 0) {
							this.preRead = r;
							return Token.create(sb.toString());
						} else {
							return Token.create(String.valueOf((char) r));
						}
					case QUOTE:
						isOpen = !isOpen;
						break;
					case EOS:
						if (sb.length() > 0) {
							return Token.create(sb.toString());
						}
						this.preReadTokens.add(Token.EOF);
						return Token.EOF;
					case SPACE:
						if (!isOpen && sb.length() > 0) {
							return Token.create(sb.toString());
						} else if (isOpen) {
							break;
						} else {
							continue;
						}
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

	public void push(Token token) {
		preReadTokens.add(token);
	}
}
