/**
 * Copyright (C) 2001-2021 by RapidMiner and the contributors
 *
 * Complete list of developers available at our web site:
 *
 * http://rapidminer.com
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses/.
 */
package com.rapidminer.tools;


import java.util.Date;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.rapidminer.settings.Settings;
import com.rapidminer.settings.SettingsConstants;


/**
 * Tests {@link DateTools}.
 *
 * @author Marco Boeck
 */
public class DateToolsTest {

	@After
	public void resetSetting() {
		Settings.setSetting(SettingsConstants.DEFAULT_TIMEZONE, null);
	}

	@Test
	public void testFormatDefault() {
		String formattedDate = DateTools.INSTANCE.formatDateTime(new Date(), null);
		Assert.assertTrue("Formatted date does not end with UTC: " + formattedDate, formattedDate.endsWith("UTC"));
	}

	@Test
	public void testFormatCustom() {
		String formattedDate = DateTools.INSTANCE.formatDateTime(new Date(), TimeZone.getTimeZone("PST"));
		Assert.assertTrue("Formatted date does not end with PST: " + formattedDate, formattedDate.endsWith("PST") || formattedDate.endsWith("PDT"));
	}

	@Test
	public void testTimezoneViaSettings() {
		Settings.setSetting(SettingsConstants.DEFAULT_TIMEZONE, "PST");
		String formattedDate = DateTools.INSTANCE.formatDateTime(new Date(), null);
		Assert.assertTrue("Formatted date does not end with PST: " + formattedDate, formattedDate.endsWith("PST") || formattedDate.endsWith("PDT"));
	}

	@Test
	public void testFormatNull() {
		Assert.assertThrows(IllegalArgumentException.class, () -> DateTools.INSTANCE.formatDateTime(null, null));
	}

}
