package co.il.nmh.easy.swing.components.table.data;

/**
 * @author Maor Hamami
 *
 */

public enum RowEvent
{
	INSERT(1), UPDATE(0), DELETE(-1);

	private int value;

	RowEvent(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	public static RowEvent forValue(int type)
	{
		switch (type)
		{
			case 1:
				return INSERT;

			case 0:
				return UPDATE;

			case -1:
				return DELETE;

			default:
				return null;
		}
	}
}
