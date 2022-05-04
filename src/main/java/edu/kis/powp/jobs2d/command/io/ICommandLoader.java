package edu.kis.powp.jobs2d.command.io;

import edu.kis.powp.jobs2d.command.DriverCommand;

import java.util.List;

public interface ICommandLoader {
	List<DriverCommand> loadCommands(String text);
}
