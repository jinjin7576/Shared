package org.joonzis.DI_3_scope;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TVUser {
	public static void main(String[] args) {
		// 1. spring 컨테이너 구동
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("apllicationContext3.xml");
		
		// 2. 컨테이너로부터 필요한 객체 생성
		TV ltv = (LgTV)ctx.getBean("ltv");
		TV ltv2 = (LgTV)ctx.getBean("ltv");
		
		ltv.powerOn();
		ltv.volumeUp();
		ltv.volumeDown();
		ltv.powerOff();
		
		//------------------
		
		TV stv = (SamsungTV)ctx.getBean("stv");
		TV stv2 = (SamsungTV)ctx.getBean("stv");
		stv.powerOn();
		stv.volumeUp();
		stv.volumeDown();
		stv.powerOff();
		
		System.out.println(ltv == ltv2 ? "lg : 같음" : "lg : 다름");
		System.out.println(stv == stv2 ? "s : 같음" : "s : 다름");
		ctx.close();
		//출력 결과
		// lg : 다름
		// s : 같음
		// lg는 객체가 서로 다른게 맞음 (prototye)
		// s는 객체가 같은게 맞음 (singleton)
	}
	
}
