package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Supplier;

public class SupplierDAO  implements IDAO<Supplier, String>{

	private String path;
	
	public SupplierDAO(String path) {
		super();
		this.path = path;
	}

	@Override
	public ArrayList<Supplier> getAll() throws JsonSyntaxException, IOException {
		ArrayList<Supplier> users = new Gson().fromJson((Files.readAllLines(Paths.get(path), 
				Charset.defaultCharset()).size() == 0) ? "" : 
					Files.readAllLines(Paths.get(path),
							Charset.defaultCharset()).get(0), 
					new TypeToken<List<Supplier>>(){}.getType());
		
		if(users == null)
			users = new ArrayList<Supplier>();
			
		return users;
	}

	@Override
	public Supplier getByID(String id) throws JsonSyntaxException, IOException {
		Supplier wantedUser = null;
		ArrayList<Supplier> suppliers = (ArrayList<Supplier>) getAll();
		if(suppliers.size()!=0)
		{
			for(Supplier supplier : suppliers) {
				if(supplier.getUsername().equals(id)) {
					wantedUser = supplier;
					break;
				}
			}
		}
		return wantedUser;
	}

	@Override
	public void create(Supplier entity) throws JsonSyntaxException, IOException {
		ArrayList<Supplier> suppliers = getAll();
		suppliers.add(entity);
		saveAll(suppliers);	
	}

	@Override
	public void update(Supplier entity) throws JsonSyntaxException, IOException {
		ArrayList<Supplier> suppliers = getAll();
		for(Supplier user : suppliers) {
			if(user.getUsername().equals(entity.getUsername())) {
				suppliers.set(suppliers.indexOf(user), entity);
				break;
			}
		}
		saveAll(suppliers);
		
	}

	@Override
	public void delete(Supplier entity) throws JsonSyntaxException, IOException {
		return;		
	}

	@Override
	public void save(Supplier entity) throws JsonSyntaxException, IOException,FileNotFoundException {
		ArrayList<Supplier> suppliers = getAll();
		suppliers.add(entity);
		saveAll(suppliers);	
		
	}

	@Override
	public void saveAll(ArrayList<Supplier> entities) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Supplier>>(){}.getType());
		writer.println(allEntities);
		writer.close();		
	}

	@Override
	public ArrayList<Supplier> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
