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
package com.rapidminer.gui.properties.belt;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.rapidminer.gui.tools.AttributeGuiTools;
import com.rapidminer.gui.tools.SwingTools;
import com.rapidminer.tools.AbstractObservable;
import com.rapidminer.tools.Observer;
import com.rapidminer.tools.belt.expression.FunctionInput;
import com.rapidminer.tools.belt.expression.FunctionInput.Category;
import com.rapidminer.tools.belt.expression.TableResolver;


/**
 * Panel which displays an input of a {@link com.rapidminer.tools.belt.expression.FunctionDescription}.
 *
 * @author Kevin Majchrzak
 * @since 9.11
 */
public class FunctionInputPanel extends JPanel {

	/** functions that should be highlighted in the description of date function constants */
	private static final String[] HIGHLIGHT_FUNCTIONS_DESCRIPTIONS = { "date_str_loc", "date_str", "date_add", "date_set",
			"date_get", "eval" };

	private static final String CLOSING_HTML_TAG = "</html>";
	private static final String OPENING_HTML_TAG = "<html>";

	/**
	 * As the FunctionDescriptionPanel is a Panel, it cannot be an observable. It owns an
	 * {@link AbstractObservable}, which informs the observers about click changes.
	 *
	 * @author Sabrina Kirstein
	 */
	private class PrivateObservable extends AbstractObservable<FunctionInputPanel> {

		@Override
		public void fireUpdate() {
			fireUpdate(FunctionInputPanel.this);
		}
	}

	private static final long serialVersionUID = -1394721896496797249L;

	/** label showing the name of the input */
	private JLabel lblInputName;

	/** the function input represented by this panel */
	private transient FunctionInput input;

	/** the default background (used for highlighting) */
	private Color defaultBackground;

	/** mouse listener to react on hover events (highlight the panel) */
	private transient MouseListener hoverMouseListener;

	/** mouse listener to send mouse events to the parent */
	private transient MouseListener dispatchMouseListener;

	/** value of the function input */
	private String inputValue;

	/** observable to update the parent on click events */
	private transient PrivateObservable observable = new PrivateObservable();

	/** defines when the width of function input names or description is cropped */
	private static final int MAX_WIDTH_OF_TEXT = 350;

	private static final Color COLOR_HIGHLIGHT = new Color(225, 225, 225);

	private static final int FIRST_ROW_HEIGHT = 35;

	private static final int ROW_HEIGHT = 20;

	private static final String HTML_TAB = "&nbsp;";

	private static final ImageIcon ICON_CUSTOM_MACRO = SwingTools.createIcon("16/keyboard_key_a_edit.png");

	private static final ImageIcon ICON_PREDEFINED_MACRO = SwingTools.createIcon("16/keyboard_key_a.png");

	private static final String NAME_CUSTOM_MACRO = "Custom Macro";

	private static final String NAME_PREDEFINED_MACRO = "Predefined Macro";

	/**
	 * Creates a panel for a given function input without a value.
	 *
	 * @param input
	 *            the related function input
	 */
	public FunctionInputPanel(FunctionInput input) {
		this(input, null);
	}

	/**
	 * Creates a panel for a given function input with a specified value.
	 *
	 * @param input
	 *            the related function input
	 * @param inputValue
	 *            value of the function input
	 */
	public FunctionInputPanel(FunctionInput input, String inputValue) {

		this.inputValue = inputValue;
		this.input = input;

		// initialize the UI
		initGUI();
		addMouseListener(createOrGetHoverMouseListener());
	}

	/**
	 * Gives the input name
	 *
	 * @return input name
	 */
	public String getInputName() {
		return input.getName();
	}

	public Category getCategory() {
		return input.getCategory();
	}

	/**
	 * Register an observer to react on click events
	 */
	public void registerObserver(Observer<FunctionInputPanel> observer) {
		observable.addObserver(observer, false);
	}

