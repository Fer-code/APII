package com.example.api;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchAddressTask extends AsyncTask<Location, Void, String[]> {

    private Context mContext;
    private OnTaskCompleted mListener;


    FetchAddressTask(Context applicationContext, OnTaskCompleted listener) {
        mContext = applicationContext;
        mListener = listener;
    }

    private final String TAG = FetchAddressTask.class.getSimpleName();

    @Override
    protected String[] doInBackground(Location... params) {
        Geocoder geocoder = new Geocoder(mContext,
                Locale.getDefault());

        Location location = params[0];
        List<Address> addresses = null;
        String[] resultMessage = new String[3];
        resultMessage[0]="";
        resultMessage[1]="";
        resultMessage[2]="";

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
            resultMessage[1]=String.valueOf(location.getLatitude());
            resultMessage[2]=String.valueOf( location.getLongitude());
        } catch (IOException ioException) {
            resultMessage[0] = mContext.getString(R.string.app_name);
            Log.e(TAG, resultMessage[0], ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            resultMessage[0] = mContext.getString(R.string.app_name);
            Log.e(TAG, resultMessage[0] + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);
        }

        if (addresses == null || addresses.size() == 0) {
            if (resultMessage[0].isEmpty()) {
                resultMessage[0] = mContext.getString(R.string.no_address_found);
                Log.e(TAG, resultMessage[0]);
            }
        } else {

            Address address = addresses.get(0);
            ArrayList<String> addressParts = new ArrayList<>();


            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressParts.add(address.getAddressLine(i));
            }

            resultMessage[0] = TextUtils.join(
                    "\n",
                    addressParts);

        }

        return resultMessage;
    }

    @Override
    protected void onPostExecute(String[] address) {
        mListener.onTaskCompleted(address);
        super.onPostExecute(address);
    }

    interface OnTaskCompleted {
        void onTaskCompleted(String[] result);
    }
}
