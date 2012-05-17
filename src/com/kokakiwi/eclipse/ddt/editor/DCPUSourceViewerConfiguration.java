package com.kokakiwi.eclipse.ddt.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class DCPUSourceViewerConfiguration extends SourceViewerConfiguration
{
    private final DCPUEditor                editor;
    
    private DCPUCodeScanner                 codeScanner;
    
    public DCPUSourceViewerConfiguration(DCPUEditor editor)
    {
        this.editor = editor;
    }
    
    @Override
    public IPresentationReconciler getPresentationReconciler(
            ISourceViewer sourceViewer)
    {
        PresentationReconciler reconciler = new PresentationReconciler();
        
        codeScanner = new DCPUCodeScanner(editor);
        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(codeScanner);
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
        
        return reconciler;
    }
}
