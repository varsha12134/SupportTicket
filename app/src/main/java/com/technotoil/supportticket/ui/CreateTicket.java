package com.technotoil.supportticket.ui;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.technotoil.supportticket.R;
import com.technotoil.supportticket.model.TicketModel;
import com.technotoil.supportticket.viewModel.AttachmentViewModel;
import com.technotoil.supportticket.viewModel.TicketViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CreateTicket extends AppCompatActivity {
    Spinner region;
    EditText subject, etdes;
    String sub,currentUser, selRegion, description, currentDateAndTime,status,imgId, file_type, file_name, filepath, file_size = "52.3kb", ticketId, mainUri;
    int userId, id ;
    Intent galleryI, cameraI, pdfI, videoI;
    RelativeLayout registerTicket;
    ImageView btnback;
    TicketViewModel ticketViewModel;
    AttachmentViewModel attachmentViewModel;
    ImageView view, iv_camera, iv_gallery, iv_document,iv_video;
    List<TicketModel> ticketmodelList;
    TicketModel ticketModel;
    UUID generatedTicketId, generatedImgId;
    FirebaseDatabase firebaseDatabase;
    Uri pdfUri, mainImgUri,fileUri;
    DatabaseReference reference;
    StorageReference storageReference;
    BottomSheetDialog bottomSheetDialog;
    private static final int PICK_IMG_REQUEST_CODE = 1;
    private static final int PICK_PDF_REQUEST_CODE = 3;
    private static final int PICK_CAMERA_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);

//        ticketViewModel = new ViewModelProvider(CreateTicket.this).get(TicketViewModel.class);
//        attachmentViewModel = new ViewModelProvider(CreateTicket.this).get(AttachmentViewModel.class);

        // Retrieve user login data from realtime database.
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        reference = FirebaseDatabase.getInstance().getReference("Ticket");
        storageReference = FirebaseStorage.getInstance().getReference( );

        registerTicket = findViewById(R.id.rl_registerTicket);
        subject = findViewById(R.id.et_subject);
        btnback = findViewById(R.id.btn_back);
        etdes = findViewById(R.id.et_des);
        region = (Spinner) findViewById(R.id.sp_region);
        ImageView iv_attach = (ImageView) findViewById(R.id.iv_attach);
        view = (ImageView) findViewById(R.id.view);
        userId = getIntent().getIntExtra("USERID", 0);
        status = String.valueOf(1);
        String[] arraySpinner = {
                "Select a region", "Riyadh", "Saudi"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        region.setAdapter(adapter);

        // generate Id for ticket and attachment
        generatedTicketId = UUID.randomUUID();
        ticketId = String.valueOf(generatedTicketId);

        generatedImgId = UUID.randomUUID();
        imgId = String.valueOf(generatedImgId);
        iv_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(CreateTicket.this);
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
                    }
                });
                iv_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        galleryI = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryI, PICK_IMG_REQUEST_CODE);
                    }
                });
                iv_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        videoI = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        videoI.setType("video/*");
                        startActivityForResult(videoI, PICK_PDF_REQUEST_CODE);
                    }
                });
            }
        });


        region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selRegion = arraySpinner[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd LLL yyyyhh:mm aaa z", Locale.getDefault());
        currentDateAndTime = sdf.format(new Date());


        registerTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTicket();
                uploadImgInStorage();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void uploadImgInStorage() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imgReference = storageRef.child( ticketId+"/"+ imgId);
        imgReference.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
    }


    public void createTicket() {
        sub = subject.getText().toString().trim();
        description = etdes.getText().toString();
        if (sub.isEmpty()) {
            subject.requestFocus();
            subject.setError("Filed can't be empty");
        } else if (description.isEmpty()) {
            etdes.requestFocus();
            etdes.setError("Filed can't be empty");
        }else{
            ticketModel = new TicketModel(ticketId,imgId,currentUser, sub,selRegion,description,status,currentDateAndTime);
            reference.push().setValue(ticketModel);
            Intent intent = new Intent(CreateTicket.this, MainActivity.class);
            startActivity(intent);

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMG_REQUEST_CODE) {
                fileUri = data.getData();
                if (data != null) {

                    filepath = fileUri.getPath();
                    String[] urlArray ;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        urlArray = new String[]{MediaStore.Images.Media.DATA};
                        Cursor cursor = managedQuery(fileUri, urlArray, null, null, null);
                        int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String s = cursor.getString(column_index);
                        mainImgUri = Uri.parse(s);
                        view.setImageURI(fileUri);
                        bottomSheetDialog.cancel();
                    }
                }
            }
            else if (requestCode == PICK_CAMERA_REQUEST_CODE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mainUri = bitmap.toString();
                view.setImageBitmap(bitmap);
                bottomSheetDialog.cancel();
            }
            else if (requestCode == PICK_PDF_REQUEST_CODE) {
                 fileUri = data.getData();
                 mainUri = String.valueOf(pdfUri);
                Picasso.get()
                        .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaV9UPGC_9dgU-kXM3VuJUkeWCrkjluNm0cw&usqp=CAU")
                        .into(view );
                bottomSheetDialog.cancel();

            }
        }
    }

    private String convertContentUriToPdfUrl(Context context, Uri contentUri) {
        ContentResolver contentResolver = context.getContentResolver();
        String pdfUrl = null;

        try {
            InputStream inputStream = contentResolver.openInputStream(contentUri);
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "converted.pdf");
            OutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            pdfUrl = outputFile.getAbsolutePath();
            mainUri=pdfUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pdfUrl;

}

}





