package ui.image;

import ui.HandImage;



public class ThreadPlay extends Thread{
	private boolean pause = false;
	private HandImage handImage;
	private int sleep=10;
	
	
	public int getSleep() {
		return sleep;
	}
	public void setSleep(int sleep) {
		this.sleep = sleep;
	}
	public ThreadPlay(HandImage handImage) {
		this.handImage=handImage;
	}
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	public void run(){
		while((!pause)){
			try {
				this.sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handImage.nextImage();
		}
		this.stop();
	}
}
