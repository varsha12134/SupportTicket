package com.impulse.supportticket.ui.chat;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.impulse.supportticket.R;
import com.impulse.supportticket.model.MessageModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.DashboardViewHolder> {

    ArrayList<MessageModel> messageModelList;
     String currentUserId, senderId, receiverId, ticketId, chatImgId;
     StorageReference storageReference,imgReference, videoReference;
    private Context context;
    private static final String LAST_DATE_KEY = "lastDisplayedDate";


    public ChattingAdapter(Context context, ArrayList<MessageModel> messageModelList, String currentUserId) {
        this.context = context;
        this.messageModelList = messageModelList;
        this.currentUserId = currentUserId;

    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_right, parent, false);
        return new DashboardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder,  int position) {

        senderId = messageModelList.get(position).getSenderId();
        receiverId = messageModelList.get(position).getReceiverId();
        ticketId = messageModelList.get(position).getTicketId();
        chatImgId = messageModelList.get(position).getChatImgId();
        storageReference = FirebaseStorage.getInstance().getReference();
        imgReference = storageReference.child(ticketId + "/Images/" + chatImgId);
        videoReference = storageReference.child(ticketId + "/Video/" + chatImgId);
        if(senderId.equals(currentUserId)){
            holder.left_view.setVisibility(View.GONE);
            holder.right_img2.setVisibility(View.GONE);
            holder.right_view.setVisibility(View.VISIBLE);
            holder.tv_rightMsg.setText(messageModelList.get(position).getMessageText());
        }else{
            holder.right_view.setVisibility(View.GONE);
            holder.left_img2.setVisibility(View.GONE);
            holder.left_view.setVisibility(View.VISIBLE);
            holder.tv_left_msg.setText(messageModelList.get(position).getMessageText());
        }
        String time = messageModelList.get(position).getCreateOn().substring(11,19);
        holder.timetv.setText(time);

        //fetch chat image and video
        if(!messageModelList.get(position).getChatImgId().equals("null")){

            if(senderId.equals(currentUserId)) {
                rightImgUriShowOnUI( holder,imgReference);
                rightVideoUriShowOnUI( holder,videoReference);
                holder.left_view.setVisibility(View.GONE);
                holder.right_msg.setVisibility(View.GONE);
                holder.right_img2.setVisibility(View.VISIBLE);

            }else{
                leftImgUriShowOnUI( holder, imgReference);
                leftVideoUriShowOnUI( holder, videoReference);
                holder.right_view.setVisibility(View.GONE);
                holder.left_msg.setVisibility(View.GONE);
                holder.left_img2.setVisibility(View.VISIBLE);

            }
        }else{
            holder.right_img2.setVisibility(View.GONE);
            holder.rightChatVideoView.setVisibility(View.GONE);
            holder.left_img2.setVisibility(View.GONE);
            holder.leftChatVideoView.setVisibility(View.GONE);
        }
    }


    private void leftImgUriShowOnUI(DashboardViewHolder holder, StorageReference reference) {
        //left image show on ui
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    holder.left_images.setVisibility(View.VISIBLE);
                    holder.left_msg.setVisibility(View.GONE);
                    holder.leftChatVideoView.setVisibility(View.GONE);
                    Picasso.get().load(uri).into(holder.left_images);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("fetchImage", "fetchImage" + e);
                }
            });

        }
    private void leftVideoUriShowOnUI(DashboardViewHolder holder, StorageReference reference) {
        //left video show on ui
        videoReference = storageReference.child(ticketId + "/Video/" + chatImgId);
        videoReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                holder.left_images.setVisibility(View.GONE);
                holder.left_msg.setVisibility(View.GONE);
                holder.leftChatVideoView.setVisibility(View.VISIBLE);
                holder.leftChatVideoView.setVideoURI(uri);
                holder.leftChatPlayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.leftChatVideoView.start();
                        holder.leftChatPlayButton.setVisibility(View.GONE);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("fetchImage", "fetchImage" + e);
            }
        });
    }

    private void rightImgUriShowOnUI(DashboardViewHolder holder, StorageReference reference) {

        //right image show on ui
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    holder.image.setVisibility(View.VISIBLE);
                    holder.right_msg.setVisibility(View.GONE);
                    holder.rightChatVideoView.setVisibility(View.GONE);
                    Picasso.get().load(uri).into(holder.images);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("fetchImage", "fetchImage" + e);
                }
            });


            }
    private void rightVideoUriShowOnUI(DashboardViewHolder holder, StorageReference reference) {
        //right video show on ui
        videoReference = storageReference.child(ticketId + "/Video/" + chatImgId);
        videoReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                holder.images.setVisibility(View.GONE);
                holder.right_msg.setVisibility(View.GONE);
                holder.rightChatVideoView.setVisibility(View.VISIBLE);
                holder.rightChatVideoView.setVideoURI(uri);
                holder.rightChatPlayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.rightChatVideoView.start();
                        holder.rightChatPlayButton.setVisibility(View.GONE);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("fetchImage", "fetchImage" + e);
            }
        });
    }




    public void setTimeTextVisibility(String createOn, long previousTs, TextView date) {
        if(previousTs==0){
            date.setVisibility(View.VISIBLE);
            date.setText(createOn);
        }else {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTimeInMillis(Long.parseLong(createOn));
            cal2.setTimeInMillis(previousTs);

            boolean sameMonth = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);

            if(sameMonth){
                date.setVisibility(View.GONE);
                date.setText("");
            }else {
                date.setVisibility(View.VISIBLE);
                date.setText( String.valueOf(previousTs));
            }

        }
    }
    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public void setList(ArrayList<MessageModel> messageModelList) {
        this.messageModelList = messageModelList;
        notifyDataSetChanged();
    }

    public static class DashboardViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout image, image2,rl_date, right_view, left_view,left_img2,right_img2;
        TextView msg,timetv, tv_left_msg,tv_rightMsg;
        LinearLayout right_msg,left_msg;
        VideoView leftChatVideoView,rightChatVideoView;
        ImageView images, left_images ,rightChatPlayButton ,leftChatPlayButton;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);

            right_view = itemView.findViewById(R.id.right_view);
            right_msg = itemView.findViewById(R.id.right_msg);
            rightChatPlayButton = itemView.findViewById(R.id.rightChatPlayButton);
            rightChatVideoView = itemView.findViewById(R.id.rightChatVideoView);
            tv_rightMsg = itemView.findViewById(R.id.tv_rightMsg);
            left_view = itemView.findViewById(R.id.left_view);
            images = itemView.findViewById(R.id.images);
            image = itemView.findViewById(R.id.img);
            rl_date = itemView.findViewById(R.id.rl_date);
            right_img2 = itemView.findViewById(R.id.right_img2);
            left_img2 = itemView.findViewById(R.id.left_img2);
            left_images = itemView.findViewById(R.id.left_images);
            leftChatVideoView = itemView.findViewById(R.id.leftChatVideoView);
            leftChatPlayButton = itemView.findViewById(R.id.leftChatPlayButton);
            tv_left_msg = itemView.findViewById(R.id.tv_left_msg);
            left_msg = itemView.findViewById(R.id.left_msg);
            timetv = itemView.findViewById(R.id.timetv);
//            chatDate = itemView.findViewById(R.id.chat_date);

//            webView.setBackgroundColor(android.R.color.holo_green_light);
        }
    }
}