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

	public EasyScrollPane(Component component)
	{
		super(component);
	}

	public EasyScrollPane(EasyTable easyTable)
	{
		super(easyTable.getTable());
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(50, 50);
	}
}
