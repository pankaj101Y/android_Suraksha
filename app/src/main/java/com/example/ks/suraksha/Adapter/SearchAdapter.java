package com.example.ks.suraksha.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ks.suraksha.AdmissionInfo;
import com.example.ks.suraksha.Database.DatabaseManager;
import com.example.ks.suraksha.R;
import com.example.ks.suraksha.Utility.Constants;
import com.example.ks.suraksha.Utility.FiltersHolder;

import java.util.ArrayList;

import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.AGE_GROUP_1;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.AGE_GROUP_2;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.AGE_GROUP_3;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.DROP_OUT;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.HAVE_AADHAR;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.NOT_HAVE_AADHAR;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.SCHOOL_GOING;


public class SearchAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static ArrayList<AdmissionInfo> searchResults=new ArrayList<>();
    private static final int ADMISSION_VIEW=0;
    private static final int FOOTER_VIEW=1;
    private static final int NO_VIEW=2;
    private RecyclerClickListener recyclerClickListener;

    public SearchAdapter(ArrayList<AdmissionInfo>admissionInfos,RecyclerClickListener recyclerClickListener){
        searchResults=admissionInfos;
        this.recyclerClickListener=recyclerClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ADMISSION_VIEW){
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_admission_view,parent,false);
            return new SearchAdapter.AdmissionViewHolder(v);
        }else if (viewType==FOOTER_VIEW){
            TextView textView=new TextView(parent.getContext());
            textView.setBackgroundResource(android.R.drawable.list_selector_background);
            return new SearchAdapter.FooterViewHolder(textView);
        }else return new SearchAdapter.NoViewHolder(new TextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SearchAdapter.AdmissionViewHolder){
            ((SearchAdapter.AdmissionViewHolder)holder).name.setText(searchResults.get(position).getName());
            ((SearchAdapter.AdmissionViewHolder)holder).enrollment.setText(searchResults.get(position).getEnrollment());

            Glide.with(((SearchAdapter.AdmissionViewHolder)holder).photo)
                    .load(searchResults.get(position).getPhoto())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                    .into(((SearchAdapter.AdmissionViewHolder)holder).photo);

            ((SearchAdapter.AdmissionViewHolder)holder).photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerClickListener.onClick(v,position);
                }
            });
        }else if (holder instanceof SearchAdapter.FooterViewHolder){
            ((SearchAdapter.FooterViewHolder)holder).footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int start= searchResults.size();
                    DatabaseManager.getDatabaseManager(((SearchAdapter.FooterViewHolder)holder).footer.getContext()).refreshAdmissions();
                    if (start- searchResults.size()!=0)
                        notifyItemRangeChanged(start, searchResults.size()-start);
                    else
                        Toast.makeText(((SearchAdapter.FooterViewHolder) holder).footer.getContext(),"NO RESULT FOUND",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return searchResults.size()+1;
    }

    public static AdmissionInfo getSearchResult(int index) {
        return searchResults.get(index);
    }

    @Override
    public int getItemViewType(int position) {
        if (position== searchResults.size())
            return FOOTER_VIEW;
        if (!isFilteredPassed(position))
            return NO_VIEW;
        return ADMISSION_VIEW;
    }

    private boolean isFilteredPassed(int i){
        FiltersHolder filtersHolder=FiltersHolder.getFiltersHolder();
        if (filtersHolder.getFilter(DROP_OUT)){
            if (!searchResults.get(i).getEnrollment().equals(Constants.getEnrollOps()[1]))
                return false;
        }

        if (filtersHolder.getFilter(SCHOOL_GOING)){
            if (!searchResults.get(i).getEnrollment().equals(Constants.getEnrollOps()[2]))
                return false;
        }

        if (filtersHolder.getFilter(AGE_GROUP_1)){
            if (!(searchResults.get(i).getAge()>=4&&searchResults.get(i).getAge()<=10))
                return false;
        }

        if (filtersHolder.getFilter(AGE_GROUP_2)){
            if (!(searchResults.get(i).getAge()>=11&&searchResults.get(i).getAge()<=15))
                return false;
        }

        if (filtersHolder.getFilter(AGE_GROUP_3)){
            if (!(searchResults.get(i).getAge()>=16&&searchResults.get(i).getAge()<=20))
                return false;
        }

        if (filtersHolder.getFilter(HAVE_AADHAR)){
            if (!searchResults.get(i).getAadharStatus().equals(Constants.getAadharOps()[1]))
                return false;
        }

        if (filtersHolder.getFilter(NOT_HAVE_AADHAR)){
            if (!searchResults.get(i).getAadharStatus().equals(Constants.getAadharOps()[2]))
                return false;
        }
        return true;
    }

    static class AdmissionViewHolder extends RecyclerView.ViewHolder{
        private ImageView photo;
        private TextView name,enrollment;
        AdmissionViewHolder(View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.single_child_image);
            name=itemView.findViewById(R.id.name);
            enrollment=itemView.findViewById(R.id.enrollment);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder{
        private TextView footer;
        FooterViewHolder(View itemView) {
            super(itemView);
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            params.setMargins(16,16,16,16);
            footer=(TextView)itemView;
            footer.setGravity(Gravity.CENTER);
            footer.setTextSize(16);
            footer.setTypeface(Typeface.DEFAULT_BOLD);
            footer.setHeight(72);
            footer.setText("LOAD MORE....");
            footer.setLayoutParams(params);
        }
    }

    static class NoViewHolder extends RecyclerView.ViewHolder{
        public NoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
