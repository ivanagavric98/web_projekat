package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.MenagerDAO;
import model.Menager;

public class MenagerService {
private MenagerDAO menagerDAO;
public MenagerService(MenagerDAO menagerDao) {
  this.menagerDAO=menagerDao;
}

public Boolean register(Menager menager) throws JsonSyntaxException, IOException {
    ArrayList<Menager>menagers=getAllMenagers();
    
    Boolean result=false;
    if(menagers == null){
        menagerDAO.create(menager);
        result=true;
    }else{
        for(Menager u : menagers){
            if(u.username.equals(menager.username)){
            	return result= false;
        }
        }
        menagerDAO.create(menager);
        result=true;
    
    }
    
    return result;
}
	 
	public ArrayList<Menager> getAllMenagers() throws JsonSyntaxException, IOException{
	    return menagerDAO.getAll();
	}
	
	public void update(Menager menager) throws JsonSyntaxException, IOException{
	     menagerDAO.update(menager);
	}
	
	public ArrayList<Menager> getAllMenagersWithoutRestaurant() throws JsonSyntaxException, IOException {
		
		ArrayList<Menager> menagers = menagerDAO.getAll();
		ArrayList<Menager> menagersWithoutRestaurants = new ArrayList<Menager>();
		
		if(menagers != null) {
			for(Menager m : menagers) {
				if(m.getRestaurant() == null) {
					menagersWithoutRestaurants.add(m);
				}
			}
		}
		return menagersWithoutRestaurants;
	}

	public Menager getMenagerByUsername(String username) throws JsonSyntaxException, IOException {
		return menagerDAO.getByID(username);
	}

}
