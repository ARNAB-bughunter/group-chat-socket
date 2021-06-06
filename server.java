import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class server extends JFrame implements ActionListener,MouseMotionListener,MouseListener{
	//static int client_no=0;
	static server frame;
	static JButton close=new JButton("CLOSE");
    static JButton bt=new JButton("START");
    static int xx=0;
    static int yy=0;
    static ServerSocket ss;
    static Socket soc;



	public void paint(Graphics g){
		super.paint(g);
		g.setFont(new Font("Gabriola",Font.BOLD,30));
		g.drawString("Press to start server",30,100);
	}
	public static void main(String[] args) throws Exception{
		frame=new server();
		bt.addActionListener(frame);
		close.addActionListener(frame);
		Container c=frame.getContentPane();
		frame.setSize(265,200);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.addMouseMotionListener(frame);
		frame.addMouseListener(frame);

        c.setBackground(Color.cyan);
        c.setLayout(null);
        bt.setBounds(92,0,80,40);
        close.setBounds(92,150,80,40);
        c.add(bt);
        c.add(close);
      
	}
	public void actionPerformed(ActionEvent ac){
		if(ac.getSource()==bt){
		bt.setEnabled(false);
		try{
		ss=new ServerSocket(9999);
		Vector<Socket> client=new Vector<Socket>();
		System.out.println("server ready.....");
		new Thread(){
			public void run(){
		while(true){
			try{
			soc=ss.accept();
		    }catch(Exception e){}
			client.add(soc);
			//client_no=client.size();
			repaint();
			new Thread(){
				public void run(){
					try{
	       BufferedReader read=new BufferedReader(new InputStreamReader(soc.getInputStream()));
		   String s;
		   while ((s=read.readLine())!=null) {
		   		for(int i=0;i<client.size();i++){
		   			PrintWriter writer=new PrintWriter(client.get(i).getOutputStream(),true);
		   			writer.println(s);
		   			writer.flush();
		   		          }
	    	           }
             		}catch(Exception e){}
				}
			}.start();
	      }
	        }
	     }.start();
 	  }catch(Exception e){}	
 	}

 	if(ac.getSource()==close){
 		System.exit(0);
    	}   
	}
	public void mouseMoved(MouseEvent mouse){}
	public void mouseExited(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseReleased(MouseEvent event){}
	public void mouseClicked(MouseEvent event){} 
    public void mousePressed(MouseEvent event){
    	xx=event.getX();
    	yy=event.getY();
    }
	public void mouseDragged(MouseEvent mouse){
		int x=mouse.getXOnScreen();
		int y=mouse.getYOnScreen();
		frame.setLocation(x-xx,y-yy);

	}
}