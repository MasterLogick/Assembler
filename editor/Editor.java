package editor;

import settings.Colors;
import settings.Fonts;
import swing.TabsPanel;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Editor extends JTextPane {
	private static File current = null;
	private static HashMap<String, String> pages = new HashMap<>();

	public Editor() {
		setBackground(Colors.MAIN_BACKGROUND_COLOR);
		setForeground(Colors.MAIN_FOREGROUND_COLOR);
		setFont(Fonts.MAIN_FONT);
		setCaretColor(Colors.CURSOR_COLOR);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Parser.parse();
			}
		});
	}

	public void open(File f) {
		TabsPanel.open(f);
	}

	public void setFile(File f) {
		System.out.println("test");
		if (!TabsPanel.tabs.isEmpty()) {
			if (!pages.containsKey(f.toString())) {
				if (current != null) {
					pages.put(current.getAbsolutePath(), getText());
				}
				setText("");
				setEnabled(true);
				load(f);
			} else {
				setText(pages.get(f.toString()));

			}
			current = f;
		} else {
			//setText("");
			setEnabled(false);
		}
		Parser.parse();
		this.repaint();
	}

	private void load(File f) {
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String s;
			while ((s = bf.readLine()) != null) {
				StyledDocument sd = getStyledDocument();
				this.setText("");
				sd.insertString(sd.getLength(), s + '\n', new SimpleAttributeSet());
			}
			bf.close();
		} catch (IOException | BadLocationException e) {
			e.printStackTrace();
		}
		try {
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parser.parse();
		this.repaint();
	}

    public static void saveAll() {
		for (Map.Entry<String,String> f:pages.entrySet()) {
			File save = new File(f.getKey());
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(save);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			pw.print(f.getValue());
			pw.close();
		}
	}
}