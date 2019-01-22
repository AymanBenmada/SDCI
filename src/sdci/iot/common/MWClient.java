package sdci.iot.common;

import sdci.iot.common.RestClient.RequestMethod;
import sdci.iot.device.IoTDevice;

public class MWClient {


	public static void createAE(IoTDevice device) throws Exception {
		if (createAECore(device) == 201) {
			if (createCNT(device, "DESCRIPTOR")==201)
				createCI(device, "DESCRIPTOR", String.format("Description = %s\r\nLocation = %s", device.getDescritpion(), device.getLocation()));
			createCNT(device, "DATA");
		}
	}
	
	public static int createAECore(IoTDevice device) throws Exception {
		RestClient rest = new RestClient(String.format("http://%s:%s/~/%s/", device.getHostingPlatform().getAddress(), device.getHostingPlatform().getPort(), device.getHostingPlatform().getID()));
		rest.addHeader("X-M2M-Origin", device.getHostingPlatform().getRequestingEntity());
		rest.addHeader("Content-Type","application/xml;ty=2");
		rest.setBodyString(""
				+ "<m2m:ae xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"" + device.getName() + "\" >\r\n" + 
				"    <api>" + device.getName() + "</api>\r\n" + 
				"    <rr>false</rr>\r\n" + 
				"</m2m:ae>");
		
		rest.execute(RequestMethod.POST);
		return rest.getResponseCode();
	}
	
	public static int createCNT(IoTDevice device, String cnt) throws Exception {
		RestClient rest = new RestClient(String.format("http://%s:%s/~/%s/%s/%s/", device.getHostingPlatform().getAddress(), device.getHostingPlatform().getPort(), device.getHostingPlatform().getID(), device.getHostingPlatform().getName(), device.getName()));
		rest.addHeader("X-M2M-Origin", device.getHostingPlatform().getRequestingEntity());
		rest.addHeader("Content-Type","application/xml;ty=3");
		rest.addHeader("X-M2M-NM",cnt);
		rest.setBodyString("<om2m:cnt xmlns:om2m=\"http://www.onem2m.org/xml/protocols\">\r\n" + 
				"</om2m:cnt>");
		
		rest.execute(RequestMethod.POST);
		return rest.getResponseCode();
	}
	
	public static int createCI(IoTDevice device, String cnt, String ci) throws Exception {
		RestClient rest = new RestClient(String.format("http://%s:%s/~/%s/%s/%s/%s/", device.getHostingPlatform().getAddress(), device.getHostingPlatform().getPort(), device.getHostingPlatform().getID(), device.getHostingPlatform().getName(), device.getName(), cnt));
		rest.addHeader("X-M2M-Origin", device.getHostingPlatform().getRequestingEntity());
		rest.addHeader("Content-Type","application/xml;ty=4");
		rest.setBodyString("<om2m:cin xmlns:om2m=\"http://www.onem2m.org/xml/protocols\">\r\n" + 
				"    <cnf>message</cnf>\r\n" + 
				"    <con>"+ ci + "</con>\r\n" + 
				"</om2m:cin>");
		
		rest.execute(RequestMethod.POST);
		return rest.getResponseCode();
	}
}
