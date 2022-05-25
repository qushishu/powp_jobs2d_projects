package edu.kis.powp.jobs2d.events;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;

public class DrawLineMouseListener extends MouseInputAdapter {

    private static JPanel drawArea;
    private static DriverManager driverManager;

    private int drawAreaWidth;
    private int drawAreaHeight;

    private final static int LEFT_BUTTON = 1;
    private final static int RIGHT_BUTTON = 3;
    
    public static void enable(JPanel drawArea, DriverManager driverManager) {
        DrawLineMouseListener.drawArea = drawArea;
        DrawLineMouseListener.driverManager = driverManager;

        drawArea.addMouseListener(new DrawLineMouseListener());
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        drawAreaWidth = drawArea.getWidth();
        drawAreaHeight = drawArea.getHeight();

        int correctedX = e.getX() - drawAreaWidth / 2;
        int correctedY = e.getY() - drawAreaHeight / 2;

        Job2dDriver currentDriver = driverManager.getCurrentDriver();

        switch (e.getButton()) {
            case LEFT_BUTTON:
                currentDriver.operateTo(correctedX, correctedY);
                break;
        
            case RIGHT_BUTTON:
                currentDriver.setPosition(correctedX, correctedY);
                break;

            default:
                break;
        }
    };
}
