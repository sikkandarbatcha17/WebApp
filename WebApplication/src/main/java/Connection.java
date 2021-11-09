import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.client.RequestOptions;
import java.util.concurrent.TimeUnit;

public class Connection implements IConnection{

	@Override
	public List<EventLogs> findEventLogs(int currentPage, int recordsPerPage,String value,String criteria) {
		
		 List<EventLogs> eventLogs=new ArrayList<EventLogs>();

	     int start = currentPage * recordsPerPage - recordsPerPage;
	        
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(new HttpHost("localhost", 9200, "http")));
		
		SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("windowslogs");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(criteria, value)); 
        searchSourceBuilder.from(start); 
        searchSourceBuilder.size(recordsPerPage);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        Map<String, Object> map=null;
        
        try {
            SearchResponse searchResponse = null;
            searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    map = hit.getSourceAsMap();
                    Object list[]= new Object[7];
                    EventLogs obj=new EventLogs();
                    int count=0;
                    for (Map.Entry<String,Object> entry : map.entrySet()){
                    	 list[count++]=entry.getValue();  
                    }
                    Object severity[]=new Object[]{"Critical","Error","Warning",
                    		"Information","Audit Success","Audit Failure"
                    };
                    int svt=(Integer) list[0];
                    if(svt==0)
                    	obj.setSeverity(severity[0]);
                    else if(svt==1)
                    	obj.setSeverity(severity[1]);
                    else if(svt==2)
                    	obj.setSeverity(severity[2]);
                    else if(svt==4)
                    	obj.setSeverity(severity[3]);
                    else if(svt==8)
                    	obj.setSeverity(severity[4]);
                    else if(svt==16)
                    	obj.setSeverity(severity[5]);
                  //  obj.setSeverity(list[0]);
                    obj.setTimeGenerated(list[1]);
                    obj.setEventId(list[2]);
                    obj.setId(list[3]);
                    obj.setSource(list[4]);
                    obj.setType(list[5]);
                    obj.setMessage(list[6]);
                    eventLogs.add(obj);   
                }
        }
     
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
		return eventLogs;
	}

	@Override
	public long getNumberOfRows(String value, String criteria) {

		long numOfRows = 0;
		
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(new HttpHost("localhost", 9200, "http")));
		
		SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("windowslogs");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(criteria, value)); 
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        Map<String, Object> map=null;
        long numHits = 0;
        try {
            SearchResponse searchResponse = null;
            searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
            	SearchHits hits= searchResponse.getHits();
            	TotalHits totalHits = hits.getTotalHits();
            	numHits = totalHits.value;
            }
            
            numOfRows=numHits;
        }
        catch (IOException e) {
           e.printStackTrace();
        }
		
		return numOfRows;
	}
	
	@Override
	public List<KeyValues> findTopHits(String value,String criteria,String eventCat){
		
		
		List<KeyValues> list = new ArrayList<KeyValues>();
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(new HttpHost("localhost", 9200, "http")));
	
		SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("windowslogs");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(criteria, value)); 
        searchSourceBuilder.from(0); 
        searchSourceBuilder.size(9000);
        searchSourceBuilder.aggregation(AggregationBuilders.terms("agg").field(eventCat).subAggregation(AggregationBuilders.topHits("top")));
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
       
        try {
            SearchResponse searchResponse = null;
            searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
            	
                Terms agg = searchResponse.getAggregations().get("agg");
                
             for (Terms.Bucket entry : agg.getBuckets()) {
            	 KeyValues obj= new KeyValues();
                 obj.setTopValue(entry.getKey());                    
                 obj.setCount(entry.getDocCount()); 
                 list.add(obj);                
                 }
             }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
       return list;
	}
	
}