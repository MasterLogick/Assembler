package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Project {

	public static void createNew() {
		final boolean[] isDirChosen = {false};
		JFrame dialog = new JFrame("Create new");
		JPanel main = new JPanel();
		JPanel filler = new JPanel();
		JTextField name = new JTextField(60);
		JTextField path = new JTextField(49);
		JButton cancel = new JButton("Cancel");
		JButton create = new JButton("Create");
		JButton browse = new JButton("Browse...");
		JFileChooser fileChooser = new JFileChooser();

		cancel.addActionListener(e -> dialog.dispose());
		browse.addActionListener(e -> {
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				path.setText(fileChooser.getSelectedFile().getAbsolutePath());
				isDirChosen[0] = true;
			}
		});
		name.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!isDirChosen[0]) {
					path.setText(path.getText().substring(0, path.getText().lastIndexOf("\\")) + "\\" + name.getText());
				}
			}
		});
		create.addActionListener(e -> {

		});

		path.setText("C:\\");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		main.setLayout(new GridLayout(3, 1));
		filler.setLayout(new FlowLayout(FlowLayout.LEFT));
		filler.add(new JLabel("Project name: "));
		filler.add(name);
		main.add(filler);
		filler = new JPanel();
		filler.add(new JLabel("Project path:  "));
		filler.add(path);
		filler.add(browse);
		main.add(filler);
		filler = new JPanel();
		filler.setLayout(new FlowLayout(FlowLayout.RIGHT));
		filler.add(create);
		filler.add(cancel);
		main.add(filler);
		dialog.add(main);
		dialog.setSize(dialog.getPreferredSize().width + 17, dialog.getPreferredSize().height + 28);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	public static void openProject() {
	}
}
