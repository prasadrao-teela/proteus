package com.flipkart.android.proteus.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ProgressIndicatorView extends View {

    private static final float DEFAULT_TEXT_SIZE = 12;   //SP
    private static final float DEFAULT_BORDER_STROKE = 1.5f;  //DP
    private static final float DEFAULT_STEP_CORNER_RADIUS = 8;    //dp
    private static final float DEFAULT_STEP_WIDTH = 40; //dp
    private static final float DEFAULT_STEP_HEIGHT = 20; //dp
    private static final float DEFAULT_LINE_WIDTH = 18; //dp
    public static final float DEFAULT_LINE_STROKE = 1.5f;

    private static final int DEFAULT_PENDING_STEP_TEXT_COLOR = Color.parseColor("#c8cde3");
    private static final int DEFAULT_CURRENT_STEP_TEXT_COLOR = Color.parseColor("#4d6cd9");
    private static final int DEFAULT_COMPLETED_STEP_TEXT_COLOR = Color.parseColor("#4d6cd9");

    private static final int DEFAULT_PENDING_STEP_BACKGROUND_COLOR = Color.WHITE;
    private static final int DEFAULT_CURRENT_STEP_BACKGROUND_COLOR = Color.parseColor("#264d6cd9");
    private static final int DEFAULT_COMPLETED_STEP_BACKGROUND_COLOR = Color.parseColor("#264d6cd9");

    private static final int DEFAULT_PENDING_STEP_BORDER_COLOR = Color.parseColor("#c8cde3");
    private static final int DEFAULT_CURRENT_STEP_BORDER_COLOR = Color.parseColor("#4d6cd9");
    private static final int DEFAULT_COMPLETED_STEP_BORDER_COLOR = Color.parseColor("#4d6cd9");


    private static final int DEFAULT_CURRENT_STEP = 0;
    private String[] entries = new String[]{"1", "2", "3", "4"};

    private float stepCornerRadius = dpToPx(DEFAULT_STEP_CORNER_RADIUS);
    private float textSize = spToPx(DEFAULT_TEXT_SIZE);
    private float borderStroke = dpToPx(DEFAULT_BORDER_STROKE);
    private float stepWidth = dpToPx(DEFAULT_STEP_WIDTH);     //DP
    private float currentStepExtraWidth =  0; //DP
    private float stepHeight = dpToPx(DEFAULT_STEP_HEIGHT);
    private float lineWidth = dpToPx(DEFAULT_LINE_WIDTH);
    private float lineStroke = dpToPx(DEFAULT_LINE_STROKE);
    private int currentStepPosition = DEFAULT_CURRENT_STEP;
    private int stepsCount = entries.length;
    private int pendingStepBackgroundColor = DEFAULT_PENDING_STEP_BACKGROUND_COLOR;
    private int currentStepBackgroundColor = DEFAULT_CURRENT_STEP_BACKGROUND_COLOR;
    private int completedStepBackgroundColor = DEFAULT_COMPLETED_STEP_BACKGROUND_COLOR;
    private int pendingStepTextColor = DEFAULT_PENDING_STEP_TEXT_COLOR;
    private int currentStepTextColor = DEFAULT_CURRENT_STEP_TEXT_COLOR;
    private int completedStepTextColor = DEFAULT_COMPLETED_STEP_TEXT_COLOR;
    private int pendingStepBorderColor = DEFAULT_PENDING_STEP_BORDER_COLOR;
    private int currentStepBorderColor = DEFAULT_CURRENT_STEP_BORDER_COLOR;
    private int completedStepBorderColor = DEFAULT_COMPLETED_STEP_BORDER_COLOR;
    private float centerY;
    private float startX;

    private Paint paint;
    private Paint paintStoke;
    private Paint paintText;
    private final Rect textBounds = new Rect();

    private boolean clickable = true;

    public ProgressIndicatorView(Context context) {
        super(context);
        initialize();
    }

    public ProgressIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ProgressIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        paint = new Paint();
        paintStoke = new Paint();
        paintText = new Paint();
        paint.setColor(pendingStepTextColor);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(lineStroke);
        paintStoke.setColor(pendingStepTextColor);
        paintStoke.setStrokeWidth(borderStroke);
        paintStoke.setStyle(Paint.Style.STROKE);
        paintStoke.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(pendingStepTextColor);
        paintText.setTextSize(textSize);
        paintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setFlags(Paint.ANTI_ALIAS_FLAG);
        invalidate();
    }

    public void setStepWidth(float stepWidth){
        this.stepWidth = stepWidth;
        invalidate();
    }

    public void setCurrentStepExtraWidth(float currentStepExtraWidth){
        this.currentStepExtraWidth = currentStepExtraWidth;
        invalidate();
    }

    public void setStepHeight(float stepHeight){
        this.stepHeight = stepHeight;
        invalidate();
    }

    public void setLineWidth(float lineWidth){
        this.lineWidth = lineWidth;
    }

    public void setPendingStepTextColor(int pendingStepTextColor) {
        this.pendingStepTextColor = pendingStepTextColor;
        invalidate();
    }

    public void setPendingStepBackgroundColor(int pendingStepBackgroundColor) {
        this.pendingStepBackgroundColor = pendingStepBackgroundColor;
        invalidate();
    }

    public void setPendingStepBorderColor(int pendingStepBorderColor) {
        this.pendingStepBorderColor = pendingStepBorderColor;
        invalidate();
    }

    public void setCurrentStepTextColor(int currentStepTextColor) {
        this.currentStepTextColor = currentStepTextColor;
        invalidate();
    }

    public void setCurrentStepBackgroundColor(int currentStepBackgroundColor) {
        this.currentStepBackgroundColor = currentStepBackgroundColor;
        invalidate();
    }

    public void setCurrentStepBorderColor(int currentStepBorderColor) {
        this.currentStepBorderColor = currentStepBorderColor;
        invalidate();
    }

    public void setCompletedStepTextColor(int completedStepTextColor) {
        this.completedStepTextColor = completedStepTextColor;
        invalidate();
    }

    public void setCompletedStepBackgroundColor(int completedStepBackgroundColor) {
        this.completedStepBackgroundColor = completedStepBackgroundColor;
        invalidate();
    }

    public void setCompletedStepBorderColor(int completedStepBorderColor){
        this.completedStepBorderColor = completedStepBorderColor;
        invalidate();
    }

    public void setCurrentStepPosition(int currentStepPosition) {
        this.currentStepPosition = currentStepPosition;
        invalidate();
    }

    public void setBorderStroke(float borderStroke) {
        this.borderStroke = borderStroke;
        paintStoke.setStrokeWidth(borderStroke);
        invalidate();
    }

    public void setLineStroke(float lineStroke) {
        this.lineStroke = lineStroke;
        paint.setStrokeWidth(lineStroke);
        invalidate();
    }

    public void setStepCornerRadius(float stepCornerRadius) {
        this.stepCornerRadius = stepCornerRadius;
        invalidate();
    }

    public void setTextSize(float textSize){
        this.textSize = textSize;
        paintText.setTextSize(textSize);
        invalidate();
    }

    public void setTextFont(Typeface font) {
        this.paintText.setTypeface(font);
        invalidate();
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
    }

    @Override
    public boolean isClickable() {
        return clickable;
    }

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }


    public void setEntries(String[] entries) {
        this.entries = entries;
        stepsCount = entries.length;
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (stepsCount <= 1) {
            setVisibility(GONE);
            return;
        }
        super.onDraw(canvas);
        float pointX = startX;

        /* draw Line */
        for (int i = 0; i < stepsCount - 1; i++) {
            pointX = pointX+stepWidth;
            if (i < currentStepPosition) {
                paint.setColor(completedStepTextColor);
                canvas.drawLine(pointX , centerY, pointX + lineWidth, centerY, paint);
            } else if (i == currentStepPosition) {
                paint.setColor(pendingStepTextColor);
                //For current position rectangle has extra width(If we add any)
                pointX = pointX + currentStepExtraWidth;
                canvas.drawLine(pointX , centerY, pointX + lineWidth, centerY, paint);
            } else {
                paint.setColor(pendingStepTextColor);
                canvas.drawLine(pointX , centerY, pointX + lineWidth, centerY, paint);
            }
            pointX = pointX +lineWidth;
        }

        /*draw Rectangle */
        pointX = startX;
        for (int i = 0; i < stepsCount; i++) {
            RectF rectF = new RectF(pointX, borderStroke, pointX + stepWidth, stepHeight-borderStroke);
            if (i < currentStepPosition) {
                //draw previous step
                paint.setColor(completedStepBackgroundColor);
                paintStoke.setColor(completedStepBorderColor);
                paintText.setColor(completedStepTextColor);
            } else if (i == currentStepPosition) {
                //For current position rectangle has extra width(If we add any)
                rectF = new RectF(pointX, borderStroke, pointX + stepWidth + currentStepExtraWidth, stepHeight-borderStroke);
                paint.setColor(currentStepBackgroundColor);
                paintStoke.setColor(currentStepBorderColor);
                paintText.setColor(currentStepTextColor);
            } else {
                //draw next step
                paint.setColor(pendingStepBackgroundColor);
                paintStoke.setColor(pendingStepBorderColor);
                paintText.setColor(pendingStepTextColor);
            }
            //Inner rectangle
            canvas.drawRoundRect(rectF, stepCornerRadius, stepCornerRadius, paint);
            //Outer Rectangle with stroke
            canvas.drawRoundRect(rectF, stepCornerRadius, stepCornerRadius, paintStoke);

            if (i == currentStepPosition) {
                //For current position rectangle has extra width(If we add any)
                drawTextCentred(canvas, paintText, entries[i], pointX + (stepWidth / 2) + (currentStepExtraWidth / 2), centerY);
                pointX = pointX + stepWidth + currentStepExtraWidth + lineWidth;
            } else {
                drawTextCentred(canvas, paintText, entries[i], pointX+stepWidth/2, centerY);
                pointX = pointX + stepWidth + lineWidth;
            }
        }
    }

    private void drawTextCentred(Canvas canvas, Paint paint, String text, float cx, float cy) {
        paint.getTextBounds(text, 0, text.length(), textBounds);
        canvas.drawText(text, cx, cy - textBounds.exactCenterY(), paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        stepHeight = getHeight();
        centerY = stepHeight / 2.0f;
        float actualViewLength = stepsCount * stepWidth + currentStepExtraWidth + (stepsCount - 1) * lineWidth;
        float remainingFreeSpace = getWidth() - actualViewLength;
        startX = remainingFreeSpace / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        stepHeight = getHeight();
        centerY = stepHeight / 2.0f;
        float occupiedLength = stepsCount * stepWidth + currentStepExtraWidth + (stepsCount - 1) * lineWidth;
        float remainingSpace = getWidth() - occupiedLength;
        startX = remainingSpace/2;
        invalidate();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.lineStroke = this.lineStroke;
        ss.borderStroke = this.borderStroke;
        ss.currentStepPosition = this.currentStepPosition;
        ss.stepsCount = this.stepsCount;
        ss.pendingStepTextColor = this.pendingStepTextColor;
        ss.currentStepTextColor = this.currentStepTextColor;
        ss.completedStepTextColor = this.completedStepTextColor;
        ss.pendingStepBackgroundColor = this.pendingStepBackgroundColor;
        ss.currentStepBackgroundColor = this.currentStepBackgroundColor;
        ss.completedStepBackgroundColor = this.completedStepBackgroundColor;
        ss.pendingStepBorderColor = this.pendingStepBorderColor;
        ss.currentStepBorderColor = this.currentStepBorderColor;
        ss.completedStepBorderColor = this.completedStepBorderColor;
        ss.stepWidth = this.stepWidth;
        ss.currentStepExtraWidth = this.currentStepExtraWidth;
        ss.stepHeight = this.stepHeight;
        ss.lineWidth = this.lineWidth;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.lineStroke = ss.lineStroke;
        this.borderStroke = ss.borderStroke;
        this.currentStepPosition = ss.currentStepPosition;
        this.stepsCount = ss.stepsCount;
        this.pendingStepTextColor = ss.pendingStepTextColor;
        this.currentStepTextColor = ss.currentStepTextColor;
        this.completedStepTextColor = ss.completedStepTextColor;
        this.pendingStepBackgroundColor = ss.pendingStepBackgroundColor;
        this.currentStepBackgroundColor = ss.currentStepBackgroundColor;
        this.completedStepBackgroundColor = ss.completedStepBackgroundColor;
        this.pendingStepBorderColor = ss.pendingStepBorderColor;
        this.currentStepBorderColor = ss.currentStepBorderColor;
        this.completedStepBorderColor = ss.completedStepBorderColor;
        this.stepWidth = ss.stepWidth;
        this.currentStepExtraWidth = ss.currentStepExtraWidth;
        this.stepHeight = ss.stepHeight;
        this.lineWidth = ss.lineWidth;

    }

    static class SavedState extends BaseSavedState {
        float lineStroke;
        float borderStroke;
        int currentStepPosition;
        int stepsCount;
        int pendingStepBackgroundColor;
        int currentStepBackgroundColor;
        int completedStepBackgroundColor;

        int pendingStepTextColor;
        int currentStepTextColor;
        int completedStepTextColor;

        int pendingStepBorderColor;
        int currentStepBorderColor;
        int completedStepBorderColor;

        float stepWidth;
        float stepHeight;
        float lineWidth;
        float currentStepExtraWidth;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            lineStroke = in.readFloat();
            borderStroke = in.readFloat();
            currentStepPosition = in.readInt();
            stepsCount = in.readInt();
            pendingStepBackgroundColor = in.readInt();
            currentStepBackgroundColor = in.readInt();
            completedStepBackgroundColor = in.readInt();

            pendingStepTextColor = in.readInt();
            currentStepTextColor = in.readInt();
            completedStepTextColor = in.readInt();

            pendingStepBorderColor = in.readInt();
            currentStepBorderColor = in.readInt();
            completedStepBorderColor = in.readInt();
            stepWidth = in.readFloat();
            stepHeight = in.readFloat();
            lineWidth = in.readFloat();
            currentStepExtraWidth = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeFloat(lineStroke);
            dest.writeFloat(borderStroke);
            dest.writeInt(currentStepPosition);
            dest.writeInt(stepsCount);
            dest.writeInt(pendingStepTextColor);
            dest.writeInt(completedStepBackgroundColor);
            dest.writeInt(pendingStepBackgroundColor);
            dest.writeInt(currentStepBackgroundColor);
            dest.writeInt(completedStepTextColor);
            dest.writeInt(currentStepTextColor);
            dest.writeFloat(stepWidth);
            dest.writeFloat(stepHeight);
            dest.writeFloat(lineWidth);
            dest.writeFloat(currentStepExtraWidth);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
