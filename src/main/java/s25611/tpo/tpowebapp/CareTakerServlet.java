package s25611.tpo.tpowebapp;

import dbController.DbConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(value = "/api/caretakers")
public class CareTakerServlet extends HttpServlet {

    DbConnect connection = new DbConnect();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String careTakerNameVal = req.getParameter("careTakerName");
        String careTakerLastNameVal = req.getParameter("careTakerLastName");
        String careTakerPhoneVal = req.getParameter("careTakerPhone");

       // System.out.println(x);

        HashMap<String, String> map = new HashMap<>();

        map.put("First_Name", careTakerNameVal);
        map.put("Last_Name", careTakerLastNameVal);
        map.put("Phone", careTakerPhoneVal);
        final String[] query =  new String[]{
                "SELECT First_Name, Last_Name, Phone, Date_of_Birth, Email " +
                " FROM caretakers "};

        boolean[] hasEntry = new boolean[]{false};
        map.forEach((key,value) -> {

            if(value == null || value.equals("")){
                return;
            }
            if(hasEntry[0]){
                query[0] = query[0] + " AND ";
            } else {
                query[0] = query[0] + "WHERE ";
                hasEntry[0] = true;
            }
            query[0] = query[0] + key + " LIKE '%" + value + "%' ";
        });


//        if(careTakerNameVal != null) {
//             query = query + " First_Name LIKE '%" + careTakerNameVal + "%' ";
//        }
//        if(careTakerLastNameVal != null){
//            query = query + " Last_Name LIKE '%'" + careTakerLastNameVal +"%'";
//        }
//        if(careTakerPhoneVal != null){
//            query = query + " Phone LIKE '%'" + careTakerPhoneVal +"%'";
//        }

//        String query = "SELECT Name, Species, Date_of_Birth, Gender, Country_of_Origin FROM animals WHERE" +
//                " Name LIKE '%" + x + "%' " +
//                "OR Species LIKE '%" + x + "%' " +
//                "OR Date_of_Birth LIKE '%" + x +
//                "%' OR Gender LIKE '%" + x + "%' " +
//                "OR Country_of_Origin LIKE '%" + x + "%'";

        System.out.println(query[0]);


        String careTakersList = connection.execSql(query[0]);

        // String animalsList = animalService.getAnimalsList();
        try {
            PrintWriter out = resp.getWriter();
            out.println("<html><head>");
            out.println("<style>");
            out.println("body { margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f2f2f2; }");
            out.println("table { width: 90%; max-width: 3000px; border-collapse: collapse; background-color: #fff; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            out.println("th, td { border: 1px solid #ddd; padding: 12px; text-align: center; }");
            out.println("th { background-color: #f5f5f5; }");
            out.println("</style>");
            out.println("</head><body>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>First Name</th>");
            out.println("<th>Last Name</th>");
            out.println("<th>Phone</th>");
            out.println("<th>Date of Birth</th>");
            out.println("<th>Email</th>");
            out.println("</tr>");

            String[] rows = careTakersList.split("\n");

            for (String row : rows) {
                String[] columns = row.split("\\s+");

                out.println("<tr>");
                for (int i = 0; i <= 4; i++) {
                    out.println("<td>" + columns[i] + "</td>");
                }
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

        //resp.getWriter().println("hello from TPO");
        //resp.addCookie(new Cookie("tpo" , "6"));




