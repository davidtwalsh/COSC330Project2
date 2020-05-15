
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StockMaster {

	public ArrayList<Stock> stocks;
	public int MAXSTOCKS = 200;
	public StockMaster() throws Exception
	{
		stocks = new ArrayList<Stock>();
		initStocks();
		populateStocks();
		removeBadData();
	}
	
	public int getSize()
	{
		return stocks.size();
	}
	
	//loop through stocks and fill up each stocks data members using financialmodelingprep.com API
	void populateStocks() throws Exception
	{
		
		for (int i=0; i < stocks.size(); i++)
		{
			String symbol = stocks.get(i).getSymbol();
			String urlString = "https://financialmodelingprep.com/api/v3/company/profile/" + symbol;
			URL url = new URL(urlString);
			
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			    for (String line; (line = reader.readLine()) != null;) {
			    //System.out.println(line);
			    	if (line.contains("price")&& !line.contains("description"))
			    	{
			    		setPrice(i,line);
			    	}
			    	if (line.contains("changesPercentage")&& !line.contains("description"))
			    	{
			    		setChangePercentage(i,line);
			    	}
			    	if (line.contains("changes") && !line.contains("changesPercentage")&& !line.contains("description"))
			    	{
			    		setChange(i,line);
			    	}
			    	if (line.contains("companyName")&& !line.contains("description"))
			    	{
			    		setCompanyName(i,line);
			    	}
			    	if (line.contains("volAvg") && !line.contains("description"))
			    	{
			    		setVolume(i,line);
			    	}
			  }
			}
			catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				//throw new Exception("Booooo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//throw new Exception("Booop");
			}
		}
	}
	
	void removeBadData()
	{
		ArrayList<Stock> tStocks = new ArrayList<Stock>();
		for (int i=0; i < stocks.size(); i++) {
			Stock s = stocks.get(i);
			if (s.getPrice() != 0)
			{
				tStocks.add(s);
			}
		}
		stocks = tStocks;
	}
	
	void setVolume(int stockIndex,String line)
	{
		int i = line.indexOf(':');
		i+=3;
		String s = "";
		while (line.charAt(i) != '"')
		{
			s+=line.charAt(i);
			i++;
		}
		int vol;
    	try {
    		vol = Integer.parseInt(s);
    	}catch (NumberFormatException e) {
    		vol = 0;
    	}
    	stocks.get(stockIndex).setVolume(vol);
		
	}
	
	void setCompanyName(int stockIndex, String line)
	{
		int i = line.indexOf(':');
    	i+= 3; //take i t start of price
    	String s = "";
    	while (line.charAt(i) != '"')
    	{
    		s+= line.charAt(i);
    		i++;
    	}
    	stocks.get(stockIndex).setCompany(s);
	}
	
	//set change for ea stock
	void setChange(int stockIndex, String line)
	{
		int i;
		String changeStr = "";
		//negative case first
		if ((i = line.indexOf('-')) != -1) //is negative
		{
			i++;
			while (line.charAt(i) != ',')
			{
				changeStr += line.charAt(i);
				i++;
			}
		}
		else //is positive change
		{
			i = line.indexOf(':');
			i++;
			while (line.charAt(i) != ',')
			{
				changeStr += line.charAt(i);
				i++;
			}
		}
		float change;
    	try {
    		change = Float.parseFloat(changeStr);
    		int sign = line.indexOf('-');
    		if (sign != -1)
    			change *= -1;
    	}catch (NumberFormatException e) {
    		change = 0;
    	}
		//float change = Float.parseFloat(changeStr);
		stocks.get(stockIndex).setChange(change);
	}
	
	//set changePercentage for ea stock
	void setChangePercentage(int stockIndex,String line)
	{
		int i = line.indexOf('(');
		i++;
		char sign = line.charAt(i);
		i++;
		String changeStr = "";
		while (line.charAt(i) != '%' && i <50)
		{
			changeStr += line.charAt(i);
			i++;
		}
		float change;
    	try {
    		change = Float.parseFloat(changeStr);
    		if (sign == '-')
    			change *= -1;
    	}catch (NumberFormatException e) {
    		change = 0;
    	}
		//float change = Float.parseFloat(changeStr);
		//if (sign == '-')
			//change *= -1;
		stocks.get(stockIndex).setChangePercent(change);
	}
	
	//sets the price of each stock
	void setPrice(int stockIndex, String line)
	{
		int j = line.indexOf(':');
    	j+= 2; //take i t start of price
    	char c = line.charAt(j);
    	String priceString = "";
    	priceString += line.charAt(j);

    	while (j < line.length() - 2 || line.charAt(j+1) != ',')
    	{
    		j++;
    		priceString += line.charAt(j);
    	}
    	
    	float currentPrice;
    	try {
    		currentPrice = Float.parseFloat(priceString);
    	}catch (NumberFormatException e) {
    		currentPrice = 0;
    	}
    	
    	//float currentPrice = Float.parseFloat(priceString);
    	stocks.get(stockIndex).setPrice(currentPrice);
	}
	
	//Fills up stocks list with empty stocks identified by stock symbol
	void initStocks()
	{
		/*
		stocks.add(new Stock("AAPL")); //apple
		stocks.add(new Stock("AMZN")); //amazon
		stocks.add(new Stock("BRK-A")); //berkshire hathway
		stocks.add(new Stock("GOOGL")); //google
		stocks.add(new Stock("WMT")); //walmart
		*/
		URL url = null;
		try {
			url = new URL("https://financialmodelingprep.com/api/v3/stock/real-time-price");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int counter = 0;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {
		    //System.out.println(line);
		    	if (line.contains("symbol"))
		    	{
		    		int i = line.indexOf(':');
		    		i+=3;
		    		String s = "";
		    		while (line.charAt(i) != '"')
		    		{
		    			s += line.charAt(i);
		    			i++;
		    		}
		    		//System.out.println(s);
		    		stocks.add(new Stock(s));
		    		counter++;
		    		
		    	}
		    	if (counter > MAXSTOCKS)
		    		break;
		    
		  }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
