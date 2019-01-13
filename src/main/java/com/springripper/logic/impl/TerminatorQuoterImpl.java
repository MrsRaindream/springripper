package com.springripper.logic.impl;

import com.springripper.annotations.AfterProxyMyOwn;
import com.springripper.annotations.DeprecatedClass;
import com.springripper.annotations.Profiling;
import com.springripper.logic.TerminatorQuoter;
import com.springripper.annotations.InjectRandomInt;

import javax.annotation.PostConstruct;

/**
 * Created by raindream on 03.01.19.
 */
@Profiling
@DeprecatedClass(newImpl = T1000.class)
public class TerminatorQuoterImpl implements TerminatorQuoter {

    @InjectRandomInt(min = 2, max = 7)
    private int repeat;
    private String message;


    public TerminatorQuoterImpl() {
        System.out.println("i'm constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("i'm for using data after creating singletons and after spring bean post processor's deals");
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @AfterProxyMyOwn
    public void sayQuote() {
        //this.repeat = 2;
        for (int i = 0; i < repeat; i++) {
            System.out.println("message = " + message);
        }
    }
}
