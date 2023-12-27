package com.technotoil.supportticket.ui.chat;

import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.technotoil.supportticket.R;
import com.technotoil.supportticket.db.RealTimeDB;
import com.technotoil.supportticket.viewModel.TicketViewModel;

public class ChatScreen extends AppCompatActivity {
    ViewPager simpleViewPager;
    TabLayout tabLayout;
    AppBarLayout appBar;
    Toolbar toolbar;
    ImageView backError;
    TextView titleName, tvWeekDayFirstLetter, tv_onlineStatus;
    int id, color, status;
    RelativeLayout rl_background;
    DatabaseReference reference;
    Bundle bundle = new Bundle();
    TicketViewModel ticketViewModel;
    String subject, region, description, createdOn, name, userId, imgId;
    String tId = "";
    String ticketId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        reference = RealTimeDB.getInstance().getReference();
        initialization();
        gradient();
        clickEvent();
        listeners();

    }

    private void listeners() {

        reference.child("Ticket").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        reference.child("Ticket").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                    /*Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("age", 26);
                    ticketId = childSnapshot.getKey();
                    reference.child(ticketId).updateChildren(updatedData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Firebase", " updated successfully");
                            } else {
                                // Data update failed
                                Log.e("Firebase", "Error updating ", task.getException());
                            }
                        }
                    });*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initialization() {
        toolbar = findViewById(R.id.chat_toolbar);
        appBar = findViewById(R.id.appbar);
        backError = findViewById(R.id.backerror);
        titleName = findViewById(R.id.tasktiltle);
        tv_onlineStatus = findViewById(R.id.tv_onlineStatus);
        tvWeekDayFirstLetter = findViewById(R.id.tvWeekDayFirstLetter);
        rl_background = findViewById(R.id.rl_background);
        simpleViewPager = (ViewPager) findViewById(R.id.simpleViewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_tablayout);

        // data of detailsFragment
        subject = getIntent().getStringExtra("SUBJECT");
        region = getIntent().getStringExtra("REGION");
        description = getIntent().getStringExtra("DESC");
        userId = getIntent().getStringExtra("USERID");
        createdOn = getIntent().getStringExtra("DATE");
        id = getIntent().getIntExtra("ID", 0);
        name = getIntent().getStringExtra("Name");
        ticketId = getIntent().getStringExtra("TICKET_ID");
        imgId = getIntent().getStringExtra("IMGID");
        status = getIntent().getIntExtra("STATUS", 0);

        bundle.putString("Subject", subject);
        bundle.putString("Region", region);
        bundle.putString("Description", description);
        bundle.putString("Date", createdOn);
        bundle.putString("TicketId", ticketId);
        bundle.putInt("Id", id);
        bundle.putInt("Status", status);
        bundle.putString("UserId", userId);
        bundle.putString("ImgId", imgId);
    }

    private void gradient() {
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        if (name.equals("A")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.A));
        } else if (name.equals("B")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.B));
        } else if (name.equals("C")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.C));
        } else if (name.equals("D")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.D));
        } else if (name.equals("E")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.E));
        } else if (name.equals("F")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.F));
        } else if (name.equals("G")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.G));
        } else if (name.equals("H")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.H));
        } else if (name.equals("I")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.I));
        } else if (name.equals("J")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.J));
        } else if (name.equals("K")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.K));
        } else if (name.equals("L")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.L));
        } else if (name.equals("M")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.M));
        } else if (name.equals("N")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.N));
        } else if (name.equals("O")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.O));
        } else if (name.equals("P")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.P));
        } else if (name.equals("Q")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.Q));
        } else if (name.equals("R")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.R));
        } else if (name.equals("S")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.S));
        } else if (name.equals("T")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.T));
        } else if (name.equals("U")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.U));
        } else if (name.equals("V")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.V));
        } else if (name.equals("W")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.W));
        } else if (name.equals("X")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.X));
        } else if (name.equals("Y")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.Y));
        } else if (name.equals("Z")) {
            draw.setColor(ContextCompat.getColor(ChatScreen.this, R.color.Z));
        }
        tvWeekDayFirstLetter.setText(name);
        rl_background.setBackground(draw);

    }

    public void clickEvent() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.close) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatScreen.this);
                    builder.setMessage("Are you sure you want to close ticket ?");
                    builder.setTitle("Close Ticket");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    reference = RealTimeDB.getInstance().getReference();

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
                return true;
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                simpleViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Chat"); // set the Text for the first Tab
// first tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
// Create a new Tab named "Second"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Details"); // set the Text for the second Tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
        PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), bundle);
        simpleViewPager.setAdapter(adapter);
// addOnPageChangeListener event change the tab on slide
        simpleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //setTitle in appBar
        titleName.setText(subject);

        backError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            tv_onlineStatus.setText("online");

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            tv_onlineStatus.setText("offline");
        }
    }
}