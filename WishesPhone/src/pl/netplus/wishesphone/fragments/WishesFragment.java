package pl.netplus.wishesphone.fragments;

import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.fragments.BaseFragment;
import pl.netplus.wishesphone.R;
import android.view.View;

public class WishesFragment extends BaseFragment<ContentObject> {
	private ContentObject item;

	public WishesFragment() {
		super(R.layout.fragment_single_wish_layout, ERepositoryTypes.SingleWish);

		item = new ContentObject();
		item.setFavorites(false);
		item.setText("Wszystkiego naj w Nowym Roku! Spe³nienia marzeñ, równie¿ tych g³êboko ukrytych. Znalezienia skrawka nieba i bliskiego serca, które bêdzie to niebo z Tob¹ dzieliæ."
				+ "Niech ten rok nada ¿yciu nowych smaków, s³odyczy poca³unków, pikantnej zabawy. Korzystaj z ¿ycia ile tylko starczy tchu!!");

	}

	@Override
	public void linkViews(View convertView) {

	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub

	}
}
