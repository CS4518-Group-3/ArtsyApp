package com.brycecorbitt.artsyapp;

import android.os.AsyncTask;

import androidx.navigation.NavController;

class AsyncAquireLocation extends AsyncTask<Void, Void, String> {
    private NavController navController;
    private int resId;
    AsyncAquireLocation(NavController navC, int id){
        navController = navC;
        resId = id;
    }

    @Override
    protected String doInBackground(Void... params) {
        while  (LocationService.Companion.getCurrent_location() == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
        navController.navigate(resId);
    }
}
