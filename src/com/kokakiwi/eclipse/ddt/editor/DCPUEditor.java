package com.kokakiwi.eclipse.ddt.editor;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.ui.editors.text.TextEditor;

public class DCPUEditor extends TextEditor
{
    
    public DCPUEditor()
    {
        super();
        
    }
    
    @Override
    protected void initializeEditor()
    {
        super.initializeEditor();
        setSourceViewerConfiguration(new DCPUSourceViewerConfiguration(this));
    }
    
    /**
     * Refreshs the editor.
     */
    public void refreshSourceViewer()
    {
        ISourceViewer isv = getSourceViewer();
        if (isv instanceof SourceViewer)
        {
            ((SourceViewer) getSourceViewer()).refresh();
        }
    }
    
}
