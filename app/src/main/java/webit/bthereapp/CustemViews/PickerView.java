package webit.bthereapp.CustemViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 04/07/2016.
 */
public class PickerView extends View {
    public static final String TAG = "PickerView";
    /**
     * text之间间距和minTextSize之比
     */
    public static final float MARGIN_ALPHA = 2.8f;
    /**
     * 自动回滚到中间的速度
     */
    public static final float SPEED = 2;

    private List<String> mDataList;
    /**
     * 选中的位置，这个位置是mDataList的中心位置，一直不变
     */
    private int mCurrentSelected;
    private Paint mPaint;

    private float mMaxTextSize = 80;
    private float mMinTextSize = 40;

    private float mMaxTextAlpha = 255;
    private float mMinTextAlpha = 120;

    private int mColorText = 0xffffff;

    private int mViewHeight;
    private int mViewWidth;

    private float mLastDownY;
    /**
     * 滑动的距离
     */
    private float mMoveLen = 0;
    private boolean isInit = false;
    private onSelectListener mSelectListener;
    private Timer timer, timerMove;
    private MyTimerTask mTask, move;
    long startTime, difference;
    int max = 0;
    int direct = 0;

    Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (Math.abs(mMoveLen) < SPEED) {
                mMoveLen = 0;
                if (mTask != null) {
                    mTask.cancel();
                    mTask = null;
                    performSelect();
                }
            } else
                // 这里mMoveLen / Math.abs(mMoveLen)是为了保有mMoveLen的正负号，以实现上滚或下滚
                mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * SPEED;
            invalidate();
        }

    };


    Handler updateHandlerMove = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (max > 200/*Math.abs(mLastDownY) < (Math.abs(f) + 15)*/) {
//                Log.d("uuu", "mLastDownY= " + mLastDownY);
                if (move != null) {
//                    Log.d("uuu", "move != null");
                    move.cancel();
                    move = null;
                    mTask = new MyTimerTask(updateHandler);
                    timer.schedule(mTask, 0, 10);
                }
            } else {
                Log.d("uuu", "doMove");
                max += 3;
                doMove((float) (mLastDownY + direct));
            }
        }
    };

    public PickerView(Context context) {
        super(context);
        init();
    }

    public PickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnSelectListener(onSelectListener listener) {
        mSelectListener = listener;
    }

    public void performSelect() {
        if (mSelectListener != null)
            mSelectListener.onSelect(mDataList.get(mCurrentSelected));
    }

    public void setData(List<String> datas) {
        mDataList = datas;
        mCurrentSelected = datas.size() / 2;
        invalidate();
    }

    public void setSelected(String selected) {
        mCurrentSelected = mDataList.indexOf(selected);
//        mCurrentSelected = selected;
        if (isInit && mCurrentSelected != -1) {
            mDataList.get(mCurrentSelected);
            invalidate();
        }
    }

    public void setSelectedtop(String selected, boolean isfrom) {
        if (isfrom)
            mCurrentSelected = ((mDataList.indexOf(selected)) + 1);
        else
            mCurrentSelected = ((mDataList.indexOf(selected)) - 1);
//        mCurrentSelected = selected;
        if (mCurrentSelected == -1) {
            mCurrentSelected = 47;
        }
        if (isInit && mCurrentSelected != -1 && mCurrentSelected != 48) {
            mDataList.get(mCurrentSelected);
            invalidate();
        }
    }

    private void moveHeadToTail() {
        String head = mDataList.get(0);
        mDataList.remove(0);
        mDataList.add(head);
        direct = -5;
    }

    private void moveTailToHead() {
        String tail = mDataList.get(mDataList.size() - 1);
        mDataList.remove(mDataList.size() - 1);
        mDataList.add(0, tail);
        direct = 5;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
        // 按照View的高度计算字体大小
        mMaxTextSize = mViewHeight / 4.0f;
        mMinTextSize = mMaxTextSize / 2f;
        isInit = true;
        invalidate();
    }

    private void init() {
        timer = new Timer();
        timerMove = new Timer();
        mDataList = new ArrayList<String>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(mColorText);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 根据index绘制view
        if (isInit)
            drawData(canvas);
    }

    private void drawData(Canvas canvas) {
        // 先绘制选中的text再往上往下绘制其余的text
        float scale = parabola(mViewHeight / 4.0f, mMoveLen);
        float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
        mPaint.setTextSize(size);
        mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        // text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
        float x = (float) (mViewWidth / 2.0);
        float y = (float) (mViewHeight / 2.0 + mMoveLen);
        Paint.FontMetricsInt fmi = mPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
        if (mCurrentSelected == 47/*48*/) {
            mCurrentSelected = 46;
        }
        if (mCurrentSelected == 0/*-1*/) {
            mCurrentSelected = 1;
        }
        canvas.drawText(mDataList.get(mCurrentSelected), x, baseline, mPaint);
        // 绘制上方data
        for (int i = 1; (mCurrentSelected - i) >= 0; i++) {
            drawOtherText(canvas, i, -1);
//            Log.d("lll","============---===============");
//            Log.d("lll","mCurrentSelected " +mCurrentSelected);
//            Log.d("lll","i " +i);
        }

        // 绘制下方data
        for (int i = 1; (mCurrentSelected + i) < mDataList.size(); i++) {
            drawOtherText(canvas, i, 1);
//            Log.d("lll","==============+++=============");
//            Log.d("lll","mCurrentSelected " +mCurrentSelected);
//            Log.d("lll","i " +i);
        }
//        if (mCurrentSelected == 0) {
//            drawOtherText(canvas, 1, 1);
//        }
//        if (mCurrentSelected == 47) {
//            drawOtherText(canvas, 1, -1);
//        }
    }

    /**
     * @param canvas
     * @param position 距离mCurrentSelected的差值
     * @param type     1表示向下绘制，-1表示向上绘制
     */
    private void drawOtherText(Canvas canvas, int position, int type) {
        float d = (float) (MARGIN_ALPHA * mMinTextSize * position + type * mMoveLen);
        float scale = parabola(mViewHeight / 4.0f, d);
        float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
        mPaint.setTextSize(size);
        mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        float y = (float) (mViewHeight / 2.0 + type * d);
        Paint.FontMetricsInt fmi = mPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
        canvas.drawText(mDataList.get(mCurrentSelected + type * position),
                (float) (mViewWidth / 2.0), baseline, mPaint);
    }

    /**
     * 抛物线
     *
     * @param zero 零点坐标
     * @param x    偏移量
     * @return scale
     */
    private float parabola(float zero, float x) {
        float f = (float) (1 - Math.pow(x / zero, 2));
        return f < 0 ? 0 : f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                doDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                doMove(event.getY());
                break;
            case MotionEvent.ACTION_UP:
                doUp(event);
                break;
        }
        return true;
    }

    private void doDown(MotionEvent event) {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        if (move != null) {
            move.cancel();
            move = null;
        }

        startTime = System.currentTimeMillis();
        mLastDownY = event.getY();
    }

    private void doMove(float event) {

        mMoveLen += (event - mLastDownY);

        if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2) {
            // 往下滑超过离开距离
            moveTailToHead();
            mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;
        } else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2) {
            // 往上滑超过离开距离
            moveHeadToTail();
            mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
        }
        mLastDownY = event;
        invalidate();
    }

