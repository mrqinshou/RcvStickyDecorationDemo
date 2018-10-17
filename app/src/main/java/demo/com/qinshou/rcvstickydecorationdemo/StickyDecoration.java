package demo.com.qinshou.rcvstickydecorationdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

/**
 * Description:RecyclerView 粘性头部效果的 Decoration
 * Created by 禽兽先生
 * Created on 2018/9/12
 */

public abstract class StickyDecoration extends RecyclerView.ItemDecoration {
    private int mHeight;
    private Paint mPaint;
    private TextPaint mTextPaint;
    private Rect mTextBounds;
    private int mSpanCount;

    public StickyDecoration() {
        this(1);
    }

    public StickyDecoration(int spanCount) {
        mHeight = 100;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.parseColor("#FF000000"));
        mTextPaint.setTextSize(48f);
        mTextBounds = new Rect();

        mSpanCount = spanCount;
    }

    /**
     * Description:在 Canvas 上绘制内容作为 RecyclerView 的 Item 的装饰，会在 Item 绘制之前绘制
     * 也就是说，如果该 Decoration 没有设置偏移的话，Item 的内容会覆盖该 Decoration。
     * Date:2018/9/14
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    /**
     * Description:在 Canvas 上绘制内容作为 RecyclerView 的 Item 的装饰，会在 Item 绘制之后绘制
     * 也就是说，如果该 Decoration 没有设置偏移的话，该 Decoration 会覆盖 Item 的内容。
     * Date:2018/9/14
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        String previousStickyHeaderName = null;
        String currentStickyHeaderName = null;
        int left = parent.getLeft();
        //Decoration 的右边位置
        int right = parent.getRight();
        //获取 RecyclerView 的 Item 数量
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            //判断上一个 position 粘性头部的文字与当前 position 的粘性头部文字是否相同，如果相同则跳过绘制
            int position = parent.getChildAdapterPosition(childView);
            currentStickyHeaderName = getStickyHeaderName(position);
            if (TextUtils.isEmpty(currentStickyHeaderName)) {
                continue;
            }
            if (position < mSpanCount || i < mSpanCount) {
                //Decoration 的底边位置
                int bottom = Math.max(childView.getTop(), mHeight);
                //当当前 Decoration 的 Bottom 比下一个 View 的 Decoration 的 Top （即下一个 View 的 getTop() - mHeight）大时
                //就应该使当前 Decoration 的 Bottom 等于下一个 Decoration 的 Top，形成推动效果
                View nextChildView = parent.getChildAt(i + mSpanCount);
                String nextStickyHeaderName = getStickyHeaderName(position + mSpanCount);
                if (nextChildView != null && !TextUtils.equals(currentStickyHeaderName, nextStickyHeaderName) && bottom > (nextChildView.getTop() - mHeight)) {
                    bottom = nextChildView.getTop() - mHeight;
                }
                //Decoration 的顶边位置
                int top = bottom - mHeight;
                c.drawRect(left, top, right, bottom, mPaint);
                //绘制文字
                mTextPaint.getTextBounds(currentStickyHeaderName, 0, currentStickyHeaderName.length(), mTextBounds);
                c.drawText(currentStickyHeaderName, left, bottom - mHeight / 2 + mTextBounds.height() / 2, mTextPaint);
                continue;
            }
            previousStickyHeaderName = getStickyHeaderName(position - mSpanCount);
            if (!TextUtils.equals(previousStickyHeaderName, currentStickyHeaderName)) {
                //Decoration 的底边位置
                int bottom = Math.max(childView.getTop(), mHeight);
                //当当前 Decoration 的 Bottom 比下一个 View 的 Decoration 的 Top （即下一个 View 的 getTop() - mHeight）大时
                //就应该使当前 Decoration 的 Bottom 等于下一个 Decoration 的 Top，形成推动效果
                View nextChildView = parent.getChildAt(i + mSpanCount);
                String nextStickyHeaderName = getStickyHeaderName(position + mSpanCount);
                if (nextChildView != null && !TextUtils.equals(currentStickyHeaderName, nextStickyHeaderName) && bottom > (nextChildView.getTop() - mHeight)) {
                    bottom = nextChildView.getTop() - mHeight;
                }
                //Decoration 的顶边位置
                int top = bottom - mHeight;
                c.drawRect(left, top, right, bottom, mPaint);
                //绘制文字
                mTextPaint.getTextBounds(currentStickyHeaderName, 0, currentStickyHeaderName.length(), mTextBounds);
                c.drawText(currentStickyHeaderName, left, bottom - mHeight / 2 + mTextBounds.height() / 2, mTextPaint);
            }
        }
    }

    /**
     * Description:为 Decoration 设置偏移
     * Date:2018/9/14
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //outRect 相当于 Item 的整体绘制区域,设置 left、top、right、bottom 相当于设置左上右下的内间距
        //如设置 outRect.top = 5 则相当于设置 paddingTop 为 5px。
        int position = parent.getChildAdapterPosition(view);
        String stickyHeaderName = getStickyHeaderName(position);
        if (TextUtils.isEmpty(stickyHeaderName)) {
            return;
        }
        if (position < mSpanCount) {
            outRect.top = mHeight;
            return;
        }
        String previousStickyHeaderName = getStickyHeaderName(position - mSpanCount);
        if (!TextUtils.equals(stickyHeaderName, previousStickyHeaderName)) {
            outRect.top = mHeight;
        }
    }

    /**
     * author：MrQinshou
     * Description:提供给外部设置每一个 position 的粘性头部的文字的方法
     * date:2018/10/14 22:14
     * param
     * return
     */
    public abstract String getStickyHeaderName(int position);
}
