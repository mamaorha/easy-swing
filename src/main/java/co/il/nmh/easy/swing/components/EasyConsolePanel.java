package co.il.nmh.easy.swing.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import co.il.nmh.easy.swing.components.text.EasyTextPane;

public class EasyConsolePanel extends JPanel
{
	private static final long serialVersionUID = -4806731606850804771L;

	protected DateFormat dateFormat;
	protected JButton clearBtn;
	protected EasyTextPane consoleRtb;

	public EasyConsolePanel()
	{
		this(null);
	}

	public EasyConsolePanel(DateFormat dateFormat)
	{
		setLayout(new GridBagLayout());

		buildPanel();
		addEvents();

		if (null == dateFormat)
		{
			dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
		}

		this.dateFormat = dateFormat;
	}

	private void buildPanel()
	{
		consoleRtb = new EasyTextPane();
		consoleRtb.setEditable(false);

		clearBtn = new JButton("clear");

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy++;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(new EasyScrollPane(consoleRtb), gbc);

		gbc.gridy++;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		add(clearBtn, gbc);
	}

	private void addEvents()
	{
		consoleRtb.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				consoleRtb.getCaret().setVisible(true);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				consoleRtb.getCaret().setVisible(false);
			}
		});

		clearBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				consoleRtb.setText("");
			}
		});
	}

	public void clearConsole()
	{
		consoleRtb.setText("");
	}

	public void writeToConsole(String text)
	{
		writeToConsole(text, null);
	}

	public void writeToConsole(String text, Color color)
	{
		try
		{
			AttributeSet attributes = null;

			if (color != null)
			{
				StyleContext sc = StyleContext.getDefaultStyleContext();
				attributes = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
			}

			Document doc = consoleRtb.getStyledDocument();
			doc.insertString(doc.getLength(), wrapText(text) + "\n", attributes);

			consoleRtb.setCaretPosition(doc.getLength());
		}

		catch (Exception exc)
		{
		}
	}

	private String wrapText(String text)
	{
		String date = dateFormat.format(new Date());

		return date + " - " + text;
	}
}