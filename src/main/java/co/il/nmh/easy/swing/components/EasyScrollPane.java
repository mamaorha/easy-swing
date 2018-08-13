package co.il.nmh.easy.swing.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import co.il.nmh.easy.swing.components.table.EasyTable;

/**
 * @author Maor Hamami
 *
 */

public class EasyScrollPane extends JScrollPane
{
	private static final long serialVersionUID = 8252709255450716963L;

	protected Dimension dimension;

	public EasyScrollPane(EasyTable easyTable)
	{
		this(easyTable.getTable());
	}

	public EasyScrollPane(EasyTable easyTable, int minWidth, int minHeight)
	{
		this(easyTable.getTable(), minWidth, minHeight);
	}

	public EasyScrollPane(Component component)
	{
		this(component, 50, 50);
	}

	public EasyScrollPane(Component component, int width, int height)
	{
		super(component);

		dimension = new Dimension(width, height);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return dimension;
	}
}
