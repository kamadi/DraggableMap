package me.kamadi.draggablemap.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Madiyar on 29.01.2015.
 */
public class MapWrapperLayout extends FrameLayout {

    private Bitmap currentImage;
    private Canvas mCanvas;
    private OnDragListener mOnDragListener;
    private OnTouchListener mTouchListener;
    private View mView;
    private Paint paint;

    public MapWrapperLayout(Context context) {
        super(context);
    }

    public boolean dispatchTouchEvent(MotionEvent motionevent) {
        boolean flag = super.dispatchTouchEvent(motionevent);
        if (mOnDragListener != null) {
            mOnDragListener.onDrag(motionevent);
        }
        if (mTouchListener != null) {
            mTouchListener.onTouch(mView, motionevent);
        }
        return flag;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mCanvas == null || mCanvas != canvas) {
            mCanvas = canvas;
        }
        if (currentImage != null) {
            canvas.drawBitmap(currentImage, (canvas.getWidth() - currentImage.getWidth()) / 2,
                    (canvas.getHeight() - 2 * currentImage.getHeight()) / 2, paint);
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        if (mCanvas == null || mCanvas != canvas) {
            mCanvas = canvas;
        }
        if (mView == null || mView != view) {
            mView = view;
        }
        boolean flag = super.drawChild(canvas, view, l);
        if (currentImage != null) {
            canvas.drawBitmap(currentImage, (canvas.getWidth() - currentImage.getWidth()) / 2,
                    (canvas.getHeight() - 2 * currentImage.getHeight()) / 2, paint);
        }
        return flag;
    }

    public Bitmap getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Bitmap bitmap) {
        currentImage = bitmap;
    }

    public void setOnDragListener(OnDragListener ondraglistener) {
        mOnDragListener = ondraglistener;
    }

    public void setTouchListener(OnTouchListener ontouchlistener) {
        mTouchListener = ontouchlistener;
    }

    public View getView() {
        return mView;
    }

    public interface OnDragListener {

        void onDrag(MotionEvent motionevent);
    }

    public interface OnTouchListener {

        void onTouch(View view, MotionEvent motionevent);
    }
}
