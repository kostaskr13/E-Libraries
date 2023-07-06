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
public class borrowBooks extends HttpServlet {

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
        String isbn = request.getParameter("isbn");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
                System.out.println(" "+username+ " "+password);
        String lib = request.getParameter("library_id");

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            EditBorrowingTable ebot = new EditBorrowingTable();
            EditBooksInLibraryTable eblt = new EditBooksInLibraryTable();
            EditStudentsTable est = new EditStudentsTable();

            Borrowing borrow = ebot.databaseToBorrowing(Integer.parseInt(lib));
            BookInLibrary bil = eblt.databaseToBookInLibrary(Integer.parseInt(lib),isbn);
            Student stu = est.databaseToStudent(username, password);
            if (borrow == null) {
                response.setStatus(403);
            } else {
                Preferences prefs = Preferences.userNodeForPackage(LocalStorageExample.class);
                if (bil.getAvailable().equals("true")) {
                    if (isbn.equals(bil.getIsbn()) ) {
                                                
                        borrow.setStatus("requested");
                        prefs.putInt("Borrowing_id", borrow.getBorrowing_id() );
                        prefs.putInt("User_id", stu.getUser_id() );
                        prefs.putBoolean("lib", true);
                        String json = eblt.bookInLibraryToJSON(bil);
                        out.println(json);
                        
                        response.setStatus(200);
                        
                    }else{
                        response.setStatus(403);
                    }
                } else {
                    prefs.putBoolean("lib", false);
                    response.setStatus(406);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(borrowBooks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(borrowBooks.class.getName()).log(Level.SEVERE, null, ex);
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
        JSON_Converter jConv = new JSON_Converter();
        //String kk = jConv.jsonToStudent(st);
        EditStudentsTable est = new EditStudentsTable();
        EditBorrowingTable ebot = new EditBorrowingTable();
        EditBooksInLibraryTable eblt = new EditBooksInLibraryTable();
        EditLibrarianTable elt = new EditLibrarianTable();

        String lib = request.getParameter("library_id");
        try {
            String cont = jConv.getJSONFromAjax(request.getReader());
            Borrowing borrow = JSON_Converter.jsonToBorrowing(cont);
            Librarian lb = JSON_Converter.jsonToLibrarian(cont);
            Preferences prefs = Preferences.userNodeForPackage(LocalStorageExample.class);
                
            if (prefs.getBoolean("lib", true)) {
                System.out.println(cont); 
                       
                borrow.setBookcopy_id(prefs.getInt("Borrowing_id", 0));
                borrow.setUser_id(prefs.getInt("User_id",0) );
                System.out.println("borrowId is "+prefs.getInt("Borrowing_id", 0)+"  UserId is "+prefs.getInt("User_id", 0));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                LocalDateTime now = LocalDateTime.now();
                borrow.setFromDate(dtf.format(now));
                //System.out.println("h1");
                borrow.setToDate(dtf.format(now.plusMonths(1)) );

               // System.out.println(borrow.getFromDate()+" to "+borrow.getToDate()); 
                
                borrow.setStatus("requested");
                ebot.createNewBorrowing(borrow);
                System.out.println(ebot.borrowingToJSON(borrow));
                System.out.println("BookcpyId " + borrow.getBookcopy_id() + " UserID " + borrow.getUser_id());
                //.addBorrowingFromJSON(ebot.borrowingToJSON(borrow));
                System.out.println("bugabuagagaga");
                response.setStatus(200);
                JsonObject jo = new JsonObject();
                jo.addProperty("success", "Success book requested");
                response.getWriter().write(jo.toString());
            } else {
                response.setStatus(402);
                JsonObject jo = new JsonObject();
                jo.addProperty("error", " Book isnt available");
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
