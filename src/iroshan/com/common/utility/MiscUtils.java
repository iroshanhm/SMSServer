package iroshan.com.common.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MiscUtils {

    public MiscUtils() {
    }

    
    public static String getMotherboardSN() throws IOException {
        String result = "";
   
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
 + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\"c\")\n"
                    + "Wscript.Echo objDrive.SerialNumber"; // see note


            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo "
                    + file.getPath());
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
      

        return result.trim();
    }

//    public static void main(String[] args) {
//        String cpuId = MiscUtils.getMotherboardSN();
//        
//        javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) null, cpuId, "Motherboard serial number",
//                javax.swing.JOptionPane.DEFAULT_OPTION);
//    }
}
