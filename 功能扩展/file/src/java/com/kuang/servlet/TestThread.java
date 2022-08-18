package com.kuang.servlet;

import java.util.UUID;

public class TestThread {
    public static void main(String[] args) {
        String uuidPath = UUID.randomUUID().toString();
        System.out.println(uuidPath);
    }
}
