package com.fieldservice.app.presentation.tickets;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;

import com.fieldservice.app.R;
import com.fieldservice.app.data.AppContainer;
import com.fieldservice.app.databinding.ActivityTicketsBinding;
import com.fieldservice.app.domain.model.Ticket;
import com.fieldservice.app.presentation.UiState;
import com.fieldservice.app.presentation.ticketdetails.TicketDetailsActivity;
import com.fieldservice.app.ui.components.TicketAdapter;

import java.util.List;

public class TicketsActivity extends AppCompatActivity {

    private ActivityTicketsBinding binding;
    private TicketsViewModel viewModel;
    private TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarTickets);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarTickets.setNavigationOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new TicketsViewModel(AppContainer.getTicketRepository());
            }
        }).get(TicketsViewModel.class);

        setUpFilterChips();
        setUpRecyclerView();
        observeViewModel();
    }

    private void setUpFilterChips() {
        for (TicketFilter filter : TicketFilter.values()) {
            Chip chip = (Chip) getLayoutInflater()
                    .inflate(R.layout.item_filter_chip, binding.chipGroupFilters, false);
            chip.setText(filter.getLabel());
            chip.setId(View.generateViewId());
            chip.setTag(filter);
            chip.setOnClickListener(v -> viewModel.onFilterSelected(filter));
            binding.chipGroupFilters.addView(chip);
        }
        Chip firstChip = (Chip) binding.chipGroupFilters.getChildAt(0);
        firstChip.setChecked(true);
    }

    private void setUpRecyclerView() {
        ticketAdapter = new TicketAdapter(ticket ->
                startActivity(TicketDetailsActivity.newIntent(this, ticket.getId()))
        );
        binding.recyclerTickets.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerTickets.setAdapter(ticketAdapter);
    }

    private void observeViewModel() {
        viewModel.getUiState().observe(this, this::render);
    }

    private void render(UiState<List<Ticket>> state) {
        binding.progressTickets.setVisibility(
                state.getType() == UiState.Type.LOADING ? View.VISIBLE : View.GONE);
        binding.textEmptyTickets.setVisibility(
                state.getType() == UiState.Type.EMPTY ? View.VISIBLE : View.GONE);
        binding.recyclerTickets.setVisibility(
                state.getType() == UiState.Type.SUCCESS ? View.VISIBLE : View.GONE);

        if (state.getType() == UiState.Type.ERROR) {
            binding.textErrorTickets.setVisibility(View.VISIBLE);
            binding.textErrorTickets.setText(state.getErrorMessage());
        } else {
            binding.textErrorTickets.setVisibility(View.GONE);
        }

        if (state.getType() == UiState.Type.SUCCESS) {
            ticketAdapter.submitList(state.getData());
        }
    }
}
