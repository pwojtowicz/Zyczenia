package pl.netplus.appbase.repositories;

import java.util.ArrayList;

import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.interfaces.IBaseRepository;

public class ContentObjectRepository implements IBaseRepository<ContentObject> {

	@Override
	public ContentObject read(int id) {
		return null;
	}

	@Override
	public ArrayList<ContentObject> readAll() {
		ArrayList<ContentObject> objects = new ArrayList<ContentObject>();

		objects.add(new ContentObject(1, "Treœæ1", 4.21, false));
		objects.add(new ContentObject(2, "Treœæ2", 4.22, true));
		objects.add(new ContentObject(3, "Treœæ3", 4.23, false));
		objects.add(new ContentObject(4, "Treœæ4", 4.24, true));
		objects.add(new ContentObject(5, "Treœæ5", 4.25, false));
		objects.add(new ContentObject(6, "Treœæ6", 4.26, true));
		objects.add(new ContentObject(7, "Treœæ7", 4.27, false));
		objects.add(new ContentObject(8, "Treœæ8", 4.28, true));
		return objects;
	}

	@Override
	public boolean insertOrUpdate(ContentObject item) {
		// TODO Auto-generated method stub
		return false;
	}

}
