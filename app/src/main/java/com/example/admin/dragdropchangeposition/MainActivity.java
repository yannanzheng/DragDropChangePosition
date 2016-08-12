package com.example.admin.dragdropchangeposition;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView image_top;
    private ImageView image_bottom;
    private String topTag = "top";
    private String bottomTag = "bottomTag";
    private RelativeLayout container;
    private float image_top_ori_x;
    private float image_top_ori_y;
    private float image_bottom_ori_x;
    private float image_bottom_ori_y;

    private int image_top_ori_width;
    private int image_top_ori_height;
    private int image_bottom_ori_width;
    private int image_bottom_ori_height;
    private RelativeLayout.LayoutParams image_top_params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image_top = (ImageView) findViewById(R.id.image_top);
        image_bottom = (ImageView) findViewById(R.id.image_bottom);
        container = (RelativeLayout) findViewById(R.id.container);


        image_top.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                long[] pattern = {0,100};
                vibrator.vibrate(pattern,1);
                ClipData.Item item = new ClipData.Item(topTag);
                ClipData data = new ClipData(topTag, new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                View view1 = new View(MainActivity.this);
                view.startDrag(data, new View.DragShadowBuilder(view), null, 0);
//                view.startDrag(data, new View.DragShadowBuilder(view), null, 0);
                view.setVisibility(View.INVISIBLE);

                return true;
            }
        });
        image_bottom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                long[] pattern = {0,100};
                vibrator.vibrate(pattern,1);
                ClipData.Item item = new ClipData.Item(bottomTag);
                ClipData data = new ClipData(bottomTag, new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                View view1 = new View(MainActivity.this);
                view.startDrag(data, new View.DragShadowBuilder(view), null, 0);
//                view.startDrag(data, new View.DragShadowBuilder(view), null, 0);
                view.setVisibility(View.INVISIBLE);

                return true;
            }
        });



        container.setOnDragListener(new View.OnDragListener() {

            private RelativeLayout.LayoutParams image_bottom_params;

            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                final int action = dragEvent.getAction();

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
//                        image_top_ori_x = image_top.getX();
//                        image_top_ori_y = image_top.getY();

                        image_top_params = (RelativeLayout.LayoutParams) image_top.getLayoutParams();
                        image_bottom_params = (RelativeLayout.LayoutParams) image_bottom.getLayoutParams();

//                        image_top_ori_width=image_top.getWidth();
//                        image_top_ori_height=image_top.getHeight();
//                        Log.i(TAG,"image_top_ori_width="+image_top_ori_width+"image_top_ori_height"+image_top_ori_height);
//                        image_bottom_ori_width=image_bottom.getWidth();
//                        image_bottom_ori_height=image_bottom.getHeight();
//                        Log.i(TAG,"image_bottom_ori_width="+image_bottom_ori_width+"image_bottom_ori_height"+image_bottom_ori_height);

                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i(TAG, "进来了ACTION_DRAG_ENTERED");
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:

//                        ClipData.Item item = dragEvent.getClipData().getItemAt(0);
//                        String dragData = item.getText().toString();
                        Rect rectZone = new Rect();
                        Log.i(TAG, "view id=" + view.getId());
                        Log.i(TAG, "image_top =" + image_top.getId());
//                        view.getId()==image_top.getId()
                        if (dragEvent.getClipDescription().getLabel().equals(topTag)) {
                            //拖动的是顶部的按钮
                            Log.i(TAG, "进来了 R.id.image_top");
                            image_bottom.getHitRect(rectZone);
                            if (rectZone.contains((int) dragEvent.getX(), (int) dragEvent.getY())) {
//                                Log.i(TAG, "进来了 ACTION_DROP");
//                                image_bottom_ori_x = image_bottom.getX();
//                                image_bottom_ori_y = image_bottom.getY();
//                                Log.i(TAG, image_top_params.toString());
//                                Log.i(TAG, image_bottom_params.toString());
//                            image_top_params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//                            image_top_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                            image_bottom_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                            image_bottom_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                image_top.setLayoutParams(image_bottom_params);
//                            image_top.setX(image_bottom_ori_x);
//                            image_top.setY(image_bottom_ori_y);
                                image_bottom.setLayoutParams(image_top_params);
//                            image_bottom.setX(image_top_ori_x);
//                            image_bottom.setY(image_top_ori_y);
                                container.invalidate();

                            }

                        }else if (dragEvent.getClipDescription().getLabel().equals(bottomTag)) {
                            Log.i(TAG, "进来了 R.id.image_bottom");
                            image_top.getHitRect(rectZone);
                            if (rectZone.contains((int) dragEvent.getX(), (int) dragEvent.getY())) {
                                image_bottom.setLayoutParams(image_top_params);
                                image_top.setLayoutParams(image_bottom_params);
                                container.invalidate();

                            }

                        }


                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        image_top.setVisibility(View.VISIBLE);
                        image_bottom.setVisibility(View.VISIBLE);
                        return true;
                    default:
                        break;


                }


                return false;

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}
