package com.kokakiwi.eclipse.ddt.editor;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * Default RuleBaseScanner.
 * 
 * @author Daniel Mitte
 * @since 13.02.2006
 */
public class PropertyChangeRuleBaseScanner extends RuleBasedScanner implements
        IPropertyChangeListener
{
    
    /** Default Token for the text attributes * */
    private Token      defToken;
    
    /** Editor need for refresh * */
    private DCPUEditor editor;
    
    /** Key for preference store * */
    private String     preferencesKey;
    
    /**
     * Constructor of PropertyChangeRuleBaseScanner
     * 
     * @param editor
     *            The given Editor.
     * @param preferencesKey
     *            The preference key to be listen on.
     */
    public PropertyChangeRuleBaseScanner(final DCPUEditor editor,
            final String preferencesKey)
    {
        this.editor = editor;
        this.preferencesKey = preferencesKey;
        
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        
        defToken = new Token(
                TextAttributeConverter.preferenceDataToTextAttribute(store
                        .getString(preferencesKey)));
        
        super.setDefaultReturnToken(defToken);
        
        Activator.getDefault().getPreferenceStore()
                .addPropertyChangeListener(this);
    }
    
    /**
     * Remove rule scanner from property listener.
     */
    public void dispose()
    {
        Activator.getDefault().getPreferenceStore()
                .removePropertyChangeListener(this);
    }
    
    /**
     * {@inheritDoc}
     */
    public void propertyChange(PropertyChangeEvent event)
    {
        if (event.getProperty().equals(preferencesKey))
        {
            defToken.setData(TextAttributeConverter
                    .preferenceDataToTextAttribute((String) event.getNewValue()));
        }
        
        editor.refreshSourceViewer();
    }
}
