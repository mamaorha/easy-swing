package co.il.nmh.easy.swing.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;

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

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(50, 50);
	}
}
