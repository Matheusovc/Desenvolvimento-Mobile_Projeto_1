package com.fieldservice.app.presentation.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fieldservice.app.R;
import com.fieldservice.app.databinding.ActivityMainBinding;
import com.fieldservice.app.presentation.home.HomeFragment;
import com.fieldservice.app.presentation.profile.ProfileFragment;
import com.fieldservice.app.presentation.tickets.TicketsFragment;

/**
 * Tela principal após o login: hospeda as abas (Início, Chamados, Perfil) por meio de
 * uma barra de navegação inferior. Cada aba é um Fragment; as instâncias são mantidas e
 * apenas mostradas/ocultadas, preservando o estado ao alternar entre elas e ao girar a tela.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG_HOME = "home";
    private static final String TAG_TICKETS = "tickets";
    private static final String TAG_PROFILE = "profile";

    private ActivityMainBinding binding;

    private Fragment homeFragment;
    private Fragment ticketsFragment;
    private Fragment profileFragment;
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();
            ticketsFragment = new TicketsFragment();
            profileFragment = new ProfileFragment();
            fm.beginTransaction()
                    .add(R.id.navHostContainer, profileFragment, TAG_PROFILE).hide(profileFragment)
                    .add(R.id.navHostContainer, ticketsFragment, TAG_TICKETS).hide(ticketsFragment)
                    .add(R.id.navHostContainer, homeFragment, TAG_HOME)
                    .commit();
            activeFragment = homeFragment;
        } else {
            // Reaproveita as instâncias que o FragmentManager já restaurou (ex.: após rotação).
            homeFragment = fm.findFragmentByTag(TAG_HOME);
            ticketsFragment = fm.findFragmentByTag(TAG_TICKETS);
            profileFragment = fm.findFragmentByTag(TAG_PROFILE);
            activeFragment = homeFragment;
            if (ticketsFragment != null && !ticketsFragment.isHidden()) {
                activeFragment = ticketsFragment;
            } else if (profileFragment != null && !profileFragment.isHidden()) {
                activeFragment = profileFragment;
            }
        }

        binding.bottomNav.setOnItemSelectedListener(this::onNavItemSelected);
    }

    private boolean onNavItemSelected(@NonNull MenuItem item) {
        Fragment target;
        int id = item.getItemId();
        if (id == R.id.navTickets) {
            target = ticketsFragment;
        } else if (id == R.id.navProfile) {
            target = profileFragment;
        } else {
            target = homeFragment;
        }
        if (target == null || target == activeFragment) {
            return true;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .hide(activeFragment)
                .show(target)
                .commit();
        activeFragment = target;
        return true;
    }
}
