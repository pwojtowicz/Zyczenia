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

		objects.add(new ContentObject(1, "Tre��1", 4.21, false));
		objects.add(new ContentObject(2, "Tre��2", 4.22, true));
		objects.add(new ContentObject(3, "Tre��3", 4.23, false));
		objects.add(new ContentObject(4, "Tre��4", 4.24, true));
		objects.add(new ContentObject(5, "Tre��5", 4.25, false));
		objects.add(new ContentObject(6, "Tre��6", 4.26, true));
		objects.add(new ContentObject(7, "Tre��7", 4.27, false));
		objects.add(new ContentObject(8, "Tre��8", 4.28, true));
		return objects;
	}

	@Override
	public boolean insertOrUpdate(ContentObject item) {
		// TODO Auto-generated method stub
		return false;
	}

}
