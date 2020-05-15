
public class StrategyOne implements StrategyBehavior {

	//Buy if changePerc > 5, sell if changePerc < 5, else hold
	@Override
	public String getStockAction(Stock stock) {
		if (stock.getChangePercent() > 5)
			return "BUY";
		else if (stock.getChangePercent() < -5)
			return "SELL";
		else
			return "HOLD";
	}

}
