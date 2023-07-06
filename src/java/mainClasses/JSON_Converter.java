/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainClasses;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;

/**
 *
 * @author micha
 */
public class JSON_Converter {
    
    public String getJSONFromAjax(BufferedReader reader) throws IOException{
	StringBuilder buffer = new StringBuilder();
	String line;
	while ((line = reader.readLine()) != null) {
		buffer.append(line);
	}
	String data = buffer.toString();
	return data;
    }

    public static Student jsonToStudent(String json) {
        Gson gson = new Gson();
        // String json1 = gson.toJson(json, Student.class);
        return gson.fromJson(json, Student.class);
    }


    public static Librarian jsonToLibrarian(String json) {
        Gson gson = new Gson();
        //String json1 = gson.toJson(json, Librarian.class);
        return gson.fromJson(json, Librarian.class);
    }

    public static Borrowing jsonToBorrowing(String json) {
        Gson gson = new Gson();
        //String json1 = gson.toJson(json, Librarian.class);
        return gson.fromJson(json, Borrowing.class);
    }
}
