package co.il.nmh.easy.swing.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class DocumentChangedListener implements DocumentListener
{
	public abstract void textChanged(DocumentEvent documentEvent);

	@Override
	public void changedUpdate(DocumentEvent documentEvent)
	{
		textChanged(documentEvent);
	}

	@Override
	public void insertUpdate(DocumentEvent documentEvent)
	{
		textChanged(documentEvent);
	}

	@Override
	public void removeUpdate(DocumentEvent documentEvent)
	{
		textChanged(documentEvent);
	}
}