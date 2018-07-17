package com.danish.attendance.popWindow;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.danish.attendance.AttendanceApplication;
import com.danish.attendance.R;
import com.danish.attendance.activity.DriverLIstActivity;
import com.danish.attendance.bean.DriverListBean;
import com.danish.attendance.utils.MyLogger;

/**
 * Created by danish on 2018/6/12.
 */

public class RegisterFacePopWindow extends PopupWindow {



//    private Context mContext;
    private DriverLIstActivity listActivity;
    private View view;
    private EditText workNo,name,trainNo;
    private Button closeBtn,saveBtn,photoBtn;
    private ImageView headerImg;
    private DriverListBean.ResultBean.ResultListBean mResultListBean;

    public interface FacePopUpdateDriverInfo {
        public void updateFaceInfo(DriverListBean.ResultBean.ResultListBean listBean);
    }

    private FacePopUpdateDriverInfo facePopUpdateDriverInfo;

    public void setFacePopUpdateDriverInfo(FacePopUpdateDriverInfo facePopUpdateDriverInfo) {
        this.facePopUpdateDriverInfo = facePopUpdateDriverInfo;
    }

    public RegisterFacePopWindow(View contentView, int width, int height, boolean focusable, DriverLIstActivity driverLIstActivity) {
        super(contentView, width, height, focusable);
        view = contentView;
        this.listActivity = driverLIstActivity;
        initPopWindow();
    }

    private void initPopWindow () {

        workNo = view.findViewById(R.id.workNo);
        name = view.findViewById(R.id.name);
        trainNo = view.findViewById(R.id.trainNo);

        closeBtn = view.findViewById(R.id.closeBtn);
        saveBtn = view.findViewById(R.id.saveBtn);
        photoBtn = view.findViewById(R.id.photoBtn);
        headerImg = view.findViewById(R.id.headerImg);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString();
                String workNoStr = workNo.getText().toString();
                if (nameStr.length() > 0 && workNoStr.length() > 0 ){
                    mResultListBean.setName(nameStr);
                    mResultListBean.setWorkNo(workNoStr);
                    String trainNoStr = trainNo.getText().toString();
                    if (trainNoStr.length() > 0) {
                        mResultListBean.setPlateNo(trainNoStr);
                    }
                    facePopUpdateDriverInfo.updateFaceInfo(mResultListBean);

//                    dismiss();
                } else {
                    Toast.makeText(listActivity,"请填写姓名或工号",Toast.LENGTH_SHORT).show();
                    MyLogger.i("nameStr-->" + nameStr + "workNo-->"+workNo);
                }

            }
        });

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                ContentValues values = new ContentValues(1);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = listActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                ((AttendanceApplication) (listActivity.getApplicationContext())).setCaptureImage(uri);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                listActivity.startActivityForResult(intent, 1);
            }
        });

        //1.构造一个PopupWindow，参数依次是加载的View，宽高
//        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT,true);
        //这些为了点击非PopupWindow区域，PopupWindow会消失的
        this.setTouchable(true);
        this.setFocusable(true);

        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        this.setAnimationStyle(R.style.ContextPopupAnim);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    public void showPopupWindow () {
        this.showAtLocation(view, Gravity.CENTER,0,0);
    }

    public void dismissPopupWindow () {
        this.dismiss();
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha( float bgAlpha)
    {
//        Activity context = (Activity)mContext;
        WindowManager.LayoutParams lp = listActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        listActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        listActivity.getWindow().setAttributes(lp);
    }

    public void setDriverInfoData (DriverListBean.ResultBean.ResultListBean listBean) {

        mResultListBean = listBean;
        workNo.setText(listBean.getWorkNo());
        name.setText(listBean.getName());
        trainNo.setText(listBean.getPlateNo());

        workNo.setSelection(listBean.getWorkNo().length());
        name.setSelection(listBean.getName().length());
        trainNo.setSelection(listBean.getPlateNo().length());
    }

    public void setFaceImg (Bitmap faceBitmap) {
        if (faceBitmap != null) {
            headerImg.setImageBitmap(faceBitmap);
            mResultListBean.setFaceFeatureExist(true);
        } else {
            headerImg.setImageResource(R.mipmap.header_img);
        }

    }


}
