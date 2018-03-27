package project;

import javax.swing.*;
import java.awt.*;

public class Project {

    public static void createNew() {
		JFrame dialog = new JFrame("Create new");
		JPanel main = new JPanel();
		JPanel filler = new JPanel();
		JTextField name = new JTextField(30);
		JTextField path = new JTextField(50);
		JButton cancel = new JButton("Cancel");
		JButton create = new JButton("Create");
		JButton browse = new JButton("Browse...");
		JFileChooser fileChooser = new JFileChooser();

		cancel.addActionListener(e -> dialog.dispose());
		browse.addActionListener(e -> {

		});
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		main.setLayout(new GridLayout(3,1));
		filler.setLayout(new BoxLayout(filler,BoxLayout.X_AXIS));
		filler.add(new JLabel("Project name: "));
		filler.add(name);
		main.add(filler);
		filler= new JPanel();
		filler.add(new JLabel("Project path: "));
		filler.add(path);
		filler.add(browse);
		main.add(filler);
		filler= new JPanel();
		filler.setLayout(new FlowLayout(FlowLayout.RIGHT));
		filler.add(create);
		filler.add(cancel);
		main.add(filler);
		dialog.add(main);
		dialog.pack();
		dialog.setVisible(true);
    }

    public static void openProject() {
    }
}
