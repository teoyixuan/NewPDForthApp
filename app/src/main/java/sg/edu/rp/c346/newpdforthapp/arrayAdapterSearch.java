package sg.edu.rp.c346.newpdforthapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class arrayAdapterSearch extends ArrayAdapter<Data> implements Filterable {

    private ArrayList<Data> datas;
    private  ArrayList<Data> newData;
    private Context context;
    private TextView tvRowSearchDate, tvRowSearchRemark, tvRowSearchAmount;
    private ImageView ivRowSearch;

    public arrayAdapterSearch(Context context, int resource, ArrayList<Data> objects) {
        super(context, resource, objects);
        datas = objects;
        this.context = context;
        newData = new ArrayList<>(datas);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_search, parent, false);

        tvRowSearchAmount = (TextView) rowView.findViewById(R.id.tvRowSearchAmount);
        tvRowSearchDate = (TextView) rowView.findViewById(R.id.tvRowSearchDate);
        tvRowSearchRemark = (TextView) rowView.findViewById(R.id.tvRowSearchRemark);
        ivRowSearch = (ImageView) rowView.findViewById(R.id.ivRowSearch);


        Data current = datas.get(position);
        tvRowSearchAmount.setText("$" + current.getAmount());
        tvRowSearchRemark.setText(current.getRemark());
        tvRowSearchDate.setText(current.getDate());

        int iconId = current.getIconId();
        if (iconId == R.drawable.minus22) {
            ivRowSearch.setImageResource(R.drawable.minus22);
        } else if (iconId == R.drawable.plus) {
            ivRowSearch.setImageResource(R.drawable.plus);
        }

        return rowView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Data> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(newData);
            } else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(int i = 0; i <datas.size(); i ++){
                    if (datas.get(i).getRemark().toLowerCase().startsWith(filterPattern)){
                        filteredList.add(datas.get(i));
                    }
                }

//                for(Data item : datas){
//                    if (item.getRemark().toLowerCase().contains(filterPattern)){
//                        filteredList.add(item);
//                    }
//                }
            }

            Log.d("hi", "performFiltering: " + filteredList.size());
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            datas.clear();
            datas.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
            Log.d("hi", "publishResults");
        }
    };
}