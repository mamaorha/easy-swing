package co.il.nmh.easy.swing.components.table.editor.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JTable;

import co.il.nmh.easy.swing.components.table.EasyTable;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableBeforeButtonsRender;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableButtonListener;
import co.il.nmh.easy.swing.components.table.panel.EasyTableButtonPanel;

/**
 * @author Maor Hamami
 *
 */

public class EasyTableButtonActionEditor extends EasyTableDefaultActionEditor
{
	protected EasyTableBeforeButtonsRender easyTableBeforeButtonsRender;
	protected Set<String> buttons;

	public EasyTableButtonActionEditor(EasyTable easyTable, EasyTableBeforeButtonsRender easyTableBeforeButtonsRender, int col, String columnName, Set<String> buttons)
	{
		super(easyTable, col, columnName);

		this.easyTableBeforeButtonsRender = easyTableBeforeButtonsRender;
		this.buttons = buttons;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		String columnName = table.getColumnName(column);
		value = table.getValueAt(row, 0);

		EasyTableButtonPanel buttonsPanel = buildPanel(value);

		if (null != easyTableBeforeButtonsRender)
		{
			easyTableBeforeButtonsRender.beforeButtonsRender(columnName, value, buttonsPanel.getButtonMap());
		}

		return buttonsPanel;
	}

	private EasyTableButtonPanel buildPanel(Object value)
	{
		EasyTableButtonPanel buttonsPanel = new EasyTableButtonPanel(buttons);

		initListeners(buttonsPanel.getButtonMap().values());

		for (JButton button : buttonsPanel.getButtonMap().values())
		{
			AbstractAction action = new AbstractAction(button.getText())
			{
				private static final long serialVersionUID = -8398032133047315119L;

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					if (button.isEnabled())
					{
						for (EasyTableButtonListener easyTableButtonListener : easyTable.getEasyTableButtonListeners())
						{
							int row = easyTable.getTable().convertRowIndexToModel(easyTable.getTable().getEditingRow());
							easyTableButtonListener.buttonClicked(value, columnName, row, col, button.getText());
						}

						easyTable.getTable().repaint();
					}
				}
			};

			button.setAction(action);
		}

		return buttonsPanel;
	}
}