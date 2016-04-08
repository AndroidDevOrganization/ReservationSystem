package com.dbis.reservationsystem.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dbis.reservationsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 课表显示View
 * 
 * @author shallcheek
 *
 */
public class TimeTableView extends LinearLayout {
	/**
	 * 配色数组
	 */
	public static int colors[] = { R.drawable.select_label_san,
			R.drawable.select_label_er, R.drawable.select_label_si,
			R.drawable.select_label_wu, R.drawable.select_label_liu,
			R.drawable.select_label_qi, R.drawable.select_label_ba,
			R.drawable.select_label_jiu, R.drawable.select_label_sss,
			R.drawable.select_label_se, R.drawable.select_label_yiw,
			R.drawable.select_label_sy, R.drawable.select_label_yiwu,
			R.drawable.select_label_yi, R.drawable.select_label_wuw };
	private final static int START = 0;
	/**
	 * 最大节数
	 */
	public final static int MAXNUM = 15;
	/**
	 * 星期
	 */
	public final static int WEEKNUM = 7;
	/**
	 * 单个View 的高度 需要转换成dp
	 */
	private final static int TimeTableHeight = 40;
	/**
	 * 线View高度 或者宽度
	 */
	private final static int TimeTableLineHeight = 2;
	private final static int TimeTableNumWidth = 20;
	private final static int TimeTableWeekNameHeight = 30;
	/**
	 * 横向星期显示
	 */
	private LinearLayout mHorizontalWeekLayout;
	/**
	 * 课表格子
	 */
	private LinearLayout mVerticalWeekLaout;

	private String[] weekname = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
	private String[] timeBlocks = {"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00"};
	public static String[] colorStr = new String[20];
	int colornum = 0;
	/**
	 * 数据类型
	 */
	private List<TimeTableModel> mLlisTimeTable = new ArrayList<TimeTableModel>();

	public TimeTableView(Context context) {
		super(context);
	}

	public TimeTableView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	/**
	 * 返回分界线
	 *
	 *
	 * @return
	 */
	private View getWeekLine() {
		TextView mWeekline = new TextView(getContext());
		mWeekline
				.setBackgroundColor(getResources().getColor(R.color.view_line));
		mWeekline.setHeight(TimeTableLineHeight);
		mWeekline.setWidth(LayoutParams.FILL_PARENT);
		return mWeekline;
	}

	/**
	 * 输入课表名循环判断是否数组存在该课表 如果存在输出true并退出循环 如果不存在则存入colorSt[20]数组
	 * 
	 * @param name
	 * 
	 */
	private void addTimeName(String name) {
		boolean isRepeat = true;
		for (int i = 0; i < 20; i++) {
			if (name.equals(colorStr[i])) {
				isRepeat = true;
				break;
			} else {
				isRepeat = false;
			}
		}
		if (!isRepeat) {
			colorStr[colornum] = name;
			colornum++;
		}

	}

	/**
	 * 获取数组中的课程名
	 * 
	 * @param name
	 * @return
	 */
	public static int getColornum(String name) {
		int num = 0;
		for (int i = 0; i < 20; i++) {
			if (name.equals(colorStr[i])) {
				num = i;
			}
		}
		return num;
	}

	/**
	 * 返回分界线
	 * 
	 * @return
	 */
	private View getStringWeekLine() {
		TextView mWeekline = new TextView(getContext());
		mWeekline
				.setBackgroundColor(getResources().getColor(R.color.view_line));
		mWeekline.setHeight(dip2px(TimeTableWeekNameHeight));
		mWeekline.setWidth((TimeTableLineHeight));
		return mWeekline;
	}

