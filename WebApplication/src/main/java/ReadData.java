

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name="ReadData", urlPatterns="{/ReadData}")
public class ReadData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
        String criteria = request.getParameter("criteria");
        String value=request.getParameter("value");
		request.setAttribute("value",value);
		request.setAttribute("criteria", criteria);
        
        Connection connection = new Connection();
        
        List<EventLogs> eventLogs = connection.findEventLogs(currentPage,
                recordsPerPage,value,criteria);
        
        request.setAttribute("eventLogs",eventLogs);
        
        long row= connection.getNumberOfRows(value,criteria);
        int rows = (int)row;
        
        int noOfPages = rows / recordsPerPage;
        
        if(noOfPages%recordsPerPage>0)
        {
        	noOfPages++;
        }
        
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("view.jsp");
        dispatcher.forward(request, response);
         
	}

	

}
