package SunSnake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
//延伸功能：速度越来越快：当长度小于10时一个速度
//延伸功能：不能撞墙
public class MPanel extends JPanel implements KeyListener , ActionListener{

	ImageIcon title = new ImageIcon("./resource/title.jpg");
	ImageIcon body = new ImageIcon("./resource/body.png");
	ImageIcon food = new ImageIcon("./resource/food.png");
	ImageIcon up = new ImageIcon("./resource/up.png");
	ImageIcon down = new ImageIcon("./resource/down.png");
	ImageIcon left = new ImageIcon("./resource/left.png");
	ImageIcon right = new ImageIcon("./resource/right.png");

	// 数据结构
	int len = 3;
	int[] snakex = new int[800];
	int[] snakey = new int[800];

	// 定义方向
	String fx = "R";
	boolean isStarted = false;
	Timer timer = new Timer(170, this);
	
	//定义食物
	int foodx;
	int foody;
	Random random = new Random();
	
	boolean isFailed = false; 
	int score = 0;

	//构造函数，程序一开始就会进入构造函数
	public MPanel() {
		initSnake();  
		this.setFocusable(true);
		this.addKeyListener(this);
		timer.start();
	}

	// 初始化方法
	public void initSnake() {
		len = 3;
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
		foodx = 25 + 25*random.nextInt(34);
		foody = 75 + 25*random.nextInt(24);
		fx = "R";
		score = 0;
	}
	
	

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		title.paintIcon(this, g, 25, 11);	
		g.fillRect(25, 75, 850, 600);
		//分数
				g.setColor(Color.white);
				g.drawString("len:"+len, 750, 40);
				g.drawString("score:"+score, 750, 50);
				
		
		//调整速度
				if (len>10) {
					timer.setDelay(150);
				}
				
		// 放置蛇头
		if (fx == "R") {
			
			right.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (fx == "L") {
			left.paintIcon(this, g, snakex[0], snakey[0]);
			
		} else if (fx == "U") {
			up.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (fx == "D") {
			down.paintIcon(this, g, snakex[0], snakey[0]);
		}
		// 画蛇的身体
		for (int i = 1; i < len; i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
			
		}

		food.paintIcon(this, g, foodx, foody);
		// 按开始按钮可以开始游戏
		if (!isStarted) {
			g.setColor(Color.green);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Press Space To Start", 150, 250);
		}
		
		//重新开始功能
		if (isFailed) {
			g.setColor(Color.red);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("failed: Press Space To ReStart", 100, 250);
		}
		

	}
    
	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode= e.getKeyCode();
		if (keyCode==KeyEvent.VK_SPACE) {
			if(isFailed){
				isFailed=false;
				initSnake();
				}else {
				isStarted = !isStarted;	
			}
			repaint();	
		}if (keyCode==KeyEvent.VK_LEFT) {
			fx="L";	
		}if (keyCode==KeyEvent.VK_RIGHT) {
			fx="R";	
		}if (keyCode==KeyEvent.VK_UP) {
			fx="U";	
		}if (keyCode==KeyEvent.VK_DOWN) {
			fx="D";	
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isStarted && !isFailed) {
			for (int i = len-1; i > 0; i--) {
				snakex[i]=snakex[i-1];
				snakey[i]=snakey[i-1];
			}
			if(fx=="R") {
				snakex[0]=snakex[0]+25;
				if(snakex[0]>850)isFailed=true;
			}else if(fx=="L") {
				snakex[0]=snakex[0]-25;
				if(snakex[0]<25)isFailed=true;
			}else if(fx=="U") {
				snakey[0]=snakey[0]-25;
				if(snakey[0]<75)isFailed=true;
			}else if(fx=="D") {
				snakey[0]=snakey[0]+25;
				if(snakey[0]>650)isFailed=true;
			}
			//吃食物
			if (snakex[0]==foodx &&snakey[0]==foody) {
				len = len+1;
				score = score + 10;
				foodx = 25 + 25*random.nextInt(34);
				foody = 75 + 25*random.nextInt(24);
			}
			//判断游戏是否结束
			for (int i = 1; i < len; i++) {
				if (snakex[i]==snakex[0] && snakey[i]==snakey[0]) {
					isFailed = true;
					
				}
			}
			//注意repaint方法重新调用了paintCompoent方法
			repaint();
		}
		
		timer.start();
	}

	
	
}
