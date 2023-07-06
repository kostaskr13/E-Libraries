/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.JsonObject;
import database.DB_Connection;
import database.tables.EditStudentsTable;
import database.tables.EditLibrarianTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import mainClasses.JSON_Converter;
import mainClasses.Student;
import mainClasses.Librarian;











/**
 *
 * @author mountant
 */
public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }
    public boolean connection(String forThis, boolean isSt, String where) {
        System.out.println("connect");
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;
            if (isSt) {
                if (where == "username") {
                    rs = stmt.executeQuery("SELECT * FROM Students WHERE username = '" + forThis + "'");
                } else if (where == "email") {
                    rs = stmt.executeQuery("SELECT * FROM Students WHERE email = '" + forThis + "'");
                } else {
                    rs = stmt.executeQuery("SELECT * FROM Students WHERE student_id = '" + forThis + "'");
                }

            } else {
                if (where == "username") {
                    rs = stmt.executeQuery("SELECT * FROM Librarians WHERE username = '" + forThis + "'");
                } else {
                    rs = stmt.executeQuery("SELECT * FROM Librarians WHERE email = '" + forThis + "'");
                }
            }
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String existsSt(Student user) {
        String value = null;
        if (connection(user.getUsername(), true, "username") || connection(user.getUsername(), false, "username")) {
            value = "USERNAME";
        }
        if (connection(user.getEmail(), true, "email") || connection(user.getEmail(), false, "email")) {
            value = "EMAIL";
        }
        if (connection(user.getStudent_id(), true, "student_id") || connection(user.getStudent_id(), false, "student_id")) {
            value = "A.M.";
        }
        return value;
    }

    public String existsLb(Librarian user) {
        String value = null;
        if (connection(user.getUsername(), false, "username") || connection(user.getUsername(), true, "username")) {
            value = "USERNAME";
        }
        if (connection(user.getEmail(), false, "email") || connection(user.getEmail(), true, "email")) {
            value = "EMAIL";
        }
        return value;
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSON_Converter jConv = new JSON_Converter();
        //String kk = jConv.jsonToStudent(st);
        EditStudentsTable est = new EditStudentsTable();
        EditLibrarianTable elt = new EditLibrarianTable();
        try {
            String cont = jConv.getJSONFromAjax(request.getReader());

            boolean isntLibrarian = cont.contains("\"libraryname\":\"\",\"libraryinfo\":\"");

            if (isntLibrarian) {
                Student st = JSON_Converter.jsonToStudent(cont);//jConv.jsonToStudent(request.getReader());
                System.out.println(st.getUsername());
                String value = existsSt(st);
                if (value == null) {
                    est.addNewStudent(st);
                    System.out.println("Student is in");
                    //est.addNewSaddStudentFromJSONtudent(student);
                    response.setStatus(200);
                    JsonObject jo = new JsonObject();
                    jo.addProperty("success", "Success Student added    ");
                    response.getWriter().write(jo.toString());
                } else {
                    response.setStatus(403);
                    JsonObject jo = new JsonObject();
                    jo.addProperty("error", value + " already taken!");
                    response.getWriter().write(jo.toString());
                }
            } else {
                Librarian lb = JSON_Converter.jsonToLibrarian(cont);
                String value = existsLb(lb);
                if (value == null) {
                    elt.addNewLibrarian(lb);
                    System.out.println("lib is in");
                    response.setStatus(200);
                    JsonObject jo = new JsonObject();
                    jo.addProperty("success", "Success Librarian added");
                    response.getWriter().write(jo.toString());
                } else {
                    response.setStatus(403);
                    System.out.println(value);
                    JsonObject jo = new JsonObject();
                    jo.addProperty("error", value + " already taken!");
                    response.getWriter().write(jo.toString());
                }
            }
        } catch (Exception e) {
            response.setStatus(403);
            JsonObject jo = new JsonObject();
            jo.addProperty("error", "Database error!");
            response.getWriter().write(jo.toString());
        }
    }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
