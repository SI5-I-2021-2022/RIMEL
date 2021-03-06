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
package com.rapidminer.tools.belt.expression.internal.function.text;

/**
 * A {@link com.rapidminer.tools.belt.expression.Function} to check whether a nominal value starts with a specific
 * string.
 *
 * @author Thilo Kamradt
 * @since 9.11
 */
public class Starts extends Abstract2StringInputBooleanOutputFunction {

	/**
	 * Creates a function to check whether a nominal value starts with a specific string
	 */
	public Starts() {
		super("text_information.starts");
	}

	@Override
	protected Boolean compute(String value1, String value2) {
		if (value1 == null || value2 == null) {
			return null;
		}
		return value1.startsWith(value2);
	}

}
