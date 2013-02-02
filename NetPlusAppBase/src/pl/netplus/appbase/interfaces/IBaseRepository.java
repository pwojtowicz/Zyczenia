package pl.netplus.appbase.interfaces;

import java.util.ArrayList;

import pl.netplus.appbase.database.DataBaseManager;
import pl.netplus.appbase.httpconnection.IHttpRequestToAsyncTaskCommunication;

public interface IBaseRepository<T> {

	public abstract boolean insertOrUpdate(T item, DataBaseManager dbManager);

	public abstract T read(int id);

	public abstract T read(int id, DataBaseManager dbManager);

	public abstract ArrayList<T> readById(int value);

	public abstract ArrayList<T> readAll();

	public abstract boolean delete(T item);

	public boolean getFromServer(IHttpRequestToAsyncTaskCommunication listener,
			DataBaseManager dbManager);

}
