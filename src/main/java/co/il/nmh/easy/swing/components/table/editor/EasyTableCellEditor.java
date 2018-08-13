package co.il.nmh.easy.swing.components.table.editor;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import co.il.nmh.easy.swing.components.EasyScrollPane;
import co.il.nmh.easy.swing.components.table.EasyTable;

/**
 * @author Maor Hamami
 *
 */

public class EasyTableCellEditor extends DefaultCellEditor
{
	private static final long serialVersionUID = 4713030293576275742L;

	private EasyTable easyTable;

	public EasyTableCellEditor(EasyTable easyTable)
	{
		super(new JTextField());

		this.easyTable = easyTable;
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
		String columnName = table.getColumnName(column);

		value = easyTable.get(row);

		Component renderObject = easyTable.renderObject(columnName, value);

		if (renderObject instanceof JLabel)
		{
			String text = ((JLabel) renderObject).getText();

			JTextField textField = new JTextField();
			textField.setText(text);
			textField.setEditable(false);

			return new EasyScrollPane(textField);
		}

		return renderObject;
	}
}
