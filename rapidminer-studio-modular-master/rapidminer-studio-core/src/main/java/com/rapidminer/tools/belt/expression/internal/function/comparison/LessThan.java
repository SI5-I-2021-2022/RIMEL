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
package com.rapidminer.tools.belt.expression.internal.function.comparison;

import com.rapidminer.tools.belt.expression.FunctionDescription;


/**
 * Class for the LESS THAN function that has two numerical or two nominal inputs
 *
 * @author Sabrina Kirstein
 * @since 9.11
 */
public class LessThan extends AbstractComparisonFunctionWith2Inputs {

	/**
	 * Constructs a LESS THAN Function with 2 parameters with {@link FunctionDescription}
	 */
	public LessThan() {
		super("comparison.less_than");
	}

	@Override
	protected Boolean compute(double left, double right) {
		if (Double.isNaN(left) || Double.isNaN(right)) {
			// like it was done before
			return false;
		} else {
			return left < right;
		}
	}

	@Override
	protected Boolean compute(String left, String right) {
		if (left == null || right == null) {
			// was an error before, consistent to double compute function
			return false;
		} else {
			return left.compareTo(right) < 0;
		}
	}
}
