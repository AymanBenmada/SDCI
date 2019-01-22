package sdci.iot.device;

public class SensorDataItem {
	String Value;
	int Duration;
	
	
	
	public SensorDataItem(String value, int duration) {
		Value = value;
		Duration = duration;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public int getDuration() {
		return Duration;
	}
	public void setDuration(int duration) {
		Duration = duration;
	}
	
	
}
