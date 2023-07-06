/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.JsonObject;
import database.DB_Connection;
import database.tables.EditBooksTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import mainClasses.JSON_Converter;
import mainClasses.Book;

/**
 *
 * @author mountant
 */
public class RegisterBook extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    public boolean connection(String forThis, String where) {
        System.out.println("connect");
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;
            if (where == "PRIMARY") {
                rs = stmt.executeQuery("SELECT * FROM Books WHERE isbn = '" + forThis + "'");
            } else {
                rs = stmt.executeQuery("SELECT * FROM Books WHERE photo = '" + forThis + "'");
            }
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String exists(Book bk) {
        String value = null;
        if (connection(bk.getIsbn(), "PRIMARY")) {
            value = "ISBN";
        }
        if (connection(bk.getTitle(), "title")) {
            value = "TITLE";
        }
        return value;
    }

    public String mistaken(Book bk) {
        String x = null;
        if (bk.getIsbn().length() < 10 && bk.getIsbn().length() > 13) {
            x = "ISBN";
        }
        if (bk.getPages() == 0) {
            x = "Pages";
        }
        if (bk.getPublicationyear() < 1200) {
            x = "Publication Year";
        }
        if (!bk.getUrl().startsWith("https")) {
            x = "URL";
        }
        if (!bk.getPhoto().startsWith("https")) {
            x = "Photo";
        }
        return x;
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
        EditBooksTable ebt = new EditBooksTable();
        try {
            String cont = jConv.getJSONFromAjax(request.getReader());

            Book bk = ebt.jsonToBook(cont);
            String value = exists(bk);
            String mistake = mistaken(bk);
            if (value == null) {
                if (mistake != null) {
                    response.setStatus(406);
                    System.out.println(value);
                    JsonObject jo = new JsonObject();
                    jo.addProperty("error", mistake + " field is incorrect!");
                    response.getWriter().write(jo.toString());
                } else {
                    ebt.createNewBook(bk);
                    System.out.println("lib is in");
                    response.setStatus(200);
                    JsonObject jo = new JsonObject();
                    jo.addProperty("success", "Success Book has been added");
                    response.getWriter().write(jo.toString());
                }
            } else {
                response.setStatus(403);
                System.out.println();
                JsonObject jo = new JsonObject();
                jo.addProperty("error", value + " already taken!");
                response.getWriter().write(jo.toString());

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
