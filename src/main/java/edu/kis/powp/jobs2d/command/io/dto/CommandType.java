package edu.kis.powp.jobs2d.command.io.dto;

public enum CommandType {
	OPERATE_TO("OPERATE_TO"), SET_POSITION("SET_POSITION");

	private final String type;

	CommandType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
