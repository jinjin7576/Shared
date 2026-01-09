package org.joonzis.DI_2;

public class TVUser {
	public static void main(String[] args) {
		TV ltv = new LgTV();
		ltv.powerOn();
		ltv.volumeUp();
		ltv.volumeDown();
		ltv.powerOff();
		
		//------------------
		
		TV stv = new SamsungTV();
		stv.powerOn();
		stv.volumeUp();
		stv.volumeDown();
		stv.powerOff();
	}
	
}
