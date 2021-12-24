package controller;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
	
}

