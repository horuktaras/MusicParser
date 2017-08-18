package com.noop.parser.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleReader {

    static Logger logger;

    public static String readString() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("Enter path to directory with music:");
                return br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Wrong input. Please try again.");
            return "";
        }
    }
}
