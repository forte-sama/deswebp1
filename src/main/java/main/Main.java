package main;

import java.util.Scanner;

public class Main {
    public static  void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Escribe todos los links que deseas evaluar... Finaliza con un \"0\"");

        while(true) {
            String url = input.nextLine();

            if(url.trim().contentEquals(new StringBuffer("0")))
                break;

            System.out.println(URLTools.parse_content(url.trim()));
        }
    }
}