	private void initView() {
		mHorizontalWeekLayout = new LinearLayout(getContext());
		mVerticalWeekLaout = new LinearLayout(getContext());
		mHorizontalWeekLayout.setOrientation(HORIZONTAL);
		mVerticalWeekLaout.setOrientation(HORIZONTAL);
		for (int i = 0; i <= WEEKNUM; i++) {
			if (i == 0) {
				// 初始化靠左20dp
				TextView mTime = new TextView(getContext());
				mTime.setHeight(dip2px(TimeTableWeekNameHeight));
				//。。。
				mTime.setWidth((dip2px(TimeTableNumWidth*2)));
				mHorizontalWeekLayout.addView(mTime);
			} else {
				// 设置显示星期一 到星期天
				LinearLayout mHoriView = new LinearLayout(getContext());
				mHoriView.setOrientation(VERTICAL);
				TextView mWeekName = new TextView(getContext());
				mWeekName.setTextColor(getResources().getColor(
						R.color.text_color));
				//注意修改
				mWeekName
						.setWidth(((getViewWidth() - dip2px(TimeTableNumWidth * 2)))
								/ WEEKNUM);
				mWeekName.setHeight(dip2px(TimeTableWeekNameHeight));
				mWeekName.setGravity(Gravity.CENTER);
				mWeekName.setTextSize(13);//改变首行的字体大小
				mWeekName.setText(weekname[i - 1]);
//				//设置背景色为透明，去掉边框
				mWeekName.setBackgroundColor(new Color().alpha(0));
				mHoriView.addView(mWeekName);
				mHorizontalWeekLayout.addView(mHoriView);
			}
			mHorizontalWeekLayout.addView(getStringWeekLine());
		}
		addView(mHorizontalWeekLayout);

		addView(getWeekLine());
		for (int i = 0; i <= WEEKNUM; i++) {
			switch (i) {
			case 0:
				LinearLayout mMonday = new LinearLayout(getContext());
				//当子容器改变不了大小时，应该注意到是否是其父容器布局上限定了大小
				ViewGroup.LayoutParams mm = new ViewGroup.LayoutParams(
						dip2px(TimeTableNumWidth*2), dip2px(MAXNUM
								* TimeTableHeight)
								+ MAXNUM * 2);
				mMonday.setLayoutParams(mm);
				mMonday.setOrientation(VERTICAL);
				for (int j = 1; j <= MAXNUM; j++) {
					TextView mTime = new TextView(getContext());
					mTime.setGravity(Gravity.CENTER);
					mTime.setTextColor(getResources().getColor(
							R.color.text_color));
					mTime.setHeight(dip2px(TimeTableHeight));
					mTime.setWidth(dip2px(TimeTableNumWidth));
					mTime.setTextSize(14);
					//加入时间轴
					mTime.setText(timeBlocks[j-1]);
					mMonday.addView(mTime);
					mMonday.addView(getWeekLine());
				}
				mMonday.setWeightSum(1);
				mVerticalWeekLaout.addView(mMonday);
				break;
			//注意下面没有写break,所有情况都默认执行case7，由于最后加的是纵向布局，所以是竖着加的
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				List<TimeTableModel> mListMon = new ArrayList<TimeTableModel>();
				for (TimeTableModel timeTableModel : mLlisTimeTable) {
					if (timeTableModel.getWeek() == i) {
						mListMon.add(timeTableModel);
					}
				}
				LinearLayout mLayout = getTimeTableView(mListMon);
				mLayout.setOrientation(VERTICAL);
				//Changed by Wu Chen 2016/3/19 0:02
				ViewGroup.LayoutParams linearParams = new ViewGroup.LayoutParams(
						(getViewWidth() - dip2px(TimeTableNumWidth*2)) / WEEKNUM,
						LayoutParams.FILL_PARENT);
				mLayout.setLayoutParams(linearParams);
				mLayout.setWeightSum(1);
				mVerticalWeekLaout.addView(mLayout);
				break;

			default:
				break;
			}
			TextView l = new TextView(getContext());
			l.setHeight(dip2px(TimeTableHeight * MAXNUM) + MAXNUM * 2);
			l.setWidth(2);
			//去掉边界线
			l.setBackgroundColor(getResources().getColor(R.color.view_line));
			mVerticalWeekLaout.addView(l);
		}
		addView(mVerticalWeekLaout);
		//去掉边界线
		addView(getWeekLine());
	}

	private int getViewWidth() {
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}

