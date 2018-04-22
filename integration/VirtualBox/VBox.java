package integration.VirtualBox;

import settings.ProgramSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VBox {
	public void init() {
        ProcessBuilder pb = new ProcessBuilder(ProgramSettings.getVBoxManagePath(),"list","ostypes","--machinereadable");
        Process p = null;
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String s = "";
        try {
            while ((s=bf.readLine())!=null){
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
