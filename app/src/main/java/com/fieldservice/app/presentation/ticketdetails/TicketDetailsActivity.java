package com.fieldservice.app.presentation.ticketdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fieldservice.app.data.AppContainer;
import com.fieldservice.app.databinding.ActivityTicketDetailsBinding;
import com.fieldservice.app.domain.model.Ticket;
import com.fieldservice.app.presentation.UiState;
import com.fieldservice.app.ui.components.PriorityBadge;
import com.fieldservice.app.ui.components.StatusBadge;

/**
 * Detalhes de um chamado. Mostra a próxima ação do fluxo de atendimento de acordo com o
 * status atual (aceitar → iniciar deslocamento → registrar chegada → iniciar atendimento →
 * finalizar), resolvida por {@link TicketAction}.
 */
public class TicketDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_TICKET_ID = "extra_ticket_id";

    /** Cria o Intent para abrir os detalhes de um chamado específico. */
    public static Intent newIntent(Context context, String ticketId) {
        Intent intent = new Intent(context, TicketDetailsActivity.class);
        intent.putExtra(EXTRA_TICKET_ID, ticketId);
        return intent;
    }

    private ActivityTicketDetailsBinding binding;
    private TicketDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarDetails.setNavigationOnClickListener(v -> finish());

        String ticketId = getIntent().getStringExtra(EXTRA_TICKET_ID);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new TicketDetailsViewModel(AppContainer.getTicketRepository(), ticketId);
            }
        }).get(TicketDetailsViewModel.class);

        viewModel.getUiState().observe(this, this::render);
        viewModel.getIsUpdating().observe(this, this::renderUpdating);
    }

    private void render(UiState<Ticket> state) {
        boolean success = state.getType() == UiState.Type.SUCCESS;
        binding.contentDetails.setVisibility(success ? View.VISIBLE : View.GONE);
        binding.textNotFound.setVisibility(success ? View.GONE : View.VISIBLE);
        if (!success) {
            return;
        }

        Ticket ticket = state.getData();
        binding.textNumber.setText(ticket.getNumber());
        binding.textCustomer.setText(ticket.getCustomerName());
        binding.textTitle.setText(ticket.getTitle());
        PriorityBadge.bind(binding.textPriorityBadge, ticket.getPriority());
        StatusBadge.bind(binding.textStatusBadge, ticket.getStatus());
        binding.textClientValue.setText(ticket.getCustomerName());
        binding.textAddressValue.setText(ticket.getAddress());
        binding.textDescriptionValue.setText(ticket.getDescription());

        TicketAction action = TicketAction.forStatus(ticket.getStatus());
        if (action != null) {
            binding.buttonAction.setVisibility(View.VISIBLE);
            binding.buttonAction.setText(action.labelRes);
            binding.buttonAction.setOnClickListener(v -> viewModel.advance(action.nextStatus));
        } else {
            binding.buttonAction.setVisibility(View.GONE);
            binding.buttonAction.setOnClickListener(null);
        }
    }

    private void renderUpdating(boolean updating) {
        binding.buttonAction.setEnabled(!updating);
    }
}
