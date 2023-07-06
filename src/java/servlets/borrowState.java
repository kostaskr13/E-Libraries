/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;
import com.google.gson.JsonObject;
import database.tables.EditBorrowingTable;
import database.tables.EditBooksInLibraryTable;
import database.tables.EditLibrarianTable;
import database.tables.EditStudentsTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Borrowing;
import mainClasses.BookInLibrary;
import mainClasses.JSON_Converter;
import mainClasses.Librarian;
import mainClasses.Student;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import java.util.Calendar;
import java.util.prefs.Preferences;

/**
 *
 * @author micha
 */
public class borrowState extends HttpServlet {

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
        //String copy = request.getParameter("bookcopy_id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("AT LEAST IS IT IN?");
        try (PrintWriter out = response.getWriter()) {
            EditLibrarianTable elt=new EditLibrarianTable();
            System.out.println("0");
            EditBorrowingTable ebt=new EditBorrowingTable();
            System.out.println("1");
            EditBooksInLibraryTable eblt=new EditBooksInLibraryTable();
            System.out.println("username "+username+"  password  "+password);
            Librarian lib=elt.databaseToLibrarian(username, password);
            System.out.println("id is "+lib.getLibrary_id());
            BookInLibrary bil=eblt.databaseToBookInLibraryWithoutIsbn(lib.getLibrary_id() );
            System.out.println("Book copy id "+bil.getBookcopy_id());
            Borrowing borrow=ebt.databaseToBorrowingBookCopyId(bil.getBookcopy_id());
            
            
            if(borrow!=null){
                String json = ebt.borrowingToJSON(borrow);
                System.out.println(json);
                
                borrow=ebt.databaseToBorrowingBookCopyId(bil.getBookcopy_id());
                json = ebt.borrowingToJSON(borrow);
                System.out.println(json);
                out.println(json);
                        
                response.setStatus(200);
            }else{
                response.setStatus(403);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(borrowState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(borrowState.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //String username = request.getParameter("username");
        //String password = request.getParameter("password");
        String id = request.getParameter("borrowing_id");
        
        try (PrintWriter out = response.getWriter()) {
            
            EditBorrowingTable ebt = new EditBorrowingTable();
            Borrowing borrow=ebt.databaseToBorrowing(Integer.parseInt(id));
            String json;
            
            if (borrow != null) {
               ebt.updateBorrowingStatus(Integer.parseInt(id));
               json=ebt.borrowingToJSON(borrow);
               System.out.println("JSON is "+json);
               out.println(json);
               response.setStatus(200);
            }else{
                response.setStatus(403);
            }

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
