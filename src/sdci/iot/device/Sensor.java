package sdci.iot.device;

import sdci.iot.common.IoTPlatform;
import sdci.iot.common.MWClient;

public class Sensor extends IoTDevice {
	String Value;
	String Measure;
	String Unit;
	
	
	public Sensor(IoTPlatform hostingPlatform, String name, String descritpion, String location) {
		super(hostingPlatform, IoTDeviceType.SENSOR, name, descritpion, location);
	}

	public String getValue() {
		return Value;
	}
	public void setValue(String value) throws Exception {
		Value = value;
		MWClient.createCI(this, "DATA", value);
	}
	public String getMeasure() {
		return Measure;
	}
	public void setMeasure(String measure) {
		Measure = measure;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	
	
}
