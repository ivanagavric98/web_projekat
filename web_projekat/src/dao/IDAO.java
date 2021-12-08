package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

public interface IDAO<T, ID> {
	ArrayList<T> getAll() throws JsonSyntaxException, IOException;
	ArrayList<T> getAllNonDeleted() throws JsonSyntaxException, IOException;
	T getByID(ID id) throws JsonSyntaxException, IOException;
	void create(T entity) throws JsonSyntaxException, IOException;
	void update(T entity) throws JsonSyntaxException, IOException;
	void delete(T entity) throws JsonSyntaxException, IOException;
	void save(T entity) throws JsonSyntaxException, IOException;
	void saveAll(ArrayList<T> entities) throws FileNotFoundException;

}
