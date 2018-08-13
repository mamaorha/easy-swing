package co.il.nmh.easy.swing.components.table.listeners;

import java.util.Map;

import javax.swing.JButton;

/**
 * @author Maor Hamami
 *
 */

public interface EasyTableBeforeButtonsRender
{
	void beforeButtonsRender(String columnName, Object value, Map<String, JButton> buttonMap);
}
