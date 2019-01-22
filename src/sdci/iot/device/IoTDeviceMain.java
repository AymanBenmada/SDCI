package sdci.iot.device;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import sdci.iot.common.IoTPlatform;
import sdci.iot.common.MWClient;
import sdci.iot.device.IoTDevice.IoTDeviceType;

public class IoTDeviceMain {
	
	public enum SensorDataGenerationType {
		PERIODIC_RANDOM,
		EVENT_USER_INPUT,
		EVENT_FILE_INPUT,
	}
	public static SensorDataGenerationType SensorDataGenTypeFromString(String str) {
		SensorDataGenerationType res = null;
		switch (str) {
			case "PERIODIC_RANDOM":
				res = SensorDataGenerationType.PERIODIC_RANDOM;
				break;
			case "EVENT_USER_INPUT":
				res = SensorDataGenerationType.EVENT_USER_INPUT;
				break;
			case "EVENT_FILE_INPUT": 
				res = SensorDataGenerationType.EVENT_FILE_INPUT;
				break; 
		}
		return res;
	}
	public static IoTDeviceType IoTDeviceTypeFromString(String str) {
		IoTDeviceType res = null;
		switch (str) {
			case "SENSOR":
				res = IoTDeviceType.SENSOR;
				break;
			case "ACTUATOR":
				res = IoTDeviceType.ACTUATOR;
				break;
		}
		return res;
	}

	
	public static void main(String[] args) throws Exception {
		
		Properties prop = new Properties();
		InputStream input = new FileInputStream("device.cfg");
		prop.load(input);
		
		IoTDeviceType DEVICE_TYPE  = IoTDeviceTypeFromString(prop.getProperty("device.type"));
		String DEVICE_NAME = prop.getProperty("device.name");
		String DEVICE_DESCRIPTION = prop.getProperty("device.name");
		String DEVICE_LOCATION = prop.getProperty("device.name");
		
		String SENSOR_MEASURE = prop.getProperty("sensor.measure");
		String SENSOR_UNIT = prop.getProperty("sesnor.unit");
		SensorDataGenerationType SENSOR_DATA_GENERATION_TYPE = SensorDataGenTypeFromString(prop.getProperty("sensor.data.generation.type"));
		int SENSOR_DATA_GENERATION_PERIOD = Integer.parseInt(prop.getProperty("sensor.data.generation.period"));
		String SENSOR_DATA_FILENAME = prop.getProperty("sensor.data.filename");
		
		
		String ACTUATOR_STATE = prop.getProperty("actuator.state");
		long ACTUATOR_POLLING_PERIOD = Long.parseLong(prop.getProperty("actuator.pollingPeriod"));
		
		String MW_NODE_REQUESTING_ENTITY = prop.getProperty("mw.node.requestingEntity");
		String MW_NODE_PORT = prop.getProperty("mw.node.port");
		String MW_NODE_ADDRESS = prop.getProperty("mw.node.address");
		String MW_NODE_NAME = prop.getProperty("mw.node.name");
		String MW_NODE_ID = prop.getProperty("mw.node.id");
		String MW_NODE_CONTEXT = prop.getProperty("mw.node.context");
		
		input.close();
		
		IoTPlatform mw = new IoTPlatform(MW_NODE_ID, MW_NODE_NAME, MW_NODE_ADDRESS, MW_NODE_PORT, MW_NODE_CONTEXT, MW_NODE_REQUESTING_ENTITY);
		switch (DEVICE_TYPE) {
			case SENSOR:
				Sensor sensor = new Sensor(mw, DEVICE_NAME, DEVICE_DESCRIPTION, DEVICE_LOCATION);
				sensor.setMeasure(SENSOR_MEASURE);
				sensor.setUnit(SENSOR_UNIT);
				MWClient.createAE(sensor);
				
				switch (SENSOR_DATA_GENERATION_TYPE) {
					case PERIODIC_RANDOM:
						Random rnd = new Random(System.currentTimeMillis());
						while(true) {
							sensor.setValue(String.format("%d", rnd.nextLong()%1000));
							System.out.println(String.format("Generating new sensor value => %s : %s %s", sensor.getMeasure(), sensor.getValue(), sensor.getUnit()));
							Thread.sleep(SENSOR_DATA_GENERATION_PERIOD);
						}
					case EVENT_USER_INPUT:
						BufferedReader myConsole = new BufferedReader(new InputStreamReader(System.in));
						while(true) {
							System.out.print("Enter a new Sensor value : ");
					        String value = myConsole.readLine();
							sensor.setValue(value);
							System.out.println(String.format("Generating new sensor value => %s : %s %s", sensor.getMeasure(), sensor.getValue(), sensor.getUnit()));
						}
					case EVENT_FILE_INPUT:
						BufferedReader myFile = new BufferedReader(new FileReader(SENSOR_DATA_FILENAME));
						String line;
						List<SensorDataItem> data = new ArrayList<SensorDataItem>();
						while ((line = myFile.readLine()) != null) {
							String items[] = line.split(" ");
							data.add(new SensorDataItem(items[0], Integer.parseInt(items[1])));
						}
						myFile.close();
						while(true) {
							
							for (SensorDataItem daton : data) {
								sensor.setValue(daton.getValue());
								System.out.println(String.format("Generating new sensor value => %s : %s %s", sensor.getMeasure(), sensor.getValue(), sensor.getUnit()));
								Thread.sleep(daton.getDuration());
							}

						}
				}				
			break;
			
			case ACTUATOR:
				Actuator actuator = new Actuator(mw, DEVICE_NAME, DEVICE_DESCRIPTION, DEVICE_LOCATION);
				actuator.setState(ACTUATOR_STATE);
				
				while (true){
					String state = actuator.getState();
					System.out.println("Actuator State is "+state);
					Thread.sleep(ACTUATOR_POLLING_PERIOD);
				}
		}
	}

}
