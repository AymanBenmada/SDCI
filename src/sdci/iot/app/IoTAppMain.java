package sdci.iot.app;

import org.json.JSONObject;

import sdci.iot.common.RestClient;
import sdci.iot.common.RestClient.RequestMethod;

public class IoTAppMain {
	
	public static void create_vnf(String vnf_name, String vnf_ip) throws Exception {
		RestClient rest = new RestClient("http://127.0.0.1:5001/restapi/compute/dc1/" + vnf_name);
		rest.addHeader("Accept", "application/json");
		rest.addParam("image", "ubuntu:trusty");
		rest.addParam("network", "(id=" + vnf_name + "-eth0,ip=" + vnf_ip + "/24)");
		rest.execute(RequestMethod.PUT);
		if (rest.getResponseCode() == 200) {
			JSONObject obj = new JSONObject(rest.getResponse());
			System.out.println(obj);
		}
		else {
			System.out.println(rest.getErrorMessage());
		}
	}
	
	public static void delete_vnf(String vnf_name) throws Exception {
		RestClient rest = new RestClient("http://127.0.0.1:5001/restapi/compute/dc1/" + vnf_name);
		rest.addHeader("Accept", "application/json");
		rest.execute(RequestMethod.DELETE);
		if (rest.getResponseCode() == 200) {
			System.out.println(rest.getResponse());
		}
		else {
			System.out.println(rest.getErrorMessage());
		}
	}
	
	public static void get_ports_description (int dpid) throws Exception {
		RestClient rest = new RestClient("http://127.0.0.1:8080/stats/portdesc/" + dpid);
		rest.execute(RequestMethod.GET);
		if (rest.getResponseCode() == 200) {
			JSONObject obj = new JSONObject(rest.getResponse());
			System.out.println(obj);
		}
		else {
			System.out.println(rest.getErrorMessage());
		}
	}
	
	public static void create_route_GWF2_GWI() throws Exception {
		RestClient rest = new RestClient("http://127.0.0.1:8080/stats/flowentry/add");
		rest.addHeader("Accept", "application/json");
		rest.setBodyString("{'dpid':1,'cookie':3,'table_id':0,'idle_timeout':0,'hard_timeout':0,'priority':11111,'flags':1,'match':{'in_port':3},'actions':[{'type':'OUTPUT','port':6}]}");
		rest.execute(RequestMethod.POST);
		if (rest.getResponseCode() == 200) {
			System.out.println(true);
			System.out.println(rest.getResponse());
		}
		else {
			System.out.println(rest.getErrorMessage());
		}
	}
	
	public static void create_route_GWF3_GWI() throws Exception {
		RestClient rest = new RestClient("http://127.0.0.1:8080/stats/flowentry/add");
		rest.addHeader("Accept", "application/json");
		rest.setBodyString("{'dpid':1,'cookie':3,'table_id':0,'idle_timeout':0,'hard_timeout':0,'priority':11111,'flags':1,'match':{'in_port':4},'actions':[{'type':'OUTPUT','port':6}]}");
		rest.execute(RequestMethod.POST);
		if (rest.getResponseCode() == 200) {
			System.out.println(true);
			System.out.println(rest.getResponse());
		}
		else {
			System.out.println(rest.getErrorMessage());
		}
	}
	
	public static void transparent_redirect() throws Exception {
		RestClient rest = new RestClient("http://127.0.0.1:8080/stats/flowentry/add");
		rest.addHeader("Accept", "application/json");
		rest.setBodyString("{'dpid':1,'cookie':3,'table_id':0,'idle_timeout':0,'hard_timeout':0,'priority':11111,'flags':1,'match':{'in_port':6},'actions':[{'type':'SET_NW_SRC', 'nw_src':'172.27.0.2'}]}");
		rest.execute(RequestMethod.POST);
		if (rest.getResponseCode() == 200) {
			System.out.println(true);
			System.out.println(rest.getResponse());
		}
		else {
			System.out.println(rest.getErrorMessage());
		}
	}
	

	public static void get_all_rules () throws Exception {
		RestClient rest = new RestClient("http://127.0.0.1:8080/stats/flow/1");
		rest.execute(RequestMethod.GET);
		if (rest.getResponseCode() == 200) {
			System.out.println(rest.getResponse());
		}
		else {
			System.out.println(rest.getErrorMessage());
		}
	}
	
	public static void delete_all_rules () throws Exception {
		RestClient rest = new RestClient("http://127.0.0.1:8080/stats/flowentry/clear/1");
		rest.execute(RequestMethod.DELETE);
		if (rest.getResponseCode() == 200) {
			System.out.println("DELETING ALL RULES");
		}
		else {
			System.out.println(rest.getErrorMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		create_route_GWF2_GWI();
		create_route_GWF3_GWI();
		transparent_redirect();
		get_all_rules();
	}

}

