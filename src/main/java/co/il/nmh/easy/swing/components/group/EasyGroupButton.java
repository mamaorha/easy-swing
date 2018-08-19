package co.il.nmh.easy.swing.components.group;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JButton;

import co.il.nmh.easy.swing.components.group.listeners.IGroupComponent;

/**
 * @author Maor Hamami
 */

public class EasyGroupButton extends JButton implements IGroupComponent
{
	private static final long serialVersionUID = 9169111082409413368L;

	protected EasyGroup easyGroup;
	protected boolean mouseHover;
	protected boolean active;
	protected Color hoverColor;
	protected Color pressedColor;
	protected Color secondColor;

	public EasyGroupButton(EasyGroup easyGroup, Icon icon)
	{
		this(easyGroup, icon, Color.decode("#ffb266"), Color.decode("#ff8000"), Color.WHITE);
	}

	public EasyGroupButton(EasyGroup easyGroup, Icon icon, Color hoverColor, Color pressedColor, Color secondColor)
	{
		super(icon);

		setBorder(null);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setPreferredSize(new Dimension(50, 50));
		setFocusPainted(false);

		this.easyGroup = easyGroup;
		this.hoverColor = hoverColor;
		this.pressedColor = pressedColor;
		this.secondColor = secondColor;

		addEvents();
	}

	private void addEvents()
	{
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent evt)
			{
				mouseHover = true;
			}

			@Override
			public void mouseExited(MouseEvent evt)
			{
				mouseHover = false;
			}
		});

		easyGroup.register(this);

		addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				easyGroup.pressed(EasyGroupButton.this);
			}
		});
	}

	@Override
	public void setActive(boolean active)
	{
		this.active = active;

		if (active)
		{
			easyGroup.pressed(this);
		}

		repaint();
	}

	@Override
	public boolean isActive()
	{
		return active;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		if (isEnabled() && (mouseHover || active))
		{
			Color color = hoverColor;

			if (active)
			{
				color = pressedColor;
			}

			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(new GradientPaint(new Point(0, 0), color, new Point(0, getHeight()), secondColor));
			g2.fillRect(0, 0, getWidth(), getHeight());
		}

		super.paintComponent(g);
	}

	public Color getHoverColor()
	{
		return hoverColor;
	}

	public void setHoverColor(Color hover)
	{
		this.hoverColor = hover;
	}

	public Color getPressedColor()
	{
		return pressedColor;
	}

	public void setPressedColor(Color pressed)
	{
		this.pressedColor = pressed;
	}

	public Color getSecondColor()
	{
		return secondColor;
	}

	public void setSecondColor(Color second)
	{
		this.secondColor = second;
	}
}
