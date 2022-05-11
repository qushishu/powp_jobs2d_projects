package edu.kis.powp.jobs2d.command.io;

import edu.kis.powp.jobs2d.command.io.impl.JsonCommandLoader;

public class CommandLoaderFactory {

	private static final String FILE_TYPE_JSON = "json";

	public ICommandLoader getLoader(String dataType) {
		if (FILE_TYPE_JSON.equals(dataType)) {
			return new JsonCommandLoader();
		}
		throw new RuntimeException("Unsupported file extension");
	}
}
