package com.danish.animationdemo;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button button;

//    @BindView(R.id.textView)
//    TextView textView;
//    @BindView(R.id.listView)
//    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.filename);
//        textView.startAnimation(animation);


//        ArrayList<String> datas = new ArrayList<>();
//        for (int i= 0;i<30;i++) {
//            datas.add("name "+ i);
//        }
//        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this,R.layout.item_list);
//        listView.setAdapter(arrayAdapter);

//        Animation listAnim = AnimationUtils.loadAnimation(this,R.anim.anim_item);
//        LayoutAnimationController controller = new LayoutAnimationController(listAnim);
//        controller.setDelay(0.5f);
//        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        listView.setLayoutAnimation(controller);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAnimate();
            }
        });

    }


    private void performAnimate() {
        ViewWrapper wrapper = new ViewWrapper(button);
        ObjectAnimator.ofInt(wrapper,"width",500).setDuration(5000).start();
    }

    private static class ViewWrapper {
        private View mTarget;
        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

    }
}
