package eventFinderServer.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import eventFinderServer.model.Customer;
import eventFinderServer.model.Event;
import eventFinderServer.model.User;
import eventFinderServer.repository.CustomerRepository;
import eventFinderServer.repository.EventRepository;
import eventFinderServer.repository.PagedEventRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600,allowCredentials = "true")
public class YelpEventAPIService {

	@Autowired
    EventRepository eventRepo;
	@Autowired
	PagedEventRepository pagedRepository;
	@Autowired
	CustomerRepository customerRepo;
	


  private String accessKey = "jxRTE_Dpy5iu5KA00c1iApZngA91VX1CcPW86PIdoo6yInYk-rzoZ0YWYAAx2chyVGgvf4M98eGJNwdfFd4D4ziOB20sLyIQkHEWxXwGdhPBAOSZ62kBRWKCYdwDXHYx";
  private OkHttpClient client = new OkHttpClient();
  ObjectMapper mapper = new ObjectMapper();
  
  
  
  
  @GetMapping("/api/search/{city}")
  List<Event> findEventBycity(
		  @RequestParam(name="offset", required=false, defaultValue="0") Integer offset,
		  @PathVariable("city") String city) throws IOException, JSONException{
	  
	  String url = "https://api.yelp.com/v3/events?sort_by=desc" +"&offset="+offset+"&limit=10&radius=2000"+ "&location=" +  city;
	  Request request = new Request.Builder()
	            .url(url)
	            .get()
	            .addHeader("authorization", "Bearer " + accessKey)
	            .addHeader("cache-control", "no-cache")
	            .addHeader("postman-token", "d2f54ea5-ca7e-db6a-9b24-1228699d1030")
	            .build();
	  
	  Response response = client.newCall(request).execute();
	  JSONObject  jsonObject = new JSONObject(response.body().string().trim());
	  JSONArray myResponse = (JSONArray) jsonObject.get("events");
	  
	  
	  //return jsonArrayToEventList(myResponse);
	  List <Event> localEvents = eventRepo.findEventByCity(city); 
	  List <Event> remoteEvents = jsonArrayToEventList(myResponse);
	  localEvents.addAll(remoteEvents);
	  
	  
	  
	  return localEvents;
  }
  
