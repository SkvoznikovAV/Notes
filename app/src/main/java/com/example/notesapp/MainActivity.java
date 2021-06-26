package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private Notes notes;
    private boolean isLansScape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLansScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        initMenu();
        initNotes();
        showStartFragment();
    }

    private void showStartFragment(){
        ListNotesFragment listNotesFragment = ListNotesFragment.newInstance(notes);

        int inContainer=R.id.maincontainer;
        if (isLansScape){
            inContainer=R.id.list_notes_container;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(inContainer, listNotesFragment);
        fragmentTransaction.commit();
    }

    private void initMenu() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            chooseMenu(id);

            drawer.closeDrawer(GravityCompat.START);
            return true;

        });
    }

    private void initNotes() {
        notes = new Notes();
        notes.add(new Note("Заметка 1","Описание заметки 1","01.06.2020"));
        notes.add(new Note("Заметка 2","Описание заметки 2","31.05.2018"));
        notes.add(new Note("Заметка 3","Описание заметки 3","25.05.2017"));
        notes.add(new Note("Заметка 4","Описание заметки 4","17.04.2021"));
        notes.add(new Note("Заметка 5","Описание заметки 5","20.06.2021"));
        notes.add(new Note("Заметка 6","Описание заметки 6","15.06.2016"));
        notes.add(new Note("Заметка 7","Описание заметки 7","10.06.2021"));
        notes.add(new Note("Заметка 8","Описание заметки 8","15.06.2018"));
        notes.add(new Note("Заметка 9","Описание заметки 9","28.02.2020"));
        notes.add(new Note("Заметка 10","Описание заметки 10","20.04.2015"));
        notes.add(new Note("Заметка 11","Описание заметки 11","05.12.2019"));
        notes.add(new Note("Заметка 12","Описание заметки 12","07.08.2020"));
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void chooseMenu(int id){
        switch(id){
            case R.id.action_add:
                Toast.makeText(this,"Добавление заметки",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_del:
                Toast.makeText(this,"Удаление заметки",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_info:
                showAboutFragment();
                break;
        }
    }

    private void showAboutFragment() {
        AboutAppFragment aboutAppFragment = new AboutAppFragment();

        int inContainer=R.id.maincontainer;
        if (isLansScape){
            inContainer=R.id.note_container;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(inContainer, aboutAppFragment);

        if (!isLansScape) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();

        chooseMenu(id);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Здесь определяем меню приложения (активити)
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this,"Поиск: "+query, Toast.LENGTH_SHORT).show();
                return true;
            }
            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

}