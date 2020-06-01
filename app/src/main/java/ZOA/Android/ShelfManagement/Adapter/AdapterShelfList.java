package ZOA.Android.ShelfManagement.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;

public class AdapterShelfList extends RecyclerView.Adapter<AdapterShelfList.ShelfViewHolder> {

    private ArrayList<ShelfStatus> statuslist;

    static class ShelfViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        CheckBox cbSend;
        TextView txtItemName;
        TextView txtItemStatus;
        TextView txtSelectStatus;

        ShelfViewHolder(View v) {
            super(v);
            cbSend = v.findViewById(R.id.asCbxA);
            txtItemName = v.findViewById(R.id.asTxtA);
            txtItemStatus = v.findViewById(R.id.asTxtB);
            txtSelectStatus = v.findViewById(R.id.asTxtC);
            //cbSend.setOnCheckedChangeListener(ShelfStatus);
            //cbSend.setOnCheckedChangeListener(this);
        }
    }

    public AdapterShelfList(ArrayList<ShelfStatus> list) {
        this.statuslist = list;
    }

    @NonNull
    @Override
    public ShelfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shelf_list, parent, false);

        // set the view's size, margins, paddings and layout parameters

        return new ShelfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShelfViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.cbSend.setChecked(statuslist.get(position).GetIsSend());
        holder.txtItemName.setText(statuslist.get(position).GetItemName());
        holder.txtItemStatus.setText(ShelfStatus.GetItemStatusText(statuslist.get(position).GetItemStatus()));
        holder.txtSelectStatus.setText(ShelfStatus.GetSelectStatusText(statuslist.get(position).GetSelectStatus()));

        holder.cbSend.setOnCheckedChangeListener((buttonView, isChecked) -> statuslist.get(position).SetIsSend(isChecked));
    }

    @Override
    public int getItemCount() {
        return statuslist.size();
    }

    public void SetShelfListIsSendAll(boolean send)
    {
        //this.
    }
}
