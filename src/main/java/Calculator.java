import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

@WebServlet(name = "calculatorServlet", urlPatterns = "/calculator")
public class Calculator extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(Calculator.class);



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Calculator servlet");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        try{
            int firstNumber = Integer.parseInt(req.getParameter("n1"));
            int secondNumber = Integer.parseInt(req.getParameter("n2"));
            String action = req.getParameter("act");
            out.println("<html><body>");
            int res;

            if (action == null) {
                out.println("<h1>" + "You forgot to add action. Pls use sum, sub, div, mul. If multiple actions needed, use - (ex. sum-sub)" + "</h1>");
            }


            if (action != null) {
                String[] paramArray = action.split("-");
                for (String value : paramArray) {
                    if (value.equals("sum")) {
                        res = firstNumber + secondNumber;
                        out.println(String.format("<h1>%d + %d = %d</h1>", firstNumber, secondNumber, res));
                    }

                    if (value.equals("sub")) {
                        res = firstNumber - secondNumber;
                        out.println(String.format("<h1>%d - %d = %d</h1>", firstNumber, secondNumber, res));
                    }

                    if (value.equals("div")) {
                        double res2 = (double) firstNumber / secondNumber;
                        DecimalFormat df = new DecimalFormat("#.########");
                        String formattedNumber = df.format(res2);
                        out.println(String.format("<h1>%d : %d = %s</h1>", firstNumber, secondNumber, formattedNumber));
                    }

                    if (value.equals("mul")) {
                        res = firstNumber * secondNumber;
                        out.println(String.format("<h1>%d * %d = %d</h1>", firstNumber, secondNumber, res));
                    }
                }
            }
            out.println("<br></html></body>");

            out.close();
        }catch (NumberFormatException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Установка статуса HTTP 400 Bad Request
            out.println("<html><body><h1>Error: Please enter valid numbers for n1 and n2</h1><br></html></body>");
        }
    }

}
