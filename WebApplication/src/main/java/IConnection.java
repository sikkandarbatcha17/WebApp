import java.util.List;
import java.util.Map;

public interface IConnection {
	List<EventLogs> findEventLogs(int currentPage, int numOfRecords,String eventId,String logEvent);
    long getNumberOfRows(String eventId,String logEvent);
    List<KeyValues> findTopHits(String eventId,String logEvent,String eventCat);
 //   List<Long> findTopValues(String eventId,String logEvent,String eventCat);
}
