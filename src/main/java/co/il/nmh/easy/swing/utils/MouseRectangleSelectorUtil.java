package co.il.nmh.easy.swing.utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import co.il.nmh.easy.swing.listeners.MouseDragListener;

/**
 * @author Maor Hamami
 */

public abstract class MouseRectangleSelectorUtil
{
	protected Point origin;
	protected Rectangle areaRect;

	public MouseRectangleSelectorUtil(JComponent component)
	{
		component.addMouseListener(new MouseDragListener()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					origin = new Point(e.getX(), e.getY());
				}

				else if (e.getButton() == MouseEvent.BUTTON3)
				{
					origin = null;
					areaRect = null;

					cancelSelection();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				origin = null;

				if (null != areaRect)
				{
					approveSelection(areaRect);
					areaRect = null;
				}
			}
		});

		component.addMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				if (null != origin)
				{
					int x = Math.min(origin.x, e.getX());
					int y = Math.min(origin.y, e.getY());

					int width = Math.abs(origin.x - e.getX());
					int height = Math.abs(origin.y - e.getY());

					areaRect = new Rectangle(x, y, width, height);
					areaRect = selectionChanged(areaRect);
				}
			}
		});
	}

	protected abstract void cancelSelection();

	protected abstract void approveSelection(Rectangle areaRect);

	protected abstract Rectangle selectionChanged(Rectangle areaRect);
}
