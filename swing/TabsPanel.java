package swing;

import main.EditorFrame;
import settings.Colors;
import settings.Fonts;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

public class TabsPanel extends Canvas {
	public TabsPanel() {
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!tabs.isEmpty()) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						int x = e.getX() - EditorFrame.getTabsPanel().getX();
						int a = 0;
						System.out.println(x + " " + e.getX() + " " + getX());
						for (Tab t : tabs) {
							if (x > t.getWidth()) {
								x -= t.getWidth();
								a++;
							} else {
								break;
							}
						}
						if (a != tabs.size()) {
							if (x >= tabs.get(a).getWidth() - 17 && x <= tabs.get(a).getWidth() - 10) {
								tabs.remove(a);
								select(a - 1);
							} else {
								select(a);
							}
						}
					}
				}
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
	}

	private void setSelected(int tab) {
	}

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
		return new Tab(new File("Test|||yj___")).getHeight() - 1;
	}

	public static void select(int index) {
		if (!tabs.isEmpty()) {
			for (int i = 0; i < tabs.size(); i++) {
				tabs.get(i).setSelected(false);
			}
			if (index < tabs.size() && index >= 0) {
				tabs.get(index).setSelected(true);
				EditorFrame.getEditor().setFile(index);
			} else if (index < 0) {
				select(0);
			}
			EditorFrame.getTabsPanel().repaint();
		} else {
			EditorFrame.getEditor().setFile(index);
		}
	}

	public static Vector<Tab> tabs = new Vector<>();
	private static Color back = Colors.MAIN_BACKGROUND_COLOR;

	public static void open(File f) {
		add(new Tab(f));
		select(tabs.size() - 1);
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
		g.setColor(getBack());
		g.fillRect(0, 0, getWidth(), getHeight());
		int size = 0;
		for (Tab t : tabs) {
			BufferedImage bufferedImage = new BufferedImage(t.getWidth(), t.getHeight(), BufferedImage.TYPE_INT_RGB);
			t.paint(bufferedImage.getGraphics());
			g.drawImage(bufferedImage, size, 1, null);
			size += t.getWidth();
		}
		g.setColor(Colors.BOUNDS_COLOR);
		g.drawLine(0, 0, getWidth(), 0);
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

	@Override
	public void update(Graphics g) {
		g.setColor(getBack());
		g.fillRect(0, 1, getWidth(), getHeight()-2);
		int size = 0;
		for (Tab t : tabs) {
			BufferedImage bufferedImage = new BufferedImage(t.getWidth(), t.getHeight(), BufferedImage.TYPE_INT_RGB);
			t.paint(bufferedImage.getGraphics());
			g.drawImage(bufferedImage, size, 1, null);
			size += t.getWidth();
		}
	}

	public static class Tab extends Canvas {
		public File getFile() {
			return name;
		}

		boolean selected = false;

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		private Dimension size = new Dimension();

		public Color getBoundsColor() {
			return boundsColor;
		}

		public void setBoundsColor(Color boundsColor) {
			this.boundsColor = boundsColor;
		}

		private Color boundsColor = Colors.BOUNDS_COLOR;
		private File name = null;
		private Color textColor = Colors.TEXT_COLOR;

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
			size = new Dimension((int) ((tl.getBounds().getWidth()) + 9 + 26), (int) (tl.getBounds().getHeight() + 7 + 5));
		}

		@Override
		public void paint(Graphics gr) {
			Graphics2D g = (Graphics2D) gr;
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			if (selected)
				g.setColor(boundsColor);
			else
				g.setColor(EditorFrame.getTabsPanel().getBack());
			g.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
			g.setColor(getTextColor());
			g.setFont(Fonts.TEXT_FONT);
			g.drawString(name.getName(), 9, (int) (new TextLayout(name.getName(), Fonts.TEXT_FONT, new FontRenderContext(null, true, true)).getBounds().getHeight() + 7));
			if (!selected) {
				g.setColor(boundsColor);
				g.setStroke(new BasicStroke(1));
				g.drawLine((int) size.getWidth() - 1, 1, (int) size.getWidth() - 1, (int) size.getHeight() - 1);
			}
			g.setColor(Colors.CROSS_COLOR);
			g.drawLine(getWidth() - 10, getHeight() - 7, getWidth() - 17, getHeight() - 14);
			g.drawLine(getWidth() - 10, getHeight() - 14, getWidth() - 17, getHeight() - 7);
		}
	}
}
