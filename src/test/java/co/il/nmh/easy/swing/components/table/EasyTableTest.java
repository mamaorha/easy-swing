package co.il.nmh.easy.swing.components.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

import co.il.nmh.easy.swing.components.EasyScrollPane;
import co.il.nmh.easy.swing.components.table.data.ComboBoxData;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableBeforeButtonsRender;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableButtonListener;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboBoxRender;
import co.il.nmh.easy.swing.components.table.listeners.EasyTableComboChangedListener;
import co.il.nmh.easy.swing.data.Person;

/**
 * @author Maor Hamami
 *
 */

public class EasyTableTest
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

		List<String> maorProfessions = new ArrayList<>();
		maorProfessions.add("software developer");
		maorProfessions.add("fire fighter");

		Person maor = new Person("maor", "hamami", 0, maorProfessions);
		easyTable.add(maor);

		List<String> testerProfessions = new ArrayList<>();
		testerProfessions.add("tester");
		testerProfessions.add("q.a");

		Person tester = new Person("Test", "Tester", 1, testerProfessions);
		easyTable.add(tester);

		frame.add(new EasyScrollPane(easyTable.getTable()), gbc);

		frame.pack();
		frame.setVisible(true);
	}

	public static EasyTable buildTable()
	{
		EasyTable easyTable = new EasyTable(new String[] { "name", "lastName", "profession", "action" })
		{
			@Override
			public Component renderObject(String columnName, Object value)
			{
				if (value instanceof Person)
				{
					Person person = (Person) value;

					if ("name".equals(columnName))
					{
						return buildLabel(person.getName());
					}

					else if ("lastName".equals(columnName))
					{
						return buildLabel(person.getLastName());
					}
				}

				return buildLabel(value.toString());
			}
		};

		addComboBox(easyTable);
		addButton(easyTable);

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

				if (value instanceof Person)
				{
					Person person = (Person) value;

					comboBoxData.setItems(person.getProfessions());
					comboBoxData.setSelectedIndex(person.getActiveProfession());
				}

				return comboBoxData;
			}
		};

		easyTable.addComboboxListener(new EasyTableComboChangedListener()
		{
			@Override
			public void comboChanged(Object value, String columnName, int row, int col, int index)
			{
				if (value instanceof Person)
				{
					Person person = (Person) value;
					person.setActiveProfession(index);
				}
			}
		});

		easyTable.setComboColumn("profession", easyTableComboBoxRender);
	}

	private static void addButton(EasyTable easyTable)
	{
		EasyTableBeforeButtonsRender easyTableBeforeButtonsRender = new EasyTableBeforeButtonsRender()
		{
			@Override
			public void beforeButtonsRender(String columnName, Object value, Map<String, JButton> buttonMap)
			{
				if (value instanceof Person)
				{
					Person person = (Person) value;

					if ("maor".equalsIgnoreCase(person.getName()) && "hamami".equalsIgnoreCase(person.getLastName()))
					{
						buttonMap.get("delete").setEnabled(false);
					}
				}
			}
		};

		easyTable.addButtonListener(new EasyTableButtonListener()
		{
			@Override
			public void buttonClicked(Object value, String columnName, int row, int col, String button)
			{
				easyTable.removeRow(row);
			}
		});

		easyTable.setButtonColumn("action", "delete", easyTableBeforeButtonsRender);
	}
}
