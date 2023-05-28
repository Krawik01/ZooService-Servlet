package s25611.tpo.tpowebapp;

import dbController.DbConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AnimalService;
import service.IAnimalService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(value = "/api/animals")
public class AnimalServlet extends HttpServlet {

    // @Inject
    // public IAnimalService animalService;

    DbConnect connection = new DbConnect();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String animalNameVal = req.getParameter("Name");
        String animalSpeciesVal = req.getParameter("Species");
        String animalGenderVal = req.getParameter("Gender");

        if (animalGenderVal.equals("Male")) {
            animalGenderVal = "Samiec";
        } else if (animalGenderVal.equals("Female")) {
            animalGenderVal = "Samica";
        } else {
            animalGenderVal = null;
        }

        HashMap<String, String> map = new HashMap<>();

        map.put("Name", animalNameVal);
        map.put("Species", animalSpeciesVal);
        map.put("Gender", animalGenderVal);
        final String[] query = new String[]{
                "SELECT a.Name, a.Species, a.Date_of_Birth, a.Gender, a.Weight, a.Length," +
                        " a.Country_of_Origin, c.First_Name AS Caretaker, e.Name AS Exhibit " +
                "FROM animals a " +
                "LEFT JOIN caretakers c ON a.Caretaker_ID = c.Caretaker_ID " +
                "LEFT JOIN enclosures e ON a.Enclosure_ID = e.Enclosure_ID"};

        boolean[] hasEntry = new boolean[]{false};
        map.forEach((key, value) -> {
            if (value == null || value.equals("")) {
                return;
            }
            if (hasEntry[0]) {
                query[0] = query[0] + " AND ";
            } else {
                query[0] = query[0] + " WHERE ";
                hasEntry[0] = true;
            }
            query[0] = query[0] + "a." + key + " LIKE '%" + value + "%' ";
        });

        System.out.println(query[0]);

        String animalsList = connection.execSql(query[0]);

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
            out.println("<th>Name</th>");
            out.println("<th>Species</th>");
            out.println("<th>Date of Birth</th>");
            out.println("<th>Gender</th>");
            out.println("<th>Weight</th>");
            out.println("<th>Height</th>");
            out.println("<th>Country of Origin</th>");
            out.println("<th>Caretaker</th>");
            out.println("<th>Exhibit</th>");
            out.println("</tr>");

            // Split the result into individual rows
            String[] rows = animalsList.split("\n");

            // Process each row and create the corresponding table cells
            for (String row : rows) {
                String[] columns = row.split("\\s+");

                out.println("<tr>");
                for (int i = 0; i < columns.length; i++) {
                    // If it's the last column (index = columns.length - 1),
                    // split the words into new lines using <br> tag
                    if (i == columns.length - 1) {
                        String[] words = columns[i].split(" ");
                        out.println("<td>");
                        for (String word : words) {
                            out.println(word + "<br>");
                        }
                        out.println("</td>");
                    } else {
                        out.println("<td>" + columns[i] + "</td>");
                    }
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
