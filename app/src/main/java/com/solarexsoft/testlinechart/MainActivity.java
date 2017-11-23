package com.solarexsoft.testlinechart;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements IAxisValueFormatter {
    LineChart mLineChart;
    XAxis xAxis;
    YAxis yAxis;

    ArrayList<Entry> mEntries = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mLineChart = (LineChart) findViewById(R.id.linechart);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setTouchEnabled(true);
        mLineChart.setPinchZoom(false);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(false);
        mLineChart.setBackgroundColor(0xff172E5E);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.getLegend().setEnabled(false);

        xAxis = mLineChart.getXAxis();
        yAxis = mLineChart.getAxisLeft();
        mLineChart.getAxisRight().setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12.0f);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(java.util.concurrent.TimeUnit.DAYS.toMillis(1));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(this);
        yAxis.setSpaceTop(20.0f);
        yAxis.setTextSize(12f);
        yAxis.setGridDashedLine(new DashPathEffect(new float[] { 1, 2, 4, 8}, 1));
        yAxis.setGridColor(Color.RED);
        yAxis.setGridLineWidth(2.0f);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawLabels(true);
        yAxis.setLabelCount(5, false);

        ArrayList<Bust> busts = new ArrayList<>(7);
        Bust bust = new Bust(1511366400000l, 92);
        busts.add(bust);
        bust = new Bust(1511280000000l, 89);
        busts.add(bust);
        bust = new Bust(1511193600000l, 90);
        busts.add(bust);
        bust = new Bust(1511107200000l, 88);
        busts.add(bust);
//        bust = new Bust(1511020800000l, 89);
//        busts.add(bust);
//        bust = new Bust(1510934400000l, 91);
//        busts.add(bust);
        bust = new Bust(1510848000000l, 93);
        busts.add(bust);
        Collections.sort(busts, new Comparator<Bust>() {
            @Override
            public int compare(Bust o1, Bust o2) {
                long diff = o1.timestamp - o2.timestamp;
                int ret = 0;
                if (diff > 0) {
                    ret = 1;
                } else if (diff < 0) {
                    ret = -1;
                }
                return ret;
            }
        });
        int size = busts.size();
        this.xAxis.setLabelCount(3,true);
        for (int i = 0; i < size; i++) {
            Bust tmp = busts.get(i);
            Entry entry = new Entry(tmp.timestamp, tmp.bust);
            mEntries.add(entry);
        }
        LineDataSet set = new LineDataSet(mEntries, "");
        set.setHighLightColor(Color.WHITE);
        set.setColor(Color.WHITE);
        set.setDrawCircles(true);
        set.setCircleColor(Color.WHITE);
        set.setCircleRadius(4.0f);
        set.setDrawCircleHole(true);
        set.setCircleHoleRadius(2.0f);
        set.setCircleColorHole(Color.WHITE);
        set.setHighlightLineWidth(1.0f);
        set.setLineWidth(2.0f);
        set.setValueTextSize(9.0f);
        set.setDrawFilled(false);
        set.setFormLineWidth(0.0f);
        set.setFormSize(15.0f);
        set.setDrawValues(false);
        set.setDrawHorizontalHighlightIndicator(false);
        set.enableDashedHighlightLine(10.0f, 15.0f, 0f);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(set);
        LineData data = new LineData(sets);
        mLineChart.setData(data);
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String text = formatter.format(new Date((long)value));
        return text;
    }
}
