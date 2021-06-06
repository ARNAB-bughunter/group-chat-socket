import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.*;
class client extends JFrame implements ActionListener,MouseMotionListener,MouseListener,FocusListener{
    static Container c;
    static  client frame;
    static JTextArea intext,outtext;
    static JButton send_bt;
    static JScrollPane  jsp1,jsp2; 
    static  JLabel label;
    static JPanel panel;
    static String name;
    static JTextField name_input;
    static JButton input_bt;
    static Socket soc;
    static JButton close,close1;
    static String from_server;
    int xx=0,yy=0;


	public static void main(String[] args){
		try{
		    soc=new Socket("localhost",9999);
	     }catch(Exception e){}
	  
        frame=new client();
        c=frame.getContentPane();
        c.setLayout(null);    
        frame.setSize(480,700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        c.setBackground(Color.black);
        frame.setUndecorated(true);
        frame.addMouseMotionListener(frame);
        frame.addMouseListener(frame);
        frame.setVisible(true); 

        /******/
        panel=new JPanel(){
        	public  void paint(Graphics g){
        		super.paint(g);
        		g.setFont(new Font("Gabriola",Font.BOLD,85));
        		g.setColor(Color.red);
        		g.drawString("WELCOME",40,200);
        		g.setColor(Color.green);
        		int j=0;
        		for(int i=0;i<100;i++){
        			g.drawLine(j,100,j,250);
        			j+=5;
        		}
        		j=100;
        		for(int i=0;i<31;i++){
        			g.drawLine(0,j,500,j);
        			j+=5;
        		}
        	}
        };
        panel.setLayout(null);
        panel.setBounds(0,225,500,300);
        panel.setBackground(Color.cyan);
        name_input=new JTextField("Register name");
        name_input.setHorizontalAlignment(SwingConstants.CENTER);
        name_input.setBounds(20,0,440,50);
        name_input.setFont(new Font("Arial",Font.ITALIC,45));
        name_input.addFocusListener(frame);
        input_bt=new JButton("LOG-IN");
        input_bt.setBounds(50,50,380,50);
        input_bt.addActionListener(frame);
        input_bt.setFont(new Font("Ink Free",Font.BOLD,45));
        close1=new JButton("CLOSE");
        close1.setBounds(0,250,500,50);
        close1.addActionListener(frame);
        panel.add(name_input);
        panel.add(input_bt);
        panel.add(close1);
        c.add(panel);
        frame.repaint();
        frame.revalidate();
        /*****/
	}
    public void	actionPerformed(ActionEvent action){
        if(action.getSource()==close || action.getSource()==close1){
            try{
            soc.close();
               }catch(Exception e){}
            System.exit(0);
        }
    	if(action.getSource()==send_bt){
    		try{
    		PrintWriter writer=new PrintWriter(soc.getOutputStream(),true);
			writer.flush();
	     	writer.println(name+":"+intext.getText());
	     	writer.flush();	
	     	intext.setText("");
	       }catch(Exception e){};
    	}

    	if(action.getSource()==input_bt){
    		name=name_input.getText();
    		c.removeAll();
    		make();
             new Thread(){
            public  void run(){
                try{
                 BufferedReader read=new BufferedReader(new InputStreamReader(soc.getInputStream()));
                 while((from_server=read.readLine())!=null){
                    outtext.setText(outtext.getText()+"\n"+from_server);
                    }
                }catch(Exception e){}
              
               }  
            }.start();  
    	}
    }
    public static void make(){
    	intext=new JTextArea();
        intext.setLineWrap(true);
        intext.setWrapStyleWord(true); 
        intext.setFont(new Font("Comic Sans MS",Font.BOLD,20));
        jsp1=new JScrollPane(intext);
        jsp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp1.setBounds(5,650,400,50);
        c.add(jsp1);
        /****/
        outtext=new JTextArea();
        outtext.setLineWrap(true);
        outtext.setWrapStyleWord(true);
        outtext.setFont(new Font("Comic Sans MS",Font.BOLD,20));
        outtext.setBackground(Color.orange);
        outtext.setEditable(false);
        jsp2=new JScrollPane(outtext);
        jsp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp2.setBounds(0,40,485,600);
        c.add(jsp2);
        /****/
        send_bt=new JButton("Send");
        send_bt.setBounds(410,650,70,50);
        send_bt.setFont(new Font("Comic Sans MS",Font.BOLD,15));
        send_bt.setBackground(new Color(0,177,69));
        send_bt.addActionListener(frame);
        c.add(send_bt);
        /***/
        label=new JLabel(""+name,JLabel.CENTER);
        label.setBackground(Color.cyan);
        label.setFont(new Font("Comic Sans MS",Font.BOLD,25));
        label.setOpaque(true);
        label.setBounds(50,0,435,40);
        c.add(label);
        /****/
        close=new JButton("X");
        close.setFont(new Font("Comic Sans MS",Font.BOLD,20));
        close.setBounds(0,0,50,40);
        close.addActionListener(frame);
        c.add(close);

        /*****/
        outtext.addMouseListener(frame);
        outtext.addMouseMotionListener(frame);
        frame.repaint();
        frame.revalidate();
        /*****/
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

    public void focusLost(FocusEvent focus){

    }
    public void focusGained(FocusEvent focus){
        name_input.setText("");
    }
}
