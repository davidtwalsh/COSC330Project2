import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PortfolioPanel extends JPanel{
	
	ArrayList<StockStruct> stocks = new ArrayList<StockStruct>();
	ArrayList<JComponent> components = new ArrayList<JComponent>();
	StockMaster stockMaster;
	int NUMCOLUMNS = 3;
	GridBagConstraints gbc = new GridBagConstraints();
	public CloseupPanel closeupPanel;
	JTabbedPane tabbedPane;
	
	public PortfolioPanel(StockMaster m,JTabbedPane p) {
		stockMaster = m;
		tabbedPane = p;
		setLayout(new GridBagLayout());
		
		gbc.weightx = 1;
		gbc.weighty = 1;
		UpdatePortfolio();
	}
	
	public void AddStock(Stock s, int amt)
	{
		for (int i=0; i< stocks.size();i++)
		{
			Stock currStock = stocks.get(i).stock;
			String symb = currStock.getSymbol();
			if (symb.equals(s.getSymbol())) //already owns stock then
			{
				stocks.get(i).quantity+= amt;
				UpdatePortfolio();
				return;
			}
		}
		//if reached here they dont own stock
		stocks.add(new StockStruct(s,amt));
		UpdatePortfolio();
	}
	
	public int RemoveStock(Stock s, int amt)
	{
		for (int i=0; i< stocks.size();i++)
		{
			Stock currStock = stocks.get(i).stock;
			String symb = currStock.getSymbol();
			if (symb.equals(s.getSymbol())) //already owns stock then
			{
				int numRemoved;
				if (stocks.get(i).quantity - amt < 0)
				{
					numRemoved = stocks.get(i).quantity;
					stocks.get(i).quantity = 0;
				}
				else
				{
					numRemoved = stocks.get(i).quantity - amt;
					stocks.get(i).quantity -= amt;
				}
				if (stocks.get(i).quantity <= 0)
				{
					stocks.remove(i);
				}
				UpdatePortfolio();
				return numRemoved;
			}
		}
		return 0;
	}
	
	void ClearComponents()
	{
		Component[] components = getComponents();
		for (Component c: components)
		{
			remove(c);
		}
		
	}
	
	int getIndexOfStockMaster(Stock s)
	{
		for (int i=0; i < stockMaster.stocks.size();i++)
		{
			Stock curr = stockMaster.stocks.get(i);
			if (curr.getSymbol().equals(s.getSymbol()))
			{
				return i;
			}
		}
		return 0;
	}
	
	void UpdatePortfolio()
	{	
		int tableFontSize = 26;
		ClearComponents();
		InitHeader();
		gbc.gridy = 1;
		for (int i = 0; i < stocks.size(); i++)
		{
			gbc.gridx = 0;
			//symbol
			JButton symbolLabel = new JButton(stocks.get(i).stock.getSymbol());
			final int indexAt = getIndexOfStockMaster(stocks.get(i).stock);
			symbolLabel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					closeupPanel.gotButtonDown(indexAt);
					tabbedPane.setSelectedIndex(0);
				}
			});
			symbolLabel.setFont(new Font("Consolas",Font.PLAIN,tableFontSize));
			components.add(symbolLabel);
			add(symbolLabel,gbc);
			//price
			gbc.gridx = 1;
			float price = stocks.get(i).stock.getPrice();
			String formattedPrice = "$" + String.format("%.2f", price);
			JLabel priceLabel = new JLabel(formattedPrice);
			priceLabel.setFont(new Font("Consolas",Font.PLAIN,tableFontSize));
			add(priceLabel,gbc);
			components.add(priceLabel);
			//quantity
			gbc.gridx = 2;
			int q = stocks.get(i).quantity;
			JLabel quantityLabel = new JLabel(Integer.toString(q));
			quantityLabel.setFont(new Font("Consolas",Font.PLAIN,tableFontSize));
			add(quantityLabel,gbc);
			components.add(quantityLabel);
			//total value
			gbc.gridx = 3;
			float totalV = price * q;
			String formattedTotalV = "$" + String.format("%.2f", totalV);
			JLabel totalValueLabel = new JLabel(formattedTotalV);
			totalValueLabel.setFont(new Font("Consolas",Font.PLAIN,tableFontSize));
			add(totalValueLabel,gbc);
			components.add(totalValueLabel);
			
			gbc.gridy++;
		}
	}
	
	void InitHeader()
	{
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel symbolHeader = new JLabel("Symbol");
		add(symbolHeader);
		JLabel priceHeader = new JLabel("Price");
		gbc.gridx++;
		add(priceHeader);
		gbc.gridx++;
		JLabel quantityHeader = new JLabel("Quantity Owned");
		add(quantityHeader);
		JLabel totalHeader = new JLabel("Total Value");
		gbc.gridx++;
		add(totalHeader);
		
		int headFontSize = 30;
		symbolHeader.setFont(new Font("Consolas",Font.BOLD,headFontSize));
		priceHeader.setFont(new Font("Consolas",Font.BOLD,headFontSize));
		quantityHeader.setFont(new Font("Consolas",Font.BOLD,headFontSize));
		totalHeader.setFont(new Font("Consolas",Font.BOLD,headFontSize));
	}
	
	public class StockStruct
	{
		public Stock stock;
		public int quantity;
		public StockStruct(Stock s, int q)
		{
			stock = s;
			quantity = q;
		}
	}
	
}
