package Event;

import java.util.Date;
import java.util.List;

public class ElectricityProductionIncomings extends Message {

	private Date date;
	private List<Double> prices;
	
	public ElectricityProductionIncomings(int senderId, int receiverId,
			Date date, List<Double> prices) {
		super(senderId, receiverId);
		
		this.date = date;
		this.prices = prices;
	}

	public Date getDate() {
		return date;
	}
	
	public List<Double> getPrices() {
		return prices;
	}
}
