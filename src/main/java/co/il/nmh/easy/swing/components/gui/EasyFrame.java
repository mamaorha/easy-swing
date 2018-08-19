package co.il.nmh.easy.swing.components.gui;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * @author Maor Hamami
 */

public abstract class EasyFrame extends JFrame
{
	private static final long serialVersionUID = 2294561558741678693L;

	public EasyFrame(String title, double minHeightDivider, double minWidthDivider)
	{
		setTitle(title);

		buildPanel();
		addEvents();

		pack();
		setVisible(true);

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int height = gd.getDisplayMode().getHeight();
		height -= height / minHeightDivider;

		setMinimumSize(new Dimension((int) (gd.getDisplayMode().getWidth() / minWidthDivider), height));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				close();
			}
		});
	}

	protected abstract void buildPanel();

	protected abstract void addEvents();

	public void close()
	{
		System.exit(0);
	}
}
