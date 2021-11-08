

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TopValue")
public class TopValue extends HttpServlet {
	private static final long serialVersionUID = 1L;

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String value=request.getParameter("value");
		String criteria=request.getParameter("criteria");
		String eventCat=request.getParameter("eventCat");
		request.setAttribute("value",value);
		request.setAttribute("eventCat", eventCat);	
		
		Connection connection = new Connection();
        
        List<KeyValues> topKey = connection.findTopHits(value,criteria,eventCat);
        
        request.setAttribute("topKey", topKey);
        
		RequestDispatcher dispatcher = request.getRequestDispatcher("view2.jsp");
        dispatcher.forward(request, response);
		
	}


}
