package pl.netplus.appbase.interfaces;

import java.util.ArrayList;

import pl.netplus.appbase.httpconnection.IHttpRequestToAsyncTaskCommunication;

public interface IBaseRepository<T> {

	public abstract boolean insertOrUpdate(T item);

	public abstract T read(int id);

	public abstract ArrayList<T> readAll();

	public ArrayList<T> getFromServer(
			IHttpRequestToAsyncTaskCommunication listener);

}
