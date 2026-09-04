package com.fieldservice.app.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fieldservice.app.R;
import com.fieldservice.app.data.AppContainer;
import com.fieldservice.app.databinding.ActivityHomeBinding;
import com.fieldservice.app.presentation.profile.ProfileActivity;
import com.fieldservice.app.presentation.ticketdetails.TicketDetailsActivity;
import com.fieldservice.app.presentation.tickets.TicketsActivity;
import com.fieldservice.app.ui.components.TicketAdapter;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private TicketAdapter priorityTicketsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarHome);

        priorityTicketsAdapter = new TicketAdapter(ticket ->
                startActivity(TicketDetailsActivity.newIntent(this, ticket.getId()))
        );
        binding.recyclerPriorityTickets.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerPriorityTickets.setAdapter(priorityTicketsAdapter);

        HomeViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HomeViewModel(
                        AppContainer.getTicketRepository(),
                        AppContainer.getAuthRepository()
                );
            }
        }).get(HomeViewModel.class);

        viewModel.getUiState().observe(this, this::render);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuViewTickets) {
            startActivity(new Intent(this, TicketsActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.menuProfile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void render(HomeUiState state) {
        binding.textGreeting.setText(getString(R.string.home_greeting, state.getTechnicianFirstName()));
        binding.textPendingCount.setText(String.valueOf(state.getPendingCount()));
        binding.textInProgressCount.setText(String.valueOf(state.getInProgressCount()));
        binding.textCompletedCount.setText(String.valueOf(state.getCompletedCount()));

        boolean hasPriorityTickets = !state.getPriorityTickets().isEmpty();
        binding.textPriorityTitle.setVisibility(hasPriorityTickets ? View.VISIBLE : View.GONE);
        priorityTicketsAdapter.submitList(state.getPriorityTickets());
    }
}
