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
package com.rapidminer.tools.belt.expression.internal.function.statistical;

import com.rapidminer.tools.belt.expression.ExpressionType;
import com.rapidminer.tools.belt.expression.FunctionDescription;
import com.rapidminer.tools.belt.expression.FunctionInputExceptionWrapper;
import com.rapidminer.tools.belt.expression.internal.function.AbstractArbitraryDoubleInputFunction;


/**
 * A {@link com.rapidminer.tools.belt.expression.Function} for average.
 *
 * @author David Arnu
 * @since 9.11
 */
public class Average extends AbstractArbitraryDoubleInputFunction {

	/**
	 * Constructs an average function.
	 */
	public Average() {
		super("statistical.avg", FunctionDescription.UNFIXED_NUMBER_OF_ARGUMENTS, ExpressionType.DOUBLE);

	}

	@Override
	protected ExpressionType computeType(ExpressionType... inputTypes) {

		for (ExpressionType input : inputTypes) {
			if (input != ExpressionType.INTEGER && input != ExpressionType.DOUBLE) {
				throw new FunctionInputExceptionWrapper("expression_parser.function_wrong_type", getFunctionName(), "numerical");
			}
		}
		return ExpressionType.DOUBLE;
	}

	@Override
	public double compute(double... values) {
		int n = values.length;
		double avg = 0;

		for (double val : values) {
			avg += val;
		}

		return avg / n;
	}

}
