package eit42.der_onlinestundenplan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TimeTableActivity extends AppCompatActivity {


    private TimeTableFragmentAdapter mTimeTableFragmentAdapter;
    private ViewPager mViewPager;
    private ImageButton lastWeekButton;
    private ImageButton nextWeekButton;
    private TextView weekTextView;

    private Toolbar topToolbar;
    private Toolbar bottomToolbar;
    private TextView toolbarSubtitle;

    //Shared Preferences
    private SharedPreferences sPrefs;
    private String sPrefsBaseKey ;
    private String sPrefsSubtitleKey;

    String prefSchoollistKey;
    String prefSchoollistDefault;
    String prefClasslistKey;
    String prefClasslistDefault;

    String currentSchool;
    String currentClass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        loadPrefSettings();
        loadCurrentPrefs();

        //Toolbar initialization
        topToolbar = (Toolbar) findViewById(R.id.app_bar);
        bottomToolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        updateSubtitleText();
        setSupportActionBar(topToolbar);
        topToolbar.inflateMenu(R.menu.menu_time_table);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mTimeTableFragmentAdapter = new TimeTableFragmentAdapter(getSupportFragmentManager());
        mTimeTableFragmentAdapter.setFragments(this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mTimeTableFragmentAdapter);


    }


    public void updateSubtitleText()
    {
        if(toolbarSubtitle == null)
        {
            toolbarSubtitle = (TextView) topToolbar.findViewById(R.id.toolbar_subtitle);
        }
        if(currentClass != "" && currentSchool != "")
        {
            toolbarSubtitle.setText(currentClass + " | " + currentSchool);
        }else toolbarSubtitle.setText(getString(R.string.no_school_error_message));

    }

    public void loadPrefSettings()
    {
        sPrefsBaseKey = getString(R.string.shared_preference_base_key);
        sPrefsSubtitleKey = getString(R.string.shared_preference_toolbar_key)+".subtitle";

        sPrefs = this.getSharedPreferences(sPrefsBaseKey,Context.MODE_PRIVATE);

        //Get school key & default
        prefSchoollistKey = getString(R.string.preference_schoollist_key);
        prefSchoollistDefault = getString(R.string.preference_schoollist_default);
        //Get school key & default
        prefClasslistKey = getString(R.string.preference_classlist_key);
        prefClasslistDefault = getString(R.string.preference_classlist_default);

    }

    public void loadCurrentPrefs()
    {
        //Set current values
        currentSchool = sPrefs.getString(prefSchoollistKey,prefSchoollistDefault);
        currentClass = sPrefs.getString(prefClasslistKey,prefClasslistDefault);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_table, menu);
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }else if(id == R.id.action_refresh)
        {
            loadCurrentPrefs();
            updateSubtitleText();

            Toast.makeText(this,"Stundenplan wird aktualisiert",Toast.LENGTH_SHORT).show();
            Toast.makeText(this,currentClass + ";" + currentSchool,Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
