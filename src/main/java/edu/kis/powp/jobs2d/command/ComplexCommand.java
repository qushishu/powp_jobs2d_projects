package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.visitor.ICommandVisitor;

import java.util.*;

public class ComplexCommand implements ICompoundCommand {

    private final List<DriverCommand> commandList;

    public ComplexCommand(List<DriverCommand> commandList) {
        this.commandList = commandList;
    }

    @Override
    public void execute(Job2dDriver driver) {
        for (DriverCommand command : this.commandList) {
            command.execute(driver);
        }
    }

    public ComplexCommand copy() {
        List<DriverCommand> commandListClone = new ArrayList<>();
        for (DriverCommand driverCommand : commandList) {
            commandListClone.add(driverCommand.copy());
        }
        return new ComplexCommand(commandListClone);
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        return commandList.iterator();
    }

    @Override
    public void accept(ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
