package com.impulse.supportticket.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.impulse.supportticket.HomeFrag;
import com.impulse.supportticket.NotificationFrag;
import com.impulse.supportticket.R;
import com.impulse.supportticket.SettingFrag;
import com.impulse.supportticket.model.UserModel;
import com.impulse.supportticket.ui.Dashboard.DashFragment;
import com.impulse.supportticket.ui.chat.ChatFragment;
import com.impulse.supportticket.ui.chat.ChatScreen;
import com.impulse.supportticket.viewModel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    RecyclerView rv_rlt_case_law;
    Spinner statu;
    FirebaseAuth mAuth;
    UserViewModel userViewModel;
    List<UserModel> usermodelList;
    int userid;
    String userName, firstWord, ticketId;
    Context context = this;
    String[] courses = { "Open", "Closed",
            "Unattended" };
    GradientDrawable draw;
    TextView tv_username, userFirstLetter;
    RelativeLayout userprofile;
    String currentDateAndTime;
    RecyclerView.Adapter rv_listNavigationAdapter;
    RecyclerView.LayoutManager rv_listNavigationLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.nav_view);

        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar=findViewById(R.id.toolbar);
        rv_rlt_case_law = findViewById(R.id.dashboard);
//        userViewModel = new ViewModelProvider(MainActivity.this).get(UserViewModel.class);

        ticketId = getIntent().getStringExtra("TicketId");

        NavigationView navigationView=findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        tv_username = headerView.findViewById(R.id.tv_navusername);
        userprofile=headerView.findViewById(R.id.userprofile);
        userFirstLetter=headerView.findViewById(R.id.userFirstLetter);


        navigationView.setNavigationItemSelectedListener(this);
        ColorStateList csl = new ColorStateList(
                new int[][] {
                        new int[] {-android.R.attr.state_checked}, // unchecked
                        new int[] { android.R.attr.state_checked}  // checked
                },
                new int[] {
                        Color.BLACK,
                        Color.BLUE
                }
        );
        navigationView.setItemTextColor(csl);
        navigationView.setItemIconTintList(csl);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DashFragment());


        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        drawerLayout.setBackgroundColor(Color.TRANSPARENT);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.bright_white));

        if(savedInstanceState==null)
        {
            Bundle bundle = new Bundle();
            bundle.putString("TICKETID", ticketId);
            DashFragment dashFragment = new DashFragment();
            dashFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,dashFragment).commit();
            navigationView.setCheckedItem(R.id.notifications);

        }

/*
        userViewModel.fetchDataFromRoom().observe(MainActivity.this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                usermodelList = userModels;
                // adapter.setList(modelList);
                handleUserModelList(usermodelList);

            }

            private void handleUserModelList(List<UserModel> usermodelList) {
                for (UserModel userModel : usermodelList) {
                    userid= userModel.getId();
                    userName = userModel.getUsername();
                    firstWord = userModel.getUsername().substring(0,1);
                    userFirstLetter.setText(firstWord);
                    tv_username.setText("Hello\n"+userName+" !");


                    draw = new GradientDrawable();
                    draw.setShape(GradientDrawable.OVAL);
                    if (firstWord.equals("A")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.A));
                    } else if (firstWord.equals("B")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.B));
                    }else if (firstWord.equals("C")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.C));
                    }else if (firstWord.equals("D")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.D));
                    }else if (firstWord.equals("E")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.E));
                    }else if (firstWord.equals("F")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.F));
                    }else if (firstWord.equals("G")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.G));
                    }else if (firstWord.equals("H")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.H));
                    }else if (firstWord.equals("I")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.I));
                    }else if (firstWord.equals("J")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.J));
                    }else if (firstWord.equals("K")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.K));
                    }else if (firstWord.equals("L")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.L));
                    }else if (firstWord.equals("M")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.M));
                    }else if (firstWord.equals("N")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.N));
                    }else if (firstWord.equals("O")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.O));
                    }else if (firstWord.equals("P")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.P));
                    }else if (firstWord.equals("Q")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.Q));
                    }else if (firstWord.equals("R")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.R));
                    }else if (firstWord.equals("S")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.S));
                    }else if (firstWord.equals("T")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.T));
                    }else if (firstWord.equals("U")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.U));
                    }else if (firstWord.equals("V")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.V));
                    }else if (firstWord.equals("W")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.W));
                    }else if (firstWord.equals("X")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.X));
                    }else if (firstWord.equals("Y")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.Y));
                    }else if (firstWord.equals("Z")) {
                        draw.setColor(ContextCompat.getColor(MainActivity.this, R.color.Z));
                    }
                    userprofile.setBackground(draw);
                }
            }
        });
*/

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.settings){
            SettingFrag settingFrag = new SettingFrag();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, settingFrag, "");
            fragmentTransaction.commit();
        } else if (itemId == R.id.home) {
            DashFragment dashFragment = new DashFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, dashFragment, "");
            fragmentTransaction.commit();
        } else if (itemId == R.id.notifications) {
            NotificationFrag notificationFrag = new NotificationFrag();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, notificationFrag, "");
            fragmentTransaction.commit();
        }
        else if (itemId == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to logout ?");
            builder.setTitle("Log out?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
            return true;

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, SignIn.class));
        }
    }

    @Override
    public void onBackPressed()
        {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer((GravityCompat.START));
            }
            else {
                super.onBackPressed();
            }


        }


}