//    private void doUp(MotionEvent event) {
//        // 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
//        if (Math.abs(mMoveLen) < 0.0001) {
//            mMoveLen = 0;
//            return;
//        }
//        if (mTask != null) {
//            mTask.cancel();
//            mTask = null;
//        }
//        mTask = new MyTimerTask(updateHandler);
//        timer.schedule(mTask, 0, 10);
//    }


    private void doUp(MotionEvent event) {
        // 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
        if (Math.abs(mMoveLen) < 0.0001) {
            mMoveLen = 0;
            return;
        }
        max = 0;
//        if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2) {
//            direct = -10;
//        } else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2) {
//            direct = 10;
//        }
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }

        if (move != null) {
            move.cancel();
            move = null;
        }
//        f = event.getY();
        difference = System.currentTimeMillis() - startTime;
        float speed = (int) (Math.abs(mMoveLen) / difference);
        Log.d("eeee", direct + "");
        Log.d("eee", "difference " + difference);
        Log.d("eee", "speed " + speed);
        Log.d("eee", "mMoveLen " + mMoveLen);
        Log.d("eee", "Math.abs(mMoveLen) " + Math.abs(mMoveLen));
        if (Math.abs(mMoveLen) > 5 && difference < 200) {
            move = new MyTimerTask(updateHandlerMove);
            timerMove.schedule(move, 0, 10);
        } else {
            mTask = new MyTimerTask(updateHandler);
            timer.schedule(mTask, 0, 10);
        }
    }

    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }

    }

    public interface onSelectListener {
        void onSelect(String text);
    }
}
