package co.il.nmh.easy.swing.components.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import co.il.nmh.easy.swing.components.EasyScrollPane;
import co.il.nmh.easy.swing.components.table.data.ComboBoxData;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboBoxRender;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboChangedListener;
import co.il.nmh.easy.swing.data.Shared;

/**
 * @author Maor Hamami
 *
 */

public class EasyTableTest2
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setLayout(new GridBagLayout());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int height = gd.getDisplayMode().getHeight();
		height -= height / 2;

		frame.setMinimumSize(new Dimension(gd.getDisplayMode().getWidth() / 3, height));
		frame.setLocationRelativeTo(null);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridy++;

		EasyTable easyTable = buildTable();

		Shared a = new Shared();
		a.add("maor", "hamami");
		a.add("Test", "Tester");

		easyTable.add(a);

		frame.add(new EasyScrollPane(easyTable.getTable()), gbc);

		frame.pack();
		frame.setVisible(true);
	}

	public static EasyTable buildTable()
	{
		EasyTable easyTable = new EasyTable(new String[] { "name", "lastName" })
		{
			@Override
			public Component renderObject(String columnName, Object value)
			{
				return buildLabel(value.toString());
			}
		};

		addComboBox(easyTable);

		return easyTable;
	}

	private static void addComboBox(EasyTable easyTable)
	{
		EasyTableComboBoxRender easyTableComboBoxRender = new EasyTableComboBoxRender()
		{
			@Override
			public ComboBoxData getComboBoxData(String columnName, Object value)
			{
				ComboBoxData comboBoxData = new ComboBoxData();

				if (value instanceof Shared)
				{
					Shared shared = (Shared) value;

					if ("name".equals(columnName))
					{
						comboBoxData.setItems(shared.getNames());
					}

					else
					{
						comboBoxData.setItems(shared.getLastNames());
					}

					comboBoxData.setSelectedIndex(shared.getActive());
				}

				return comboBoxData;
			}
		};

		easyTable.addComboboxListener(new EasyTableComboChangedListener()
		{
			@Override
			public void comboChanged(Object value, String columnName, int row, int col, int index)
			{
				if (value instanceof Shared)
				{
					Shared shared = (Shared) value;

					shared.setActive(index);
				}
			}
		});

		easyTable.setComboColumn("name", easyTableComboBoxRender);
		easyTable.setComboColumn("lastName", easyTableComboBoxRender);
	}

}
