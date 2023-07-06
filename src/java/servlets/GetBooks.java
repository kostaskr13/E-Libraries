/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
public class GetBooks extends HttpServlet {

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
        String genre = request.getParameter("genre");
        String from = request.getParameter("fromParam");
        String to = request.getParameter("toParam");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String fromP = request.getParameter("fromPages");
        String toP = request.getParameter("toPages");

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            EditBooksTable ebt = new EditBooksTable();
            ArrayList<Book> books ;
            if(genre.equals("") && from.equals("") && to.equals("") && title.equals("") && author.equals("") && fromP.equals("") && toP.equals("")  ){
                books=ebt.databaseToBooks();
            }else{
                books= ebt.databaseToBooksParams(genre, from, to, title ,author, fromP ,toP);
            }
            Gson gson1 = new Gson();
            JsonArray jsonBook = gson1.toJsonTree(books).getAsJsonArray();
            System.out.println("Librarians\n" + jsonBook + "\n");
            
            out.println(jsonBook  );
//            System.out.println("YES");
            response.setStatus(200);
            /*Book bk;
            String jsoon = "";
            for (int i = 0; i < book.size(); i++) {
                bk = book.get(i);
                System.out.println(i + 1 + " " + book.size() + " of size " + book.get(i).getTitle());
                int year = book.get(i).getPublicationyear();
                System.out.println("0");
                if (year > Integer.parseInt(from) && year < Integer.parseInt(to)) {

                    // Book book=ebt.databaseToBooks();
                    if (bk == null) {
                        System.out.println("bk==null");
                        //response.setStatus(403);
                    } else {
                        System.out.println("hi");

                        String json = ebt.bookToJSON(book.get(i));
                        System.out.println("1");
                        System.out.println(json);
                        System.out.println("2");
                        //jsoon += json + " ";
                        System.out.println("3");
                        i = book.size();
                        out.println(json);
                    }
                } else {
                    // response.setStatus(403);
                }
*/
            

        } catch (SQLException ex) {
            Logger.getLogger(GetBooks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetBooks.class.getName()).log(Level.SEVERE, null, ex);
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
