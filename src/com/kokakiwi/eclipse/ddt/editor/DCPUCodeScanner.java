package com.kokakiwi.eclipse.ddt.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.RGB;

public class DCPUCodeScanner extends RuleBasedScanner
{
    private final DCPUEditor editor;
    
    private Token            opToken;
    private Token            regToken;
    private Token            valueToken;
    private Token            labelToken;
    private Token            comment;
    
    public DCPUCodeScanner(DCPUEditor editor)
    {
        this.editor = editor;
        
        List<IRule> rules = new ArrayList<IRule>();
        createTokens(editor.getSite().getShell().getDisplay());
        
        WordRuleCaseInsensitive wordRule = new WordRuleCaseInsensitive();
        
        wordRule.addWord("SET", opToken);
        wordRule.addWord("ADD", opToken);
        wordRule.addWord("SUB", opToken);
        wordRule.addWord("MUL", opToken);
        wordRule.addWord("MLI", opToken);
        wordRule.addWord("DIV", opToken);
        wordRule.addWord("DVI", opToken);
        wordRule.addWord("MOD", opToken);
        wordRule.addWord("MDI", opToken);
        wordRule.addWord("AND", opToken);
        wordRule.addWord("BOR", opToken);
        wordRule.addWord("XOR", opToken);
        wordRule.addWord("SHR", opToken);
        wordRule.addWord("ASR", opToken);
        wordRule.addWord("SHL", opToken);
        wordRule.addWord("IFB", opToken);
        wordRule.addWord("IFC", opToken);
        wordRule.addWord("IFE", opToken);
        wordRule.addWord("IFN", opToken);
        wordRule.addWord("IFG", opToken);
        wordRule.addWord("IFA", opToken);
        wordRule.addWord("IFL", opToken);
        wordRule.addWord("IFU", opToken);
        wordRule.addWord("ADX", opToken);
        wordRule.addWord("SBX", opToken);
        wordRule.addWord("STI", opToken);
        wordRule.addWord("STD", opToken);
        wordRule.addWord("JSR", opToken);
        wordRule.addWord("INT", opToken);
        wordRule.addWord("IAG", opToken);
        wordRule.addWord("IAS", opToken);
        wordRule.addWord("RFI", opToken);
        wordRule.addWord("IAQ", opToken);
        wordRule.addWord("HWN", opToken);
        wordRule.addWord("HWQ", opToken);
        wordRule.addWord("HWI", opToken);
        wordRule.addWord("DAT", opToken);
        
        wordRule.addWord("A", regToken);
        wordRule.addWord("B", regToken);
        wordRule.addWord("C", regToken);
        wordRule.addWord("X", regToken);
        wordRule.addWord("Y", regToken);
        wordRule.addWord("Z", regToken);
        wordRule.addWord("I", regToken);
        wordRule.addWord("J", regToken);
        wordRule.addWord("PC", regToken);
        wordRule.addWord("EX", regToken);
        wordRule.addWord("IA", regToken);
        wordRule.addWord("SP", regToken);
        wordRule.addWord("POP", regToken);
        wordRule.addWord("PUSH", regToken);
        wordRule.addWord("PEEK", regToken);
        
        wordRule.addWord("#^:[a-z0-9_-]+$", labelToken);
        
        wordRule.addWord("#^[0-9]+$", valueToken);
        wordRule.addWord("#^0x[0-9a-f]+$", valueToken);
        
        rules.add(wordRule);
        
        rules.add(new EndOfLineRule(";", comment));
        
        setRules(rules.toArray(new IRule[0]));
    }
    
    private void createTokens(Device device)
    {
        // IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        
        opToken = new Token(new TextAttribute(new Color(device, new RGB(
                128 + 64, 0, 0)), null, SWT.BOLD));
        regToken = new Token(new TextAttribute(new Color(device, new RGB(0,
                128, 0)), null, SWT.BOLD));
        valueToken = new Token(new TextAttribute(new Color(device, new RGB(0,
                0, 128)), null, SWT.BOLD));
        labelToken = new Token(new TextAttribute(new Color(device, new RGB(128,
                64, 32)), null, SWT.BOLD));
        comment = new Token(new TextAttribute(new Color(editor.getSite()
                .getShell().getDisplay(), new RGB(128, 128, 128))));
    }
}
