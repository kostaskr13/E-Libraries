/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditStudentsTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Student;

/**
 *
 * @author micha
 */
public class updateStudent extends HttpServlet {

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
        System.out.println("HIIIIIIII");
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String ID = request.getParameter("student_id");
        String studentAt = request.getParameter("university");
        String email = request.getParameter("email");
        System.out.println("this nigga again"
                + username + password + ID + studentAt + email);

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            EditStudentsTable est = new EditStudentsTable();
            Student su = est.databaseToStudent(username, password);

            //bk.setPages(pages);
            String json;
            json = est.studentToJSON(su);
            System.out.println(json);
            if (ID != null) {
                est.updateStudentID(username, ID);
                System.out.println(ID);
            }
            if (studentAt != null) {
                est.updateStudentAt(username, studentAt);
                System.out.println(studentAt);
            }
            if (email != null) {
                est.updateStudentEmail(username, email);
                System.out.println(email);
            }
            System.out.println(json);
            out.println(json);
            response.setStatus(200);

        } catch (SQLException ex) {
            Logger.getLogger(GetBooks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetBooks.class.getName()).log(Level.SEVERE, null, ex);
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
