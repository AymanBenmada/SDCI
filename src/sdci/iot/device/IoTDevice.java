package sdci.iot.device;

import sdci.iot.common.IoTPlatform;

public class IoTDevice {
	
	public enum IoTDeviceType {
		SENSOR,
		ACTUATOR
	}
	
	IoTDeviceType Type;
	String Name;
	String Descritpion;
	String Location;
	
	IoTPlatform HostingPlatform;
	
	public IoTDevice(IoTPlatform hostingPlatform, IoTDeviceType type, String name, String descritpion, String location) {
		Type = type;
		Name = name;
		Descritpion = descritpion;
		Location = location;
		HostingPlatform = hostingPlatform;
	}

	public IoTDeviceType getType() {
		return Type;
	}

	public void setType(IoTDeviceType type) {
		Type = type;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescritpion() {
		return Descritpion;
	}

	public void setDescritpion(String descritpion) {
		Descritpion = descritpion;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public IoTPlatform getHostingPlatform() {
		return HostingPlatform;
	}

	public void setHostingPlatform(IoTPlatform hostingPlatform) {
		HostingPlatform = hostingPlatform;
	}
	
	
}