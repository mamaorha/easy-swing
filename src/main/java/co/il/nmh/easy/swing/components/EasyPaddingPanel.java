package co.il.nmh.easy.swing.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Maor Hamami
 */

public class EasyPaddingPanel extends JPanel
{
	private static final long serialVersionUID = -7410007856154653454L;

	public EasyPaddingPanel(JComponent component, int top, int left, int bottom, int right)
	{
		super(new GridBagLayout());

		setBorder(new EmptyBorder(top, left, bottom, right));

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.weightx = 1;

		add(component, gridBagConstraints);
	}
}
