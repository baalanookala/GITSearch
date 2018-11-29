package com.nookala.gitsearch.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nookala.gitsearch.GitItem;

import java.util.List;

/**
 * Created by nookaba on 11/24/2018.
 */

public class Github implements Parcelable{

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<GitItem> items = null;

    protected Github(Parcel in) {
        if (in.readByte() == 0) {
            totalCount = null;
        } else {
            totalCount = in.readInt();
        }
        byte tmpIncompleteResults = in.readByte();
        incompleteResults = tmpIncompleteResults == 0 ? null : tmpIncompleteResults == 1;
        items = in.createTypedArrayList(GitItem.CREATOR);
    }

    public static final Creator<Github> CREATOR = new Creator<Github>() {
        @Override
        public Github createFromParcel(Parcel in) {
            return new Github(in);
        }

        @Override
        public Github[] newArray(int size) {
            return new Github[size];
        }
    };

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<GitItem> getItems() {
        return items;
    }

    public void setItems(List<GitItem> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (totalCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalCount);
        }
        parcel.writeByte((byte) (incompleteResults == null ? 0 : incompleteResults ? 1 : 2));
        parcel.writeTypedList(items);
    }
}
