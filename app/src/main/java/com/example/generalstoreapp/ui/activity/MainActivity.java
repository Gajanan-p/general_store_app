package com.example.generalstoreapp.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.databinding.ActivityMainBinding;
import com.example.generalstoreapp.models.LoginModel;
import com.example.generalstoreapp.models.Users;
import com.example.generalstoreapp.models.UsersModel;
import com.example.generalstoreapp.repository.AuthRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;
import com.example.generalstoreapp.utils.SharedPreferencesUtils;
import com.example.generalstoreapp.utils.TokenProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.generalstoreapp.utils.PermissionUtils;
import com.example.generalstoreapp.utils.PrintUtils;
import com.example.generalstoreapp.viewmodel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private UsersModel usersModel;
    private LoginModel loginModel;
    private AuthRepository authRepository;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        setTheme(R.style.Theme_GeneralStoreApp_NoActionBar);
        super.onCreate(savedInstanceState);

        loginModel = SharedPreferencesUtils.getLoginDataPreferences(this);
        if (loginModel == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, windowInsets) -> {
            androidx.core.graphics.Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, 0, insets.right, insets.bottom);
            return windowInsets;
        });

        authRepository = new AuthRepository(this);
        setSupportActionBar(binding.appBarMain.toolbar);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();

        DrawerLayout drawer = binding.drawerLayout;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.salesFragment,
                R.id.productFragment,
                R.id.settingsFragment
        )
                .setOpenableLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            invalidateOptionsMenu();
        });

        binding.navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_logout) {
                logout();
            } else {
                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
                if (handled) {
                    binding.drawerLayout.closeDrawers();
                }
            }
            return true;
        });

        fetchUsersRolesAndPermission();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init(this);
        
        UsersModel cachedUser = SharedPreferencesUtils.getUserMeDataPreferences(this);
        if (cachedUser != null) {
            handledPermissionWiseViews(cachedUser);
            updateNavHeader(cachedUser);
        }
    }

    private void logout() {
        SharedPreferencesUtils.clearLoginDataPreferences(this);
        TokenProvider.get(this).clear();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        boolean isHome = navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == R.id.nav_home;
        
        boolean canReadBilling = PermissionUtils.hasPermission(this, "BILLING_READ");
        boolean canReadReports = PermissionUtils.hasPermission(this, "REPORT_READ");
        
        android.view.MenuItem printItem = menu.findItem(R.id.action_print);
        if (printItem != null) {
            printItem.setVisible(isHome && (canReadBilling || canReadReports));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_print) {
            printTodayReport();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void printTodayReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String datePart = sdf.format(new Date());
        String fromDate = datePart + "T00:00:00Z";
        String toDate = datePart + "T23:59:59Z";

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Preparing report...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        homeViewModel.fetchBillingList(null, fromDate, toDate, 100, 0);
        homeViewModel.getBillingListLiveData().observe(this, list -> {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                if (list != null && !list.isEmpty()) {
                    PrintUtils.printReport(this, list);
                } else {
                    Toast.makeText(this, "No data found for today", Toast.LENGTH_SHORT).show();
                }
                homeViewModel.getBillingListLiveData().removeObservers(this);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void fetchUsersRolesAndPermission(){
        authRepository.getUsers(result -> {
            if (result.status == ApiResult.Status.SUCCESS) {
                usersModel = result.data;
                SharedPreferencesUtils.setUserMeDataPreferences(this, usersModel);
                handledPermissionWiseViews(usersModel);
                updateNavHeader(usersModel);
            } else {
                String message = !TextUtils.isEmpty(result.message) ? result.message : "Error fetching users";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateNavHeader(UsersModel usersModel) {
        if (usersModel == null || usersModel.getUser() == null) return;

        View headerView = binding.navView.getHeaderView(0);
        TextView textName = headerView.findViewById(R.id.userName);
        TextView textMobile = headerView.findViewById(R.id.userMobile);
        TextView textRole = headerView.findViewById(R.id.userRole);
        TextView textPermissions = headerView.findViewById(R.id.viewPermissions);

        Users user = usersModel.getUser();
        String fullName = user.getFirstName() + " " + (user.getMiddleName() != null ? user.getMiddleName() + " " : "") + user.getLastName();
        textName.setText(fullName);
        textMobile.setText("Mobile: " + user.getMobile());

        if (usersModel.getRoles() != null && !usersModel.getRoles().isEmpty()) {
            StringBuilder roles = new StringBuilder("Role: ");
            for (int i = 0; i < usersModel.getRoles().size(); i++) {
                roles.append(usersModel.getRoles().get(i).getName());
                if (i < usersModel.getRoles().size() - 1) roles.append(", ");
            }
            textRole.setText(roles.toString());
        }

        textPermissions.setOnClickListener(v -> showPermissionsDialog(usersModel.getPermissions()));
    }

    private void showPermissionsDialog(List<String> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            Toast.makeText(this, "No permissions allocated", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String p : permissions) {
            sb.append("• ").append(p).append("\n");
        }

        new AlertDialog.Builder(this)
                .setTitle("Allocated Permissions")
                .setMessage(sb.toString())
                .setPositiveButton("Close", null)
                .show();
    }

    public void handledPermissionWiseViews(UsersModel usersModel){
        if (usersModel == null || usersModel.getPermissions() == null) return;

        List<String> permissions = usersModel.getPermissions();
        Menu navMenu = binding.navView.getMenu();
        Menu bottomMenu = ((BottomNavigationView) findViewById(R.id.bottom_navigation)).getMenu();

        checkAndSetVisible(navMenu, R.id.categoriesFragment, permissions.contains("CATEGORY_READ"));
        checkAndSetVisible(navMenu, R.id.unitsFragment, permissions.contains("UNIT_READ"));
        checkAndSetVisible(navMenu, R.id.productFragment, permissions.contains("PRODUCT_READ"));
        checkAndSetVisible(navMenu, R.id.roleFragment, permissions.contains("ROLE_READ"));
        checkAndSetVisible(navMenu, R.id.usersFragment, permissions.contains("USER_READ"));
        checkAndSetVisible(navMenu, R.id.customersFragment, permissions.contains("CUSTOMER_READ"));
        checkAndSetVisible(navMenu, R.id.supplierFragment, permissions.contains("SUPPLIER_READ"));
        checkAndSetVisible(navMenu, R.id.purchaseFragment, permissions.contains("PURCHASE_READ"));
        checkAndSetVisible(navMenu, R.id.billingFragment, permissions.contains("BILLING_READ"));
        checkAndSetVisible(navMenu, R.id.salesFragment, permissions.contains("REPORT_READ"));
        checkAndSetVisible(navMenu, R.id.settingsFragment, permissions.contains("RBAC_READ") || permissions.contains("ROLE_READ"));

        checkAndSetVisible(bottomMenu, R.id.productFragment, permissions.contains("PRODUCT_READ"));
        checkAndSetVisible(bottomMenu, R.id.settingsFragment, permissions.contains("RBAC_READ") || permissions.contains("ROLE_READ"));
    }

    private void checkAndSetVisible(Menu menu, int id, boolean visible) {
        android.view.MenuItem item = menu.findItem(id);
        if (item != null) {
            item.setVisible(visible);
        }
    }
}
