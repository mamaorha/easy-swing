package co.il.nmh.easy.swing.components.list.listeners;

/**
 * @author Maor Hamami
 */

public interface EasyListElementListener
{
	void elementRemoved(int index, String element);

	void elementAdd(int index, String element);
}
