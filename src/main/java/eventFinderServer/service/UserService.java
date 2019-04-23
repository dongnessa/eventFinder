package eventFinderServer.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eventFinderServer.model.Admin;
import eventFinderServer.model.Customer;
import eventFinderServer.model.Seller;
import eventFinderServer.model.User;
import eventFinderServer.repository.AdminRepository;
import eventFinderServer.repository.CustomerRepository;
import eventFinderServer.repository.SellerRepository;
import eventFinderServer.repository.UserRepository;

@Transactional
@RestController
@CrossOrigin(origins = "*",maxAge=3600,allowCredentials = "true")
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired 
	private SellerRepository sellerRepo;
	@Autowired
	  AdminRepository adminRepository;
	
	
	
	
	@PostMapping("/api/register")
	public User register(@RequestBody User user,HttpSession session) {
		session.setAttribute("currentUser", user);
		Optional<User> t = userRepo.findUserByUsername(user.getUsername());
		if(t!= null)
			return null;
		userRepo.save(user);
		return user;
	}
	@PutMapping("/api/profile")
	public User updateProfile(@RequestBody User user,HttpSession session) {
		Optional<User> opt = userRepo.findById(user.getId());
		if(!opt.isPresent())
			return null;
		user.setPassword(opt.get().getPassword());
		userRepo.save(user);
		return user;
	}
	
	@GetMapping("/api/profile")
	public User profile(HttpSession session) {
		User currentUser = (User)session.getAttribute("currentUser");	
		return currentUser;
	}
	
	@PostMapping("/api/login")
	public User login(@RequestBody User credential, HttpSession session) {
		Optional<User> user = userRepo.findUserByUsername(credential.getUsername());
		if(user!= null)
		{
			
			User data = user.get();
			if (data.getPassword().equals(credential.getPassword())){
			session.setAttribute("currentUser",data);
			}
			return data;
		}
		return null;
	}
	
	@PostMapping("/api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	@GetMapping("/api/user")
	public List<User> findAllUser(){
		return (List<User>)userRepo.findAll();
	}
	
	@GetMapping("/api/user/{uid}")
	public User findUserById(@PathVariable("uid") Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if(user.isPresent())
			return user.get();
		return null;
	}
	
	@DeleteMapping("/api/user/delete/{uid}")
	  public boolean deleteUserById(@PathVariable("uid") long userId, HttpSession session){
		User u = (User)session.getAttribute("currentUser");
		Optional<User> currentUser = userRepo.findById(u.getId());
		if(u.getId()!=userId&&currentUser.isPresent()) {
			User u2 = currentUser.get();
			
		if(u2.getUserType().equals("ADMIN_USER")){	
		
	    userRepo.deleteById(userId);
	    return true;
		}
	}
		return false;
	}
	  
	
	// finders apis
	
		@GetMapping("/api/user/customer")
		  public List<Customer> findAllCustomers() {
		    return (List<Customer>) customerRepository.findAll();
		  }

		@GetMapping("/api/user/seller")
		  public List<Seller> findAllSellers() {
		    return (List<Seller>) sellerRepo.findAll();
		  }
		@GetMapping("/api/admin/user")
		  public List<User> findAllUsers() {
		    return (List<User>) userRepo.findAll();
		  }
		
		
		@GetMapping("/api/currentUser")
		  public User findCurrentUser(HttpSession session) {
		    User currentUser = (User) session.getAttribute("currentUser");
		    if (currentUser == null) {
		      return null;
		    } else {
		      Optional<User> data = userRepo.findById(currentUser.getId());
		      return data.orElse(null);
		    }
		  }
		
		@PostMapping("/api/customer/signUp")
		  public Customer customerRegister(@RequestBody Customer customer, HttpSession session) {
		    String username = customer.getUsername();

		   Optional <User> data = userRepo.findUserByUsername(username);
		    if (!data.isPresent() ) {
		      Customer newCustomer = new Customer();
		      newCustomer.setUsername(customer.getUsername());
		      newCustomer.setPassword(customer.getPassword());
		      

		      newCustomer.setFirstName(customer.getFirstName());
		      newCustomer.setLastName(customer.getLastName());
		      newCustomer.setEmail(customer.getEmail());
		      newCustomer.setPassword(customer.getPassword());
		      newCustomer.setAddress(customer.getAddress());
		      newCustomer.setPhone(customer.getPhone());
		      newCustomer.setPhotoLink(customer.getPhotoLink());

		      Customer savedCustomer = userRepo.save(newCustomer);
		      //session.setAttribute("currentUser", savedCustomer);
		      return savedCustomer;
		    }
		    return null;
		}
		
		@PostMapping("/api/seller/signUp")
		  public Seller sellerRegister(@RequestBody Seller s, HttpSession session) {
		    String username = s.getUsername();

		   Optional<User> data = userRepo.findUserByUsername(username);
		    if (!data.isPresent()) {
		      Seller newSeller = new Seller();
		      newSeller.setUsername(s.getUsername());
		      newSeller.setPassword(s.getPassword());
		      newSeller.setFirstName(s.getFirstName());
		      newSeller.setLastName(s.getLastName());
		      newSeller.setEmail(s.getEmail());
		      newSeller.setPassword(s.getPassword());
		      newSeller.setAddress(s.getAddress());
		      newSeller.setPhone(s.getPhone());
		      newSeller.setPhotoLink(s.getPhotoLink());

		      Seller savedSeller = userRepo.save(newSeller);
		      //session.setAttribute("currentUser", savedSeller);
		      return savedSeller;
		    }
		    return null;
		}
		
		
		@PostMapping("/api/admin/signUp")
		  public Admin adminRegister(@RequestBody Admin s, HttpSession session) {
		    String username = s.getUsername();

		   Optional <User> data = userRepo.findUserByUsername(username);
		    if (!data.isPresent()) {
		      Admin newAdmin = new Admin();
		      newAdmin.setUsername(s.getUsername());
		      newAdmin.setPassword(s.getPassword());
		      newAdmin.setFirstName(s.getFirstName());
		      newAdmin.setLastName(s.getLastName());
		      newAdmin.setEmail(s.getEmail());
		      newAdmin.setPassword(s.getPassword());
		      newAdmin.setAddress(s.getAddress());
		      newAdmin.setPhone(s.getPhone());
		      newAdmin.setPhotoLink(s.getPhotoLink());
		   
		      Admin savedAdmin = adminRepository.save(newAdmin);
		     // session.setAttribute("currentUser", savedAdmin);
		      return savedAdmin;
		    }
		    return null;
		}
		
		
		
		//logins
		
		
		// logins
		  @PostMapping("/api/customer/login")
		  public Customer customerLogin(@RequestBody Customer credentials, HttpSession session) {
		    String username = credentials.getUsername();
		    String password = credentials.getPassword();

		    Optional<Customer> result = customerRepository.findUserByCredentials(username, password);

		    if (result.isPresent()) {
		      Customer customer = result.get();
		      session.setAttribute("currentUser", customer);
		      return customer;
		    }

		    return null;

		  }

		
		@PostMapping("/api/seller/login")
		  public Seller sellerLogin(@RequestBody Seller credentials, HttpSession session) {
		    String username = credentials.getUsername();
		    String password = credentials.getPassword();

		    Optional<Seller> result = sellerRepo.findUserByCredentials(username, password);

		    if (result.isPresent()) {
		      Seller s = result.get();    
		      session.setAttribute("currentUser", s);
		      return s;
		    }

		    return null;
		  }
		
		@PostMapping("/api/admin/login")
		  public Admin adminLogin(@RequestBody Admin credentials, HttpSession session) {
		    String username = credentials.getUsername();
		    String password = credentials.getPassword();
		    Optional<Admin> result = adminRepository.findUserByCredentials(username, password);
		    if (result.isPresent()) {
		      Admin admin = result.get();
		      session.setAttribute("currentUser", admin);
		      return admin;
		    }
		    return null;
		  }
		
		
		//how to check user type
		
		@GetMapping("/api/isCustomer")
		public Boolean isCustomer( HttpSession session) {
			User u = (User) session.getAttribute("currentUser");
			if (u.getUserType().equals("CUSTOMER_USER")){
				return  true;
			}
			return false;
			
		}
		
		@GetMapping("/api/isAdmin")
		public Boolean isAdmin( HttpSession session) {
			User u = (User) session.getAttribute("currentUser");
			if (u.getUserType().equals("ADMIN_USER")){
				return  true;
			}
			return false;
			
		}
		
		@GetMapping("/api/isSeller")
		public Boolean isSeller( HttpSession session) {
			User u = (User) session.getAttribute("currentUser");
			if (u.getUserType().equals("SELLER_USER")){
				return  true;
			}
			return false;
			
		}
		
		
		
		@GetMapping("/api/isLogin")
		 public Boolean checkLogin(HttpSession session) {
			User c = (User) session.getAttribute("currentUser");
			Optional<User> data = userRepo.findById(c.getId());
			if(data.isPresent()) {
				User u = data.get();
			if(u.getUserType().equals("SELLER_USER")
					||u.getUserType().equals("ADMIN_USER")||(u.getUserType().equals("CUSTOMER_USER"))){
				return true;
				
			}
			
			}
			return false;
					
			
		}
			
		
		
		@PostMapping("/api/admin/create/user")
		  public User adminCreateUser(@RequestBody User user){
		    switch (user.getUserType()) {
		      case "CUSTOMER_USER":
		        Customer customer = new Customer();
		        customer.setPassword(user.getPassword());
		        customer.setUsername(user.getUsername());
		        return customerRepository.save(customer);
		      case "OWNER_USER":
		       Seller  s = new Seller();
		        s.setPassword(user.getPassword());
		        s.setUsername(user.getUsername());
		        return sellerRepo.save(s);
		      default:
		        return null;
		    }
		  }
		
		
		
		
		@PutMapping("/api/customer/profile/update")
		  public Customer customerProfileUpdate(@RequestBody Customer user, HttpSession session) {
		    Optional<Customer> data = customerRepository.findById(user.getId());
		    if (data.isPresent()) {
		      Customer existedUser = data.get();
		      existedUser.setUsername(user.getUsername());
		      existedUser.setFirstName(user.getFirstName());
		      existedUser.setLastName(user.getLastName());
		      existedUser.setEmail(user.getEmail());
		      existedUser.setPassword(user.getPassword());
		      existedUser.setAddress(user.getAddress());
		      existedUser.setPhone(user.getPhone());
		      existedUser.setPhotoLink(user.getPhotoLink());
		      session.setAttribute("currentUser", existedUser);
		      return customerRepository.save(existedUser);
		    } else {
		      return null;
		    }

		  }
		
		@PutMapping("/api/seller/profile/update")
		  public Seller sellerProfileUpdate(@RequestBody Seller user, HttpSession session) {
		    Optional<Seller> data = sellerRepo.findById(user.getId());
		    if (data.isPresent()) {
		      Seller existedUser = data.get();
		      existedUser.setUsername(user.getUsername());
		      existedUser.setFirstName(user.getFirstName());
		      existedUser.setLastName(user.getLastName());
		      existedUser.setEmail(user.getEmail());
		      existedUser.setPassword(user.getPassword());
		      existedUser.setAddress(user.getAddress());
		      existedUser.setPhone(user.getPhone());
		      existedUser.setPhotoLink(user.getPhotoLink());
		      session.setAttribute("currentUser", existedUser);
		      return sellerRepo.save(existedUser);
		    } else {
		      return null;
		    }

		  }
		
		@PutMapping("/api/user/profile/update")
		public User userProfileUpdate(@RequestBody User user, HttpSession session) {
		Optional<User> data = userRepo.findById(user.getId());
		if (data.isPresent()) {
		User existedUser = data.get();
		existedUser.setUsername(user.getUsername());
		existedUser.setFirstName(user.getFirstName());
		existedUser.setLastName(user.getLastName());
		existedUser.setEmail(user.getEmail());
		existedUser.setPassword(user.getPassword());
		existedUser.setAddress(user.getAddress());
		existedUser.setPhone(user.getPhone());
		existedUser.setPhotoLink(user.getPhotoLink());
		return userRepo.save(existedUser);
		} else {
		return null;
		}
		}
		
		
		
		  @PutMapping("/api/admin/user/update")
		  public User adminUpdateUser(@RequestBody User user) {
		    Optional<User> data = userRepo.findById(user.getId());
		    if (data.isPresent()) {
		      User existedUser = data.get();
		      existedUser.setUsername(user.getUsername());
		      existedUser.setEmail(user.getEmail());
		      existedUser.setFirstName(user.getFirstName());
		      existedUser.setLastName(user.getLastName());
		      existedUser.setPassword(user.getPassword());
		      existedUser.setAddress(user.getAddress());
		      existedUser.setPhone(user.getPhone());
		      existedUser.setPhotoLink(user.getPhotoLink());
		      return userRepo.save(existedUser);
		    } else {
		      return null;
		    }

		  }
		  

		
	
		
		
		
		
		
		
		
		
		
		
		
		
	
}