package com.jasonrovis.brewdog.MainActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jasonrovis.brewdog.MainActivity.Fragments.RecyclerViewFragment;
import com.jasonrovis.brewdog.MainActivity.Fragments.TextFragment;
import com.jasonrovis.brewdog.R;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static boolean ascending = true;
    private MainActivityPresenter presenter;

    private ProgressDialog progress;

    private FragmentManager manager = getSupportFragmentManager();
    Fragment testFragment;
    Fragment recyclerFragment;

    EditText editText;
    String editTextString = "";
    ImageButton orderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);
        editText = (EditText)findViewById(R.id.editText);

        testFragment = manager.findFragmentById(R.id.fragment_container);
        if (testFragment == null) {
            loadTextFragment(getString(R.string.launch_message), false);
        }

        ImageButton searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSearchBtn();
            }
        });

        orderBtn= (ImageButton)findViewById(R.id.orderBtn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOrderBtn();
            }
        });

    }

    private void clickOrderBtn() {

        if (ascending) {
            ascending = false;
            orderBtn.setImageResource(R.drawable.decrbtn);
        } else {
            ascending = true;
            orderBtn.setImageResource(R.drawable.incrbtn);
        }

        if (recyclerFragment != null && recyclerFragment.isVisible()  ) {
            presenter.setUpRecyclerView();
        }
    }

    private void clickSearchBtn() {

        editTextString = editText.getText().toString().replace(" ", "_");
        presenter.getBeersList(editTextString);

        removeKeyboard();
        System.out.println("button pressed");
    }

    public void loadRecyclerFragment() {

        recyclerFragment = RecyclerViewFragment.newInstance(editTextString.replace("_", " "));
        manager.beginTransaction().replace(R.id.fragment_container, recyclerFragment).commit();

    }

    public void loadTextFragment(String text, boolean isError ) {
        testFragment = TextFragment.newInstance(text, isError);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, testFragment);
        transaction.commit();
    }

    public void showProgressDialog() {
        progress = new ProgressDialog(MainActivity.this);
        progress.setTitle("Please wait");
        progress.setMessage("The beers are being specially picked");
        progress.setCancelable(false);
        progress.show();
    }

    public void removeProgressDialog() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    public static boolean isAscending() {
        return ascending;
    }

    public MainActivityPresenter getPresenter() {
        return presenter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        removeKeyboard();
        return true;
    }

    private void removeKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

}
