package Event;

import java.util.Date;
import java.util.List;

public class CollectedActualProductionInfo extends Message {

	private Date date;
	private List<Double> productionList;
	
	public CollectedActualProductionInfo(
			int senderId, int receiverId,
			Date date, List<Double> productionList) {
		super(senderId, receiverId);
		
		this.date = date;
		this.productionList = productionList;
	}

	public Date getDate() {
		return date;
	}
	
	public List<Double> getProductionList() {
		return productionList;
	}
}
