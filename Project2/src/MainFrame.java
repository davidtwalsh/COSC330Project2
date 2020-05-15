import javax.swing.*;

public class MainFrame extends JFrame{

	public StockMaster stockMaster;
	GeneralPanel generalPanel;
	CloseupPanel closeupPanel;
	PortfolioPanel portfolioPanel;
	JTabbedPane tabbedPane = new JTabbedPane();
	
	public MainFrame() {
	
		InitStockMaster();
		portfolioPanel = new PortfolioPanel(stockMaster,tabbedPane);
		closeupPanel = new CloseupPanel(stockMaster,tabbedPane,portfolioPanel);
		generalPanel = new GeneralPanel(stockMaster,closeupPanel);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(generalPanel),new JScrollPane(closeupPanel));
		tabbedPane.add("Stock Info", splitPane);
		tabbedPane.add("Portfolio",new JScrollPane(portfolioPanel));
		add(tabbedPane);
		
	
	}

	public static void main(String[] args) {
		
		MainFrame mainFrame = new MainFrame();
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	//Creates Stockmaster which populates stock data
	void InitStockMaster()
	{
		try {
			stockMaster = new StockMaster();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
