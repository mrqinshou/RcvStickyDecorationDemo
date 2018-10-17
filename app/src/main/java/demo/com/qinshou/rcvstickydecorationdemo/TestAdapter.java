package demo.com.qinshou.rcvstickydecorationdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {
    private List<String> dataList;

    public TestAdapter() {
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_test, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTest.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTest;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTest = itemView.findViewById(R.id.tv_test);
        }
    }
}
