import java.awt.*;
import java.applet.*;

public class TrafficSimulation extends Applet implements Runnable{
	Image car1;
	Image car2;
	Image offScreenImage;
	Dimension offDimension;
	Graphics offGraphics;
	MediaTracker mt;
	Thread thisThread;
	boolean enableCar1;
	boolean enableCar2;
	int car1X;
	int car1Y;
	int car2X;
	int car2Y;


	public void init(){

		enableCar1=true;
		enableCar2=true;

		car1=getImage(getCodeBase(),"Image1.jpg");
		car2=getImage(getCodeBase(),"Image2.jpg");

		car1X=0;
		car1Y=(int) (((400-car1.getHeight(this))-0+1)*Math.random()+0);
		car2X=(int)(((350-car2.getWidth(this))-0+1)*Math.random()+0);
		car2Y=0;

		mt=new MediaTracker(this);
		mt.addImage(car1,1);
		mt.addImage(car2,2);
		setBackground(Color.black);
		try{
			mt.waitForAll();
		}catch(InterruptedException e){}
        resize(400,400);

		thisThread=new Thread(this);
		thisThread.start();
	}

        public synchronized void paint(Graphics g){
		Dimension d=getSize();
		if(offGraphics==null || d.width!=offDimension.width || d.height!=offDimension.height){
			offDimension=d;
			offScreenImage=createImage(d.width,d.height);
			offGraphics=offScreenImage.getGraphics();

		}
		offGraphics.setColor(Color.black);
		offGraphics.fillRect(0,0,d.width,d.height);
		offGraphics.drawImage(car1,car1X,car1Y,this);
		offGraphics.drawImage(car2,car2X,car2Y,this);
		offGraphics.setColor(Color.white);
		offGraphics.setFont(new Font("Verdana",Font.BOLD,12));
		offGraphics.drawString("Best when viewed in appletviewer",30,20);
		g.drawImage(offScreenImage,0,0,this);

	}

	public void update(Graphics g){
			paint(g);
	}

	public void start(){
		if(thisThread==null){
			thisThread=new Thread(this);
			thisThread.start();
		}
	}

	public void stop(){
		if(thisThread!=null){
			thisThread=null;
		}
	}

	public void run(){
		while(true){

            if((enableCar2==true) && (car1X+car1.getWidth(this)+20>car2X) && (car1X+car1.getWidth(this)<car2X)) {
				if((car1Y<car2Y+car2.getHeight(this)) && (car1Y+car1.getHeight(this)>car2Y)){
					enableCar1=false;
				}
			}


            if((enableCar1==true) && (car2Y+car2.getHeight(this)+20>car1Y) && (car2Y+car2.getHeight(this)<car1Y+20)){
                if((car2X<car1X+car1.getWidth(this)) && ((car2X>car1X)||(car2X+car2.getWidth(this)>car1X)))
					enableCar2=false;
			}

			if(enableCar1==false){
				if(car2Y>car1Y+car1.getHeight(this))
					enableCar1=true;
			}

			if(enableCar2==false){
                                if(car1X>car2X+car2.getWidth(this))
					enableCar2=true;
			}

			if(enableCar1)
                                car1X+=5;
			if(enableCar2)
                                car2Y+=5;

			if(car1X>=400){
				car1X=-100;
				car1Y=(int) (((400-car1.getHeight(this))-0+1)*Math.random()+0);
                enableCar1=true;
                enableCar2=true;
			}

			if(car2Y>=400){
				car2Y=-100;
				car2X=(int)(((300-car2.getWidth(this))-0+1)*Math.random()+0);
                enableCar1=true;
                enableCar2=true;
			}
			repaint();


			try{
            	Thread.sleep(5);
            	showStatus("Best when viewed in appletviewer");
			}catch(InterruptedException e){}

		}
	}
}
