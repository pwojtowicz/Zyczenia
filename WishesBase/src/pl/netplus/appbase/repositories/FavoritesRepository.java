package pl.netplus.appbase.repositories;

import java.util.ArrayList;

import pl.netplus.appbase.entities.Favorite;
import pl.netplus.appbase.httpconnection.IHttpRequestToAsyncTaskCommunication;
import pl.netplus.appbase.interfaces.IBaseRepository;

public class FavoritesRepository implements IBaseRepository<Favorite> {

	@Override
	public Favorite read(int id) {
		return null;
	}

	@Override
	public ArrayList<Favorite> readAll() {
		return null;
	}

	@Override
	public boolean insertOrUpdate(Favorite item) {
		return false;
	}

	@Override
	public ArrayList<Favorite> getFromServer(
			IHttpRequestToAsyncTaskCommunication listener) {
		// TODO Auto-generated method stub
		return null;
	}

}
