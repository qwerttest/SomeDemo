package com.example;

public class Reflect {
        public void run(int i) throws Test.ZeroException {
            Test.B b = new Test.B();
            b.run(i);
        }
    }