package Event;

public class Electricity extends Message {

	private double amount;
	public Electricity(int senderId, int receiverId, double amount) {
		super(senderId, receiverId);
		
		this.amount = amount;
	}

	public double getAmount() {
		return amount; 
	}
}
