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

public class GeocodingFragment extends Fragment implements View.OnClickListener{

    private EditText edtAddress;
    private TextView txtLocation;
    private Button btnProcessar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geocoding, container, false);

        edtAddress = (EditText) view.findViewById(R.id.edt_endereco);
        txtLocation = (TextView) view.findViewById(R.id.txt_local_value);
        btnProcessar = (Button) view.findViewById(R.id.btn_processar);
        btnProcessar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String address = edtAddress.getText().toString();
        new GeocodingTask().execute(address);
    }

    private class GeocodingTask extends AsyncTask<String, Void, double[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnProcessar.setEnabled(false);
            edtAddress.setEnabled(false);
        }

        @Override
        protected double[] doInBackground(String... params) {
            try {
                return GeocoderHelper.doGeocoding(getActivity(), params[0]);
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
        protected void onPostExecute(double[] location) {
            if(location != null){
                txtLocation.setText(String.format("%.4f <-> %.4f", location[0], location[1]));
            }else{
                txtLocation.setText(getActivity().getString(R.string.not_defined));
            }

            btnProcessar.setEnabled(true);
            edtAddress.setEnabled(true);
        }
    }
}
