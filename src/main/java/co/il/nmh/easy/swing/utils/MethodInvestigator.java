package co.il.nmh.easy.swing.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Maor Hamami
 */

public class MethodInvestigator
{
	public static final MethodInvestigator INSTANCE = new MethodInvestigator();

	private Map<Class<?>, Map<String, Method>> classMethodMap;

	private MethodInvestigator()
	{
		classMethodMap = new ConcurrentHashMap<>();
	}

	public Collection<Method> getClassMethods(Class<?> clazz)
	{
		return getClassMethodsMap(clazz).values();
	}

	public Map<String, Method> getClassMethodsMap(Class<?> clazz)
	{
		if (!classMethodMap.containsKey(clazz))
		{
			synchronized (classMethodMap)
			{
				if (!classMethodMap.containsKey(clazz))
				{
					classMethodMap.put(clazz, investigateClass(clazz));
				}
			}
		}

		return classMethodMap.get(clazz);
	}

	private Map<String, Method> investigateClass(Class<?> clazz)
	{
		Map<String, Method> methodsMap = new HashMap<>();

		Class<?> tempClazz = clazz;

		do
		{
			Method[] declaredMethods = tempClazz.getDeclaredMethods();

			for (Method method : declaredMethods)
			{
				if ((method.getModifiers() & Modifier.FINAL) != Modifier.FINAL && (method.getModifiers() & Modifier.STATIC) != Modifier.STATIC)
				{
					if (!methodsMap.containsKey(method.getName()))
					{
						methodsMap.put(method.getName(), method);
					}
				}
			}

			tempClazz = tempClazz.getSuperclass();

		} while (tempClazz != null);

		return methodsMap;
	}
}
