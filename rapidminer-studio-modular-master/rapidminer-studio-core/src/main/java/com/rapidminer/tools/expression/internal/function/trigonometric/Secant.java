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
package com.rapidminer.tools.expression.internal.function.trigonometric;

import com.rapidminer.tools.Ontology;
import com.rapidminer.tools.expression.internal.function.Abstract1DoubleInputFunction;


/**
 *
 * A {@link Function} computing the trigonometric secant of a radian.
 *
 * @author Denis Schernov
 *
 * @deprecated since 9.11, see {@link com.rapidminer.tools.belt.expression.ExpressionParser}
 */
@Deprecated
public class Secant extends Abstract1DoubleInputFunction {

	public Secant() {
		super("trigonometrical.sec", Ontology.NUMERICAL);
	}

	@Override
	protected double compute(double value) {
		return Double.isNaN(value) || value == Math.PI / 2 ? Double.NaN : 1.0 / Math.cos(value);
	}
}
