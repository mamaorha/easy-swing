package co.il.nmh.easy.swing.components.table.data;

/**
 * @author Maor Hamami
 *
 */

public enum RowSelectionMode
{
	SINGLE_SELECTION(0), SINGLE_INTERVAL_SELECTION(1), MULTIPLE_INTERVAL_SELECTION(2);

	private int value;

	RowSelectionMode(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
}
