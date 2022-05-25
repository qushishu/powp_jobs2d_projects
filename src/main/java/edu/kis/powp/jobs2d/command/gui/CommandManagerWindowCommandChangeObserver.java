package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.LoadedCommandPreview;
import edu.kis.powp.observer.Subscriber;

public class CommandManagerWindowCommandChangeObserver implements Subscriber {

	private CommandManagerWindow commandManagerWindow;

	public CommandManagerWindowCommandChangeObserver(CommandManagerWindow commandManagerWindow) {
		super();
		this.commandManagerWindow = commandManagerWindow;
	}

	public String toString() {
		return "Current command change observer for command manager window";
	}

	@Override
	public void update() {
		commandManagerWindow.updateCurrentCommandField();
		LoadedCommandPreview loadedCommandPreview = new LoadedCommandPreview(commandManagerWindow.getCurrentCommandPreviewPanel(), commandManagerWindow.getCommandManager());
		loadedCommandPreview.updateCurrentCommandPreviewPanel();
//		commandManagerWindow.updateCurrentCommandPreviewPanel();
	}

}
