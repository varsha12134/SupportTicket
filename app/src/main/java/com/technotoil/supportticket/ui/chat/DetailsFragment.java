package com.technotoil.supportticket.ui.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.technotoil.supportticket.R;
import com.technotoil.supportticket.model.UserModel;
import com.technotoil.supportticket.viewModel.AttachmentViewModel;
import com.technotoil.supportticket.viewModel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {

    int id;
    String subject, region, description, createdOn, tID,file_type;
    TextView tv_Id, tv_subject, tv_region, tv_description, tv_date, tv_username;
    UserViewModel userViewModel;
    AttachmentViewModel attachmentViewModel;
    String userName, imageId;
    ImageView detailimage,playButton;
    VideoView videoView;

    Uri imguri;
    File fileUri;
    StorageReference storageReference, imgReference;
    List<UserModel> userModelList = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsFragment() {
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
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
         storageReference = FirebaseStorage.getInstance().getReference();
        /*userViewModel.fetchDataFromRoom().observe(getActivity(), new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                userModelList=userModels;
            }
        });*/
//        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
//        attachmentViewModel = new ViewModelProvider(getActivity()).get(AttachmentViewModel.class);

        tv_Id = view.findViewById(R.id.tv_ticketID);
        tv_subject = view.findViewById(R.id.tv_subject);
        tv_region = view.findViewById(R.id.tv_region);
        tv_description = view.findViewById(R.id.tv_description);
        tv_date = view.findViewById(R.id.tv_date);
        detailimage = view.findViewById(R.id.detailimage);
        tv_username = view.findViewById(R.id.tv_username);
        playButton = view.findViewById(R.id.playButton);
        videoView = view.findViewById(R.id.videoView);

        Bundle bundle = getArguments();
        subject = bundle.getString("Subject");
        region = bundle.getString("Region");
        description = bundle.getString("Description");
        createdOn = bundle.getString("Date");
        tID = bundle.getString("TicketId");
        imageId = bundle.getString("ImgId");

        fetchImgFromStorage();


//        Uri fileUri = Uri.fromFile(new java.io.File(Uri.encode("/Internal storage/Android/media/com.whatsaap/WhatsApp/Media/WhatsApp/Document/Sent/Varsha(1).pdf")));
//        String path = fileUri.toString();

/*
        attachmentViewModel.getAttachmentData(tID).observe(getActivity(), new Observer<AttachmentModel>() {
            @Override
            public void onChanged(AttachmentModel attachmentModels) {
                String img = attachmentModels.getFilePath();
                if(img.contains("jpg")){
                    uri = Uri.parse(img);
                     detailimage.setImageURI(uri);
                } else if (img.contains("Bitmap")) {
                    Bitmap b=StringToBitMap(img);
                    detailimage.setImageBitmap(b);

                } else{
                    Picasso.get()
                            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaV9UPGC_9dgU-kXM3VuJUkeWCrkjluNm0cw&usqp=CAU")
                            .into(detailimage );

                    detailimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String sourceFilePath = "/storage/emulated/0/Download/converted.pdf";
                            String destinationFilePath = "/storage/emulated/0/NewFolder/converted.pdf";

                            File sourceFile = new File(sourceFilePath);
                            File destinationFile = new File(destinationFilePath);
                            try {
                                InputStream is = new FileInputStream(sourceFile);
                                OutputStream os = new FileOutputStream(destinationFile);
                                byte[] buffer = new byte[1024];
                                int length;
                                while ((length = is.read(buffer)) > 0) {
                                    os.write(buffer, 0, length);
                                }
                                os.close();
                                is.close();
                                Log.e("downloadFile","File copied successfully");
                                System.out.println("File copied successfully");
                            } catch (IOException e) {
                                Log.e("downloadFile","File Error successfully");

                                System.out.println("Error copying file: " + e.getMessage());
                            }
                        }



                    });
                }

            }
        });
*/

        /*userViewModel.fetchDataFromRoom().observe(getActivity(), new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                List<UserModel> usermodelList = userModels;
                // adapter.setList(modelList);
                handleUserModelList(usermodelList);

            }

            private void handleUserModelList(List<UserModel> usermodelList) {
                for (UserModel userModel : usermodelList) {
                    Log.d("varsha", String.valueOf(userModel.getId()));
                    userName = userModel.getUsername();
                    tv_username.setText(userName);
                }
            }
        });*/


//        String Id = String.valueOf("#" + id);
        tv_Id.setText(tID);
        tv_subject.setText(subject);
        tv_region.setText(region);
        tv_description.setText(description);
        tv_date.setText(createdOn);
        return view;
    }

    private void fetchImgFromStorage() {
        imgReference = storageReference.child( tID+"/"+imageId);
        imgReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                file_type= storageMetadata.getContentType();
                uriShowOnUI(file_type);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("tag", "Exception occur while gettig metadata: "+exception.toString());
            }
        });
    }

    private void uriShowOnUI(String fileType) {
        if(fileType.contains("image")) {
            imgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoView.setVisibility(View.GONE);
                    detailimage.setVisibility(View.VISIBLE);
                    Picasso.get().load(uri).into(detailimage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("fetchImage", "fetchImage" + e);
                }
            });
        }
        else if(fileType.contains("video")){
            imgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                   videoView.setVisibility(View.VISIBLE);
                    videoView.setVideoURI(uri);
                    playButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            videoView.start();
                            detailimage.setVisibility(View.GONE);
                            playButton.setVisibility(View.GONE);
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void copyFile(File file, File destinationFile)throws IOException {

            FileInputStream inputStream = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        }

    private Bitmap StringToBitMap(String img) {
        try{
            byte [] encodeByte= Base64.decode(img,Base64.DEFAULT);

            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }


}
