package co.il.nmh.easy.swing.components.table.listeners;

/**
 * @author Maor Hamami
 *
 */

public interface EasyTableElementListener
{
	void elementRemoved(int index, Object element);

	void elementAdd(int index, Object element);
}
