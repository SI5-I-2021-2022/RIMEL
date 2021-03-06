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
package com.rapidminer.tools.belt.expression.internal;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rapidminer.belt.column.type.StringList;
import com.rapidminer.belt.column.type.StringSet;
import com.rapidminer.gui.properties.ExpressionPropertyDialog;
import com.rapidminer.tools.I18N;
import com.rapidminer.tools.belt.expression.Constant;
import com.rapidminer.tools.belt.expression.ExpressionParser;
import com.rapidminer.tools.belt.expression.ExpressionType;
import com.rapidminer.tools.belt.expression.FunctionInput;
import com.rapidminer.tools.belt.expression.FunctionInput.Category;
import com.rapidminer.tools.belt.expression.ConstantResolver;


/**
 * {@link ConstantResolver} for the {@link Constant}s supplied to the constructor. These constants will be shown in
 * the expression parser dialog under the category defined by "gui.dialog.function_input.key.constant_category".
 *
 * @author Gisa Meier, Kevin Majchrzak
 * @since 9.11
 */
public class SimpleConstantResolver implements ConstantResolver {

	/** the key suffix to get the category name */
	private static final String KEY_SUFFIX = ".constant_category";
	private static final String VARIABLE_DOES_NOT_EXIST_ERROR_MESSAGE = "Variable does not exist";

	private final Map<String, Constant> constantMap;
	private final String categoryName;

	private static final String GUI_KEY_PREFIX = "gui.dialog.function_input.";

	/**
	 * Creates a {@link ConstantResolver} that knows the constants. The constants are used by the
	 * {@link ExpressionParser} and shown in the {@link ExpressionPropertyDialog} under the category
	 * defined by "gui.dialog.function_input.key.constant_category".
	 *
	 * @param key
	 *            the key for the category name
	 * @param constants
	 *            the constants this resolver knows
	 */
	public SimpleConstantResolver(String key, List<Constant> constants) {
		constantMap = new LinkedHashMap<>();
		for (Constant constant : constants) {
			if (constant != null) {
				constantMap.put(constant.getName(), constant);
			}
		}
		categoryName = I18N.getGUIMessage(GUI_KEY_PREFIX + key + KEY_SUFFIX);
	}

	@Override
	public List<FunctionInput> getAllVariables() {
		List<FunctionInput> functionInputs = new ArrayList<>(constantMap.size());
		for (Constant constant : constantMap.values()) {
			functionInputs.add(new FunctionInput(Category.CONSTANT, categoryName, constant.getName(),
					constant.getType(),	constant.getAnnotation(), false, constant.isInvisible()));
		}
		return functionInputs;
	}

	@Override
	public ExpressionType getVariableType(String variableName) {
		if (constantMap.get(variableName) == null) {
			return null;
		}
		return constantMap.get(variableName).getType();
	}

	@Override
	public String getStringValue(String variableName) {
		if (constantMap.get(variableName) == null) {
			throw new IllegalArgumentException(VARIABLE_DOES_NOT_EXIST_ERROR_MESSAGE);
		}
		return constantMap.get(variableName).getStringValue();
	}

	@Override
	public double getDoubleValue(String variableName) {
		if (constantMap.get(variableName) == null) {
			throw new IllegalArgumentException(VARIABLE_DOES_NOT_EXIST_ERROR_MESSAGE);
		}
		return constantMap.get(variableName).getDoubleValue();
	}

	@Override
	public boolean getBooleanValue(String variableName) {
		if (constantMap.get(variableName) == null) {
			throw new IllegalArgumentException(VARIABLE_DOES_NOT_EXIST_ERROR_MESSAGE);
		}
		return constantMap.get(variableName).getBooleanValue();
	}

	@Override
	public Instant getInstantValue(String variableName) {
		if (constantMap.get(variableName) == null) {
			throw new IllegalArgumentException(VARIABLE_DOES_NOT_EXIST_ERROR_MESSAGE);
		}
		return constantMap.get(variableName).getInstantValue();
	}

	@Override
	public LocalTime getLocalTimeValue(String variableName) {
		if (constantMap.get(variableName) == null) {
			throw new IllegalArgumentException(VARIABLE_DOES_NOT_EXIST_ERROR_MESSAGE);
		}
		return constantMap.get(variableName).getLocalTimeValue();
	}

	@Override
	public StringSet getStringSetValue(String variableName) {
		if (constantMap.get(variableName) == null) {
			throw new IllegalArgumentException(VARIABLE_DOES_NOT_EXIST_ERROR_MESSAGE);
		}
		return constantMap.get(variableName).getStringSetValue();
	}

	@Override
	public StringList getStringListValue(String variableName) {
		if (constantMap.get(variableName) == null) {
			throw new IllegalArgumentException(VARIABLE_DOES_NOT_EXIST_ERROR_MESSAGE);
		}
		return constantMap.get(variableName).getStringListValue();
	}

}