  @GetMapping("/api/search/{city}/{page}")
  List<Event> findPagedEventByCity(@PathVariable("page") int page, @PathVariable("city") String city) throws IOException, JSONException{
	  
	  int pageSize = 10;
	 List <Event> localEvents = eventRepo.findEventByCity(city); 
	 List <Event> renderEvent = new ArrayList<>();
	 int size = localEvents.size();
	 
	 
	 /*if (localEvents.size()<=10){
		 return localEvents;
	 }*/
	 if(size<pageSize*page) {
		 int offset = pageSize*page - size;
		 
		 String url = "https://api.yelp.com/v3/events?sort_by=desc&limit=" + offset+"&radius=2000"+ "&location=" +  city;
		  Request request = new Request.Builder()
		            .url(url)
		            .get()
		            .addHeader("authorization", "Bearer " + accessKey)
		            .addHeader("cache-control", "no-cache")
		            .addHeader("postman-token", "d2f54ea5-ca7e-db6a-9b24-1228699d1030")
		            .build();
		  
		  Response response = client.newCall(request).execute();
		  JSONObject  jsonObject = new JSONObject(response.body().string().trim());
		  JSONArray myResponse = (JSONArray) jsonObject.get("events");
		  List <Event> remoteEvents = jsonArrayToEventList(myResponse);
		  localEvents.addAll(remoteEvents);
		  System.out.println(localEvents.size());
		 for (int i = pageSize*(page-1);i< pageSize*page;i++) {
			 renderEvent.add(localEvents.get(i));	 
		 }
		 	 
		 
	 }  	  
	  
	  return renderEvent;
  }
  
  
  
 
  
  
  /*
  @GetMapping("/api/search/{city}")
  List<Event> findEventBycity(
		 
		  @PathVariable("city") String city) throws IOException, JSONException{
	  
	  String url = "https://api.yelp.com/v3/events?limit=5&radius=2000"+ "&location=" +  city;
	  Request request = new Request.Builder()
	            .url(url)
	            .get()
	            .addHeader("authorization", "Bearer " + accessKey)
	            .addHeader("cache-control", "no-cache")
	            .addHeader("postman-token", "d2f54ea5-ca7e-db6a-9b24-1228699d1030")
	            .build();
	  
	  Response response = client.newCall(request).execute();
	  JSONObject  jsonObject = new JSONObject(response.body().string().trim());
	  JSONArray myResponse = (JSONArray) jsonObject.get("events");
	  
	  
	  List <Event> localEvents = eventRepo.findEventByCity(city);
	  
	  List <Event> remoteEvents = jsonArrayToEventList(myResponse);
	  localEvents.addAll(remoteEvents);
	  eventRepo.saveAll(remoteEvents);
	  
	  return localEvents;
  }
  */
  /*
  @GetMapping("/api/search/{city}/{category}")
  List<Event> findEventBycityAndTerm(@PathVariable("category") String category,@PathVariable("city") String city) throws IOException, JSONException{
	  
	  String url = "https://api.yelp.com/v3/events?limit=20";
	  city = city.replace(" ", "%20");
	   category = category.replace(" ", "%20");
	    url =  url+"&location=" + city + "&categories=" + category;

	  Request request = new Request.Builder()
	            .url(url)
	            .get()
	            .addHeader("authorization", "Bearer " + accessKey)
	            .addHeader("cache-control", "no-cache")
	            .addHeader("postman-token", "d2f54ea5-ca7e-db6a-9b24-1228699d1030")
	            .build();
	  
	  Response response = client.newCall(request).execute();
	  JSONObject  jsonObject = new JSONObject(response.body().string().trim());
	  JSONArray myResponse = (JSONArray) jsonObject.get("events");
	  
	  
	  List <Event> localEvents = eventRepo.findEventByCityAndCategories(category,city);  
	  List <Event> remoteEvents = jsonArrayToEventList(myResponse);
	  localEvents.addAll(remoteEvents);
	  
	  return localEvents;
  }*/
  
  
  @GetMapping("/api/detail/info/{event_id}")
  public Event findEventDetailByEventId(@PathVariable("event_id") String event_id)
          throws IOException, JSONException { 
	  
	  Optional <Event> e = eventRepo.findEventByEvent_id(event_id);
	  if(e.isPresent()) {
		  return e.get();
	  }else{
	  
	  
	  String url = "https://api.yelp.com/v3/events/" +  event_id;
	  Request request = new Request.Builder()
	            .url(url)
	            .get()
	            .addHeader("authorization", "Bearer " + accessKey)
	            .addHeader("cache-control", "no-cache")
	            .addHeader("postman-token", "d2f54ea5-ca7e-db6a-9b24-1228699d1030")
	            .build();
	  
	  Response response = client.newCall(request).execute();
	  JSONObject  jsonObject = new JSONObject(response.body().string().trim());
     
      return jsonToEvent(jsonObject);
	  }
	 
  }
  

  /*
	@PostMapping("/api/customer/like/event/{eid}")
	public void likeEventByCustomer(@PathVariable("eid") String eid, HttpSession session) throws JSONException, IOException {
		Customer c = (Customer) session.getAttribute("currentUser");
		System.out.print(c.getUsername());
		Event e = findLikedEventByEventId(eid);
		
		if (e!=null)
			e.setInterested_count(e.getInterested_count()+1);
			c.likeEvent(e);
			customerRepo.save(c);
			
		
		
	}*/
  
