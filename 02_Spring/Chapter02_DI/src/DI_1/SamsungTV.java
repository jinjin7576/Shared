package org.joonzis.DI_1;

public class SamsungTV {
	public SamsungTV() {
		System.out.println("SamsungTV 객체 생성");
	}
	public void powerOn() {
		System.out.println("SamsungTV 전원 켜짐");
	}
	public void powerOff() {
		System.out.println("SamsungTV 전원 꺼짐");
	}
	public void volumeUp() {
		System.out.println("SamsungTV 볼륨 업");
	}
	public void volumeDown() {
		System.out.println("SamsungTV 볼륨 다운");
	}
}
