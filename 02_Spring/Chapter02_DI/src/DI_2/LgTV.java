package org.joonzis.DI_2;

public class LgTV implements TV{
	public LgTV() {
		System.out.println("LgTv 객체 생성");
	}
	public void powerOn() {
		System.out.println("LgTv 전원 켜짐");
	}
	public void powerOff() {
		System.out.println("LgTv 전원 꺼짐");
	}
	public void volumeUp() {
		System.out.println("LgTv 볼륨 업");
	}
	public void volumeDown() {
		System.out.println("LgTv 볼륨 다운");
	}
	
}
