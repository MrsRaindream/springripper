package com.springripper;

import com.springripper.logic.TerminatorQuoter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main( String[] args ) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
//        while(true) {
//            Thread.sleep(100);
//            context.getBean(TerminatorQuoter.class).sayQuote();
//        }
    }

}
