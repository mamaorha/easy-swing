package co.il.nmh.easy.swing.utils;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * @author Maor Hamami
 *
 */

public class DocumentUtils
{
	public static void supportUndoRedo(JComponent component, Document document)
	{
		UndoManager manager = new UndoManager();

		// Listen for undo and redo events
		document.addUndoableEditListener(new UndoableEditListener()
		{
			@Override
			public void undoableEditHappened(UndoableEditEvent evt)
			{
				manager.addEdit(evt.getEdit());
			}
		});

		// Create an undo action and add it to the text component
		component.getActionMap().put("Undo", new AbstractAction("Undo")
		{
			private static final long serialVersionUID = -4718612889300257184L;

			@Override
			public void actionPerformed(ActionEvent evt)
			{
				try
				{
					if (manager.canUndo())
					{
						manager.undo();
					}
				}
				catch (CannotUndoException e)
				{
				}
			}
		});

		// Bind the undo action to ctl-Z
		component.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");

		// Create a redo action and add it to the text component
		component.getActionMap().put("Redo", new AbstractAction("Redo")
		{
			private static final long serialVersionUID = -1275508515609712838L;

			@Override
			public void actionPerformed(ActionEvent evt)
			{
				try
				{
					if (manager.canRedo())
					{
						manager.redo();
					}
				}
				catch (CannotRedoException e)
				{
				}
			}
		});

		// Bind the redo action to ctl-Y
		component.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
	}
}
