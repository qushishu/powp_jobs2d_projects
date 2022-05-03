package edu.kis.powp.jobs2d.command.io.dto;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

public class CommandDTO {
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
