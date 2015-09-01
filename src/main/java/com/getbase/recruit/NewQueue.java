package com.getbase.recruit;

import java.util.Scanner;

public class NewQueue {
    private static Scanner in;

	public static void main(String[] args){
       
        in = new Scanner(System.in);
        Kolejka kolejka = new Kolejka();
        Kolejka kolejka2 = new Kolejka();
       
        int t;
        boolean bflag = true;
       
        while(bflag){
        	Kolejka.menu();
            t=in.nextInt();
           
            switch(t){
	            case 1: String el =in.next();
	                    kolejka.add(el);
	                    break;
	            case 2: kolejka.delete();break;
	            case 3: kolejka.show(); break;
	            case 4: System.out.println(kolejka.zlicz());break;
	            case 5: kolejka.copy(kolejka2);
	                    kolejka2.show();
	                    break;
	            case 6: kolejka.scal(kolejka2);
	                    break;
	            case 7: System.exit(0);
            }
        }
       
    }
}
