import settings.Fonts;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

public class TabsPanel extends Canvas {
    static Vector<Tab> tabs = new Vector<>();
    public static void open(File f){
        add(new Tab(f.getName()));
        Editor.open(f);
        EditorFrame.getInstance().repaint();
    }
    static void add(Tab t){
        tabs.add(t);
    }
    @Override
    public void paint(Graphics g) {
        int size = 0;
        for (Tab t : tabs) {
            System.out.println(t.getWidth()+" "+ t.getHeight());
            BufferedImage bufferedImage = new BufferedImage(t.getWidth(), t.getHeight(), BufferedImage.TYPE_INT_RGB);
            t.paint(bufferedImage.getGraphics());
            g.drawImage(bufferedImage, size, 0, null);
            size += t.getWidth();
        }
    }

    static class Tab extends Canvas{
        private Dimension size = new Dimension();

        public Color getBoundsColor() {
            return boundsColor;
        }

        public void setBoundsColor(Color boundsColor) {
            this.boundsColor = boundsColor;
        }

        private Color boundsColor = new Color(75, 75, 75);
        private String name = "";
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

        public Tab(String name) {
            this.name = name;
            TextLayout tl = new TextLayout(name, Fonts.MAIN_FONT, new FontRenderContext(null, true, true));
            size = new Dimension((int) ((tl.getBounds().getWidth()) + 9 + 26), 22);
        }

        @Override
        public void paint(Graphics gr) {
            Graphics2D g = (Graphics2D) gr;
            //System.out.println(g.getClipBounds().x+" "+g.getClipBounds().y+" "+g.getClipBounds().width+" "+g.getClipBounds().height);
            g.setColor(getBackground());
            g.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
            g.setColor(getForeground());
            g.setFont(Fonts.MAIN_FONT);
            g.drawString(name, 9, 14);
            g.setColor(boundsColor);

            g.setStroke(new BasicStroke(2));
            gr.drawLine(0, 0, (int) size.getWidth(), 0);
            g.drawLine(0, 0, 0, (int) size.getHeight());
            //g.setColor(boundsColor);
            g.drawLine((int) size.getWidth() - 1, 0, (int) size.getWidth() - 1, (int) size.getHeight() - 1);
            g.drawLine(0, (int) size.getHeight() - 1, (int) size.getWidth() - 1, (int) size.getHeight() - 1);
        }
    }
}
