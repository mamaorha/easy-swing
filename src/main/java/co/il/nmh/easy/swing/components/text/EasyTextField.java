package co.il.nmh.easy.swing.components.text;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

import co.il.nmh.easy.swing.components.text.listeners.TextChangedListener;
import co.il.nmh.easy.swing.listeners.DocumentChangedListener;
import co.il.nmh.easy.swing.utils.DocumentUtils;

/**
 * @author Maor Hamami
 *
 */

public class EasyTextField extends JTextField
{
	private static final long serialVersionUID = 7203044240404693846L;

	protected Set<TextChangedListener> textChangedListeners;

	public EasyTextField(int size)
	{
		super(size);

		textChangedListeners = new HashSet<>();

		addEvents();
	}

	private void addEvents()
	{
		getDocument().addDocumentListener(new DocumentChangedListener()
		{
			@Override
			public void textChanged(DocumentEvent paramDocumentEvent)
			{
				for (TextChangedListener textChangedListener : textChangedListeners)
				{
					textChangedListener.textChanged(getText());
				}
			}
		});

		DocumentUtils.supportUndoRedo(this, getDocument());
	}

	public void addTextChangedListener(TextChangedListener textChangedListener)
	{
		textChangedListeners.add(textChangedListener);

		textChangedListener.textChanged(getText());
	}

	public void clear()
	{
		setText("");
	}
}
