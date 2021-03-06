/**
 * Copyright (C) 2001-2021 by RapidMiner and the contributors
 *
 * Complete list of developers available at our web site:
 *
 * http://rapidminer.com
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.tools.expression.internal.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.RecognitionException;


/**
 * {@link FunctionExpressionLexer} that does not try to recover after encountering the first
 * inadmissible symbol.
 *
 * @author Gisa Schaefer
 * @deprecated since 9.11, see {@link com.rapidminer.tools.belt.expression.ExpressionParser}
 */
@Deprecated
class CapitulatingFunctionExpressionLexer extends FunctionExpressionLexer {

	CapitulatingFunctionExpressionLexer(CharStream input) {
		super(input);
	}

	@Override
	public void recover(LexerNoViableAltException e) {
		throw new CapitulatingRuntimeException();
	}

	@Override
	public void recover(RecognitionException re) {
		throw new CapitulatingRuntimeException();
	}

}
