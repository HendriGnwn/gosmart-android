package com.atc.gosmartlesmagistra.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.model.Bank;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hendrigunawan on 7/31/17.
 */

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Bank> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name, branch;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            branch = (TextView) view.findViewById(R.id.branch);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
//                    intent = new Intent(mContext, MenuDetailListActivity.class);
//                    MenuCategory menuCategory = menuList.get(getAdapterPosition());
//                    intent.putExtra("menuCategory", menuCategory);
//                    mContext.startActivity(intent);
                }
            });
        }
    }

    public BankListAdapter(Context mContext, List<Bank> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Bank bank = list.get(position);

        holder.branch.setText(bank.getBranch());
        holder.name.setText(bank.getName());
        Picasso.with(mContext).load(App.URL + "files/banks/" + bank.getImage()).error(R.drawable.zzz_alert).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Bank> lists) {
        list.addAll(lists);
        notifyDataSetChanged();
    }

    public void add(Bank bank) {
        list.add(bank);
        notifyDataSetChanged();
    }
}
