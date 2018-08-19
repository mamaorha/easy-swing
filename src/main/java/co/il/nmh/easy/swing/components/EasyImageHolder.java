package co.il.nmh.easy.swing.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;

import co.il.nmh.easy.swing.components.gui.EasyPanel;
import co.il.nmh.easy.utils.ImageUtils;

/**
 * @author Maor Hamami
 */

public class EasyImageHolder extends EasyPanel
{
	private static final long serialVersionUID = 6713439460893872059L;

	protected BufferedImage bufferedImage;
	protected Rectangle imageRect;
	protected double maxRatio;
	protected int shiftX;
	protected int shiftY;

	@Override
	protected void buildPanel()
	{
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (null != bufferedImage)
		{
			int clientWidth = bufferedImage.getWidth();
			int clientHeight = bufferedImage.getHeight();

			double widthRatio = (double) clientWidth / this.getWidth();
			double heightRatio = (double) clientHeight / this.getHeight();

			maxRatio = Math.max(widthRatio, heightRatio);

			if (maxRatio > 1)
			{
				clientWidth /= maxRatio;
				clientHeight /= maxRatio;
			}

			shiftX = (this.getWidth() - clientWidth) / 2;
			shiftY = (this.getHeight() - clientHeight) / 2;

			Image scaledImage = ImageUtils.scale(bufferedImage, clientWidth, clientHeight);

			imageRect = new Rectangle(shiftX, shiftY, clientWidth, clientHeight);
			g.drawImage(scaledImage, shiftX, shiftY, clientWidth, clientHeight, this);

			additionalPainting(g);
		}
	}

	protected void additionalPainting(Graphics g)
	{
	}

	public void setBufferedImage(BufferedImage bufferedImage)
	{
		this.bufferedImage = bufferedImage;
		repaint();
	}

	public BufferedImage getBufferImage()
	{
		return bufferedImage;
	}
}
