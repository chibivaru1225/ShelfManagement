package ZOA.Android.ShelfManagement.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ZOA.Android.ShelfManagement.Activity.ActivityModeSelect;
import ZOA.Android.ShelfManagement.Activity.ActivityNextOperation;
import ZOA.Android.ShelfManagement.Interface.InterfaceShelfList;
import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;

import static androidx.core.content.ContextCompat.startActivity;

public class AdapterShelfList extends RecyclerView.Adapter<AdapterShelfList.ShelfViewHolder> {

    private ArrayList<ShelfStatus> statuslist;
    private InterfaceShelfList listener;

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

    public AdapterShelfList(ArrayList<ShelfStatus> list, InterfaceShelfList parent) {
        this.statuslist = list;
        this.listener = parent;
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
        holder.txtItemName.setText(statuslist.get(position).GetShohinCode());
        holder.txtItemStatus.setText(ShelfStatus.GetItemStatusText(statuslist.get(position).GetItemStatus()));
        holder.txtSelectStatus.setText(ShelfStatus.GetSelectStatusText(statuslist.get(position).GetSelectStatus()));

        holder.cbSend.setOnCheckedChangeListener((buttonView, isChecked) -> statuslist.get(position).SetIsSend(isChecked));

        holder.txtItemName.setOnClickListener((buttonView) -> OpenSelectStatus(statuslist.get(position)));
        holder.txtItemStatus.setOnClickListener((buttonView) -> OpenSelectStatus(statuslist.get(position)));
        holder.txtSelectStatus.setOnClickListener((buttonView) -> OpenSelectStatus(statuslist.get(position)));

        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.WHITE);
        else
            holder.itemView.setBackgroundColor(Color.LTGRAY);
    }


    public void OpenSelectStatus(ShelfStatus status) {
        if (this.listener != null)
            this.listener.OpenNewActivity(status);
    }

    @Override
    public int getItemCount() {
        return statuslist.size();
    }
}
