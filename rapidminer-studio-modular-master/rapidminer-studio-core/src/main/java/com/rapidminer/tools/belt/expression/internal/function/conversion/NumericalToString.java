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
package com.rapidminer.tools.belt.expression.internal.function.conversion;

import java.util.concurrent.Callable;

import com.rapidminer.tools.Tools;
import com.rapidminer.tools.belt.expression.DoubleCallable;
import com.rapidminer.tools.belt.expression.ExpressionContext;
import com.rapidminer.tools.belt.expression.ExpressionEvaluator;
import com.rapidminer.tools.belt.expression.ExpressionExceptionWrapper;
import com.rapidminer.tools.belt.expression.ExpressionType;
import com.rapidminer.tools.belt.expression.FatalExpressionExceptionWrapper;
import com.rapidminer.tools.belt.expression.FunctionDescription;
import com.rapidminer.tools.belt.expression.FunctionInputExceptionWrapper;
import com.rapidminer.tools.belt.expression.internal.ExpressionEvaluatorFactory;
import com.rapidminer.tools.belt.expression.internal.function.AbstractFunction;


/**
 *
 * A {@link com.rapidminer.tools.belt.expression.Function} parsing a number to a string.
 *
 * @author Marcel Seifert
 * @since 9.11
 */
public class NumericalToString extends AbstractFunction {

	/**
	 * Constructs an AbstractFunction with {@link FunctionDescription} generated from the arguments
	 * and the function name generated from the description.
	 */
	public NumericalToString() {
		super("conversion.str", 1, ExpressionType.STRING);
	}

	@Override
	public ExpressionEvaluator compute(Callable<Void> stopChecker, ExpressionContext context,
									   ExpressionEvaluator... inputEvaluators) {
		if (inputEvaluators.length != 1) {
			throw new FunctionInputExceptionWrapper("expression_parser.function_wrong_input", getFunctionName(), 1,
					inputEvaluators.length);
		}
		getResultType(inputEvaluators);
		ExpressionEvaluator input = inputEvaluators[0];

		return ExpressionEvaluatorFactory.ofString(makeStringCallable(input), isResultConstant(inputEvaluators));
	}

	/**
	 * Builds a String Callable from a double input argument
	 *
	 * @param inputEvaluator
	 *            the input
	 * @return the resulting callable<String>
	 */
	protected Callable<String> makeStringCallable(final ExpressionEvaluator inputEvaluator) {
		final DoubleCallable func = inputEvaluator.getDoubleFunction();

		try {
			if (inputEvaluator.isConstant()) {
				final String result = compute(func.call());
				return () -> result;
			} else {
				return () -> compute(func.call());
			}
		} catch (ExpressionExceptionWrapper e) {
			throw e;
		} catch (Exception e) {
			throw new FatalExpressionExceptionWrapper(e);
		}
	}

	/**
	 * Computes the result for one input double value.
	 *
	 * @param value
	 *            the double value to parse
	 *
	 * @return the result of the computation.
	 */
	protected String compute(double value) {
		if (Double.isNaN(value)) {
			return null;
		}
		return Tools.formatIntegerIfPossible(value);
	}

	@Override
	protected ExpressionType computeType(ExpressionType... inputTypes) {
		ExpressionType input = inputTypes[0];
		if (input == ExpressionType.INTEGER || input == ExpressionType.DOUBLE) {
			return ExpressionType.STRING;
		} else {
			throw new FunctionInputExceptionWrapper("expression_parser.function_wrong_type", getFunctionName(), "numerical");
		}
	}

}
