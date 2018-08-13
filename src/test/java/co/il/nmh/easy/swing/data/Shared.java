package co.il.nmh.easy.swing.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * @author Maor Hamami
 *
 */

@Data
@Getter
public class Shared
{
	private int active = 0;
	private List<String> names = new ArrayList<>();
	private List<String> lastNames = new ArrayList<>();

	public void add(String name, String lastName)
	{
		names.add(name);
		lastNames.add(lastName);
	}

}
