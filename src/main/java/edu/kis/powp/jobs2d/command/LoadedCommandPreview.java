package edu.kis.powp.jobs2d.command;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;

import javax.swing.*;

public class LoadedCommandPreview {
    private JPanel currentCommandPreviewPanel;
    private DriverCommandManager commandManager;

    public LoadedCommandPreview(JPanel currentCommandPreviewPanel, DriverCommandManager commandManager) {
        this.currentCommandPreviewPanel = currentCommandPreviewPanel;
        this.commandManager = commandManager;
    }

    public void updateCurrentCommandPreviewPanel() {
        DrawPanelController previewPanelDrawerController = new DrawPanelController();
        previewPanelDrawerController.initialize(currentCommandPreviewPanel);
        Job2dDriver driver = new LineDriverAdapter(previewPanelDrawerController, LineFactory.getBasicLine(), "basic");
        DriverCommand currentCommand = commandManager.getCurrentCommand();
        previewPanelDrawerController.clearPanel();
        if (currentCommand != null) {
            currentCommand.execute(driver);
        }
    }
}
