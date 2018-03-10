import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Editor extends JTextPane {
	private boolean stop = false;

	public Editor() {
		setBackground(Colors.MAIN_BACKGROUND_COLOR);
		setForeground(Colors.MAIN_FOREGROUND_COLOR);
		setFont(new Font("Monospaced", Font.PLAIN, 12));
		setCaretColor(new Color(187, 187, 187));
		new Thread(new Parser()).start();
	}

	private class Parser implements Runnable {
		/*public void parse(String s, Color color) {
			StyledDocument sd = getStyledDocument();
			StringBuilder text = null;
			try {
				text = new StringBuilder(sd.getText(0, sd.getLength()).toLowerCase());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			int a = 0, b = 0;
			while ((a = text.indexOf(s)) != -1) {
				SimpleAttributeSet sas = new SimpleAttributeSet();
				StyleConstants.setForeground(sas, color);
				sd.setCharacterAttributes(a + b * s.length(), s.length(), sas, false);
				//System.out.println(String.valueOf(a-b*s.length())+ " "+String.valueOf(a-b*s.length()+s.length())+" "+text);
				text.delete(a, a + s.length());
				b++;
//                System.out.println(text);

			}
		}*/
		public void parse/*Patten*/(String pattern, Color color){
			StyledDocument sd = getStyledDocument();
			StringBuilder text = null;
			try {
				text = new StringBuilder(sd.getText(0, sd.getLength()).toLowerCase());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			Matcher m = null;
			m = Pattern.compile(pattern).matcher(text);
			while (m.find()) {
				//System.out.println(text+ " " +m.start()+ " "+m.end());
				SimpleAttributeSet sas = new SimpleAttributeSet();
				StyleConstants.setForeground(sas, color);
				sd.setCharacterAttributes(m.start(), m.end()-m.start(), sas, false);
			}
		}

		@Override
		public void run() {
			while (!stop) {
//                System.out.println("debug mes");
				StyledDocument sd = getStyledDocument();
				SimpleAttributeSet sas = new SimpleAttributeSet();
				StyleConstants.setForeground(sas, Colors.MAIN_FOREGROUND_COLOR);
				sd.setCharacterAttributes(0, sd.getLength(), sas, false);

				for (String a : Colors.PARSE_TABLE) {
					parse((String) a, Colors.COMMANDS_COLOR);
				}
				parse("//[a-z0-9]+\n",Colors.COMMENT_COLOR);
				sd.setCharacterAttributes(0, 0, sas, false);
				//parse(" a ", new Color(204, 120, 50));
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void stop() {
		stop = true;
	}
}
