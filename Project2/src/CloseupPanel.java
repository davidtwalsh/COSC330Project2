import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class CloseupPanel extends JPanel{
	
	StockMaster stockMaster;
	JTabbedPane tabbedPane;
	PortfolioPanel portfolioPanel;
	
	JPanel gridPanel = new JPanel();
	JPanel strategyPanel = new JPanel();
	JPanel buyPanel = new JPanel();
	JPanel sellPanel = new JPanel();
	JPanel resultsPanel = new JPanel();
	
	JLabel companyLabel = new JLabel("Company Name");
	JLabel priceLabel = new JLabel("Price");
	JLabel imageLabel = new JLabel("");
	JLabel changeLabel = new JLabel("Change");
	JLabel changePercLabel = new JLabel("Change perc");
	JLabel volumeLabel = new JLabel("Volume");
	JButton adviseButton = new JButton("Advise on this stock");
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	//Strategy STUFF
	StockDecider stockDecider = new StockDecider();
	Stock currentStock;
	JButton strategy1 = new JButton("Strategy 1");
	JButton strategy2 = new JButton("Strategy 2");
	JButton strategy3 = new JButton("Strategy 3");
	JButton strategy4 = new JButton("Strategy 4");
	
	//For portfolio, BUY/SELL STUFF
	JLabel resultsLabel = new JLabel("");
	JButton buyBtn = new JButton("BUY");
	JTextField buyAmount = new JTextField("Buy Amount");
	JButton sellBtn = new JButton("SELL");
	JTextField sellAmount = new JTextField("Sell Amount");
	
	public CloseupPanel(StockMaster m, JTabbedPane tp, PortfolioPanel p)
	{
		
		stockMaster = m;
		tabbedPane = tp;
		portfolioPanel = p;
		portfolioPanel.closeupPanel = this;
		setLayout(new GridBagLayout());
			
		initStockInfo();
		initStrategy();
		initBuySell();
		initResults();
		
		gotButtonDown(0);
	}
	
	void initResults() {
		gbc.gridy = 4;
		resultsPanel.add(resultsLabel);
		add(resultsPanel,gbc);
	}
	
	void initBuySell()
	{
		buyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String buyInputString = buyAmount.getText();
				int buyAmt;
				try {
		    		buyAmt = Integer.parseInt(buyInputString);
		    	}catch (NumberFormatException e1) {
		    		buyAmt = 0;
		    	}
				resultsLabel.setText("Bought " + buyAmt + " Stocks of " + currentStock.getSymbol());
				portfolioPanel.AddStock(currentStock, buyAmt);
			}
		});
		sellBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sellInputString = sellAmount.getText();
				int sellAmt;
				try {
		    		sellAmt = Integer.parseInt(sellInputString);
		    	}catch (NumberFormatException e1) {
		    		sellAmt = 0;
		    	}
				int amtSold = portfolioPanel.RemoveStock(currentStock, sellAmt);
				resultsLabel.setText("Sold " + amtSold + " Stocks of " + currentStock.getSymbol());
			}
		});
		
		sellAmount.setColumns(10);
		buyAmount.setColumns(10);
		buyPanel.add(buyBtn);
		buyPanel.add(buyAmount);
		sellPanel.add(sellBtn);
		sellPanel.add(sellAmount);
		
		gbc.gridy = 2;
		add(buyPanel,gbc);
		gbc.gridy = 3;
		add(sellPanel,gbc);
	}

	void initStrategy(){
		gbc.gridx = 0;
		gbc.gridy = 1;
		//testPanel.setLayout(new GridLayout(1,4,0,0));
		strategyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		strategy1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stockDecider.setStrategy(new StrategyOne());
			}
		});
		strategy2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stockDecider.setStrategy(new StrategyTwo());
			}
		});
		strategy3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stockDecider.setStrategy(new StrategyThree());
			}
		});
		strategy4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stockDecider.setStrategy(new StrategyFour());
			}
		});
		strategyPanel.add(strategy1);
		strategyPanel.add(strategy2);
		strategyPanel.add(strategy3);
		strategyPanel.add(strategy4);
		
		add(strategyPanel,gbc);
	}
	
	void initStockInfo() {
		gbc.gridx = 0;
		gbc.gridy = 0;
		companyLabel.setFont(new Font("Ariel",Font.PLAIN,32));
		companyLabel.setSize(100, 500);
		
		priceLabel.setFont(new Font("Ariel",Font.PLAIN,28));
		changeLabel.setFont(new Font("Ariel",Font.PLAIN,28));
		changePercLabel.setFont(new Font("Ariel",Font.PLAIN,28));
		volumeLabel.setFont(new Font("Ariel",Font.PLAIN,28));
		adviseButton.setFont(new Font("Ariel",Font.PLAIN,28));
		
		imageLabel.setIcon(new ImageIcon("upTriangle64.png"));
		
		adviseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				adviseButton.setText("ADVICE: " + stockDecider.makeDecision(currentStock));
				
			}
			
		});
		
		//rows,columns,horizontal gap, vertical gap
		gridPanel.setLayout(new GridLayout(7,1,0,0));
		
		gridPanel.add(companyLabel);
		gridPanel.add(priceLabel);
		gridPanel.add(imageLabel);
		gridPanel.add(changeLabel);
		gridPanel.add(changePercLabel);
		gridPanel.add(volumeLabel);
		gridPanel.add(adviseButton);
		
		add(gridPanel,gbc);
	}
	
	public void gotButtonDown(int indexAt) {
		//company name
		currentStock = stockMaster.stocks.get(indexAt);
		Stock s = stockMaster.stocks.get(indexAt);
		String labelText = String.format("<html><div WIDTH=%d>%s</div></html>", 600, s.getCompany());
		companyLabel.setText(labelText);
		
		//price label
		float price = s.getPrice();
		String formattedPrice = "Price: " + String.format("%.2f", price);
		priceLabel.setText(formattedPrice);
		
		//changeLabel
		float change = s.getChange();
		String changeStr = "";
		if (change >=0)
		{
			changeStr = "Change: " + "+" + String.format("%.2f", change);
		}
		else
			changeStr = "Change: " + String.format("%.2f", change);
		changeLabel.setText(changeStr);
		if (change >= 0)
		{
			changeLabel.setForeground(Color.GREEN);
		}
		else
			changeLabel.setForeground(Color.RED);
		
		//changePercLabel
		float changePercent = s.getChangePercent();
		String changePercStr;
		if (changePercent >= 0)
		{
			changePercStr = "Change Percent: " +"+" + String.format("%.2f", changePercent) + "%";
		}
		else
		{
			changePercStr = "Change Percent: " + String.format("%.2f", changePercent) + "%";
		}
		changePercLabel.setText(changePercStr);
		if (changePercent >= 0)
			changePercLabel.setForeground(Color.GREEN);
		else
			changePercLabel.setForeground(Color.RED);
		
		//volume label
		volumeLabel.setText("Volume: " + Integer.toString(s.getVolume()));
		
		//image icon
		if (change >= 0)
			imageLabel.setIcon(new ImageIcon("upTriangle64.png"));
		else
			imageLabel.setIcon(new ImageIcon("downTriangle64.png"));
		
		
	}
}
