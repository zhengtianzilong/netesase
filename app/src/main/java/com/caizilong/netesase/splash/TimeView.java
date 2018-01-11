package com.caizilong.netesase.splash;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Printer;
import android.view.MotionEvent;
import android.view.View;

import com.caizilong.netesase.R;

/**
 * Created by caizilong on 2018/1/11.
 */

public class TimeView extends View{

    private TextPaint mTextPaint;

    private String content = "跳过";
    // 文字的间距
    private int padding = 5;

    // 内直径
    private int inner;

    // 外圈的直径
    private int all;

    private Paint circleP;
    private Paint outPaint;
    private RectF outerRect;

    // 外圈的角度
    public int degree;

    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeView);

        int innerColor = typedArray.getColor(R.styleable.TimeView_innerColor, Color.BLACK);
        int outerColor = typedArray.getColor(R.styleable.TimeView_ringColor, Color.BLACK);


        mTextPaint = new TextPaint();

        // 抗锯齿
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(22);
        mTextPaint.setColor(Color.WHITE);

        float text_width = mTextPaint.measureText(content);

        // 内圆圈的直径
        inner = (int)text_width + 2 * padding;
        // 外圆圈的直径
        all = inner + 2 * padding;

        // 画笔
        circleP = new Paint();
        circleP.setFlags(Paint.ANTI_ALIAS_FLAG);
        circleP.setColor(innerColor);


        outPaint = new Paint();
        outPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        outPaint.setColor(outerColor);
        // 是空心画笔
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeWidth(padding);

        outerRect = new RectF(padding/2, padding/ 2, all - padding/2, all - padding/2);

        // 使用完成之后,一定要回收
        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 文字的宽度 + 内圆的边距 * 2 + 2 * 画笔的宽度 * 2
        setMeasuredDimension(all, all);

    }

    public  void  setD(int d){
        this.degree = d;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLUE);

        canvas.drawCircle(all/2, all/2, inner / 2, circleP);

        canvas.save();
        canvas.rotate(-90, all/2, all / 2);
        canvas.drawArc(outerRect,0, degree, false, outPaint);

        canvas.restore();


        float de = mTextPaint.descent();

        float a = mTextPaint.ascent();

        float y = canvas.getHeight() / 2;

        canvas.drawText(content, 2 * padding,y - ((de + a)/2), mTextPaint);

    }

    public void setProgress(int total, int now){
        int space = 360 / total;

        degree = space * now;

        invalidate();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }
}
