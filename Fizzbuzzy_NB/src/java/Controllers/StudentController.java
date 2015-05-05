package Controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Database.StudentDB;
import Models.Student;

public class StudentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/index.jsp";
        
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "Login";  // default action
        }

        // perform action and set URL to appropriate page
        if (action.equals("Login")) {
            // get parameters from the request
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            //store data in Student object
            Student student = null;
                              
            //validate the parameters
            String message = "";
            if (username == null || email == null || password == null | username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                message = "Please fill out all the text boxes";
                url = "/index.jsp";
            } else {
               
                student = StudentDB.selectStudent(email);
                
                if (student == null) {
                    message = "You don't have any profile yet. Please recheck your credentials"
                            + " and sign in again, or register for a new account.";
                    url = "/index.jsp";
                       
                } else {
                    message = "Sign in successfully";
                    url = "/profile.jsp";
                }

            }
            request.setAttribute("student", student);
            request.setAttribute("message", message);
        } 
        else if (action.equals("Register")) {
            // get parameters from the request
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            //store data in Student object
            Student student = new Student(username, password, email);
                              
            //validate the parameters
            String message = "";
            if (username == null || email == null || password == null | username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                message = "Please fill out all the text boxes";
                url = "/index.jsp";
            } else {
                message = "Register successfully";
                url = "/profile.jsp";
                StudentDB.insert(student);

            }
            request.setAttribute("student", student);
            request.setAttribute("message", message);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }    
}