   @PostMapping("/api/like/event/{eid}")
	public void likeEventByCustomer(@PathVariable("eid") String eid, HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
		//Customer c = (Customer) session.getAttribute("currentUser");
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			User currentUser = (User) session.getAttribute("currentUser");
			if(currentUser.getUserType().equals("CUSTOMER_USER")){
				Customer c  = (Customer)currentUser;
		Event e = findLikedEventByEventId(eid);
		
		if (e!=null) {
			e.setInterested_count(e.getInterested_count()+1);
			c.likeEvent(e);
			eventRepo.save(e);
			customerRepo.save(c);
		}
			}
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
	}
  
	
	@PostMapping("/api/in/event/{eid}")

	public void customerAttendEvent(@PathVariable("eid") String eid, HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			User currentUser = (User) session.getAttribute("currentUser");
			if(currentUser.getUserType().equals("CUSTOMER_USER")){
				Customer c  = (Customer)currentUser;

				Event e = findLikedEventByEventId(eid);
				
				if (e!=null) {
					e.setAttending_count(e.getAttending_count()+1);
					System.out.print(e.getAttending_count());
					c.attendEvent(e);
					eventRepo.save(e);
					customerRepo.save(c);
					
				}	
				
			}
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
	}
	
	
	
	
	
	
	private Event findLikedEventByEventId( String event_id)
	          throws IOException, JSONException { 
		  
		  Optional <Event> e = eventRepo.findEventByEvent_id(event_id);
		  if(e.isPresent()) {
			  return e.get();
		  }else{
		  
		  
		  String url = "https://api.yelp.com/v3/events/" +  event_id;
		  Request request = new Request.Builder()
		            .url(url)
		            .get()
		            .addHeader("authorization", "Bearer " + accessKey)
		            .addHeader("cache-control", "no-cache")
		            .addHeader("postman-token", "d2f54ea5-ca7e-db6a-9b24-1228699d1030")
		            .build();
		  
		  Response response = client.newCall(request).execute();
		  JSONObject  jsonObject = new JSONObject(response.body().string().trim());
		   Event e2 = jsonToEvent(jsonObject);
		   eventRepo.save(e2);
	     
	      return  e2;
		  }
		  
		 
	  }
  
	
	
  

  private List<Event> jsonArrayToEventList(JSONArray response) throws JSONException {
    List<Event> events = new ArrayList<>();
    for (int i = 0; i < response.length(); i++) {
      JSONObject temp = response.getJSONObject(i);
      Event e = jsonToEvent(temp);
      events.add(e);
    }
    
    return events;
  }


  private Event jsonToEvent(JSONObject object) throws JSONException{  
    Event res = new Event(); 
    res.setId(object.getString("id"));
    res.setName(object.getString("name"));
    res.setCity(object.getJSONObject("location").getString("city"));
    try {
    res.setCost(object.getDouble("cost"));
    } catch(Exception e) {
    	res.setCost(0);
    }
    res.setAttending_count(object.getInt("attending_count"));
    res.setEvent_site_url(object.getString("event_site_url"));
    res.setImage_url(object.getString("image_url"));
    res.setInterested_count(object.getInt("interested_count"));
    res.setCategory(object.getString("category"));
    res.setBusiness_id(object.getString("business_id"));
    res.setIs_canceled(object.getBoolean("is_canceled"));
    res.setIs_free(object.getBoolean("is_free"));
    res.setIs_official(object.getBoolean("is_official"));
    res.setTicket_url(object.getString("tickets_url"));
    res.setLatitude(object.getDouble("latitude"));
    res.setState(object.getJSONObject("location").getString("state"));
    res.setZip_code(object.getJSONObject("location").getString("zip_code"));
    res.setTime_end(object.getString("time_end"));
    res.setTime_start(object.getString("time_start"));
    res.setCountry(object.getJSONObject("location").getString("country"));
    res.setLongitude(object.getDouble("longitude"));
    
    JSONArray displayAddress = object.getJSONObject("location").getJSONArray("display_address");
    StringBuffer address = new StringBuffer();
    for (int j = 0; j < displayAddress.length(); j++) {
      address.append(displayAddress.get(j)).append(", ");
    }
    res.setAddress(address.substring(0, address.length() - 2).toString());
    
    res.setDescription(object.getString("description"));
    return res;

  }

}

