package pl.netplus.appbase.interfaces;

import java.util.ArrayList;

import pl.netplus.appbase.httpconnection.IHttpRequestToAsyncTaskCommunication;

public interface IBaseRepository<T> {

	public abstract boolean insertOrUpdate(T item);

	public abstract T read(int id);

	public abstract int readTotalCount();

	public abstract ArrayList<T> readById(int value);

	public abstract ArrayList<T> readAll();

	public abstract boolean delete(T item);

	public ArrayList<T> getFromServer(
			IHttpRequestToAsyncTaskCommunication listener);

}
