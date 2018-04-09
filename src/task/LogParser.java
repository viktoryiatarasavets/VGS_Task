package task;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LogParser {

    public static void main(String args[]) {

        /*create an empty log file for composed result from initial log file*/
        File outputLogFile = new File("src/resources/output_server.log");

        DAOService daoService = new DAOService();

        /*here user enters the company name*/
        String companyName = getInputCompanyName();

        /*an empty list for users of entered company*/
        List<String> usersList = new ArrayList<String>();

        /*add to a created list result from DB with users*/
        usersList = daoService.findBy(companyName);

        /*adding to outputLogFile log records for every user for requested company*/
        try {
            for (int i = 0; i < usersList.size(); i++) {

                List<String> logList = new ArrayList<String>();
                logList = getValues(usersList.get(i));

                for (int j = 0; j < logList.size(); j++) {

                    BufferedWriter writer = null;
                    writer = new BufferedWriter(new FileWriter(outputLogFile));
                    writer.write(logList.get(j));
                    //Close writer
                    writer.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* returns company name entered by a user*/
    public static String getInputCompanyName() {
        Scanner scanner = new Scanner(System.in);
        /*  prompt for the company's name*/
        System.out.print("Enter company name: ");
        /* get their input as a String */
        String companyName = scanner.next();
        return companyName;
    }


    /* returns composed log entry for provided user*/
    public static ArrayList<String> getValues(String user) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream("src/resources/server.log");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            while ((strLine = reader.readLine()) != null) {
                String lastWord = strLine.substring(strLine.lastIndexOf(" ") + 1);
                if (lastWord.contains(user)) {
                    lines.add(strLine + "\n");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(lines);
        return lines;
    }


}