	/**
	 * initializes the graphical user interface
	 */
	private void initGUI() {

		defaultBackground = getBackground();
		int height = FIRST_ROW_HEIGHT;

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;

		// label showing the type of the input
		JLabel lblTypeIcon = new JLabel(getTypeIcon());
		lblTypeIcon.setToolTipText(getIconToolTip());
		lblTypeIcon.addMouseListener(createOrGetDispatchMouseListener());
		add(lblTypeIcon, gbc);

		gbc.gridx += 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		String text = input.getName();
		lblInputName = new JLabel();
		lblInputName.addMouseListener(createOrGetDispatchMouseListener());
		lblInputName.setToolTipText(text);
		String croppedText = OPENING_HTML_TAG
				+ SwingTools.getStrippedJComponentText(this, HTML_TAB + HTML_TAB + HTML_TAB + text, MAX_WIDTH_OF_TEXT, 0)
				+ CLOSING_HTML_TAG;
		lblInputName.setText(croppedText);
		lblInputName.setAlignmentX(LEFT_ALIGNMENT);

		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		add(lblInputName, gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.gridx += 1;
		JPanel gapPanel = new JPanel();
		gapPanel.addMouseListener(createOrGetDispatchMouseListener());

		gapPanel.setOpaque(false);
		add(gapPanel, gbc);

		// add a row to show the input value, if it is defined
		if (inputValue != null) {
			gbc.gridy += 1;
			gbc.gridx = 1;
			height += ROW_HEIGHT;
			//  label showing the value of the input, if given
			JLabel lblInputValue = new JLabel();
			if (TableResolver.KEY_SPECIAL_ATTRIBUTES.equals(input.getCategoryName())) {
				lblInputValue.setToolTipText("<html><b>Role:</b> " + inputValue + CLOSING_HTML_TAG);
			} else {
				lblInputValue.setToolTipText(OPENING_HTML_TAG + inputValue + CLOSING_HTML_TAG);
			}
			croppedText = SwingTools.getStrippedJComponentText(this, HTML_TAB + HTML_TAB + HTML_TAB + inputValue,
					MAX_WIDTH_OF_TEXT, 0);
			for (String highlightFunction : HIGHLIGHT_FUNCTIONS_DESCRIPTIONS) {
				croppedText = croppedText.replaceAll(highlightFunction, "<i>" + highlightFunction + "</i>");
			}
			lblInputValue.setText(OPENING_HTML_TAG + croppedText + CLOSING_HTML_TAG);
			lblInputValue.setForeground(Color.GRAY);
			lblInputValue.setAlignmentX(LEFT_ALIGNMENT);
			lblInputValue.addMouseListener(createOrGetDispatchMouseListener());
			add(lblInputValue, gbc);
		}

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// add the input name to the expression
				observable.fireUpdate();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				highlightInputName(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				highlightInputName(true);
			}
		});

		setMinimumSize(new Dimension(getMinimumSize().width, height));
		setPreferredSize(new Dimension(getPreferredSize().width, height));
		setMaximumSize(new Dimension(getMaximumSize().width, getPreferredSize().height));
	}

	/**
	 * Creates the {@link MouseListener} which highlights the panel. If it is already created this
	 * will return the current instance.
	 *
	 * @return
	 */
	private MouseListener createOrGetHoverMouseListener() {
		if (hoverMouseListener == null) {
			hoverMouseListener = new MouseAdapter() {

				@Override
				public void mouseExited(MouseEvent e) {
					highlight(false);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					highlight(true);
				}
			};
		}
		return hoverMouseListener;
	}

	/**
	 * Creates the {@link MouseListener} which delivers {@link MouseEvent}s to the
	 * {@link FunctionInputPanel}. Some GUI elements, like {@link JLabel} with Tooltips, may consume
	 * all events and does not inform the parent component.
	 */
	private MouseListener createOrGetDispatchMouseListener() {
		if (dispatchMouseListener == null) {
			dispatchMouseListener = new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					FunctionInputPanel.this.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e,
							FunctionInputPanel.this));
				}

				@Override
				public void mousePressed(MouseEvent e) {
					FunctionInputPanel.this.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e,
							FunctionInputPanel.this));
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					FunctionInputPanel.this.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e,
							FunctionInputPanel.this));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					FunctionInputPanel.this.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e,
							FunctionInputPanel.this));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					FunctionInputPanel.this.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e,
							FunctionInputPanel.this));
				}

			};
		}
		return dispatchMouseListener;
	}

	/**
	 * Sets whether to highlight the {@link #lblInputName}.
	 */
	private void highlightInputName(boolean highlight) {

		if (highlight) {
			lblInputName.setForeground(SwingTools.RAPIDMINER_ORANGE);
		} else {
			lblInputName.setForeground(Color.BLACK);
		}
		highlight(highlight);
	}

	/**
	 * Sets whether to highlight the {@link FunctionInputPanel}.
	 */
	private void highlight(boolean highlight) {
		if (highlight) {
			setBackground(COLOR_HIGHLIGHT);
		} else {
			if (defaultBackground != null) {
				setBackground(defaultBackground);
			}
		}
	}

	/**
	 * @return tool tip containing the written type of the {@link FunctionInput}
	 */
	private String getIconToolTip() {
		if (input.getCategory() == Category.SCOPE) {
			if (input.useCustomIcon()) {
				return NAME_CUSTOM_MACRO;
			} else {
				return NAME_PREDEFINED_MACRO;
			}
		}
		return FunctionDescriptionPanel.mapExpressionTypeToDisplayName(input.getType());
	}

	/**
	 * @return icon representing the type of the {@link FunctionInput}
	 */
	private ImageIcon getTypeIcon() {
		if (input.getCategory() == Category.SCOPE) {
			if (input.useCustomIcon()) {
				return ICON_CUSTOM_MACRO;
			} else {
				return ICON_PREDEFINED_MACRO;
			}
		}
		return AttributeGuiTools.getIconForExpressionType(input.getType(), true);
	}

}
