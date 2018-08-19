package co.il.nmh.easy.swing.components.alert;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.il.nmh.easy.swing.components.text.EasyPasswordField;
import co.il.nmh.easy.swing.components.text.EasyTextField;

/**
 * @author Maor Hamami
 */

public class AlertInput
{
	public static String getPassword(String inputText, String title)
	{
		return getText(inputText, title, true);
	}

	public static String getText(String inputText, String title)
	{
		return getText(inputText, title, false);
	}

	private static String getText(String inputText, String title, boolean hideText)
	{
		String text;

		do
		{
			JTextField textField = getTextField(hideText);

			JPanel panel = new JPanel();
			panel.add(new JLabel(inputText));
			panel.add(textField);

			JOptionPane optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION)
			{
				private static final long serialVersionUID = -1820349582850134111L;

				@Override
				public void selectInitialValue()
				{
					textField.requestFocusInWindow();
				}
			};

			optionPane.createDialog(null, title).setVisible(true);

			text = textField.getText();
		} while (text != null && text.isEmpty());

		return text;
	}

	private static JTextField getTextField(boolean hideText)
	{
		if (hideText)
		{
			return new EasyPasswordField(10);
		}

		else
		{
			return new EasyTextField(10);
		}
	}
}
