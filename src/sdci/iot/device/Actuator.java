package sdci.iot.device;

import java.util.HashMap;

import sdci.iot.common.IoTPlatform;
import sdci.iot.common.RestClient;

public class Actuator extends IoTDevice {
	String State;
	HashMap<String, String> Actions;
	
	public Actuator(IoTPlatform hostingPlatform, String name, String descritpion, String location) {
		super(hostingPlatform, IoTDeviceType.ACTUATOR, name, descritpion, location);
	}

	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public HashMap<String, String> getActions() {
		return Actions;
	}
	public void setActions(HashMap<String, String> actions) {
		Actions = actions;
	}
	
	
	
}