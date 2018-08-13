package co.il.nmh.easy.swing.components.table.editor.action;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.EventObject;
import java.util.Map;
import java.util.Objects;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;

import co.il.nmh.easy.swing.components.table.EasyTable;
import co.il.nmh.easy.swing.utils.MethodInvestigator;

/**
 * @author Maor Hamami
 *
 */

public abstract class EasyTableDefaultActionEditor implements TableCellEditor
{
	protected transient ChangeEvent changeEvent;

	protected EasyTable easyTable;
	protected int col;
	protected String columnName;
	protected EditingStopHandler handler;

	private EventListenerList listenerList;

	public EasyTableDefaultActionEditor(EasyTable easyTable, int col, String columnName)
	{
		this.easyTable = easyTable;
		this.col = col;
		this.columnName = columnName;

		try
		{
			Field listenerListField = JTable.class.getSuperclass().getDeclaredField("listenerList");
			listenerListField.setAccessible(true);

			listenerList = (EventListenerList) listenerListField.get(easyTable.getTable());
		}
		catch (Exception e)
		{
		}

		handler = new EditingStopHandler();

		easyTable.getTable().addMouseListener(handler);
	}

	public void initListeners(Collection<? extends Component> components)
	{
		for (Component component : components)
		{
			component.addMouseListener(handler);

			Map<String, Method> classMethodsMap = MethodInvestigator.INSTANCE.getClassMethodsMap(component.getClass());
			Method method = classMethodsMap.get("addActionListener");

			if (null != method)
			{
				try
				{
					method.setAccessible(true);
					method.invoke(component, handler);
				}
				catch (Exception e)
				{
				}
			}
		}
	}

	@Override
	public Object getCellEditorValue()
	{
		return "";
	}

	@Override
	public boolean isCellEditable(EventObject anEvent)
	{
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent)
	{
		return true;
	}

	@Override
	public boolean stopCellEditing()
	{
		fireEditingStopped();
		return true;
	}

	@Override
	public void cancelCellEditing()
	{
		fireEditingCanceled();
	}

	@Override
	public void addCellEditorListener(CellEditorListener l)
	{
		listenerList.add(CellEditorListener.class, l);
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l)
	{
		listenerList.remove(CellEditorListener.class, l);
	}

	protected void fireEditingStopped()
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();

		// Process the listeners last to first, notifying
		// those that are interested in this event

		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == CellEditorListener.class)
			{
				// Lazily create the event:
				if (Objects.isNull(changeEvent))
				{
					changeEvent = new ChangeEvent(this);
				}

				try
				{
					((CellEditorListener) listeners[i + 1]).editingStopped(changeEvent);
				}
				catch (Exception e)
				{

				}
			}
		}
	}

	protected void fireEditingCanceled()
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == CellEditorListener.class)
			{
				// Lazily create the event:
				if (Objects.isNull(changeEvent))
				{
					changeEvent = new ChangeEvent(this);
				}
				((CellEditorListener) listeners[i + 1]).editingCanceled(changeEvent);
			}
		}
	}

	private class EditingStopHandler extends MouseAdapter implements ActionListener
	{
		@Override
		public void mousePressed(MouseEvent e)
		{
			Object o = e.getSource();

			if (o instanceof TableCellEditor)
			{
				actionPerformed(null);
			}

			else if (o instanceof JButton)
			{
				// DEBUG: view button click -> control key down + edit button(same cell) press -> remain selection color
				ButtonModel m = ((JButton) e.getComponent()).getModel();
				if (m.isPressed() && easyTable.getTable().isRowSelected(easyTable.getTable().getEditingRow()) && e.isControlDown())
				{
					easyTable.getTable().setBackground(easyTable.getTable().getBackground());
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			EventQueue.invokeLater(() -> fireEditingStopped());
		}
	}
}
