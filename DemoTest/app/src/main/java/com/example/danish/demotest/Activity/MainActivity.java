package com.example.danish.demotest.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.danish.demotest.Fragment.AboutFragment;
import com.example.danish.demotest.Fragment.DataBoardFragment;
import com.example.danish.demotest.Fragment.MonitorFragment;
import com.example.danish.demotest.Fragment.RankingFragment;
import com.example.danish.demotest.Fragment.WarnFragment;
import com.example.danish.demotest.R;
import com.example.danish.demotest.Widgets.MainMenuLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.layout_content)
    FrameLayout layoutContent;
    @BindView(R.id.temp_line)
    View tempLine;
    @BindView(R.id.main_databillboard_menu)
    MainMenuLayout mainDatabillboardMenu;
    @BindView(R.id.main_ranking_menu)
    MainMenuLayout mainRankingMenu;
    @BindView(R.id.main_stutas_menu)
    MainMenuLayout mainStutasMenu;
    @BindView(R.id.main_warning_menu)
    MainMenuLayout mainWarningMenu;
    @BindView(R.id.main_aboutus_menu)
    MainMenuLayout mainAboutusMenu;
    @BindView(R.id.main_tab_ll)
    LinearLayout mainTabLl;

    DataBoardFragment dataBoardFragment;
    RankingFragment rankingFragment;
    MonitorFragment monitorFragment;
    WarnFragment warnFragment;
    AboutFragment aboutFragment;

    private int lastFragmentIndex = 0;

    private final static int DATA_FRAGMENT = 0X1001;
    private final static int MONITOR_FRAGMENT = 0X1002;
    private final static int WARN_FRAGMENT = 0X1003;
    private final static int ABOUT_FRAGMENT = 0X1004;
    private final static int RANKING_FRAGMENT = 0x1005;

    public static Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainActivity = this;

        dataBoardFragment = new DataBoardFragment();
        monitorFragment = new MonitorFragment();
        warnFragment = new WarnFragment();
        aboutFragment = new AboutFragment();
        rankingFragment = new RankingFragment();


        layoutContent = (FrameLayout) findViewById(R.id.layout_content);
        mainDatabillboardMenu.setOnClickListener(this);
        mainStutasMenu.setOnClickListener(this);
        mainWarningMenu.setOnClickListener(this);
        mainRankingMenu.setOnClickListener(this);
        mainAboutusMenu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.main_databillboard_menu:
                switchFragment(DATA_FRAGMENT);
                break;
            case R.id.main_stutas_menu:
                switchFragment(MONITOR_FRAGMENT);
                break;
            case R.id.main_warning_menu:
                switchFragment(WARN_FRAGMENT);
                break;
            case R.id.main_aboutus_menu:
                switchFragment(ABOUT_FRAGMENT);
                break;
            case R.id.main_ranking_menu:
                switchFragment(RANKING_FRAGMENT);
                break;
        }
    }

    /*
     * 切换fragment
     * */
    private void switchFragment(int fragmentIndex) {
        if (lastFragmentIndex == fragmentIndex) {
            return;
        }

        Fragment lastFragment = returnFragment(lastFragmentIndex);
        Fragment showFragment = returnFragment(fragmentIndex);
        changeMenu(lastFragmentIndex,fragmentIndex);

        showSelectFragment(lastFragment,showFragment);
        lastFragmentIndex = fragmentIndex;
    }

    private Fragment returnFragment(int fragmentIndex) {
        switch (fragmentIndex) {
            case DATA_FRAGMENT:
                return  dataBoardFragment;
            case MONITOR_FRAGMENT:
                return monitorFragment;
            case WARN_FRAGMENT:
                return warnFragment;
            case ABOUT_FRAGMENT:
                return aboutFragment;
            case RANKING_FRAGMENT:
                return rankingFragment;
        }
        return dataBoardFragment;
    }

    private void changeMenu(int lastFragmentIndex,int showFragmentIndex) {
        switch (lastFragmentIndex) {
            case DATA_FRAGMENT:
                mainDatabillboardMenu.selectMenu(false);
            case MONITOR_FRAGMENT:
                mainStutasMenu.selectMenu(false);
            case WARN_FRAGMENT:
                mainWarningMenu.selectMenu(false);
            case ABOUT_FRAGMENT:
                mainAboutusMenu.selectMenu(false);
            case RANKING_FRAGMENT:
                mainRankingMenu.selectMenu(false);
                break;
        }

        switch (showFragmentIndex) {
            case DATA_FRAGMENT:
                mainDatabillboardMenu.selectMenu(true);
            case MONITOR_FRAGMENT:
                mainStutasMenu.selectMenu(true);
            case WARN_FRAGMENT:
                mainWarningMenu.selectMenu(true);
            case ABOUT_FRAGMENT:
                mainAboutusMenu.selectMenu(true);
            case RANKING_FRAGMENT:
                mainRankingMenu.selectMenu(true);
                break;
        }
    }

    private void showSelectFragment(Fragment lastFragment, Fragment nowFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (lastFragment.isAdded()) {
            ft.hide(lastFragment);
        }

        if (!nowFragment.isAdded()) {
            ft.add(R.id.layout_content,nowFragment);
            ft.show(nowFragment);
        }else  {
            ft.show(nowFragment);
        }
        ft.commitAllowingStateLoss();
    }
}
