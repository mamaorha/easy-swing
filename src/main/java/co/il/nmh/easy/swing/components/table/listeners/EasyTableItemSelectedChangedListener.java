package co.il.nmh.easy.swing.components.table.listeners;

/**
 * @author Maor Hamami
 *
 */

public interface EasyTableItemSelectedChangedListener
{
	void selectionClear();

	void selected(int[] selectedRows);
}
