package org.hitlabnz.helloworld;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.app.Card;
import com.google.android.glass.app.Card.ImageLayout;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class InfoActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CardScrollView cardScrollView = new CardScrollView(this);
        cardScrollView.setAdapter(new InfoCardScrollAdapter(this));
        cardScrollView.activate();
        setContentView(cardScrollView);
	}
	
	
	private class InfoCardScrollAdapter extends CardScrollAdapter {
		
		private List<Card> infoCards;
		
		public InfoCardScrollAdapter(Context context) {
			infoCards = new ArrayList<Card>();
			
			Card card = new Card(context);
			card.setText("The Hello World glassware is made for the Glass Class at the HIT Lab NZ.");
			card.setFootnote("The Glass Class @ HIT Lab NZ (c) 2014");
			infoCards.add(card);
			
			card = new Card(context);
			card.setText("The Human Interface Technology Laboratory\nNew Zealand\n(HIT Lab NZ)");
			card.setFootnote("http://www.hitlabnz.org");
			card.setImageLayout(ImageLayout.LEFT);
			card.addImage(R.drawable.hitlabnz);
			infoCards.add(card);
			
			card = new Card(context);
			card.setText("Hello Glass Class!");
			card.setFootnote("http://www.hitlabnz.org");
			card.setImageLayout(ImageLayout.FULL);
			card.addImage(R.drawable.hitlabnz);
			infoCards.add(card);
		}
		
		@Override
		public int getPosition(Object item) {
			return infoCards.indexOf(item); // return the position of the given item (card)
		}

		@Override
		public int getCount() {
			return infoCards.size(); // return number of items (cards)
		}

		@Override
		public Object getItem(int position) {
			return infoCards.get(position); // return the item (card) at given position
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parentView) {
			return infoCards.get(position).getView(); // return the view at the given position
		}

		
		
	}

}
