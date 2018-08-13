package co.il.nmh.easy.swing.components.table.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellEditor;

import co.il.nmh.easy.swing.components.table.EasyTable;
import co.il.nmh.easy.swing.components.table.data.ComboBoxData;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboBoxRender;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboChangedListener;

/**
 * @author Maor Hamami
 *
 */

public class EasyTableComboBoxCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private static final long serialVersionUID = 4713030293576275742L;

	protected EasyTable easyTable;
	protected EasyTableComboBoxRender easyTableComboBoxRender;
	protected boolean cellEditingStopped = false;

	public EasyTableComboBoxCellEditor(EasyTable easyTable, EasyTableComboBoxRender easyTableComboBoxRender)
	{
		this.easyTable = easyTable;
		this.easyTableComboBoxRender = easyTableComboBoxRender;
	}

	private int row;

	@Override
	public Object getCellEditorValue()
	{
		if (row == 0)
		{
			return easyTable.get(row);
		}

		else
		{
			return null;
		}
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		this.row = row;
		String columnName = table.getColumnName(column);

		value = easyTable.get(row);

		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
		comboBox.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());

		if (null != easyTableComboBoxRender)
		{
			ComboBoxData comboBoxData = easyTableComboBoxRender.getComboBoxData(columnName, value);

			if (null != comboBoxData)
			{
				Collection<String> items = comboBoxData.getItems();

				if (null != items)
				{
					for (String item : items)
					{
						comboBox.addItem(item);
					}

					if (comboBoxData.getSelectedIndex() >= comboBox.getItemCount())
					{
						comboBoxData.setSelectedIndex(0);
					}

					if (comboBox.getItemCount() > 0)
					{
						comboBox.setSelectedIndex(comboBoxData.getSelectedIndex());
					}
				}
			}
		}

		comboBox.addActionListener(new ComboBoxListener(comboBox, columnName, row, column, value));
		comboBox.addPopupMenuListener(new PopupMenuListener()
		{
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e)
			{
				cellEditingStopped = false;
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
			{
				cellEditingStopped = true;
				fireEditingCanceled();
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e)
			{
			}
		});
		return comboBox;
	}

	@Override
	public boolean stopCellEditing()
	{
		return cellEditingStopped;
	}

	private class ComboBoxListener implements ActionListener
	{
		private JComboBox<String> comboBox;
		private String columnName;
		private int row;
		private int col;
		private Object value;

		private int currentIndex;

		public ComboBoxListener(JComboBox<String> comboBox, String columnName, int row, int col, Object value)
		{
			this.comboBox = comboBox;
			this.columnName = columnName;
			this.row = row;
			this.col = col;
			this.value = value;

			currentIndex = comboBox.getSelectedIndex();
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			int newIndex = comboBox.getSelectedIndex();

			if (newIndex != currentIndex)
			{
				Set<EasyTableComboChangedListener> easyTableComboBoxListeners = easyTable.getEasyTableComboBoxListeners();

				for (EasyTableComboChangedListener easyTableComboChangedListener : easyTableComboBoxListeners)
				{
					easyTableComboChangedListener.comboChanged(value, columnName, row, col, newIndex);
				}

				currentIndex = newIndex;
				easyTable.getTable().repaint();
			}
		}
	}
}
