package me.ysobj.sqlparser.model;

public class Token {
	public static final Token EOF = new Token("");
	private String original;
	public Token(String str){
		this.original = str;
	}
	public String getOriginal() {
		return original;
	}

}
