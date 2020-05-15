
public class StrategyFour implements StrategyBehavior{

	//change > 1 BUY, change < 1 SELL, else HOLD
	@Override
	public String getStockAction(Stock stock) {
		
		if (stock.change > 1)
			return "BUY";
		else if (stock.change < 1)
			return "SELL";
		else
			return "HOLD";

	}

}
