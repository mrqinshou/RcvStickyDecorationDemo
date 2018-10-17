package demo.com.qinshou.rcvstickydecorationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final List<String> list = getList(120);
        final RecyclerView rvTest = (RecyclerView) findViewById(R.id.rv_test);
        TestAdapter testAdapter = new TestAdapter();
        rvTest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvTest.addItemDecoration(new StickyDecoration() {
            @Override
            public String getStickyHeaderName(int position) {
                return list.get(position);
            }
        });
//        rvTest.setLayoutManager(new GridLayoutManager(this, 4));
//        rvTest.addItemDecoration(new StickyDecoration(4) {
//            @Override
//            public String getStickyHeaderName(int position) {
//                return list.get(position);
//            }
//        });
        rvTest.setAdapter(testAdapter);
        testAdapter.setDataList(list);
    }

    private List<String> getList(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            if (i < size / 3) {
                list.add("力量英雄");
            } else if (i < size / 3 * 2) {
                list.add("敏捷英雄");
            } else {
                list.add("智力英雄");
            }
        }
        return list;
    }
}
