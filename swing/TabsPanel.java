package swing;

import main.EditorFrame;
import settings.Colors;
import settings.Fonts;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

public class TabsPanel extends Canvas {
	@Override
	public int getWidth() {
		return EditorFrame.getInstance().getWidth();
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(getWidth(), getHeight());
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(getWidth(), getHeight());
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), getHeight());
	}

	@Override
	public int getHeight() {
		return 22;
	}

	static Vector<Tab> tabs = new Vector<>();
	private static Color back = Colors.MAIN_BACKGROUND_COLOR;

	public static void open(File f) {
		add(new Tab(f));
	}

	public static void setBack(Color c) {
		back = c;
	}

	public static Color getBack() {
		return back;
	}

	static void add(Tab t) {
		tabs.add(t);
	}

	@Override
	public void paint(Graphics g) {
		//System.out.println("test");
		g.setColor(getBack());
		g.fillRect(0, 0, getWidth(), getHeight());
		int size = 0;
		for (Tab t : tabs) {
			System.out.println(t.getWidth() + " " + t.getHeight());
			BufferedImage bufferedImage = new BufferedImage(t.getWidth(), t.getHeight(), BufferedImage.TYPE_INT_RGB);
			t.paint(bufferedImage.getGraphics());
			g.drawImage(bufferedImage, size, 0, null);
			size += t.getWidth();
		}
	}

	private static class Tab extends Canvas {
		private Dimension size = new Dimension();

		public Color getBoundsColor() {
			return boundsColor;
		}

		public void setBoundsColor(Color boundsColor) {
			this.boundsColor = boundsColor;
		}

		private Color boundsColor = new Color(75, 75, 75);
		private File name = null;
		private Color textColor = new Color(187, 187, 187);

		@Override
		public Dimension getPreferredSize() {
			return size;
		}

		@Override
		public Dimension getMinimumSize() {
			return size;
		}

		@Override
		public Dimension getMaximumSize() {
			return size;
		}

		public Color getTextColor() {
			return textColor;
		}

		public void setTextColor(Color textColor) {
			this.textColor = textColor;
		}

		@Override
		public int getWidth() {
			return (int) size.getWidth();
		}

		@Override
		public int getHeight() {
			return (int) size.getHeight();
		}

		Tab(File name) {
			this.name = name;
			TextLayout tl = new TextLayout(name.getName(), Fonts.TEXT_FONT, new FontRenderContext(null, true, true));
			size = new Dimension((int) ((tl.getBounds().getWidth()) + 9 + 26), (int) (tl.getBounds().getHeight() + 5 + 5));
		}

		@Override
		public void paint(Graphics gr) {
			//System.out.println("test");
			Graphics2D g = (Graphics2D) gr;
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			//System.out.println(g.getClipBounds().x+" "+g.getClipBounds().y+" "+g.getClipBounds().width+" "+g.getClipBounds().height);
			g.setColor(EditorFrame.getTabsPanel().getBack());
			g.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
			g.setColor(getTextColor());
			g.setFont(Fonts.TEXT_FONT);
			g.drawString(name.getName(), 9, (int) (new TextLayout(name.getName(), Fonts.TEXT_FONT, new FontRenderContext(null, true, true)).getBounds().getHeight()+5));
			g.setColor(boundsColor);
			g.setStroke(new BasicStroke(1));
			gr.drawLine(0, 0, (int) size.getWidth(), 0);
			g.drawLine(0, 0, 0, (int) size.getHeight());
			//g.setColor(boundsColor);
			g.drawLine((int) size.getWidth() - 1, 1, (int) size.getWidth() - 1, (int) size.getHeight() - 1);
			g.drawLine(1, (int) size.getHeight() - 1, (int) size.getWidth() - 1, (int) size.getHeight() - 1);
		}
	}
}
