
package fileadminstration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import process.Process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;

public class file {
    
    
    private File archivo;
    private String route;

    public file() {
        this.route = System.getProperty("user.dir") + "\\data.txt";
        
        try {
            this.archivo = new File(this.route);
            if (this.archivo.exists()) {
                BufferedWriter newRecord = new BufferedWriter(new FileWriter(archivo, true));
                newRecord.write("Fecha de registro de datos: " + Date.from(Instant.now()));
                newRecord.newLine();
                newRecord.close();
            }
            else {
                BufferedWriter newRecord = new BufferedWriter(new FileWriter(archivo));
                newRecord.write("Fecha de registro de datos: " + Date.from(Instant.now()));
                newRecord.newLine();
                newRecord.close();
            }
        } catch (Exception e) {
            e.getCause();
        }
    }


    public void addProcess(Process newProcess) throws IOException{
        BufferedWriter writer;

        if (this.archivo.exists()) {
            writer = new BufferedWriter(new FileWriter(archivo, true));
            writer.write(newProcess.toStringFile());
            writer.close();
        }
        else {
            writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(newProcess.toStringFile());
            writer.newLine();
            writer.close();
        }
    }

    public void saveChanges() {

    }
}
