package edu.kis.powp.jobs2d.command.gui;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.io.CommandLoaderFactory;
import edu.kis.powp.jobs2d.command.io.ICommandLoader;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.observer.Subscriber;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class CommandManagerWindow extends JFrame implements WindowComponent {

	private final DrawPanelController previewPanelDrawerController;
	private final Job2dDriver driver;
	private DriverCommandManager commandManager;

	private JTextArea currentCommandField;
	private JPanel currentCommandPreviewPanel;

	private String observerListString;
	private JTextArea observerListField;

	/**
	 *
	 */
	private static final long serialVersionUID = 9204679248304669948L;

	public CommandManagerWindow(DriverCommandManager commandManager) {
		this.setTitle("Command Manager");
		this.setSize(400, 500);
		Container content = this.getContentPane();
		content.setLayout(new GridBagLayout());

		this.commandManager = commandManager;

		GridBagConstraints c = new GridBagConstraints();

		observerListField = new JTextArea("");
		observerListField.setEditable(false);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.weighty = 1;
		content.add(observerListField, c);
		updateObserverListField();

		currentCommandField = new JTextArea("");
		currentCommandField.setEditable(false);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.weighty = 1;
		content.add(currentCommandField, c);
		updateCurrentCommandField();

		currentCommandPreviewPanel = new JPanel();
		previewPanelDrawerController = new DrawPanelController();
		previewPanelDrawerController.initialize(currentCommandPreviewPanel);
		driver = new LineDriverAdapter(previewPanelDrawerController, LineFactory.getBasicLine(), "basic");
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.weighty = 5;
		content.add(currentCommandPreviewPanel, c);

		JButton btnLoadCommands = new JButton("Load commands");
		btnLoadCommands.addActionListener((ActionEvent e) -> this.loadCommands());
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.weighty = 1;
		content.add(btnLoadCommands, c);

		JButton btnClearCommand = new JButton("Clear command");
		btnClearCommand.addActionListener((ActionEvent e) -> this.clearCommand());
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.weighty = 1;
		content.add(btnClearCommand, c);

		JButton btnClearObservers = new JButton("Delete observers");
		btnClearObservers.addActionListener((ActionEvent e) -> this.deleteObservers());
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.weighty = 1;
		content.add(btnClearObservers, c);
	}

	public void updateCurrentCommandPreviewPanel() {
		DriverCommand currentCommand = commandManager.getCurrentCommand();
		previewPanelDrawerController.clearPanel();
		if (currentCommand != null) {
			currentCommand.execute(driver);
		}
	}

	private void clearCommand() {
		commandManager.clearCurrentCommand();
		updateCurrentCommandField();
		updateCurrentCommandPreviewPanel();
	}

	public void updateCurrentCommandField() {
		currentCommandField.setText(commandManager.getCurrentCommandString());
	}

	public void deleteObservers() {
		commandManager.getChangePublisher().clearObservers();
		this.updateObserverListField();
	}

	public void loadCommands() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.json", "json"));

		int fileChooserState = fileChooser.showOpenDialog(this);

		if (fileChooserState == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String fileExtension = getFileExtension(selectedFile.getName());
			String data = null;

			try {
				data = new Scanner(selectedFile).useDelimiter("\\Z").next();
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}

			ICommandLoader loader = new CommandLoaderFactory().getLoader(fileExtension);
			List<DriverCommand> complexCommands = loader.loadCommands(data);

			commandManager.setCurrentCommand(complexCommands, selectedFile.getName());
		}
	}

	private String getFileExtension(String selectedFile) {
		return selectedFile.substring(selectedFile.lastIndexOf(".") + 1);
	}


	private void updateObserverListField() {
		observerListString = "";
		List<Subscriber> commandChangeSubscribers = commandManager.getChangePublisher().getSubscribers();
		for (Subscriber observer : commandChangeSubscribers) {
			observerListString += observer.toString() + System.lineSeparator();
		}
		if (commandChangeSubscribers.isEmpty())
			observerListString = "No observers loaded";

		observerListField.setText(observerListString);
	}

	@Override
	public void HideIfVisibleAndShowIfHidden() {
		updateObserverListField();
		if (this.isVisible()) {
			this.setVisible(false);
		} else {
			this.setVisible(true);
		}
	}

}
