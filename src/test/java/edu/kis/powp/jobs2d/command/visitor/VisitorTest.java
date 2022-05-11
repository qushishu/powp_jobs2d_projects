package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.LoggerDriver;
import edu.kis.powp.jobs2d.command.ComplexCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.util.LinkedList;
import java.util.List;

public class VisitorTest {
    /**
     * Visitor test.
     */
    public static void main(String[] args) {
        CountCommandVisitor countCommandVisitor = new CountCommandVisitor();
        countCommandVisitor.visit(new OperateToCommand(10, 10));
        int countedCommands = countCommandVisitor.getCountedCommands();
        assert(countedCommands == 1) : "Invalid value! Expected 1";

        List<DriverCommand> driverCommands = new LinkedList<>();
        driverCommands.add(new OperateToCommand(10, 10));
        driverCommands.add(new SetPositionCommand(10, 15));
        driverCommands.add(new OperateToCommand(20, 20));
        countCommandVisitor.visit(new ComplexCommand(driverCommands));
        countedCommands = countCommandVisitor.getCountedCommands();
        assert(countedCommands == 3) : "Invalid value! Expected 3";
    }
}
