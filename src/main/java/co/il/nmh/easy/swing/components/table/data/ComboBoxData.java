package co.il.nmh.easy.swing.components.table.data;

import java.util.Collection;

import lombok.Data;

/**
 * @author Maor Hamami
 *
 */
@Data
public class ComboBoxData
{
	private int selectedIndex;
	private Collection<String> items;
}
