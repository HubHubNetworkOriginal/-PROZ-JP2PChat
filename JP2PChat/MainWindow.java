package JP2PChat;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import networking.MessageListener;
import networking.MessageSender;

public class MainWindow extends JFrame implements WritableGUI{
	/*
	 * TODO : cosmetics in GUI
	 */
	private static final int width = 683;
	private static final int height = 550;
	
	private static final int readBoxXPos = 20;
	private static final int readBoxYPos = 50;
	private static final int readBoxXSize = 600;
	private static final int readBoxYSize = 400;
	
	private static final int writeBoxXPos = readBoxXPos;
	private static final int writeBoxYPos = readBoxYSize + readBoxYPos + 10;
	private static final int writeBoxXSize = readBoxXSize - 130;
	private static final int writeBoxYSize = 40; // TO SET

	private static final int ipBarXPos = readBoxXPos;
	private static final int ipBarYPos = 10;
	private static final int ipBarXSize = 150;
	private static final int ipBarYSize = 20; // TO SET
	//create variable for separator size to eliminate 10 in code
	private static final int listenPortBarXPos = readBoxXPos + ipBarXSize + 10;
	private static final int listenPortBarYPos = 10;
	private static final int listenPortBarXSize = 75;
	private static final int listenPortBarYSize = ipBarYSize; // TO SET
	
	private static final int sendPortBarXPos = listenPortBarXPos + listenPortBarXSize + 10;
	private static final int sendPortBarYPos = 10;
	private static final int sendPortBarXSize = 75;
	private static final int sendPortBarYSize = ipBarYSize; // TO SET
	
	private static final int connectBtnXPos = readBoxXPos + readBoxXSize - 100;
	private static final int connectBtnYPos = 10;
	private static final int connectBtnXSize = 100;
	private static final int connectBtnYSize = 35; // TO SET
	
	//private static final int disconnectBtnXPos = connectBtnXPos + connectBtnXSize + 10;
	//private static final int disconnectBtnYPos = 10;
	//private static final int disconnectBtnXSize = 120;
	//private static final int disconnectBtnYSize = ipBarYSize; 
	// TO SET
	
	private static final int sendBtnXPos = writeBoxXPos + writeBoxXSize + 10;
	private static final int sendBtnYPos = writeBoxYPos;
	private static final int sendBtnXSize = 120;
	private static final int sendBtnYSize = 40; // TO SET
	
	private JPanel mainPanel;
	private JTextArea readBox; //append
	private JScrollPane scrollPanel;
	private JTextField writeBox;
	private JTextField ipBar;
	private JTextField listenPortBar;
	private JTextField sendPortBar;
	private JButton connectBtn;
	//private JButton disconnectBtn;
	private JButton sendBtn;
	
	private MessageListener listener;
	private MessageSender transmitter;
	
	public MainWindow() {
    	//listener = new MessageListener(this);
	    mainPanel = new JPanel();
		readBox = new JTextArea();
		scrollPanel = new JScrollPane(readBox);
		writeBox = new JTextField();
		ipBar = new JTextField();
		listenPortBar = new JTextField();
		sendPortBar = new JTextField();
		connectBtn = new JButton("Connect");
//		disconnectBtn = new JButton("disConnect");
		sendBtn = new JButton("Send");

		connectBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				listenButtonActionPerformed(e);
			}
		});
		
		sendBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				sendButtonActionPerformed(e);
			}
		});
		
		scrollPanel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    });
		
		//setVisible(true);
		arrangeItems();
	}
	
	public void arrangeItems () {
		add(mainPanel);
		setSize(width, height);
		setTitle("JP2PChat alpha");
		setLayout(null); //TO CHECK LATER
		
		/*
		readBox.setBounds(readBoxXPos, readBoxYPos,
				readBoxXSize, readBoxYSize);
		//TODO move writing cursor to bottom of text field
		add(readBox);
		*/
		scrollPanel.setBounds(readBoxXPos, readBoxYPos,
				readBoxXSize, readBoxYSize);
		//TODO move writing cursor to bottom of text field
		add(scrollPanel);
		
		writeBox.setBounds(writeBoxXPos, writeBoxYPos,
				writeBoxXSize, writeBoxYSize);
		add(writeBox);
		
		ipBar.setBounds(ipBarXPos, ipBarYPos,
				 ipBarXSize, ipBarYSize);
		add(ipBar);
		
		listenPortBar.setBounds(listenPortBarXPos, listenPortBarYPos,
				listenPortBarXSize, listenPortBarYSize);
		add(listenPortBar);
		
		sendPortBar.setBounds(sendPortBarXPos, sendPortBarYPos,
				sendPortBarXSize, sendPortBarYSize);
		add(sendPortBar);
		
		connectBtn.setBounds(connectBtnXPos, connectBtnYPos, 
				connectBtnXSize, connectBtnYSize);

		add(connectBtn);
		
		sendBtn.setBounds(sendBtnXPos, sendBtnYPos, 
				sendBtnXSize, sendBtnYSize);
		add(sendBtn);
		
//		disconnectBtn.setBounds(disconnectBtnXPos, disconnectBtnYPos, 
//		   disconnectBtnXSize, disconnectBtnYSize);
//		add(disconnectBtn);
		
		//pack();
	}
	
	public void write (String str) {
		readBox.append(str + System.lineSeparator());
	}
	
	private void listenButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if(listenPortBar.getText().equals(""))
			return;
		
		listener = new MessageListener(this, Integer.parseInt(listenPortBar.getText()));
		listener.start();
	}
	
	private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if(!(writeBox.getText().equals("") || ipBar.getText().equals("") 
										 || sendPortBar.getText().equals(""))) {
			
		transmitter = new MessageSender(writeBox.getText(), ipBar.getText(), 
									    Integer.parseInt(sendPortBar.getText()));
		transmitter.start();
		}
		writeBox.setText("");
	}
}
