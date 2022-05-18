package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

public class SearchTread extends Thread {
    boolean subDirs = false;
    String fileRegx, startDir, searchS = null;
    CommonPanel com;
    int num;
    private boolean isSuspended = false;

    public SearchTread(int number, String regx, String startDir, String searchStr, boolean inSubDir, int prior, CommonPanel cp) {
        super();
        this.num = number;
        com = cp;
        setPriority(prior);
        if(!validDir(startDir))
            throw new IllegalArgumentException("Invalid directory \"" + startDir + "\".");
        com.add();
        if(regx != null && !regx.equals("")) {
            regx = regx.replaceAll("[.]", "[.]");
            regx = regx.replaceAll("[*]", ".*");
            regx = regx.replaceAll("[?]", ".");
        }
        fileRegx = regx;
        this.startDir = startDir;
        searchS = searchStr;
        subDirs = inSubDir;
        com.addString(getTemplate() + "init()\n");
    }

    private void mySearch(String dir) {
        if(checkPause() == -1)
            return;
        File fileDir = new File(dir);
        String[] list = fileDir.list();
        if(list == null)
            return;
        for(String item : list) {
            if(checkPause() == -1)
                return;
            File newDir = new File(dir + item);
            if(newDir.isDirectory() && subDirs) {
                String pathname2 = dir + item + "\\";
                mySearch(pathname2);
            }
            if(newDir.isFile()) {
                boolean isGood = false;
                if(fileRegx != null && newDir.getName().matches(fileRegx))
                    isGood = true;
                if(fileRegx == null || isGood) {
                    if(searchS != null) {
                        if(newDir.canRead()) {
                            try {
                                isGood = readData(newDir);
                            }
                            catch(FileNotFoundException e) {
                                com.addString(num + ":\t--- " + newDir + " --- (???????? ? ???????)\n");
                                continue;
                            }
                        }
                        else
                            isGood = false;
                    }
                    else
                        isGood = true;
                }
                if(isGood)
                    com.addString(num + ": + " + dir + item + "\n");
            }
        }
    }

    private String getTemplate() {
        return num + ": " + new Date() + "\t--  ";
    }

    public void run() {
        com.addString(getTemplate() + "run()\n");
        String dir = Paths.get(startDir).toString();
        if(dir.charAt(dir.length() - 1) != '\\')
            dir += '\\';
        if(checkPause() != -1)
            mySearch(dir);
        myInterrupt();
    }

    public void myResume() {
        isSuspended = false;
    }

    public void mySuspend() {
        com.addString(getTemplate() + "suspend()\n");
        isSuspended = true;
    }

    public void myInterrupt() {
        com.addString(getTemplate() + "interrupt()\n");
        interrupt();
    }

    private int checkPause() {
        while(isSuspended) {
            try {
                Thread.sleep(30);
            }
            catch(InterruptedException e) {
                return -1;
            }
        }
        return 0;
    }

    private synchronized boolean readData(File newDir) throws FileNotFoundException {
        Scanner fr = new Scanner(newDir);
        while(fr.hasNextLine()) {
            if(fr.nextLine().contains(searchS)) {
                fr.close();
                return true;
            }
        }
        fr.close();
        return false;
    }

    private boolean validDir(String startDir) {
        return !startDir.equals("") && Files.isDirectory(Paths.get(startDir));
    }
}