
public class Stock {
	
	String symbol;
	String company;
	float price;
	float change;
	float changePercent;
	int volume;
	
	
	public Stock(String _symbol)
	{
		symbol = _symbol;
	}
	public void setPrice(float p)
	{
		price = p;
	}
	public void setChange(float c)
	{
		change = c;
	}
	public void setChangePercent(float c)
	{
		changePercent = c;
	}
	public void setCompany(String s)
	{
		company = s;
	}
	public void setVolume(int v)
	{
		volume = v;
	}
	public String getSymbol()
	{
		return symbol;
	}
	public float getPrice()
	{
		return price;
	}
	public String getCompany()
	{
		return company;
	}
	public int getVolume() {
		return volume;
	}
	public float getChange() {
		return change;
	}
	public float getChangePercent() {
		return changePercent;
	}
	public void printStock()
	{
		System.out.println("Symbol: " + symbol);
		System.out.println("Company: " + company);
		System.out.println("Price: " + price);
		System.out.println("Change: " + change);
		System.out.println("Change Percent: " + changePercent + "%");
		System.out.println("Volume: " + volume);
		System.out.println("***************");
	}
}
