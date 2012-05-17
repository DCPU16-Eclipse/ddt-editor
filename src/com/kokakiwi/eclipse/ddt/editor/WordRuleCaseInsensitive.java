package com.kokakiwi.eclipse.ddt.editor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

/**
 * A special WordRule, which ingnores the case of a given word.
 * 
 * @author Andy Reek
 * @since 25.11.2005
 */
public class WordRuleCaseInsensitive extends WordRule
{
    
    /**
     * Buffer used for pattern detection
     */
    private StringBuffer fBuffer = new StringBuffer();
    
    /**
     * The constructor.
     */
    public WordRuleCaseInsensitive()
    {
        this(Token.UNDEFINED);
    }
    
    /**
     * Creates a rule which. If no token has been associated, the specified
     * default token will be returned.
     * 
     * @param defaultToken
     *            the default token to be returned on success if nothing else is
     *            specified, may not be <code>null</code>
     * 
     * @see #addWord(String, IToken)
     */
    public WordRuleCaseInsensitive(IToken defaultToken)
    {
        super(new IWordDetector() { // A dummy. WordDetector will be
                    // replaced a
                    // few rows below.
                    public boolean isWordPart(char c)
                    {
                        return false;
                    }
                    
                    public boolean isWordStart(char c)
                    {
                        return false;
                    }
                }, defaultToken);
        
        fDetector = new MyWordDetector();
    }
    
    /**
     * {@inheritDoc}
     */
    public IToken evaluate(ICharacterScanner scanner)
    {
        int c = 0;
        
        if (scanner.getColumn() > 0)
        {
            scanner.unread();
            c = scanner.read();
            if (!Character.isWhitespace((char) c) && c != ',' && c != '[' && c != ']')
            {
                return fDefaultToken;
            }
        }
        
        c = scanner.read();
        c = Character.toLowerCase((char) c);
        if (fDetector.isWordStart((char) c))
        {
            if (fColumn == UNDEFINED || (fColumn == scanner.getColumn() - 1))
            {
                
                fBuffer.setLength(0);
                do
                {
                    fBuffer.append((char) c);
                    c = scanner.read();
                    c = Character.toLowerCase((char) c);
                } while (c != ICharacterScanner.EOF
                        && fDetector.isWordPart((char) c) && c != ',' && c != '[' && c != ']');
                scanner.unread();
                
                IToken token = (IToken) fWords.get(fBuffer.toString());
                if (token != null)
                {
                    return token;
                }
                else
                {
                    token = check();
                    if (token != null)
                    {
                        return token;
                    }
                }
                
                if (fDefaultToken.isUndefined())
                {
                    unreadBuffer(scanner);
                }
                
                return fDefaultToken;
            }
        }
        
        scanner.unread();
        return Token.UNDEFINED;
    }
    
    protected IToken check()
    {
        IToken token = null;
        
        for (Object key : fWords.keySet())
        {
            if (token == null)
            {
                String n = key.toString();
                
                if (n.charAt(0) == '#')
                {
                    Matcher matcher = Pattern.compile(n.substring(1)).matcher(
                            fBuffer);
                    if (matcher.find())
                    {
                        token = (IToken) fWords.get(key);
                    }
                }
            }
        }
        
        return token;
    }
    
    /**
     * {@inheritDoc}
     */
    public void addWord(String word, IToken token)
    {
        super.addWord(word.toLowerCase(), token);
    }
    
    /**
     * Returns the characters in the buffer to the scanner.
     * 
     * @param scanner
     *            the scanner to be used
     */
    protected void unreadBuffer(ICharacterScanner scanner)
    {
        for (int i = fBuffer.length() - 1; i > -1; i--)
        {
            scanner.unread();
        }
    }
    
    /**
     * A WordDetector, which recognizes all typable characters.
     * 
     * @author andre
     * 
     */
    private class MyWordDetector implements IWordDetector
    {
        /**
         * {@inheritDoc}
         */
        public boolean isWordStart(char c)
        {
            return ((c > ' ') && (c <= '~'));
        }
        
        /**
         * {@inheritDoc}
         */
        public boolean isWordPart(char c)
        {
            return ((c > ' ') && (c <= '~'));
        }
    }
}
