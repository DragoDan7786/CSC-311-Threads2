/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.csc311_threadslab2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author soblab
 */
public class main {

    //SumData Example
    static int grandTotal = 0;
    static Queue<String> fileNames = new LinkedList<>();

    static Object grandTotalLock = new Object();
    static Object queueRemoveLock = new Object();

    public static void sumFile() {
        String fileName = "";
        synchronized(queueRemoveLock){
        //get a file name to sum
        fileName = fileNames.remove();
        }
        try {
            Scanner theScan = new Scanner(new FileReader(fileName));

            int tempSum = 0;
            //reads all data from file
            while (theScan.hasNextInt()) {
                tempSum += theScan.nextInt();
            }
            synchronized (grandTotalLock) {
                grandTotal += tempSum;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {

//        Object myLock = new Object();
//
//        synchronized (myLock) {
//            //Single Thread Executor Example
//            ExecutorService exec = Executors.newSingleThreadExecutor();
//            for (int i = 0; i < 10; i++) {
//                final int loopNum = i;
//                exec.submit(() -> {
//                    String tName = Thread.currentThread().getName();
//                    System.out.printf("Message from %s\n", tName + ", loop " + loopNum);
//
//                });
//            }
//
//            exec.shutdown();
//            System.out.println("");
//        }

//        //Fixed Pool Executor Example
//        ExecutorService fixedPoolExec = Executors.newFixedThreadPool(3);
//        for (int i = 0; i < 10; i++) {
//            final int loopNum = i;
//            fixedPoolExec.submit(() -> {
//                String tName = Thread.currentThread().getName();
//                System.out.printf("Message from %s\n", tName + ", loop " + loopNum);
//
//            });
//        }
//        fixedPoolExec.shutdown();

        //SumData Code
        fileNames.add("File1.txt");
        fileNames.add("File2.txt");
        fileNames.add("File3.txt");
        fileNames.add("File4.txt");
        fileNames.add("File5.txt");
        
        //Fixed Pool Executor for Sum
        ExecutorService sumPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            final int loopNum = i;
            sumPool.submit(() -> {
                sumFile();
            });
        }
        sumPool.shutdown();
        
        System.out.println("Grand total is: " + grandTotal);
    }
}
