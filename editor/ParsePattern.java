package editor;

import java.awt.*;

public interface ParsePattern {
    public Color getColor(String type);
    public boolean isWord(String type);
    public String getPattern(String type,String expression);
}
