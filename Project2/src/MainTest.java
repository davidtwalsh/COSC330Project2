import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MainTest {

	public static void main(String[] args) throws Exception {
		StockMaster stockMaster = new StockMaster();
		
		
		for (int i=0; i <stockMaster.stocks.size();i++)
		{
			stockMaster.stocks.get(i).printStock();
		}
		

	}

}
