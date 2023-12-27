package com.technotoil.supportticket.ui.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technotoil.supportticket.R;
import com.technotoil.supportticket.model.TicketModel;
import com.technotoil.supportticket.model.UserModel;
import com.technotoil.supportticket.ui.CreateTicket;
import com.technotoil.supportticket.viewModel.TicketViewModel;
import com.technotoil.supportticket.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    RecyclerView recyclerView;
    String[] dropdownItems = {"Item 1", "Item 2", "Item 3"};
    TicketViewModel ticketViewModel;
    UserViewModel userViewModel;
    ArrayList<TicketModel> ticketList;
    List<UserModel> usermodelList;
    RecyclerView.Adapter rv_listNavigationAdapter;
    RecyclerView.LayoutManager rv_listNavigationLayoutManager;
    DashAdapter adapter;
    private String mParam2;
    LinearLayout ll_closed, ll_activestatus,ll_awaitingList;
    int userid ,openSize, closeSize,awaitingSize;
    TextView tv_openSize, tv_closedSize, all_awaitingStatus;
    FrameLayout fragment_container;
    ProgressBar chat_progressBar;
    String ticketId;
    DatabaseReference reference;
    ArrayList<TicketModel> ticketClosedList;
    public DashFragment() {
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
    public static DashFragment newInstance(String param1, String param2) {
        DashFragment fragment = new DashFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dash, container, false);

        ticketList = new ArrayList<>();
        usermodelList = new ArrayList<>();
        ticketClosedList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.dashboard);
        ll_closed = view.findViewById(R.id.ll_closed);
        ll_activestatus = view.findViewById(R.id.ll_activestatus);
        ll_awaitingList = view.findViewById(R.id.ll_awaitingList);
        fragment_container = view.findViewById(R.id.fragment_container);
        chat_progressBar = view.findViewById(R.id.chat_progressBar);
        tv_openSize = view.findViewById(R.id.tv_openSize);
        all_awaitingStatus = view.findViewById(R.id.all_awaitingStatus);
        tv_closedSize = view.findViewById(R.id.tv_closeSize);

        ticketId = getArguments().getString("TICKETID");
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
       /* ticketViewModel = new ViewModelProvider(getActivity()).get(TicketViewModel.class);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
*/
       /* // Show size of Open or Awaiting ticket status list
       ticketViewModel.fetchActiveDataFromRoom().observe(getActivity(), new Observer<List<TicketModel>>() {
            @Override
            public void onChanged(List<TicketModel> ticketModels) {
                modelList = ticketModels;
                openSize = ticketModels.size();
                String open = String.valueOf(openSize);
                tv_openSize.setText(open);
            }
        });*/

        //show Ticket data from realtime db
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Ticket").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ticketList.add(snapshot.getValue(TicketModel.class));
                adapter.setList(ticketList);
                openSize = ticketList.size();
                tv_openSize.setText(String.valueOf(openSize));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ticketList.remove(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                adapter.notifyDataSetChanged();
            }
        });



        // show size of Open ticket status list
       /* ticketViewModel.fetchOpenList().observe(getActivity(), new Observer<List<TicketModel>>() {
            @Override
            public void onChanged(List<TicketModel> ticketModels) {

//                ticketModel = ticketModels;
                openSize = ticketModels.size();
                String open = String.valueOf(openSize);
                tv_openSize.setText(open);
            }
        });*/

        // Show size of closed ticket status list
        /*ticketViewModel.fetchClosedList().observe(getActivity(), new Observer<List<TicketModel>>() {
            @Override
            public void onChanged(List<TicketModel> ticketModels) {

//                ticketModel = ticketModels;
                closeSize = ticketModels.size();
                String close = String.valueOf(closeSize);
                tv_closedSize.setText(close);
            }
        });*/

        // Show size of Awaiting ticket status list
       /* ticketViewModel.fetchAwaitingList().observe(getActivity(), new Observer<List<TicketModel>>() {
            @Override
            public void onChanged(List<TicketModel> ticketModels) {
//                ticketModel = ticketModels;
                awaitingSize = ticketModels.size();
                String awaiting = String.valueOf(awaitingSize);
                all_awaitingStatus.setText(awaiting);
            }
        });*/

        // set list of Active Data on Dashbord Fragment ----(directly shown on Dashbord)
        /*ticketViewModel.fetchActiveDataFromRoom().observe(getActivity(), new Observer<List<TicketModel>>() {
            @Override
            public void onChanged(List<TicketModel> ticketModels) {
//                ticketModel = ticketModels;
                adapter.setList(ticketModel);
            }
        });*/

        //fatch User Id for ticket foregin key
        /*userViewModel.fetchDataFromRoom().observe(getActivity(), new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                usermodelList = userModels;
                // adapter.setList(modelList);
                handleUserModelList(usermodelList);

            }
        });*/

        /*ll_awaitingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketViewModel.fetchAwaitingList().observe(getActivity(), new Observer<List<TicketModel>>() {
                    @Override
                    public void onChanged(List<TicketModel> ticketModels) {
//                        ticketModel = ticketModels;
                        adapter.setList(ticketModel);
                    }
                });
            }
        });*/

        // set list of Active Data on Dashbord Fragment ----(show on open layout click)
        /*ll_activestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketViewModel.fetchActiveDataFromRoom().observe(getActivity(), new Observer<List<TicketModel>>() {
                    @Override
                    public void onChanged(List<TicketModel> ticketModels) {
//                        ticketModel = ticketModels;
                        adapter.setList(ticketModel);
                    }
                });
            }
        });*/

        // Show closed status list
       /* ll_closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketViewModel.fetchClosedList().observe(getActivity(), new Observer<List<TicketModel>>() {
                    @Override
                    public void onChanged(List<TicketModel> ticketModels) {
//                        ticketModel = ticketModels;
                        adapter.setList(ticketModel);
                    }
                });

            }
        });*/

        // Set the layout manager and adapter to the RecyclerView
        adapter = new DashAdapter(requireContext(), ticketList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        recyclerView
//                = view.findViewById(R.id.rv_sub);
        FloatingActionButton create = view.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), CreateTicket.class);
                i.putExtra("USERID", userid);
                startActivity(i);
            }
        });
        // Set the LayoutManager that
        // this RecyclerView will use.

        // adapter instance is set to the
        // recyclerview to inflate the items.
    }

    private void handleUserModelList(List<UserModel> userModels) {
        // Example: Log the user IDs to the console
        for (UserModel userModel : userModels) {
            Log.d("varsha", String.valueOf(userModel.getId()));
//            userid= userModel.getId();

        }

    }
}