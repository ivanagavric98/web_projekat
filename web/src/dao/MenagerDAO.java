package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Menager;
import model.User;

public class MenagerDAO implements IDAO<Menager, String>{

	private String path;
    private ArrayList<Menager
    > users;

	
	public MenagerDAO(String path) {
		super();
		this.path = path;
		
		try {
            getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public ArrayList<Menager> getAll() throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Menager>>(){}.getType();
        BufferedReader br = new BufferedReader(new FileReader("data/menagers.json"));
        this.users = gson.fromJson(br, token);
		return users;
	}

	@Override
	public Menager getByID(String id) throws JsonSyntaxException, IOException {
		Menager wanted = null;
		ArrayList<Menager> menagers = (ArrayList<Menager>) getAll();
		if(menagers.size()!=0)
		{
			for(Menager menager : menagers) {
				if(menager.getUsername().equals(id)) {
					wanted = menager;
					break;
				}
			}
		}
		return wanted;
	}

	@Override
	public void create(Menager entity) throws JsonSyntaxException, IOException {
		ArrayList<Menager> menagers = getAll();
		if (menagers == null) {
			menagers = new ArrayList<Menager>();
		}
		menagers.add(entity);
		saveAll(menagers);	
	}

	@Override
	public void update(Menager entity) throws JsonSyntaxException, IOException {
		ArrayList<Menager> menagers = getAll();
		for(Menager user : menagers) {
			if(user.getUsername().equals(entity.getUsername())) {
				menagers.set(menagers.indexOf(user), entity);
				break;
			}
		}
		saveAll(menagers);
		
	}

	@Override
	public void delete(Menager entity) throws JsonSyntaxException, IOException {
		return;		
	}

	@Override
	public void save(Menager entity) throws JsonSyntaxException, IOException,FileNotFoundException {
		ArrayList<Menager> menagers= getAll();
		menagers.add(entity);
		saveAll(menagers);	
		
	}

	@Override
	public void saveAll(ArrayList<Menager> entities) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Menager>>(){}.getType());
		writer.println(allEntities);
		writer.close();		
	}

	@Override
	public ArrayList<Menager> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
