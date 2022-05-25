package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.ComplexCommandFactory;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.transformers.*;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;

import edu.kis.powp.jobs2d.events.DrawLineMouseListener;
import edu.kis.powp.jobs2d.drivers.composite.DriverComposite;
import edu.kis.powp.jobs2d.drivers.gui.DriverUpdateInfoPrinterObserver;

import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class TestJobs2dApp {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 *
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
			DriverFeature.getDriverManager());
		SelectTestFigure2OptionListener selectTestFigure2OptionListener = new SelectTestFigure2OptionListener(
			DriverFeature.getDriverManager());

		application.addTest("Figure Joe 1", selectTestFigureOptionListener);
		application.addTest("Figure Joe 2", selectTestFigure2OptionListener);
	}

	/**
	 * Setup test using driver commands in context.
	 *
	 * @param application Application context.
	 */
	private static void setupCommandTests(Application application) {
		application.addTest("Load secret command", new SelectLoadSecretCommandOptionListener());
		application.addTest("Test complex command builder", new SelectLoadComplexCommandListener(
				ComplexCommandFactory.getSquareCommand()));
		application.addTest("Run command", new SelectRunCurrentCommandOptionListener(DriverFeature.getDriverManager()));

		// Selecting another driver resets previous transformer commands for this driver.
		addTranslateTest(application);
		addScaleTest(application);
		addRotateTest(application);
		addComplexTransformationTest(application);

		DriverFeature.updateDriverInfo();
	}

	private static void addComplexTransformationTest(Application application) {
		List<TransformerCommand> complexTransformerCommands = new ArrayList<>();
		complexTransformerCommands.add(new TranslateCommand(50, 50));
		complexTransformerCommands.add(new ScaleCommand(0.5, 0.5));
		complexTransformerCommands.add(new RotateCommand(180));
		ComplexTransformerCommand complexTransformerCommand =
			new ComplexTransformerCommand(complexTransformerCommands);
		application.addTest("Complex transform",
			new SelectTransformCommandOptionListener(
				DriverFeature.getDriverManager(), complexTransformerCommand, "Complex"));
	}

	private static void addRotateTest(Application application) {
		List<TransformerCommand> rotateCommands = new ArrayList<>();
		rotateCommands.add(new RotateCommand(10));
		ComplexTransformerCommand rotateComplexCommand = new ComplexTransformerCommand(rotateCommands);
		application.addTest("Rotate",
			new SelectTransformCommandOptionListener(
				DriverFeature.getDriverManager(), rotateComplexCommand, "Rotate"));
	}

	private static void addScaleTest(Application application) {
		List<TransformerCommand> scaleCommands = new ArrayList<>();
		scaleCommands.add(new ScaleCommand(1.1, 0.9));
		ComplexTransformerCommand scaleComplexCommand = new ComplexTransformerCommand(scaleCommands);
		application.addTest("Scale",
			new SelectTransformCommandOptionListener(
				DriverFeature.getDriverManager(), scaleComplexCommand, "Scale"));
	}

	private static void addTranslateTest(Application application) {
		List<TransformerCommand> translateCommands = new ArrayList<>();
		translateCommands.add(new TranslateCommand(10, 10));
		ComplexTransformerCommand translateComplexCommand = new ComplexTransformerCommand(translateCommands);
		application.addTest("Translate",
			new SelectTransformCommandOptionListener(
				DriverFeature.getDriverManager(), translateComplexCommand, "Translate"));
	}

	/**
	 * Setup driver manager, and set default Job2dDriver for application.
	 *
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		DriverUpdateInfoPrinterObserver driverObserver = new DriverUpdateInfoPrinterObserver();
		DriverFeature.getDriverManager().getChangePublisher().addSubscriber(driverObserver);

		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger driver", loggerDriver);

		DrawPanelController drawerController = DrawerFeature.getDrawerController();
		Job2dDriver driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
		DriverFeature.addDriver("Line Simulator", driver);

		DriverComposite driverComposite = new DriverComposite();
		driverComposite.add(loggerDriver);
		driverComposite.add(driver);
		DriverFeature.addDriver("Composite Simulator", driverComposite);

		DriverFeature.getDriverManager().setCurrentDriver(driver);

		driver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
		DriverFeature.addDriver("Special line Simulator", driver);

		DriverFeature.updateDriverInfo();
	}

	private static void setupWindows(Application application) {

		CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getDriverCommandManager());
		application.addWindowComponent("Command Manager", commandManager);

		CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(
			commandManager);
		CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(windowObserver);
	}

	/**
	 * Setup menu for adjusting logging settings.
	 *
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {

		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
			(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
			(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
			(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	private static void setMouseDrawer(Application application) {
		DrawLineMouseListener.enable(application.getFreePanel(), DriverFeature.getDriverManager());
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("Jobs 2D");
				DrawerFeature.setupDrawerPlugin(app);
				CommandsFeature.setupCommandManager();
				
				DriverFeature.setupDriverPlugin(app);
				setMouseDrawer(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupCommandTests(app);
				setupLogger(app);
				setupWindows(app);

				app.setVisibility(true);
			}
		});
	}

}
