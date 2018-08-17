package co.il.nmh.easy.swing.components.list;

import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import co.il.nmh.easy.swing.components.EasyScrollPane;
import co.il.nmh.easy.swing.components.list.listeners.EasyListElementListener;
import co.il.nmh.easy.swing.components.list.listeners.EasyListItemSelectedChangedListener;

/**
 * @author Maor Hamami
 */

public class EasyList
{
	protected JList<String> list;
	protected DefaultListModel<String> listModel;

	protected int[] selectedIndices;

	protected Set<EasyListElementListener> easyListElementListeners;
	protected Set<EasyListItemSelectedChangedListener> easyListItemSelectedChangedListeners;

	public EasyList()
	{
		listModel = new DefaultListModel<>();

		list = new JList<>(listModel);

		easyListElementListeners = new HashSet<>();
		easyListItemSelectedChangedListeners = new HashSet<>();

		addEvents();
	}

	private void addEvents()
	{
		list.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if (e.getValueIsAdjusting())
				{
					return;
				}

				selectedIndices = list.getSelectedIndices();

				if (selectedIndices.length < 1)
				{
					for (EasyListItemSelectedChangedListener easyListItemSelectedChangedListener : easyListItemSelectedChangedListeners)
					{
						easyListItemSelectedChangedListener.selectionClear();
					}
				}

				else
				{
					for (EasyListItemSelectedChangedListener easyListItemSelectedChangedListener : easyListItemSelectedChangedListeners)
					{
						easyListItemSelectedChangedListener.selected(selectedIndices);
					}
				}
			}
		});
	}

	public void clear()
	{
		for (int i = listModel.size() - 1; i >= 0; i--)
		{
			remove(i);
		}
	}

	public void addElement(String element)
	{
		listModel.addElement(element);

		int index = listModel.size() - 1;

		for (EasyListElementListener easyListElementListener : easyListElementListeners)
		{
			easyListElementListener.elementAdd(index, element);
		}
	}

	public void addElement(int index, String element)
	{
		listModel.add(index, element);

		for (EasyListElementListener easyListElementListener : easyListElementListeners)
		{
			easyListElementListener.elementAdd(index, element);
		}
	}

	public void removeSelected()
	{
		if (null != selectedIndices)
		{
			for (int i = selectedIndices.length - 1; i >= 0; i--)
			{
				int index = selectedIndices[i];
				remove(index);
			}
		}
	}

	public void remove(int index)
	{
		String element = listModel.remove(index);

		for (EasyListElementListener easyListElementListener : easyListElementListeners)
		{
			easyListElementListener.elementRemoved(index, element);
		}
	}

	public String getElement(int index)
	{
		return listModel.getElementAt(index);
	}

	public int getSelectedIndex()
	{
		return list.getSelectedIndex();
	}

	public void setEnabled(boolean enabled)
	{
		list.setEnabled(enabled);
	}

	public EasyScrollPane getComponent()
	{
		return new EasyScrollPane(list);
	}

	public JList<String> getList()
	{
		return list;
	}

	public DefaultListModel<String> getListModel()
	{
		return listModel;
	}

	public void addEasyListElementListener(EasyListElementListener easyListElementListener)
	{
		easyListElementListeners.add(easyListElementListener);

		for (int i = 0; i < listModel.size(); i++)
		{
			String element = getElement(i);
			easyListElementListener.elementAdd(i, element);
		}
	}

	public void addEasyListItemSelectedChangedListener(EasyListItemSelectedChangedListener easyListItemSelectedChangedListener)
	{
		easyListItemSelectedChangedListeners.add(easyListItemSelectedChangedListener);
	}
}
