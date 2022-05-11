package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.visitor.ICommandVisitor;

import java.util.*;

public class ComplexCommand implements ICompoundCommand {

	public static final String DEFAULT_NAME = "DEFAULT COMMAND NAME";
	private final List<DriverCommand> commandList;

	private final String name;

	public ComplexCommand(List<DriverCommand> commandList) {
		this.commandList = commandList;
		this.name = DEFAULT_NAME;
	}
	public ComplexCommand(List<DriverCommand> commandList, String name) {
		this.commandList = commandList;
		this.name = name;
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
		return new ComplexCommand(commandListClone, name);
	}

	@Override
	public Iterator<DriverCommand> iterator() {
		return commandList.iterator();
	}

	@Override
	public void accept(ICommandVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return name;
	}
}
