package com.example.michael.geocoding;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class RevGeocogingFragment extends Fragment implements View.OnClickListener{

    private EditText edtLatitude;
    private EditText edtLongitude;
    private TextView txtAddress;
    private Button btnProcessar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revgeocoding, container, false);

        edtLatitude = (EditText) view.findViewById(R.id.edt_latitude);
        edtLongitude = (EditText) view.findViewById(R.id.edt_longitude);
        txtAddress = (TextView) view.findViewById(R.id.txt_endereco_value);
        btnProcessar = (Button) view.findViewById(R.id.btn_processar);
        btnProcessar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Double latitude = Double.valueOf(edtLatitude.getText().toString());
        Double longitude = Double.valueOf(edtLongitude.getText().toString());
        new GeocodingTask().execute(latitude, longitude);
    }

    private class GeocodingTask extends AsyncTask<Double, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnProcessar.setEnabled(false);
            edtLatitude.setEnabled(false);
            edtLongitude.setEnabled(false);
        }

        @Override
        protected String doInBackground(Double... params) {
            try {
                return GeocoderHelper.doReverseGeocoding(getActivity(), params[0], params[1]);
            }catch (final IOException e){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Erro " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String address) {
            if(address != null){
                txtAddress.setText(address);
            }else{
                txtAddress.setText(getActivity().getString(R.string.not_defined));
            }

            btnProcessar.setEnabled(true);
            edtLatitude.setEnabled(true);
            edtLongitude.setEnabled(true);
        }
    }

}
