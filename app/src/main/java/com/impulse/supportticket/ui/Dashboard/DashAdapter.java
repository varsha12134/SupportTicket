package com.impulse.supportticket.ui.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.impulse.supportticket.R;
import com.impulse.supportticket.model.TicketModel;
import com.impulse.supportticket.ui.chat.ChatScreen;

import java.util.ArrayList;
import java.util.List;

public class DashAdapter extends RecyclerView.Adapter<DashAdapter.DashboardViewHolder> {
    Context ctx;
    List<Data> data;
    ArrayList<TicketModel> ticketModelList;
    String Name, imgId, ticketKey;
    GradientDrawable draw ;
    public DashAdapter(Context context, ArrayList<TicketModel> ticketModelList) {
        ctx = context;
        this.ticketModelList = ticketModelList;
    }

    @NonNull
    @Override
    public DashAdapter.DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dashboard, parent, false);
        return new DashAdapter.DashboardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DashAdapter.DashboardViewHolder holder, final int position) {

         Name = ticketModelList.get(position).getSubject().substring(0,1);
        holder.name.setText(Name);
        holder.subject.setText(ticketModelList.get(position).getSubject());
        imgId = ticketModelList.get(position).getImgId();
        holder.description.setText(ticketModelList.get(position).getDescription());
        String date = ticketModelList.get(position).getCreatedOn().substring(0,11);
        String time = ticketModelList.get(position).getCreatedOn().substring(11,19).toString().toUpperCase();
        holder.date.setText(date);
        holder.time.setText(time);
        String Status = ticketModelList.get(position).getStatus();
        String tc = ticketModelList.get(position).getTicketId();
        if(Status.equals("0")) {
            holder.status.setText("Closed");
            holder.status.setTextColor(Color.GRAY);
        }else if(Status.equals("1")){
            holder.status.setText("Open");
            holder.status.setTextColor(Color.RED);
        }else if(Status.equals("2")){
            holder.status.setText("Awaiting Response");
            holder.status.setTextColor(ContextCompat.getColor(ctx, R.color.green));
        }else{}


        draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        if (Name.equals("A")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.A));
        } else if (Name.equals("B")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.B));
        }else if (Name.equals("C")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.C));
        }else if (Name.equals("D")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.D));
        }else if (Name.equals("E")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.E));
        }else if (Name.equals("F")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.F));
        }else if (Name.equals("G")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.G));
        }else if (Name.equals("H")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.H));
        }else if (Name.equals("I")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.I));
        }else if (Name.equals("J")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.J));
        }else if (Name.equals("K")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.K));
        }else if (Name.equals("L")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.L));
        }else if (Name.equals("M")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.M));
        }else if (Name.equals("N")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.N));
        }else if (Name.equals("O")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.O));
        }else if (Name.equals("P")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.P));
        }else if (Name.equals("Q")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.Q));
        }else if (Name.equals("R")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.R));
        }else if (Name.equals("S")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.S));
        }else if (Name.equals("T")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.T));
        }else if (Name.equals("U")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.U));
        }else if (Name.equals("V")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.V));
        }else if (Name.equals("W")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.W));
        }else if (Name.equals("X")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.X));
        }else if (Name.equals("Y")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.Y));
        }else if (Name.equals("Z")) {
            draw.setColor(ContextCompat.getColor(ctx, R.color.Z));
        }
        holder.name.setBackground(draw);
   /*     holder.status.setText(ticketModelList.get(position).getStatus());
        if (data.get(position).getStatus()=="Awaiting Response"){
            holder.status.setTextColor(ContextCompat.getColor(ctx, R.color.green));
        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, ChatScreen.class);
                i.putExtra("SUBJECT",ticketModelList.get(position).getSubject());
                i.putExtra("DESC",ticketModelList.get(position).getDescription());
                i.putExtra("REGION",ticketModelList.get(position).getRegion());
                i.putExtra("DATE",date);
                i.putExtra("USERID",ticketModelList.get(position).getUserId());
                i.putExtra("IMGID",ticketModelList.get(position).getImgId());
                i.putExtra("STATUS",ticketModelList.get(position).getStatus());
                i.putExtra("TICKET_ID",ticketModelList.get(position).getTicketId());
                i.putExtra("Name",ticketModelList.get(position).getSubject().substring(0,1));
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketModelList.size();
    }

    public  void setList(ArrayList<TicketModel> ticketModelList) {
        this.ticketModelList = ticketModelList;
        notifyDataSetChanged();
    }
    public static class DashboardViewHolder extends RecyclerView.ViewHolder {

        TextView name,subject,description,date,time,status, tvWeekDayFirstLetter;
        RelativeLayout image;
        View generatedColorView;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvWeekDayFirstLetter);
            subject = itemView.findViewById(R.id.subject);
            image = itemView.findViewById(R.id.image);
            description = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
//            webView.setBackgroundColor(android.R.color.holo_green_light);
        }
    }
}