import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.lang.invoke.ConstantCallSite;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Editor extends JTextPane{
    Object[][] parseTable = new Object[][]{
            {"aaa ",new Color(187, 187, 187)},
            {"bbb ",new Color(187, 187, 187)}
    };
    private boolean stop = false;
    public Editor(){
        setBackground(new Color(0x292929));
        setForeground(new Color(187, 187, 187));
        setFont(new Font("Monospaced",Font.PLAIN,12));
        setCaretColor(new Color(187, 187, 187));
        new Thread(new Parser()).start();
    }
    private class Parser implements Runnable{
        public void parse(String s, Color color){
            StyledDocument sd = getStyledDocument();
            StringBuilder text = null;
            try {
                text = new StringBuilder(sd.getText(0,sd.getLength()));
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            int a = 0,b=0;
            while((a=text.indexOf(s))!=-1) {
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setForeground(sas, color);
                sd.setCharacterAttributes(a+b*s.length(), s.length(), sas, false);
                //System.out.println(String.valueOf(a-b*s.length())+ " "+String.valueOf(a-b*s.length()+s.length())+" "+text);
                text.delete(a,a+s.length());
                b++;
//                System.out.println(text);
            }

        }
        @Override
        public void run() {
            while (!stop){
//                System.out.println("debug mes");
                StyledDocument sd = getStyledDocument();
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setForeground(sas,new Color(187, 187, 187));
                sd.setCharacterAttributes(0,sd.getLength(),sas,false );
                parse(" a ",new Color(204, 120, 50));
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
