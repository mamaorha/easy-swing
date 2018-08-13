package co.il.nmh.easy.swing.components.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import co.il.nmh.easy.swing.components.table.editor.EasyTableCellEditor;
import co.il.nmh.easy.swing.components.table.editor.EasyTableComboBoxCellEditor;
import co.il.nmh.easy.swing.components.table.editor.action.EasyTableButtonActionEditor;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableBeforeButtonsRender;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableButtonListener;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableClickListener;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboBoxRender;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboChangedListener;
import co.il.nmh.easy.swing.components.table.render.EasyTableButtonCellRenderer;
import co.il.nmh.easy.swing.components.table.render.EasyTableCellRenderer;
import co.il.nmh.easy.swing.components.table.render.EasyTableComboBoxCellRenderer;
import co.il.nmh.easy.swing.listeners.MouseClickListener;

/**
 * @author Maor Hamami
 *
 */

public abstract class EasyTable
{
	protected JTable table;

	protected Map<String, Integer> columnNameToIndexMap;
	protected Set<EasyTableClickListener> easyTableClickListeners;
	protected Set<EasyTableButtonListener> easyTableButtonListeners;
	protected Set<EasyTableComboChangedListener> easyTableComboChangedListeners;

	public EasyTable(String[] columns)
	{
		columnNameToIndexMap = new HashMap<>();
		easyTableClickListeners = new HashSet<>();
		easyTableButtonListeners = new HashSet<>();
		easyTableComboChangedListeners = new HashSet<>();

		initTable(columns);

		addEvents();
	}

	private void addEvents()
	{
		table.addMouseListener(new MouseClickListener()
		{
			@Override
			public void mouseClicked(MouseEvent mouseEvent)
			{
				int row = table.rowAtPoint(mouseEvent.getPoint());
				int col = table.columnAtPoint(mouseEvent.getPoint());

				if (row >= 0 && col >= 0)
				{
					String columnName = table.getColumnName(col);

					for (EasyTableClickListener easyTableClickListener : easyTableClickListeners)
					{
						easyTableClickListener.clicked(columnName, row, col);
					}
				}
			}
		});
	}

	private void initTable(String[] columns)
	{
		table = new JTable()
		{
			private static final long serialVersionUID = -5854292525741524992L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

				return component;
			}
		};

		table.setRowHeight(36);

		DefaultTableModel model = getModel();

		for (int i = 0; i < columns.length; i++)
		{
			String column = columns[i];

			model.addColumn(column);
			columnNameToIndexMap.put(column, i);
		}

		table.setDefaultEditor(Object.class, new EasyTableCellEditor(this));
		table.setDefaultRenderer(Object.class, new EasyTableCellRenderer(this));
	}

	// basic actions
	public JTable getTable()
	{
		return table;
	}

	public DefaultTableModel getModel()
	{
		return (DefaultTableModel) table.getModel();
	}

	public int rowCount()
	{
		return getModel().getRowCount();
	}

	public void setAutoCreateRowSorter(boolean autoCreateRowSorter)
	{
		table.setAutoCreateRowSorter(autoCreateRowSorter);
	}

	public void add(Object object)
	{
		DefaultTableModel model = getModel();
		model.addRow(new Object[] { object });
	}

	public Object get(int row)
	{
		Object value = table.getValueAt(row, 0);
		return value;
	}

	public void removeRow(int row)
	{
		getModel().removeRow(row);
	}

	public void clear()
	{
		DefaultTableModel model = getModel();
		model.setRowCount(0);
	}

	// special actions
	public abstract Component renderObject(String columnName, Object value);

	protected JLabel buildLabel(String text)
	{
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setForeground(Color.BLACK);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setText(text);

		return label;
	}

	public void setButtonColumn(String columnName, String button)
	{
		setButtonColumn(columnName, button, null);
	}

	public void setButtonColumn(String columnName, String button, EasyTableBeforeButtonsRender easyTableBeforeButtonsRender)
	{
		Set<String> buttons = new HashSet<>();
		buttons.add(button);

		setButtonsColumn(columnName, buttons, easyTableBeforeButtonsRender);
	}

	public void setButtonsColumn(String columnName, Set<String> buttons)
	{
		setButtonsColumn(columnName, buttons, null);
	}

	public void setButtonsColumn(String columnName, Set<String> buttons, EasyTableBeforeButtonsRender easyTableBeforeButtonsRender)
	{
		Integer col = columnNameToIndexMap.get(columnName);

		if (null == col)
		{
			throw new IllegalStateException("column doesn't exist");
		}

		TableColumn column = table.getColumnModel().getColumn(col);
		column.setCellRenderer(new EasyTableButtonCellRenderer(this, easyTableBeforeButtonsRender, buttons));
		column.setCellEditor(new EasyTableButtonActionEditor(this, easyTableBeforeButtonsRender, col, columnName, buttons));
	}

	public void setComboColumn(String columnName, EasyTableComboBoxRender easyTableComboBoxRender)
	{
		Integer col = columnNameToIndexMap.get(columnName);

		if (null == col)
		{
			throw new IllegalStateException("column doesn't exist");
		}

		TableColumn column = table.getColumnModel().getColumn(col);
		column.setCellRenderer(new EasyTableComboBoxCellRenderer(this, easyTableComboBoxRender));
		column.setCellEditor(new EasyTableComboBoxCellEditor(this, easyTableComboBoxRender));
	}

	// listeners
	public void addClickListener(EasyTableClickListener listener)
	{
		easyTableClickListeners.add(listener);
	}

	public void addButtonListener(EasyTableButtonListener listener)
	{
		easyTableButtonListeners.add(listener);
	}

	public void addComboboxListener(EasyTableComboChangedListener listener)
	{
		easyTableComboChangedListeners.add(listener);
	}

	public Set<EasyTableButtonListener> getEasyTableButtonListeners()
	{
		return easyTableButtonListeners;
	}

	public Set<EasyTableComboChangedListener> getEasyTableComboBoxListeners()
	{
		return easyTableComboChangedListeners;
	}
}