package co.il.nmh.easy.swing.components.group;

import java.util.HashSet;
import java.util.Set;

import co.il.nmh.easy.swing.components.group.listeners.IGroupComponent;

/**
 * @author Maor Hamami
 */

public class EasyGroup
{
	protected Set<IGroupComponent> groupComponents;
	protected IGroupComponent active;

	public EasyGroup()
	{
		groupComponents = new HashSet<>();
	}

	public void register(IGroupComponent groupComponent)
	{
		groupComponents.add(groupComponent);
	}

	public void pressed(IGroupComponent groupComponent)
	{
		if (groupComponent == active)
		{
			return;
		}

		active = groupComponent;

		for (IGroupComponent curr : groupComponents)
		{
			curr.setActive(curr == active);
		}
	}
}
