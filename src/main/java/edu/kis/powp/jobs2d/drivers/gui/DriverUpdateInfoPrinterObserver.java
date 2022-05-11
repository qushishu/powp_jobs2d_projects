package edu.kis.powp.jobs2d.drivers.gui;

import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.observer.Subscriber;

public class DriverUpdateInfoPrinterObserver implements Subscriber {

	public DriverUpdateInfoPrinterObserver() {
	}

	@Override
	public void update() {
		DriverFeature.updateDriverInfo();
	}
}
