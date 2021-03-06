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
package com.rapidminer.tools.belt.expression;

import java.util.Objects;

import com.rapidminer.gui.properties.ExpressionPropertyDialog;
import com.rapidminer.tools.I18N;


/**
 * Describes a function with a name, description, number of arguments and return type.
 *
 * @author Kevin Majchrzak
 * @since 9.11
 */
public class FunctionDescription {

	private final String displayName;
	private int numberOfArguments;
	private String helpTextName;

	/**
	 * the part which is shown as description in the {@link ExpressionPropertyDialog}
	 */
	private String description;

	/**
	 * name of the group under which this is shown in the {@link ExpressionPropertyDialog}
	 */
	private final String groupName;

	/**
	 * the part which is shown as return type in the {@link ExpressionPropertyDialog}
	 */
	private ExpressionType returnType;

	/**
	 * the part which is shown as title in the {@link ExpressionPropertyDialog}
	 */
	private String functionNameWithParameters;

	/**
	 * Constant representing an unknown number of arguments.
	 */
	public static final int UNFIXED_NUMBER_OF_ARGUMENTS = -1;

	/**
	 * the i18n key suffix for getting the function name with parameters
	 */
	private static final String KEY_SUFFIX_PARAMETERS = ".parameters";

	/**
	 * the i18n key suffix for getting the function description
	 */
	private static final String KEY_SUFFIX_DESCRIPTION = ".description";

	/**
	 * the i18n key suffix for getting the help text name
	 */
	private static final String KEY_SUFFIX_HELP = ".help";

	/**
	 * the i18n key suffix for getting the function name
	 */
	private static final String KEY_SUFFIX_NAME = ".name";

	/**
	 * the i18n key suffix for getting the function name
	 */
	private static final String KEY_SUFFIX_GROUP = ".group";

	/**
	 * the i18n key prefix for function descriptions
	 */
	private static final String GUI_KEY_PREFIX = "gui.dialog.function.";

	/**
	 * Creates a {@link FunctionDescription} with number of arguments and return type as given and with name,
	 * description, etc. read from the i18nKey. The functionName is read from "gui.dialog.function.i18nKey.name", the
	 * helpTextName from ".help", the groupName from ".group", the description from ".description" and the function with
	 * parameters from ".parameters". If ".parameters" is not present, the ".name" is taken for the function with
	 * parameters.
	 *
	 * @param i18nKey
	 * 		the key determining the function description
	 * @param numberOfArguments
	 * 		the number of arguments the function takes, or {@link #UNFIXED_NUMBER_OF_ARGUMENTS} if this is not a fixed
	 * 		number
	 * @param returnType
	 * 		the {@link ExpressionType} of the result of the function
	 */
	public FunctionDescription(String i18nKey, int numberOfArguments, ExpressionType returnType) {
		String key = GUI_KEY_PREFIX + i18nKey;
		this.displayName = I18N.getGUIMessage(key + KEY_SUFFIX_NAME);
		this.helpTextName = I18N.getGUIMessage(key + KEY_SUFFIX_HELP);
		this.description = I18N.getGUIMessage(key + KEY_SUFFIX_DESCRIPTION);
		this.numberOfArguments = numberOfArguments;
		this.returnType = returnType;
		this.groupName = I18N.getGUIMessage(key + KEY_SUFFIX_GROUP);
		String funcNameWithParams = I18N.getGUIMessageOrNull(key + KEY_SUFFIX_PARAMETERS);
		this.functionNameWithParameters = funcNameWithParams == null ? displayName : funcNameWithParams;
	}

	/**
	 * Returns the name of the function that should be displayed when inserting the function. Ends with () if the
	 * function is not an operation.
	 *
	 * @return the function name
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * Returns the help text name that is displayed in the help text tooltip.
	 *
	 * @return the help text name of the function
	 */
	public String getHelpTextName() {
		return this.helpTextName;
	}

	/**
	 * Returns the name of the group this function belongs to. The function is displayed under this group name in the
	 * {@link ExpressionPropertyDialog}.
	 *
	 * @return the group the function belongs to
	 */
	public String getGroupName() {
		return this.groupName;
	}

	/**
	 * Returns the description of the function.
	 *
	 * @return the function description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Returns the number of arguments one should check for this function. Returns {@link #UNFIXED_NUMBER_OF_ARGUMENTS}
	 * if there is more than one possible number of arguments.
	 *
	 * @return the number of arguments to check for this function or {@link #UNFIXED_NUMBER_OF_ARGUMENTS}
	 */
	public int getNumberOfArguments() {
		return this.numberOfArguments;
	}

	/**
	 * Returns the expression type of this function's result.
	 *
	 * @see ExpressionType
	 */
	public ExpressionType getReturnType() {
		return this.returnType;
	}

	/**
	 * Returns the function name with parameter type and dummy name
	 */
	public String getFunctionNameWithParameters() {
		return this.functionNameWithParameters;
	}

	@Override
	public int hashCode() {
		return Objects.hash(groupName, displayName);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof FunctionDescription)) {
			return false;
		}
		FunctionDescription otherFunctionDescription = (FunctionDescription) other;
		return getGroupName().equals(otherFunctionDescription.getGroupName())
				&& getDisplayName().equals(otherFunctionDescription.getDisplayName());
	}
}

