package SunSnake;

import javax.swing.JFrame;

public class Msnake{

	public static void main(String[] args) {
	
		JFrame frame = new JFrame();
		//设置大小
		frame.setBounds(10, 10, 900, 720);
		//能不能手动拖动去改变大小
		frame.setResizable(false);
		//点×应该做什么
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new MPanel());
		//是否显示出来
		frame.setVisible(true);
		
	}

}
