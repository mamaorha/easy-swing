package co.il.nmh.easy.swing.components.table.listeners;

import co.il.nmh.easy.swing.components.table.data.ComboBoxData;

/**
 * @author Maor Hamami
 *
 */

public interface EasyTableComboBoxRender
{
	ComboBoxData getComboBoxData(String columnName, Object value);
}
