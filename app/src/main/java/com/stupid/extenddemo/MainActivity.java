package com.stupid.extenddemo;

import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.stupid.extend.android.StringViewUtils;
import com.stupid.extend.lang.WebStringUtils;

public class MainActivity extends AppCompatActivity {

    private static final String SOURCE_KEY = "source_key";

    private static final String ANSWER_VISIBLE_KEY = "answer_visible_key";

    private static final String ANSWER_KEY = "answer_key";

    private Button upBtn;

    private Button goBtn;

    private EditText source;

    private TextView type;

    private TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upBtn = (Button) findViewById(R.id.text_up);
        goBtn = (Button) findViewById(R.id.text_go);
        source = (EditText) findViewById(R.id.text_source);
        type = (TextView) findViewById(R.id.text_type);
        answer = (TextView) findViewById(R.id.text_answer);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SOURCE_KEY)) {
                source.setText(savedInstanceState.getString(SOURCE_KEY));
            }
            if (savedInstanceState.containsKey(ANSWER_KEY)) {
                answer.setText(savedInstanceState.getString(ANSWER_KEY));
            }
            if (savedInstanceState.containsKey(ANSWER_VISIBLE_KEY)) {
                if (savedInstanceState.getBoolean(ANSWER_VISIBLE_KEY)) {
                    answer.setVisibility(View.VISIBLE);
                }
            }
        }

        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source.setText(answer.getText());
                answer.setVisibility(View.INVISIBLE);
            }
        });

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable editable = source.getText();
                if (editable == null) {
                    return;
                }
                answer.setText(new String(WebStringUtils.base64Encode(editable.toString())));
                answer.setVisibility(View.VISIBLE);
            }
        });

        Drawable leftIcon = ContextCompat.getDrawable(this, R.mipmap.ic_play_arrow_black_48dp);
//        leftIcon.setBounds(0, 0, 20, type.getMeasuredHeight());
        leftIcon.setBounds(new Rect(0, 0, 75, 75));

        type.setCompoundDrawables(leftIcon, null, null, null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SOURCE_KEY, StringViewUtils.getString(source));
        outState.putString(ANSWER_KEY, StringViewUtils.getString(answer));
        outState.putBoolean(ANSWER_VISIBLE_KEY, answer.isShown());
    }
}
