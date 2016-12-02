package util;

import Event.IPRequest;
import Event.IPResponse;
import Event.Message;

public class InternetUtil {

	public static final String KEY_INTERNET_ERROR = "InternetError";
	
	private InternetUtil() {
		// TODO Auto-generated constructor stub
	}

	public static IPRequest createIPRequest(Message msg) {
		IPRequest request = new IPRequest(
				msg.getSenderId(),
				msg.getReceiverId(),
				msg);
		
		return request;
	}
	
	public static IPResponse createIPResponse(Message msg) {
		IPResponse response = new IPResponse(
				msg.getSenderId(),
				msg.getReceiverId(),
				IPResponse.SUCCESS,
				msg);
		
		return response;
	}
}
