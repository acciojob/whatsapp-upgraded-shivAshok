package com.driver;

public class UserExists extends RuntimeException{
   public  UserExists(){
        super("User already exists");
    }
}
