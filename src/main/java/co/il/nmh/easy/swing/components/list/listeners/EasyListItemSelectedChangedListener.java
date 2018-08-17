package co.il.nmh.easy.swing.components.list.listeners;

/**
 * @author Maor Hamami
 */

public interface EasyListItemSelectedChangedListener
{
	void selectionClear();

	void selected(int[] selectedIndices);
}