	private View addStartView(int startnum) {
		Log.v("Start", startnum + "");
		LinearLayout mStartView = new LinearLayout(getContext());
		mStartView.setOrientation(VERTICAL);
		//本身已经弃用
//		 ViewGroup.LayoutParams linearParams = new ViewGroup.LayoutParams(
//		 dip2px(40), dip2px(100));

		for (int i = 1; i < startnum; i++) {
			TextView mTime = new TextView(getContext());
			mTime.setGravity(Gravity.CENTER);
			mTime.setHeight(dip2px(TimeTableHeight));
			mTime.setWidth(dip2px(TimeTableHeight));
			mStartView.addView(mTime);
			//去掉边界线
			mStartView.addView(getWeekLine());
		}
		return mStartView;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	private LinearLayout getTimeTableView(List<TimeTableModel> model) {
		LinearLayout mTimeTableView = new LinearLayout(getContext());
		mTimeTableView.setOrientation(VERTICAL);
		int modesize = model.size();
		if (modesize <= 0) {
			mTimeTableView.addView(addStartView(MAXNUM));
		} else
			for (int i = 0; i < modesize; i++) {
				if (i == 0) {
					mTimeTableView.addView(addStartView(model.get(0)
							.getStartnum()));
					mTimeTableView.addView(getMode(model.get(0)));
				} else if (model.get(i).getStartnum()
						- model.get(i - 1).getStartnum() > 0) {
					mTimeTableView.addView(addStartView(model.get(i)
							.getStartnum() - model.get(i - 1).getStartnum()-(model.get(i-1).getEndnum()-model.get(i-1).getStartnum())));//减去持续的时间 modifed by WuChen 3.24
					mTimeTableView.addView(getMode(model.get(i)));

				}

				if (i + 1 == modesize) {
					mTimeTableView.addView(addStartView(MAXNUM
							- model.get(i).getEndnum()));
				}

			}
		return mTimeTableView;
	}

	/**
	 * 获取单个课表Viwe 也可以自定义 我这个
	 * 
	 * @param model
	 *            数据类型
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private View getMode(final TimeTableModel model) {
		LinearLayout mTimeTableView = new LinearLayout(getContext());
		mTimeTableView.setOrientation(VERTICAL);
		TextView mTimeTableNameView = new TextView(getContext());
		int num = model.getEndnum() - model.getStartnum();
		mTimeTableNameView.setHeight(dip2px((num + 1) * TimeTableHeight) + num
				* 2);
		//改变预约记录中的字的颜色
		mTimeTableNameView.setTextColor(getContext().getResources().getColor(
				android.R.color.white));
//		mTimeTableNameView.setTextColor(getResources().getColor(
//				R.color.text_color));
		mTimeTableNameView.setWidth(dip2px(50));
		mTimeTableNameView.setTextSize(16);
		mTimeTableNameView.setGravity(Gravity.CENTER);
		mTimeTableNameView
				.setText(model.getName() + "@" + model.getClassroom());
		mTimeTableView.addView(mTimeTableNameView);
		//去掉边界线
		mTimeTableView.addView(getWeekLine());
		mTimeTableView.setBackgroundDrawable(getContext().getResources()
				.getDrawable(colors[getColornum(model.getName())]));
		mTimeTableView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Toast.makeText(getContext(), model.getName() + "@" + model.getClassroom(), Toast.LENGTH_SHORT).show();
			}
		});
		return mTimeTableView;
	}

	/**
	 * 转换dp
	 * 
	 * @param dpValue
	 * @return
	 */
	public int dip2px(float dpValue) {
		float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public void setTimeTable(List<TimeTableModel> mlist) {
		this.mLlisTimeTable = mlist;
		for (TimeTableModel timeTableModel : mlist) {
			addTimeName(timeTableModel.getName());
		}

	}
	//mind that weekname's num must be 7
	public void setWeekname(String[] weekname) {
		this.weekname = weekname;
	}
	/**
	 * 3.19 by Wu Chen
	 * 容器入口
	 *
	 * @param weekname，mlist
	 *         需要设置的横栏名称，需要设置的日程
	 * @return
	 */
	public void startTimeTable(String[] weekname,List<TimeTableModel> mlist) {
		setWeekname(weekname);
		setTimeTable(mlist);
		//这里之后初始化界面
		initView();
		invalidate();
	}
}
