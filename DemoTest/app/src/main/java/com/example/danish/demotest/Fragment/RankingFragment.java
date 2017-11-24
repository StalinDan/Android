package com.example.danish.demotest.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danish.demotest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by danish on 2017/11/17.
 */

public class RankingFragment extends BaseFragment {


    @BindView(R.id.topBar_leftImg)
    ImageView topBarLeftImg;
    @BindView(R.id.topBar_title)
    TextView topBarTitle;
    @BindView(R.id.topBar_rightImg)
    ImageView topBarRightImg;
    Unbinder unbinder;
    @BindView(R.id.sendBtn)
    Button sendBtn;

    private Activity activity;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, null);
        unbinder = ButterKnife.bind(this, view);

        activity = getActivity();
        mContext = activity.getApplicationContext();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("com.example.danish.demotest.ChangeMonitorTitle");
                activity.sendBroadcast(intent);
            }
        });

        if (activity == null) {
            Log.d("RankingFragment", "为空");
        }
        {
            Log.d("RankingFragment", "不为空");
        }

        topBarLeftImg.setVisibility(View.VISIBLE);
        topBarRightImg.setVisibility(View.VISIBLE);

        topBarLeftImg.setImageResource(R.mipmap.call);
        topBarLeftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RankingFragment", "拨打电话");

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.CALL_PHONE
                    }, 1);
                } else {
                    call();
                }


                //拨号
//                Intent intent =new Intent();
//                intent.setAction(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:10086"));
//                startActivity(intent);


            }
        });

        topBarRightImg.setImageResource(R.mipmap.call);
        topBarRightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发短信
                Log.d("RankingFragment", "发短信");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + 10086));
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void call() {
        try {
            //拨打电话
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel" + 10086));
            activity.startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Toast.makeText(mContext, "你拒绝了权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
