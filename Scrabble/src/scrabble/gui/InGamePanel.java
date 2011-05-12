package scrabble.gui;

import java.awt.*; 
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.*;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import scrabble.dataservice.*;
import scrabble.game.Board;
import scrabble.Player;
import scrabble.gui.inGameComponents.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InGamePanel extends JPanel{
	private MainFrame mainFrame;
	private PlayerPanel playerPanel;
	private ChatPanel chatPanel;
	private GamePanel gamePanel;
	private Board board;
	private Player player;
	private GameClient client;
	Image bgimage = null;
	
	public InGamePanel(){}
	
	public InGamePanel(MainFrame f, GameClient _client)
	{
		client = _client;
		mainFrame = f;
		board = client.getBoard();
		player = client.getPlayer();
		System.out.println("in game");
		setLayout(null);
		
		addPlayerPanel();
		addGamePanel();
		addChatPanel();
		addStartGameButton();
		addResignButton();
		addQuitGameButton();
		try{
			bgimage = ImageIO.read(new File("images/background.png"));
			bgimage = bgimage.getScaledInstance(800, 600, Image.SCALE_AREA_AVERAGING);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
			
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	    g.drawImage(bgimage, 0, 0, null);
	}
	
	// add panel contains players
	private void addPlayerPanel()
	{
		playerPanel = new PlayerPanel();
		playerPanel.setBounds(20, 0, 800, 80);
		add(playerPanel);
	}
	
	// add chat panel
	private void addChatPanel()
	{
		chatPanel = new ChatPanel();
		chatPanel.setBounds( 530, 200, 250, 360);
		add(chatPanel);
	}
	
	// add panel contains game stuffs
	private void addGamePanel()
	{
		gamePanel = new GamePanel(client);
		gamePanel.setBounds(0, 80, 550, 500);
		add(gamePanel);
	}
	
	/**
	 * Button to start game
	 */
	private void addStartGameButton()
	{
		if (client.isMaster()){
			JButton b = new MyButton("Start game", 120, 30, 600, 100, null);
			add(b);
		}
	}
	
	/**
	 * Button to resign
	 */
	private void addResignButton()
	{
		JButton b = new MyButton("Resign", 120, 30, 530, 150, null);
		add(b);
	}
	
	/**
	 * Button to quit game
	 */
	private void addQuitGameButton()
	{
		JButton b = new MyButton("Quit", 120, 30, 660, 150, null);
		add(b);
	}

}


