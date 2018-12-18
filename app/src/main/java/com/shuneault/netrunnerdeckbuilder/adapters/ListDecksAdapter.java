package com.shuneault.netrunnerdeckbuilder.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuneault.netrunnerdeckbuilder.R;
import com.shuneault.netrunnerdeckbuilder.game.Deck;
import com.shuneault.netrunnerdeckbuilder.helper.ImageDisplayer;

import java.util.ArrayList;

/**
 * Created by sebast on 2014-11-22.
 */
public class ListDecksAdapter extends RecyclerView.Adapter<ListDecksAdapter.DeckViewHolder> {

    private ArrayList<Deck> mDeckList;
    private DeckViewHolder.IViewHolderClicks mListener;

    public ListDecksAdapter(ArrayList<Deck> deckList, DeckViewHolder.IViewHolderClicks listener) {
        super();
        mDeckList = deckList;
        mListener = listener;
    }

    public void setData(ArrayList<Deck> newDeckList) {
        mDeckList.clear();
        mDeckList.addAll(newDeckList);
        notifyDataSetChanged();
    }

    @Override
    public DeckViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_deck, viewGroup, false);
        return new DeckViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final DeckViewHolder viewHolder, int i) {
        viewHolder.setItem(mDeckList.get(i));
    }

    @Override
    public int getItemCount() {
        return mDeckList.size();
    }


    public static class DeckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtDeckTitle;
        public TextView txtDeckNotes;
        public ImageView imgDeckIdentity;
        private CheckBox chkStarred;
        private IViewHolderClicks mListener;
        private Deck deck;

        DeckViewHolder(View v, IViewHolderClicks listener) {
            super(v);
            mListener = listener;
            // set click listener to ripple view
            v.findViewById(R.id.ripple).setOnClickListener(this);
            txtDeckTitle = v.findViewById(R.id.txtDeckTitle);
            txtDeckNotes = v.findViewById(R.id.txtDeckNotes);
            imgDeckIdentity = v.findViewById(R.id.imgDeckIdentity);
            // Favourite / Star image
            chkStarred = v.findViewById(R.id.chkStar);
            chkStarred.setOnClickListener(v1 -> mListener.onDeckStarred(deck, chkStarred.isChecked()));
        }

        @Override
        public void onClick(View v) {
            mListener.onDeckClick(this.deck);
        }

        public void setItem(Deck deck) {
            this.deck = deck;
            // Set the values
            Context context = txtDeckNotes.getContext();

            txtDeckTitle.setText(deck.getName());
            String deckNotes = deck.getNotes();
            if(deck.hasUnknownCards()){
                deckNotes = context.getString(R.string.has_unknown_cards);
            }
            txtDeckNotes.setText(deckNotes);
            ImageDisplayer.fill(imgDeckIdentity, deck.getIdentity(), context);
            chkStarred.setChecked(deck.isStarred());
        }

        public interface IViewHolderClicks {
            void onDeckClick(Deck deck);

            void onDeckStarred(Deck deck, boolean isStarred);
        }
    }

}