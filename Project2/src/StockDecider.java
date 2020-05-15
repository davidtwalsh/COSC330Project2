
public class StockDecider {
	StrategyBehavior strategyBehavior;
	
	public StockDecider() {
		strategyBehavior = new StrategyOne();
	}
	public void setStrategy(StrategyBehavior sB)
	{
		strategyBehavior = sB;
	}
	public String makeDecision(Stock s)
	{
		return strategyBehavior.getStockAction(s);
	}
	
}
