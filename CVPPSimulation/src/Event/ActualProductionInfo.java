package Event;

import java.util.Date;
import java.util.List;

public class ActualProductionInfo extends Message {

	private Date date;
	private List<Double> productionList;
	
	public ActualProductionInfo(int senderId, int receiverId, Date date, List<Double> productionList) {
		super(senderId, receiverId);
		
		this.date = date;
		this.productionList = productionList;
	}

	public Date getDate() {
		// TODO Auto-generated method stub
		return date;
	}
	
	public List<Double> getProductionList() {
		return productionList;
	}
}
