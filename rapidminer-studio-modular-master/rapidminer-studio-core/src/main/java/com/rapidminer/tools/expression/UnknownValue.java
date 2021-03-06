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
package com.rapidminer.tools.expression;

import com.rapidminer.tools.Ontology;


/**
 * This is an enumeration for possible unknown values. This is used to determine, of which type an
 * returned unknown actually is.
 * 
 * @author Sebastian Land
 * @deprecated since 9.11, see {@link com.rapidminer.tools.belt.expression.ExpressionParser}
 */
@Deprecated
public enum UnknownValue {
	UNKNOWN_NOMINAL(Ontology.NOMINAL),
	// UNKNOWN_NUMERICAL(Ontology.NUMERICAL), Numerical Unknowns must be encoded by Double.NaN
	UNKNOWN_BOOLEAN(Ontology.BINOMINAL), UNKNOWN_DATE(Ontology.DATE_TIME);

	private int valueType;

	UnknownValue(int valueType) {
		this.valueType = valueType;
	}

	public int getValueType() {
		return valueType;
	}
}
