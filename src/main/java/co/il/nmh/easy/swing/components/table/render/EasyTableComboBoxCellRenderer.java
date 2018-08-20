package co.il.nmh.easy.swing.components.table.render;

import java.awt.Component;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JTable;

import co.il.nmh.easy.swing.components.table.EasyTable;
import co.il.nmh.easy.swing.components.table.data.ComboBoxData;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboBoxRender;

/**
 * @author Maor Hamami
 *
 */

public class EasyTableComboBoxCellRenderer extends EasyTableCellRenderer
{
	protected EasyTableComboBoxRender easyTableComboBoxRender;

	public EasyTableComboBoxCellRenderer(EasyTable easyTable, EasyTableComboBoxRender easyTableComboBoxRender)
	{
		super(easyTable);

		this.easyTableComboBoxRender = easyTableComboBoxRender;
	}

	@Override
	protected Component render(JTable table, boolean isSelected, int row, int column, String columnName, Object value)
	{
		JComboBox<String> comboBox = new JComboBox<>();

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

		comboBox.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
		comboBox.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());

		return comboBox;
	}
}
