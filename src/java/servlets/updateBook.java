/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditBooksTable;
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
import mainClasses.Book;

/**
 *
 * @author micha
 */
public class updateBook extends HttpServlet {

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
        String isbn = request.getParameter("isbn");
        String pages = request.getParameter("pages");

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            EditBooksTable ebt = new EditBooksTable();
            ArrayList<Book> book = ebt.databaseToBooks();
            Book bk;
            for (int i = 0; i < book.size(); i++) {
                bk = book.get(i);
                //System.out.println(book.get(i).getIsbn() + " equals " + isbn);
                if (book.get(i).getIsbn().equals(isbn)) {
                    System.out.println("is isbn");
                    // Book book=ebt.databaseToBooks();
                    if (bk == null) {
                        response.setStatus(403);
                    } else {
                        if (Integer.parseInt(pages) > 0) {
                            System.out.println("Update");
                            //bk.setPages(pages);
                            String json;
                            json = ebt.bookToJSON(book.get(i));
                            System.out.println(json);
                            ebt.updateBook(isbn, pages);
                            System.out.println(json);
                            out.println(json);
                            response.setStatus(200);

//                            i = book.size();
                        } else {
                            response.setStatus(406);
                        }
                    }
                } else {
                    response.setStatus(403);
                }
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
