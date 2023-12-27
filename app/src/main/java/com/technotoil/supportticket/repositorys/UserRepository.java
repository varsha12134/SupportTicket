package com.technotoil.supportticket.repositorys;

public class UserRepository {
   /* Context context;
    RoomDB roomDB;
    private LiveData<List<UserModel>> liveData;
    public UserRepository(Context context) {
        this.context = context;
         roomDB = (RoomDB) RoomDB.getInstance(context);
        liveData = new MutableLiveData<>();
    }

    public void insert(UserModel userModel){
        new InsertUserAsyncTask(roomDB.userDao()).execute(userModel);

    }

    public LiveData<List<UserModel>> getData() {
        liveData = roomDB.userDao().featchUserData();
        return liveData;
    }

    public static class InsertUserAsyncTask extends AsyncTask<UserModel, Void,Void>{
        private UserDao userDao;
        private InsertUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }
       @Override
       protected Void doInBackground(UserModel... userModels) {
            userDao.insertLoginData(userModels[0]);
            return null;
       }
   }*/
}
