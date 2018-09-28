package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            // INSERT YOUR CODE HERE
            
            //declare your arrays
            JSONArray colHeaders = new JSONArray();
            JSONArray rowHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            
            //declare your object
            JSONObject jsonObject = new JSONObject();
            
            
            //Fill the ColHeaders Array with the objects from the first iterator array 
            
            //Bring the first array in to a string array
            String[] stringArray = null;
            if (iterator.hasNext()){
                stringArray = iterator.next();
            }
            
            //Using a foor loop, populate the JSONArray colHeaders
            for (int i = 0; i < stringArray.length; ++i){
                colHeaders.add(stringArray[i]);
            }
            
            /*Repeat the process for rowHeaders and data (we are going to
            combine these two together under one operation
            so the code wont be so massive)*/
            
            //we use a while loop due to us populating 2 arrays at once
            while (iterator.hasNext()){
               
                stringArray = iterator.next();
                
                //bring the first line of each column into rowHeader
                for (int i = 0; i < 1; i++){
                    rowHeaders.add(stringArray[i]);
                }
                
                // declare another array for the data variables
                List <Integer> DataInt = new ArrayList<Integer>();
                
                //Begin populating that declared array with data. We will
                //convert string to integers and add them to the array.
                for (int j = 1; j < stringArray.length; ++j){
                    DataInt.add(Integer.parseInt(stringArray[j]));
                }
                //Add the dataInt to the Data Array
                data.add(DataInt);
                        
            }
            
                //take the JSONObject and add the diddly doos
                jsonObject.put("colHeaders" , colHeaders);
                jsonObject.put("rowHeaders", rowHeaders);
                jsonObject.put("data",data);
                
                results = JSONValue.toJSONString(jsonObject);
        }        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            //declare your array values
            JSONArray colHeaders = new JSONArray();
            JSONArray rowHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            
            //take your JSONObjects and put it in the array variables
            colHeaders = (JSONArray) jsonObject.get("colHeaders");
            rowHeaders = (JSONArray) jsonObject.get("rowHeaders");
            data = (JSONArray) jsonObject.get("data");
            
            //create string arrays
            String[] colHeadersString = new String[colHeaders.size()];
            String[] rowHeadersString = new String[rowHeaders.size()];
            String[] dataString = new String[data.size()];
            
            //populate the colHeadersString Array by getting data out from the
            //colHeaders and casting it as a string
            for (int i = 0; i < colHeadersString.length; i++){
                colHeadersString[i] = (String)colHeaders.get(i);
            }
            
            csvWriter.writeNext(colHeadersString);
            
            for (int i = 0; i < rowHeadersString.length; i++){
                rowHeadersString[i] = (String)rowHeaders.get(i);
            }
            for (int i = 0; i < dataString.length; i++){
                dataString[i] = data.get(i).toString();
            }
            
            //we must now write ONE header,then ONE array
            int totalLength = rowHeaders.size() + data.size();
            String row;
            
            for(int i = 0; i < totalLength; i++){
                row = rowHeadersString[i];
                
            }
            
            //convert it all to string
            results = writer.toString();
 
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}