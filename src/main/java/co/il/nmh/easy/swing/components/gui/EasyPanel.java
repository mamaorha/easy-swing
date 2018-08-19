package co.il.nmh.easy.swing.components.gui;

import javax.swing.JPanel;

/**
 * @author Maor Hamami
 */

public abstract class EasyPanel extends JPanel
{
	private static final long serialVersionUID = 3962100742151571604L;

	public EasyPanel(Object... params)
	{
		init(params);
		buildPanel();
		addEvents();
	}

	protected void init(Object[] params)
	{
	}

	protected abstract void buildPanel();

	protected void addEvents()
	{
	}
}
