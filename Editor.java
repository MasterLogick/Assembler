import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.lang.invoke.ConstantCallSite;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Editor extends JTextPane{
    private boolean stop = false;
    public Editor(){
        setBackground(new Color(0x292929));
        setForeground(new Color(187, 187, 187));
        setFont(new Font("Monospaced",Font.PLAIN,12));
        new Thread(new Parser()).start();
    }
    private class Parser implements Runnable{
        public void parse(String s, Color color){
            StyledDocument sd = getStyledDocument();
            String text = null;
            try {
                text = sd.getText(0,sd.getLength());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            Pattern p = Pattern.compile(s);
            Matcher m = p.matcher(s);
            while (m.find()){
            SimpleAttributeSet sas = new SimpleAttributeSet();
            StyleConstants.setForeground(sas,color);
            sd.setCharacterAttributes(m.start(),m.start()+s.length(),sas,false );
            }
        }
        @Override
        public void run() {
            while (!stop){
                /*StyledDocument sd = getStyledDocument();
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setForeground(sas,new Color(204, 120, 50));
                sd.setCharacterAttributes(0,10,sas,false );*/
                parse(" aaa ",new Color(204, 120, 50));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void stop(){
        stop=true;
    }
}
