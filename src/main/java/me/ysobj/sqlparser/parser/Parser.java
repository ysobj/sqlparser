package me.ysobj.sqlparser.parser;

import me.ysobj.sqlparser.model.ASTree;
import me.ysobj.sqlparser.tokenizer.Tokenizer;

public interface Parser {
	ASTree parse(Tokenizer tokenizer);
}
