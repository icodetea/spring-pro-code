package com.lib;

import org.springframework.context.annotation.Bean;

public class TypicalHelloService implements HelloService {

    @Override
    public void greet() {
        System.out.println("Hello, Typical!");
    }

}
