package com.technotoil.supportticket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.technotoil.supportticket.model.AttachmentModel;
import com.technotoil.supportticket.repositorys.AttachmentRepository;

public class AttachmentViewModel extends AndroidViewModel {
    AttachmentRepository repository;

    public AttachmentViewModel(@NonNull Application application) {
        super(application);
        repository = new AttachmentRepository(application);
    }
    public void insertAttachedData(AttachmentModel attachmentModel){
        repository.insertAttachmentDataInRoom(attachmentModel);
    }
    public LiveData<AttachmentModel> getAttachmentData(String ticketId ) {
        return repository.getActiveData(ticketId);

    }
}
