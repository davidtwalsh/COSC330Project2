
public class StrategyThree implements StrategyBehavior{

	//if symbol 4 chars ->Buy, if time is btwn 1:00pm and 3:22pm->SELL,else HOLD
	@Override
	public String getStockAction(Stock stock) {
		
		if (stock.getSymbol().length() == 4)
			return "BUY";
		else if (stock.price == 0) //go back to replace with time 
			return "SELL";
		else
			return "HOLD";
	}

}
