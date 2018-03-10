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

	private static class ParsingExpressions {
		public static final String[] REGISTERS = new String[]{
				"AH", "AL", "AX", "EAX", "BL", "BH", "BX", "EBX", "CL", "CH", "CX", "ECH", "DL", "DH", "DX", "EDX",
				"SI", "DI", "ESI", "EDI", "BP", "EBP",
				"SP", "ESP", "IP", "EIP",
				"CS", "DS", "SS", "ES", "FS", "GS"
		};
		public static final String[] PARSE_TABLE = new String[]{
				"adc",
				"add",
				"and",
				"bsf",
				"bsr",
				"bt",
				"btc",
				"btr",
				"bts",
				"call",
				"cbw",
				"cld",
				"clc",
				"cli",
				"cmc",
				"cdq",
				"cmp",
				"cwd",
				"cwde",
				"dec",
				"div",
				"idiv",
				"enter",
				"imul",
				"inc",
				"jmp",
				"lahe",
				"leave",
				"loop",
				"loopnz",
				"loopne",
				"loopz",
				"loope",
				"mov",
				"movsx",
				"movzx",
				"mul",
				"neg",
				"nop",
				"not",
				"or",
				"pop",
				"popa",
				"popad",
				"popf",
				"popfd",
				"push",
				"pusha",
				"pushad",
				"pushf",
				"pushfd",
				"rcl",
				"rcr",
				"ret",
				"retn",
				"rete",
				"rol",
				"ror",
				"sahe",
				"sal",
				"sar",
				"sbb",
				"shl",
				"shld",
				"shr",
				"shrd",
				"stc",
				"std",
				"sti",
				"sub",
				"test",
				"xchg",
				"xor",
		};
	}

	private class Parser implements Runnable {
		public static final boolean MUST_HAVE_NON_CHAR_SMB = true;
		public final boolean MUST_HAVE_CHAR_SMB = false;

		public void parse/*Patten*/(String pattern, Color color, boolean flag) {
			StyledDocument sd = getStyledDocument();
			StringBuilder text = null;
			try {
				text = new StringBuilder(sd.getText(0, sd.getLength()).toLowerCase());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			Matcher m = null;
			if (flag) {
				m = Pattern.compile("[^a-zA-Z]"+pattern+"[^a-zA-Z]", Pattern.CASE_INSENSITIVE).matcher(text);
			} else {
				m = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(text);
			}
			while (m.find()) {
				//System.out.println(text+ " " +m.start()+ " "+m.end());
				SimpleAttributeSet sas = new SimpleAttributeSet();
				StyleConstants.setForeground(sas, color);
				if (flag) {
					sd.setCharacterAttributes(m.start() + 1, m.end() - m.start() + 1, sas, false);
				} else {
					sd.setCharacterAttributes(m.start(), m.end() - m.start(), sas, false);
				}
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

				for (String a : ParsingExpressions.PARSE_TABLE) {
					parse((String) a, Colors.COMMANDS_COLOR,MUST_HAVE_NON_CHAR_SMB);
				}
				parse("//[a-z0-9]+\n", Colors.COMMENT_COLOR,MUST_HAVE_CHAR_SMB);
				for (String s : ParsingExpressions.REGISTERS) {
					parse(s, Colors.REGISTER_COLOR,MUST_HAVE_NON_CHAR_SMB);
				}
				parse(",", Colors.ZPT_COLOR,MUST_HAVE_NON_CHAR_SMB);
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
