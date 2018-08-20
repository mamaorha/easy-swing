package co.il.nmh.easy.swing.components.table.render;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import co.il.nmh.easy.swing.components.table.EasyTable;

public class EasyTableCellRenderer implements TableCellRenderer
{
	protected EasyTable easyTable;

	public EasyTableCellRenderer(EasyTable easyTable)
	{
		this.easyTable = easyTable;
	}

	@Override
	public final Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		String columnName = table.getColumnName(column);

		value = easyTable.get(row);

		return render(table, isSelected, row, column, columnName, value);
	}

	protected Component render(JTable table, boolean isSelected, int row, int column, String columnName, Object value)
	{
		return easyTable.renderObject(columnName, value);
	}
}