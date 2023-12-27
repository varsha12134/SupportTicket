package com.technotoil.supportticket.ui.chat;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.technotoil.supportticket.R;
import com.technotoil.supportticket.db.RealTimeDB;
import com.technotoil.supportticket.model.MessageModel;
import com.technotoil.supportticket.model.TicketModel;
import com.technotoil.supportticket.viewModel.TicketViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private static final int PICK_VIDEO_REQUEST_CODE = 2;
    private static final int PICK_CAMERA_REQUEST_CODE = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    FloatingActionButton create;
    private String mParam2;
    RecyclerView rv_chat;
    LinearLayout chatlayout;
    ImageButton btn_send;
    ImageView attachbtn;
    Intent galleryI, cameraI, pdfI, videoI;

    EditText et_msg;
    TextView chat_date;
    ImageView view, iv_camera, iv_gallery, iv_document,iv_video;

    String msg, currentDateAndTime, file_type, file_name, filepath, ticketId, date;
    Context context;
    RelativeLayout close_chat_message;
    ChattingAdapter adapter;
    int id, status;
    List<MessageModel> list = new ArrayList<>();
    ArrayList<TicketModel> ticketList = new ArrayList<>();
    DatabaseReference reference;
    ArrayList<MessageModel> msgArrList ;
    TicketViewModel ticketViewModel;
    MessageModel messageModel;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mauth;
    Uri mainImgUri, fileUri, mainVideoUri;
    StorageReference storageRef,imgReference,videoReference;
    String senderId, nodeKey, receiver_id, uId, imgId, chatImgId = "null";
    BottomSheetDialog bottomSheetDialog;
    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);

         storageRef = FirebaseStorage.getInstance().getReference();

        firebaseDatabase = RealTimeDB.getInstance();
        reference = RealTimeDB.getInstance().getReference();
        msgArrList = new ArrayList<>();
        mauth=FirebaseAuth.getInstance();
        senderId = mauth.getCurrentUser().getUid();


        rv_chat = view.findViewById(R.id.chatleft);
        btn_send = view.findViewById(R.id.bt_send);
        et_msg = view.findViewById(R.id.et_msg);
        attachbtn = view.findViewById(R.id.attachbtn);
        msg = et_msg.getText().toString();
        chatlayout = view.findViewById(R.id.chatlayout);
        close_chat_message = view.findViewById(R.id.close_chat_message);
        close_chat_message.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        ticketId = bundle.getString("TicketId");
        status = bundle.getInt("Status");
        date = bundle.getString("Date");
        uId = bundle.getString("UserId");
        imgId = bundle.getString("ImgId");

        if(senderId.equals(uId)){
        }else{
            receiver_id = uId;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd LLL yyyyhh:mm aaa", Locale.getDefault());
        currentDateAndTime = sdf.format(new Date());
        fetchMessageData();
        fetchImageUri();



       /* messageViewModel.fetchMessageDataFromRoom(id).observe(getActivity(), new Observer<List<MessageModel>>() {
            @Override
            public void onChanged(List<MessageModel> messageModelList) {
                list = messageModelList;
                handleUserModelList(list);
                adapter.setList(list);
                int size = list.size();
                if (status == 1){
                    if (size != 0) {
                        ticketViewModel.updateStatus(id, 2);
                    } else {
                    }
            }
            }
        });*/

            /*ticketViewModel.fetchClosedDataById(id).observe(getActivity(), new Observer<TicketModel>() {
                @Override
                public void onChanged(TicketModel ticketModel) {
                    status = ticketModel.getStatus();
                    if(status == 0){
                        //set User Status close Visibility
                        chatlayout.setVisibility(View.GONE);
                        close_chat_message.setVisibility(View.VISIBLE);
                    }  else{
                        chatlayout.setVisibility(View.VISIBLE);
                    }
                }
            });*/



        // Create the adapter and pass the chat messages



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = et_msg.getText().toString();
                if(msg.isEmpty()){
                    et_msg.requestFocus();
                    if (String.valueOf(mainVideoUri).contains("video")) {
                        uploadVideoInStorage(mainVideoUri);
                        et_msg.setText(" ");
                    }else if(String.valueOf(mainImgUri).contains("images")){
                        uploadImageInStorage(mainImgUri);
                        et_msg.setText(" ");
                    }else
                    {
                    }
                }
                    MessageModel model = new MessageModel(ticketId, chatImgId, senderId, receiver_id, msg, currentDateAndTime);
                    reference.child(ticketId).push().setValue(model);
                    et_msg.setText("");
            }
        });

         // Replace `MyAdapter` with your adapter class
        // Step 4: Set adapter on RecyclerView
        rv_chat.setLayoutManager(new LinearLayoutManager(requireContext())); //

        return view;
    }



    private void fetchImageUri() {
        attachbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatImgId =  UUID.randomUUID().toString();
                        bottomSheetDialog = new BottomSheetDialog(getActivity());
                        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
                        iv_camera = bottomSheetDialog.findViewById(R.id.iv_camera);
                        iv_gallery = bottomSheetDialog.findViewById(R.id.iv_gallery);
                        iv_video = bottomSheetDialog.findViewById(R.id.iv_video);
                        bottomSheetDialog.show();
                        iv_camera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cameraI = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraI, PICK_CAMERA_REQUEST_CODE);
                                bottomSheetDialog.cancel();

                            }
                        });
                        iv_gallery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                galleryI = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryI, PICK_IMAGE_REQUEST_CODE );
                                bottomSheetDialog.cancel();

                            }
                        });
                          iv_video.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                videoI = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                videoI.setType("video/*");
                                startActivityForResult(videoI, PICK_VIDEO_REQUEST_CODE);
                                bottomSheetDialog.cancel();

                            }
                        });
                    }
                });
            }


    private void fetchMessageData() {

        reference.child(ticketId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                msgArrList.add(snapshot.getValue(MessageModel.class));
                ChattingAdapter adapter1 = new ChattingAdapter(getContext(), msgArrList,senderId);
                rv_chat.setAdapter(adapter1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                msgArrList.remove(snapshot.getValue(String.class));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void uploadImageInStorage(Uri mainImgUri) {
        imgReference = storageRef.child( ticketId+"/Images/"+ chatImgId);
        imgReference.putFile(mainImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
    }

    private void uploadVideoInStorage(Uri mainVideoUri) {
            videoReference = storageRef.child( ticketId+"/Video/"+ chatImgId);
            videoReference.putFile(mainVideoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("FailVideo","FailVideo"+e);
                }
            });


    }
    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        }

        @Override
        public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
                if (data != null) {
                    mainImgUri = data.getData();
                    msg = ".jpg";

                }
            } else if (requestCode == PICK_VIDEO_REQUEST_CODE && resultCode == RESULT_OK) {
                if (data != null) {
                    mainVideoUri = data.getData();
                    msg = ".video";
                }
            }
        }

        private void sendMessage(){
            /*{
                msg = et_msg.getText().toString().trim();
                if(TextUtils.isEmpty(msg))
                {
                    Toast.makeText(getActivity(),"Please enter message first..",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String messageSenderRef="Messages/"+senderId+"/"+"2wGYkNkVHYUA5fysdsldTgUAJpM2";
                    String messageReceiverRef="Messages/"+"2wGYkNkVHYUA5fysdsldTgUAJpM2"+"/"+senderId;

                    reference.child(senderId).push();
                    String messagePushID=reference.child().getKey();
                    Map messageTextBody=new HashMap();
                    messageTextBody.put("message",msg);
                    messageTextBody.put("from",senderId);
                    messageTextBody.put("to","2wGYkNkVHYUA5fysdsldTgUAJpM2");
                    messageTextBody.put("messageID",messagePushID);
                    messageTextBody.put("currentDateTime",currentDateAndTime);

                    Map messageBodyDetails =new HashMap();
                    messageBodyDetails.put(messageSenderRef+"/"+messagePushID,messageTextBody);
                    messageBodyDetails.put(messageReceiverRef+"/"+messagePushID,messageTextBody);

                    reference.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful())
                            {
                                // Toast.makeText(ChatActivity.this,"Message sent Successfully...",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Error:",Toast.LENGTH_SHORT).show();
                            }
                            et_msg.setText("");
                        }
                    });
                }
            }*/
        }

}