package co.il.nmh.easy.swing.components.table.panel;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EasyTableButtonPanel extends JPanel
{
	private static final long serialVersionUID = 5978268904411530016L;

	protected Map<String, JButton> buttonMap;

	public EasyTableButtonPanel(Set<String> buttons)
	{
		buttonMap = new LinkedHashMap<>();

		setOpaque(true);

		for (String button : buttons)
		{
			JButton jbutton = new JButton(button);
			jbutton.setFocusable(false);
			jbutton.setRolloverEnabled(false);

			buttonMap.put(button, jbutton);
		}

		for (JButton jbutton : buttonMap.values())
		{
			add(jbutton);
		}
	}

	public Map<String, JButton> getButtonMap()
	{
		return buttonMap;
	}
}