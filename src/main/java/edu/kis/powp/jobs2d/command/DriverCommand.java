package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.visitor.ICommandVisitor;

/**
 * DriverCommand interface.
 */
public interface DriverCommand{

	/**
	 * Execute command on driver.
	 * 
	 * @param driver driver.
	 */
	void execute(Job2dDriver driver);
	void accept(ICommandVisitor visitor);
	DriverCommand copy();
}
