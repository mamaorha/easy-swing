package co.il.nmh.easy.swing.components.text;

import javax.swing.JPasswordField;

/**
 * @author Maor Hamami
 */

public class EasyPasswordField extends JPasswordField
{
	private static final long serialVersionUID = 4605691285023537547L;

	public EasyPasswordField(int size)
	{
		super(size);
	}

	@Override
	public String getText()
	{
		return new String(getPassword());
	}
}
