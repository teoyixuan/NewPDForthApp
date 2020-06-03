package sg.edu.rp.c346.newpdforthapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class arrayAdapter extends ArrayAdapter<Data> {

    private ArrayList<Data> datas;
    private Context context;
    private TextView tvRowDate, tvRowRemark, tvRowAmount;
    private ImageView ivRow;

    public arrayAdapter(Context context, int resource, ArrayList<Data> objects) {
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        datas = objects;
        // Store Context object as we would need to use it later
        this.context = context;
    }

    // getView() is the method ListView will call to get the
    //  View object every time ListView needs a row
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row_main_page, parent, false);

        // Get the TextView object
        tvRowAmount = (TextView) rowView.findViewById(R.id.tvRowAmount);
        tvRowDate = (TextView) rowView.findViewById(R.id.tvRowDate);
        tvRowRemark = (TextView) rowView.findViewById(R.id.tvRowRemark);
        ivRow = (ImageView) rowView.findViewById(R.id.ivRow);


        // The parameter "position" is the index of the
        //  row ListView is requesting.
        //  We get back the food at the same index.
        Data current = datas.get(position);
        // Set the TextView to show the food


        tvRowAmount.setText("$" + current.getAmount());
        tvRowRemark.setText(current.getRemark());
        tvRowDate.setText(current.getDate());

        int iconId = current.getIconId();
        if (iconId == R.drawable.minus22) {
            ivRow.setImageResource(R.drawable.minus22);
        } else if (iconId == R.drawable.plus) {
            ivRow.setImageResource(R.drawable.plus);
        }

        // Return the nicely done up View to the ListView
        return rowView;
    }
}