package com.ud.main;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class MainApp 
{
	public static Scanner cin= new Scanner(System.in);
	
	public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/application-context.xml");

        cin.nextBigInteger();
    }
}
