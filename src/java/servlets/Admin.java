/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import database.tables.EditStudentsTable;
import database.tables.EditLibrarianTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Student;
import mainClasses.Librarian;


/**
 *
 * @author micha
 */
public class Admin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            EditStudentsTable est = new EditStudentsTable();
            EditLibrarianTable elt = new EditLibrarianTable();

            ArrayList<Librarian> libs = elt.databaseToLibrarians();
            ArrayList<Student> stus = est.databaseToStudents();
            Gson gson1 = new Gson();
            Gson gson2 = new Gson();
            JsonArray jsonlibs = gson1.toJsonTree(libs).getAsJsonArray();
            JsonArray jsonSt = gson2.toJsonTree(stus).getAsJsonArray();
            System.out.println("Librarians\n" + jsonlibs + "\n");
            System.out.println("Students\n" + jsonSt + "\n");
            JsonArray combinedArray = new JsonArray();
            for (int i = 0; i < jsonlibs.size(); i++) {
                combinedArray.add(jsonlibs.get(i));
            }
            for (int i = 0; i < jsonSt.size(); i++) {
                combinedArray.add(jsonSt.get(i));
            }
            
            System.out.println("Combined\n" + combinedArray + "\n");
            out.println(combinedArray  );
            System.out.println("YES");
            response.setStatus(200);
            System.out.println("YES");
             
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
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
