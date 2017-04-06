/**
 * Project : sqlparser
 * Date : 2017/04/05 kougo
 *
 * Copyright 2017 TechMatrix Corporation, Inc. All rights reserved.
 */
package me.ysobj.sqlparser.parser;

import me.ysobj.sqlparser.exception.ParseException;
import me.ysobj.sqlparser.model.ASTree;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

/**
 * <code>RepeatParser</code>クラスは○○するクラスです。
 * <p>
 * TODO クラスを説明するJavadocが未記載です。
 * 
 * @author kougo
 */
public class RepeatParser implements Parser {
	private Parser parser;

	/**
	 * コンストラクター
	 *
	 * @param keywordParser
	 */
	public RepeatParser(Parser parser) {
		this.parser = parser;
	}

	/**
	 * parseメソッドのオーバーライド
	 *
	 * @param tokenizer
	 * @return
	 * @throws ParseException
	 * @see me.ysobj.sqlparser.parser.Parser#parse(me.ysobj.sqlparser.tokenizer.Tokenizer)
	 */
	@Override
	public ASTree parse(Tokenizer tokenizer) throws ParseException {
		parser.parse(tokenizer);
		try {
			while (true) {
				parser.parse(tokenizer);
			}
		} catch (ParseException e) {
		}
		return new ASTree();
	}
}
