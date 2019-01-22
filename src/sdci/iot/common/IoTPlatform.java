package sdci.iot.common;

public class IoTPlatform {
	String ID;
	String Name;
	String Address;
	String Port;
	String Context;
	String RequestingEntity;
	
	
	
	
	public IoTPlatform(String iD, String name, String address, String port, String context, String requestingEntity) {
		ID = iD;
		Name = name;
		Address = address;
		Port = port;
		Context = context;
		RequestingEntity = requestingEntity;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPort() {
		return Port;
	}
	public void setPort(String port) {
		Port = port;
	}
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
	public String getRequestingEntity() {
		return RequestingEntity;
	}
	public void setRequestingEntity(String requestingEntity) {
		RequestingEntity = requestingEntity;
	}
	
	
}
