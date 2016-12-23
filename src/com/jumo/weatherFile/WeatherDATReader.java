/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jumo.weatherFile;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by AGNES KARONJI on 12/23/2016.
 * Version 1.0.0
 * Email: karonjiagnesm@gmail.com
 * This class reads through a weather.dat file, and displays the maximum spread
 * i.e: maximum difference between maxTemp and minTemp values contained there-in.
 */
public class WeatherDATReader {
    //Use system-dependent file separator to cater for various OS.
    static String weatherDataFilePath = System.getProperty("user.dir") + File.separator + "weather.dat";

    /**
     * This function reads through the .dat file and presents the data contained there-in.
     *
     * @return data in the .dat file, in type String.
     * @throws Exception: FileNotFound Exception, IOException, these exceptions will be unrecoverable if not handled.
     *                    FileNotFoundException, eg: where the system cannot find the .dat file specified
     */
    public static String presentFileData() throws Exception {
        FileReader fileReader = new FileReader(weatherDataFilePath); // create a FileReader and read the loaded .dat file
        //use BufferedReader for resource management
        BufferedReader bufferedReader = new BufferedReader(fileReader); // create a BufferedReader and load the created fileReader
        StringBuilder stringBuilder = new StringBuilder(); // create empty StringBuilder, capacity 16
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line); //append the string argument to the string builder
            stringBuilder.append(System.lineSeparator()); //append system-dependent line separator to cater for various OS.
        }
        fileReader.close(); //close the fileReader

        return stringBuilder.toString(); // convert stringBuilder to string object and return it
    }

    /**
     * This function loads the data in the .dat file into a list and returns the List<String>.
     *
     * @return data in the .dat file, in type list.
     * @throws Exception: FileNotFound Exception, IOException, these exceptions will be unrecoverable if not handled.
     */
    public static List<String> loadDataToList() throws Exception {
        FileReader fileReader = new FileReader(weatherDataFilePath); // create a FileReader and read the loaded .dat file
        BufferedReader bufferedReader = new BufferedReader(fileReader); // create a BufferedReader and load the created fileReader
        List<String> list = new ArrayList<>(); // implement the list collection
        String line;
        // add content to the created list, where the data buffer us not empty
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }
        fileReader.close(); // close the fileReader object

        return list; // ordered list of the data contained in the .dat file
    }


    /**
     * This function receives a list of the data in the .dat file, and loops through it,
     * creating a format with the first three columns (day, maxTemp and minTemp),
     * creates a map and assigns day and spread of the day to the created map.
     *
     * @param dataList: list of the data in the .dat file
     * @return String containing the day with maximum spread and its spread
     */
    public static String formatDataList(List<String> dataList) {
        String formattedResult = "";
        Map<String, Float> spreadMap = new HashMap<>(); //Map<String, Float> to hold each day and its spread, for the month

        for (String dataRow : dataList) { // for every index of dataList, assign value to dataRow
            String[] dataRowArray = dataRow.trim().split("\\s"); // trim spaces at the beginning and end of list rows

            //converts String[] array to list, to enable efficient sanitization of the data
            List<String> dataRowList = new ArrayList<>(Arrays.asList(dataRowArray));
            dataRowList.removeAll(Arrays.asList(""));

            String day = dataRowList.get(0); //value of day - first index of the ArrayList, dataRowList
            // use regex
            float maxTemp = Float.parseFloat(dataRowList.get(1).replaceAll("[`~!@#$%^&*()_+[\\\\]\\\\\\\\;\\',/{}|:\\\"<>?]", ""));
            float minTemp = Float.parseFloat(dataRowList.get(2).replaceAll("[`~!@#$%^&*()_+[\\\\]\\\\\\\\;\\',/{}|:\\\"<>?]", ""));
            float spread = maxTemp - minTemp; //calculate the spread ie: difference between maxTemp and minTemp.
           
            spreadMap.put(day, spread);

        }
        formattedResult = formatResultMap(spreadMap); // call the formatResultMap to get day with maximum spread
        return formattedResult; // (day + "   " + maxSpread);
    }


    /**
     * This function receives a map of the day,maxTemp, minTemp and spread for the month,
     * It finds and prints the row with the maximum spread in the format Day Spread.
     *
     *  it uses <Collections> to get the maximum value and also the key with the max value
     *
     * @param spreadMap: Map<String, Float> created from list of the relevant values (Day, spread)
     * @return Day with the maximum spread, the actual Spread (ie: day - spread)
     */
    public static String formatResultMap(Map<String, Float> spreadMap) {
        String day = Collections.max(spreadMap.keySet()); // get the day with the maximum spread
        float maxSpread = Collections.max(spreadMap.values()); //get the actual maximum spread value
        return ("\n\n" + day + "   " + maxSpread); // day (with max spread) and the actual spread
    }


    /**
     * The main function, from where program execution begins.
     *
     * @param args
     * @throws Exception: various exceptions under the lang package have to be handled including:-
     *                    Number FormatException, e.g: where the Mxt or MnT contains special characters.
     *                    FileNotFoundException, eg: where the system cannot find the .dat file specified 
     */
    public static void main(String[] args) throws Exception {
//        System.out.println(presentFileData()); // print data as contained in the .dat file to standard output
        
        List<String> dataList = loadDataToList(); // assign the data formatted as list in loadDataToList() function to a List<String>

        dataList.remove(0); // remove the header section
        dataList.removeAll(Arrays.asList("")); //remove space between header section and first row
        dataList.remove(dataList.size() - 1); // remove the totals row from the dataList obtained

        System.out.println(formatDataList(dataList)); // prints formatted day, maxTemp, minTemp and spread for the month to standard output
    }
}
