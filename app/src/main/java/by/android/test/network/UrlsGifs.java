package by.android.test.network;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import by.android.test.network.response.Datum;

/**
 * Created by Павел on 01.07.2016.
 */
public class UrlsGifs implements Parcelable {

    private List<String> mList = new ArrayList<String>();


    public UrlsGifs() {

    }

    public List<String> getmList() {
        return mList;
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    public static final Creator<UrlsGifs> CREATOR = new Creator<UrlsGifs>() {
        @Override
        public UrlsGifs createFromParcel(Parcel in) {

//            List<String> myList = null;
            UrlsGifs urlsGifs = new UrlsGifs();

//            in.readList(myList, List.class.getClassLoader());
//            urlsGifs.setmList(myList);


            return urlsGifs;
        }

        @Override
        public UrlsGifs[] newArray(int size) {
            return new UrlsGifs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(mList);
    }


}

