package edu.kis.powp.jobs2d.command.io.impl;

import com.google.gson.Gson;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.io.ICommandLoader;
import edu.kis.powp.jobs2d.command.io.dto.ComplexCommandDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JsonCommandLoader implements ICommandLoader {
	@Override
	public List<DriverCommand> loadCommandsFromFile(String fileData) {
		Gson gson = new Gson();

		ComplexCommandDTO[] loadedCommands = gson.fromJson(fileData, ComplexCommandDTO[].class);

		return Arrays.stream(loadedCommands)
				.map(ComplexCommandDTO::convertToComplexCommand)
				.collect(Collectors.toList());
	}
}
