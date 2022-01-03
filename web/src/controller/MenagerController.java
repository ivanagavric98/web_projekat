package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Article;
import model.Menager;
import service.MenagerService;

public class MenagerController {
    private MenagerService menagerService;
	private static Gson gson = new Gson();

	public MenagerController(MenagerService menagerService) {
		this.menagerService = menagerService;
	}

	public Boolean register(Menager menager) throws JsonSyntaxException, IOException{			
		return menagerService.register(menager);
	}
	
	public ArrayList<Menager> getAllManagersWithoutRestaurant() throws JsonSyntaxException, IOException{
		return menagerService.getAllMenagersWithoutRestaurant();
	}
	
	public Menager getMenagerByUsername(String username) throws JsonSyntaxException, IOException{
		 return menagerService.getMenagerByUsername(username);
	}

}

