package edu.kis.powp.jobs2d.command.io.impl;

import com.google.gson.Gson;
import edu.kis.powp.jobs2d.command.ComplexCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.io.ICommandLoader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JsonCommandLoader implements ICommandLoader {
	@Override
	public List<DriverCommand> loadCommands(String text) {
		Gson gson = new Gson();

		ComplexCommandDTO[] loadedCommands = gson.fromJson(text, ComplexCommandDTO[].class);

		return Arrays.stream(loadedCommands)
				.map(ComplexCommandDTO::convertToComplexCommand)
				.collect(Collectors.toList());
	}

	private static class ComplexCommandDTO {
		private List<CommandDTO> commandList;

		public ComplexCommand convertToComplexCommand() {
			List<DriverCommand> commandList = this.commandList.stream()
					.map(CommandDTO::convertToDriverCommand)
					.collect(Collectors.toList());

			return new ComplexCommand(commandList);
		}
	}

	private static class CommandDTO {
		private CommandType type;
		private int posX, posY;

		public DriverCommand convertToDriverCommand() throws RuntimeException {
			switch (type) {
				case OPERATE_TO:
					return new OperateToCommand(posX, posY);
				case SET_POSITION:
					return new SetPositionCommand(posX, posY);
				default:
					throw new RuntimeException("Command converting error. Unsupported command type");
			}
		}
	}

	private enum CommandType {
		OPERATE_TO("OPERATE_TO"), SET_POSITION("SET_POSITION");

		private final String type;

		CommandType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
}
