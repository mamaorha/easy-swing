package co.il.nmh.easy.swing.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Maor Hamami
 *
 */

@Data
@AllArgsConstructor
public class Person
{
	private String name;
	private String lastName;

	private int activeProfession;
	private List<String> professions;
}
