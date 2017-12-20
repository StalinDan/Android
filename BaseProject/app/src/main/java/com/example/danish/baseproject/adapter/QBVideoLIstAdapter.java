package com.example.danish.baseproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.bean.QBVideoListBean;
import com.example.danish.baseproject.bitmaputils.ImageLoadUtils;

import java.util.List;

/**
 * Created by danish on 2017/12/18.
 */

public class QBVideoLIstAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<QBVideoListBean.ContentBean> mList;
    public QBVideoLIstAdapter(Context context, List list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final QBVideoListBean.ContentBean contentBean = mList.get(position);
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        ImageLoadUtils.loadImageView(mContext,contentBean.getThumbnail(),myViewHolder.videoImg);


        if (contentBean.isPlayed()) {
            myViewHolder.videoImg.setVisibility(View.INVISIBLE);
            myViewHolder.playBtn.setVisibility(View.INVISIBLE);
            myViewHolder.videoView.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.videoImg.setVisibility(View.VISIBLE);
            myViewHolder.playBtn.setVisibility(View.VISIBLE);
            myViewHolder.videoView.setVisibility(View.INVISIBLE);
        }


        myViewHolder.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("QBVideoLIstAdapter","position===>"+position);
//                //   0.  定义好视频的路径
//                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+contentBean.getVideo());
//                //  1.  先设定好Intent
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                //  2.  设置好 Data：播放源，是一个URI
//                //      设置好 Data的Type：类型是 “video/mp4"
//                intent.setDataAndType(uri,"video/*");
//                //  3.  跳转：
//                mContext.startActivity(intent);


                contentBean.setPlayed(true);
                notifyDataSetChanged();
                mList.set(position,contentBean);


                final VideoView videoView = myViewHolder.videoView;

                Uri uri = Uri.parse(contentBean.getVideo());
                //设置视频控制器
                videoView.setMediaController(new MediaController(mContext));
                //播放完成回调
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        contentBean.setPlayed(false);
                        mList.set(position,contentBean);
                        notifyDataSetChanged();
                    }
                });

                //设置视频路径
                videoView.setVideoURI(uri);
                //开始播放视频
                videoView.start();

            }
        });


    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( mContext, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView videoImg;
        private Button playBtn;
        private VideoView videoView;
        public MyViewHolder(View itemView) {
            super(itemView);
            videoImg = itemView.findViewById(R.id.videoImg);
            playBtn = itemView.findViewById(R.id.playBtn);
            videoView = itemView.findViewById(R.id.videoView);
        }
    }
}
