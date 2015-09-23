package com.example.nmendiratta.mortgagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import  java.lang.Math;
import java.util.Locale;

public class CalculateLoan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calculate_loan);
        final EditText home_value = (EditText) findViewById(R.id.HomeValue);
        final Button button = (Button) findViewById(R.id.Button);
        final Spinner spinner2 = (Spinner) findViewById(R.id.terms);
        final EditText down_payment = (EditText) findViewById(R.id.DownPaymentValue);
        final EditText apr = (EditText) findViewById(R.id.InterestRateValue);
        final EditText taxrate  = (EditText) findViewById(R.id.TaxRate);
        final EditText paydate = (EditText) findViewById(R.id.PayOffDate);
        final EditText monthlypayment = (EditText) findViewById(R.id.MonthlyPayment);
        final EditText tip1 = (EditText) findViewById(R.id.TotalInterestPaid);
        final TextView tax_paid = (TextView) findViewById(R.id.TotalTaxPaid);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                double hv = 0;
                double dp = 0;
                double tr = 0;
                double apr1 = 0;

                if (home_value.getText().toString().length() <= 0) {
                    home_value.setError("Please enter a number for Home Value");
                } else if (down_payment.getText().toString().length() < 0) {
                    down_payment.setError("Please enter a number for Down Payment");
                } else if (apr.getText().toString().length() <= 0 || Double.parseDouble(apr.getText().toString()) > 100) {
                    apr.setError("Please enter a number less than 100 for APR");
                } else if (taxrate.getText().toString().length() <= 0 || Double.parseDouble(taxrate.getText().toString()) >100) {
                    taxrate.setError("Please enter a number for Tax Rate less than 100");
                } else if (Double.parseDouble(home_value.getText().toString()) <
                        Double.parseDouble(down_payment.getText().toString())) {
                    down_payment.setError("Down Payment must be smaller than Home Value");
                } else {
                    hv = Double.parseDouble(home_value.getText().toString());
                    dp = Double.parseDouble(down_payment.getText().toString());
                    apr1 = Double.parseDouble(apr.getText().toString());
                    tr = Double.parseDouble(taxrate.getText().toString());
                    int terms = Integer.parseInt(spinner2.getSelectedItem().toString());
                    double mp = 0, mmp = 0, mpt = 0, tip, tpt = 0, P, i, N;
                    P = hv - dp;
                    i = apr1 / (100 * 12);
                    N = terms * 12;
                    tpt = (hv * tr * terms) / 100;
                    mpt = tpt / N;
                    double temp1 = Math.pow((1 + i), N);
                    mmp = (P * i * temp1) / (temp1 - 1);
                    mp = mmp + mpt;
                    double temp2 = Math.ceil((mp * 100.0) / 100.0);
                    tip = (mp * N) - P - tpt;

                    Calendar now = Calendar.getInstance();
                    int y = now.get(Calendar.YEAR) + terms;
                    now.add(Calendar.DATE, terms * 365);
                    String s = now.getDisplayName(now.MONTH, Calendar.SHORT, Locale.US);
                    paydate.setText(s + " " + y + "");
                    monthlypayment.setText("" + Math.round(mp * 100.00) / 100.00);
                    tip1.setText("" + Math.round(tip * 100.00) / 100.00);
                    tax_paid.setText("" + Math.round(tpt * 100.00) / 100.00);

                }
            }
    });
        final Button reset = (Button) findViewById(R.id.Reset);
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    spinner2.setSelection(0);
                    home_value.setText("");
                    down_payment.setText("");
                    apr.setText("");
                    taxrate.setText("");
                    paydate.setText("");
                    monthlypayment.setText("");
                    tip1.setText("");
                    tax_paid.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculate_loan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
