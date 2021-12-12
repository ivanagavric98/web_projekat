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

public Boolean register(Menager menager,String restaurantString) throws JsonSyntaxException, IOException {
    ArrayList<Menager>menagers=getAllMenagers();
    if(!restaurantString.equals("x")){
        menager.setRestaurant(restaurantString);
    }else{
        menager.setRestaurant("");
    }
    Boolean result=false;
    if(menagers.size()==0){
        menagerDAO.create(menager);
        result=true;
    }else{
        for(Menager u : menagers){
            if(u.username.equals(menager.username)){
            result= false;
            }else{
                menagerDAO.create(menager);
                result=true;
            }
        }
    }
    
    return result;
}
 
public ArrayList<Menager> getAllMenagers() throws JsonSyntaxException, IOException{
    return menagerDAO.getAll();
}

}
