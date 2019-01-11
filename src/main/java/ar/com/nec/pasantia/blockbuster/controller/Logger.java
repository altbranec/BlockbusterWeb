package ar.com.nec.pasantia.blockbuster.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    protected File fl = (File) crearLog();
    private FileWriter fw = null;
    private BufferedWriter bw = null;

    private File crearLog() {
        try {
            fl = new File("logger.txt");

            fl.createNewFile();

            fw = new FileWriter(fl);
            bw = new BufferedWriter(fw);

        } catch (IOException e) {
            System.out.println("MI ERROR: " + e.getMessage());
        }
        return fl;
    }

    public void escribirLog(String msg) throws IOException {
        //ofPattern("yyyy/MM/dd").format(LocalDateTime.now()) +
        bw.write(msg);
    }
}
