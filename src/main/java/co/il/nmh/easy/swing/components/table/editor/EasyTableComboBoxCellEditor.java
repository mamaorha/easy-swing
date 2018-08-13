package co.il.nmh.easy.swing.components.table.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

import co.il.nmh.easy.swing.components.table.EasyTable;
import co.il.nmh.easy.swing.components.table.data.ComboBoxData;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboBoxRender;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboChangedListener;

/**
 * @author Maor Hamami
 *
 */

public class EasyTableComboBoxCellEditor extends DefaultCellEditor
{
	private static final long serialVersionUID = 4713030293576275742L;

	protected EasyTable easyTable;
	protected EasyTableComboBoxRender easyTableComboBoxRender;

	public EasyTableComboBoxCellEditor(EasyTable easyTable, EasyTableComboBoxRender easyTableComboBoxRender)
	{
		super(new JComboBox<>());

		this.easyTable = easyTable;
		this.easyTableComboBoxRender = easyTableComboBoxRender;
	}

	private Object oldValue;

	@Override
	public Object getCellEditorValue()
	{
		return oldValue;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		String columnName = table.getColumnName(column);
		this.oldValue = value;

		value = table.getValueAt(row, 0);

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

					comboBox.setSelectedIndex(comboBoxData.getSelectedIndex());
				}
			}
		}

		comboBox.addActionListener(new ComboBoxListener(comboBox, columnName, row, column, value));

		return comboBox;
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
