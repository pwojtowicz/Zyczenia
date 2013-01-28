package pl.netplus.wishesbase.adapters;

import java.util.ArrayList;

import pl.netplus.wishesbase.entities.ModelBase;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseListAdapter<T> extends BaseAdapter{

	protected ArrayList<T> items;
	protected Context context;
	protected LayoutInflater inflater;
	private int viewLayout;
	
	public BaseListAdapter(Context context, ArrayList<T> items, int viewLayout) {
		this.context = context;
		this.items = items;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		this.viewLayout=viewLayout;
	}
	
	public void reloadItems(ArrayList<T> list) {
		this.items = list;
		this.notifyDataSetChanged();
	}

	public void addItem(T item) {
		items.add(item);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int index) {
		return items.get(index);
	}

	@Override
	public long getItemId(int index) {
		return ((ModelBase) items.get(index)).getId();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null)
			convertView = inflater.inflate(viewLayout, null);
		
		return fillRow(getItem(position),convertView);		
	}

	protected abstract View fillRow(Object item, View convertView);
}
