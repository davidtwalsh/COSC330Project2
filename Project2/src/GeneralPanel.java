import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GeneralPanel extends JPanel {
	

	int NUMCOLS = 6;
	int NUMROWS;
	
	StockMaster stockMaster;
	CloseupPanel closeupPanel;
	
	JPanel dataPanel = new JPanel();
	
	public GeneralPanel(StockMaster m,CloseupPanel c)
	{
		stockMaster = m;
		closeupPanel = c;
		NUMROWS = stockMaster.getSize() + 1;

		setLayout(new FlowLayout(FlowLayout.LEFT));
		createDataPanel();
		add(dataPanel);
		

	}
	void createDataPanel()
	{
		dataPanel.setBackground(Color.white);
		//rows,colums,horizontal gap, vertical gap
		dataPanel.setLayout(new GridLayout(NUMROWS,NUMCOLS,0,0));
		
		createDataHeader();
		
		for (int i=0; i < stockMaster.getSize(); i++)
		{
			Stock s = stockMaster.stocks.get(i);
			//symbol label
			JLabel symbolLabel = new JLabel(s.getSymbol());
			//volume label
			JLabel volumeLabel = new JLabel(Integer.toString(s.getVolume()));
			//price label
			float price = s.getPrice();
			String formattedPrice = String.format("%.2f", price);
			JLabel priceLabel = new JLabel(formattedPrice);
			//change label
			float change = s.getChange();
			String changeStr = "";
			if (change >=0)
			{
				changeStr = "+" + String.format("%.2f", change);
			}
			else
				changeStr = String.format("%.2f", change);
			JLabel changeLabel = new JLabel(changeStr);
			if (change >= 0)
			{
				changeLabel.setForeground(Color.GREEN);
			}
			else
				changeLabel.setForeground(Color.RED);
			//change percent
			float changePercent = s.getChangePercent();
			String changePercStr;
			if (changePercent >= 0)
			{
				changePercStr = "+" + String.format("%.2f", changePercent) + "%";
			}
			else
			{
				changePercStr = String.format("%.2f", changePercent) + "%";
			}
			JLabel changePercentLabel = new JLabel(changePercStr);
			if (changePercent >= 0)
				changePercentLabel.setForeground(Color.GREEN);
			else
				changePercentLabel.setForeground(Color.RED);
			JButton btn = new JButton();
			final Integer index = new Integer(i);
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					closeupPanel.gotButtonDown(index);
				}
				
			});
			dataPanel.add(symbolLabel);
			dataPanel.add(volumeLabel);
			dataPanel.add(priceLabel);
			dataPanel.add(changeLabel);
			dataPanel.add(changePercentLabel);
			dataPanel.add(btn);
		}
	}
	void createDataHeader()
	{
		JLabel headerSymbol = new JLabel("Symbol");
		JLabel headerVolume = new JLabel("Volume");
		JLabel headerPrice = new JLabel("Price");
		JLabel headerChange = new JLabel("Change");
		JLabel headerChangePerc = new JLabel("Change %");
		JLabel headerBlank = new JLabel("");
		int headFontSize = 20;
		headerSymbol.setFont(new Font("Consolas",Font.BOLD,headFontSize));
		headerVolume.setFont(new Font("Consolas",Font.BOLD,headFontSize));
		headerPrice.setFont(new Font("Consolas",Font.BOLD,headFontSize));
		headerChange.setFont(new Font("Consolas",Font.BOLD,headFontSize));
		headerChangePerc.setFont(new Font("Consolas",Font.BOLD,headFontSize));
		dataPanel.add(headerSymbol);
		dataPanel.add(headerVolume);
		dataPanel.add(headerPrice);
		dataPanel.add(headerChange);
		dataPanel.add(headerChangePerc);
		dataPanel.add(headerBlank);
		
	}
}
