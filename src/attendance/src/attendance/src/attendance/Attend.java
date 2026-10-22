package attendance;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Attend")
  public class Attend extends HttpServlet {
        private static final long serialVersionUID = 1L;
        private static final Map<String, String> sid2seat = new HashMap<String, String>();

    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
                          handle(request, response);
                }

    @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
                          handle(request, response);
                }

    private void handle(HttpServletRequest request, HttpServletResponse response)
                throws IOException {
                          response.setContentType("text/html;charset=UTF-8");
                          PrintWriter out = response.getWriter();

            String studentId = request.getParameter("studentId");
                          String seatId    = request.getParameter("seatId");

            out.println("Simple Attendance by Seat Id<br><br>");

            if (studentId == null) {
                          synchronized (sid2seat) {
                                            if (sid2seat.isEmpty()) {
                                                                  out.println("No sign-in record yet.<br><br>");
                                            } else {
                                                                  for (Map.Entry<String, String> e : sid2seat.entrySet()) {
                                                                                            out.println(e.getKey() + " : " + e.getValue() + "<br>");
                                                                  }
                                            }
                          }
                          out.println("<br><a href=\"index.html\">Attendance Submission</a>");
            } else if (!studentId.trim().isEmpty()) {
                          synchronized (sid2seat) {
                                            sid2seat.put(studentId.trim(), seatId == null ? "" : seatId.trim());
                          }
                          out.println(studentId + ":" + seatId + " is recorded:<br><br>");
                          out.println("<a href=\"index.html\">Attendance Submission</a> | "
                                                            + "<a href=\"Attend\">Attendee List</a>");
            } else {
                          out.println("Student Id can not be blank<br><br>");
                          out.println("<a href=\"index.html\">Attendance Submission</a> | "
                                                            + "<a href=\"Attend\">Attendee List</a>");
            }
                }
  }
