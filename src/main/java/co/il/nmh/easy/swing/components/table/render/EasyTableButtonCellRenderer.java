package co.il.nmh.easy.swing.components.table.render;

import java.awt.Component;
import java.util.Set;

import javax.swing.JTable;

import co.il.nmh.easy.swing.components.table.EasyTable;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableBeforeButtonsRender;
import co.il.nmh.easy.swing.components.table.panel.EasyTableButtonPanel;

/**
 * @author Maor Hamami
 *
 */

public class EasyTableButtonCellRenderer extends EasyTableCellRenderer
{
	protected EasyTableBeforeButtonsRender easyTableBeforeButtonsRender;
	protected Set<String> buttons;

	public EasyTableButtonCellRenderer(EasyTable easyTable, EasyTableBeforeButtonsRender easyTableBeforeButtonsRender, Set<String> buttons)
	{
		super(easyTable);

		this.easyTableBeforeButtonsRender = easyTableBeforeButtonsRender;
		this.buttons = buttons;
	}

	@Override
	protected Component render(JTable table, boolean isSelected, int row, int column, String columnName, Object value)
	{
		EasyTableButtonPanel buttonsPanel = new EasyTableButtonPanel(buttons);

		if (null != easyTableBeforeButtonsRender)
		{
			easyTableBeforeButtonsRender.beforeButtonsRender(columnName, value, buttonsPanel.getButtonMap());
		}

		int minWidth = table.getColumnModel().getColumn(column).getMinWidth();
		int width = buttonsPanel.getPreferredSize().width;

		width = Math.max(minWidth, width);

		table.getColumnModel().getColumn(column).setMinWidth(width);

		return buttonsPanel;
	}
}
