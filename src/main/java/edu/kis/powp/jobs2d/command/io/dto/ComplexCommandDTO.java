package edu.kis.powp.jobs2d.command.io.dto;

import edu.kis.powp.jobs2d.command.ComplexCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;

import java.util.List;
import java.util.stream.Collectors;

public class ComplexCommandDTO {
	private List<CommandDTO> commandList;

	public ComplexCommand convertToComplexCommand() {
		List<DriverCommand> commandList = this.commandList.stream()
				.map(CommandDTO::convertToDriverCommand)
				.collect(Collectors.toList());

		return new ComplexCommand(commandList);
	}
}
