package com.example.razor;
//a simple app for implement payment gateway(RazorPay)
//source code @ github.com/eziocode/RazorpayDemo

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

//implements PaymentResultListener to handle success and failure events

public class MainActivity extends AppCompatActivity implements PaymentResultListener {
    //creating local variables
    Button payBtn;
    TextView payTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //preload payment resources
        //import checkout class
        Checkout.preload(getApplicationContext());
        payBtn = findViewById(R.id.payBtn);
        payTxt = findViewById(R.id.payTxtV);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment(); //creating a function
            }
        });

    }

    private void makePayment() {
        //creating an object of a class
        Checkout checkout = new Checkout();
        //set the API key obtained from Razorpay(RP)
        checkout.setKeyID("rzp_test_BEptq3LXUcWKIN");
        //ur logo but exceptional
        checkout.setImage(R.drawable.logo);

        /* referring current activity */
        final Activity activity = this;

        //To pass payment details in JSON format to the RP
        try {

            JSONObject options = new JSONObject();//initiate json object
//          COMPLETE PARAMETERS CAN BE FOUND IN RP DOCUMENTATION
            options.put("name", "Ezio Pays");
            options.put("description", "Test payment");
//          options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//          options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#528FF0");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount ,value/100=500
            options.put("prefill.email", "aswin2kumarforme@gmail.com");
            options.put("prefill.contact", "7904301481");
//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);

            checkout.open(activity, options); //to launch the checkout

        } catch (Exception e) { // TO CATCH EXCEPTION ,LOG LEVEL
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }
//METHODS CREATED FROM PaymentResultListener TO PROVIDE SUCCESS AND ERROR EVENTS
    @Override
    public void onPaymentSuccess(String s) {
//        Context context = getApplicationContext();
//        Toast toast;
//        Toast.makeText(context,"Payment Successful ID:"+s,Toast.LENGTH_LONG).show();
        payTxt.setText("Payment Successful ID:" + s);


    }

    @Override
    public void onPaymentError(int i, String s) {
//        Context context = getApplicationContext();
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast;
//        Toast.makeText(context ,"Payment Failed ID:"+s, duration).show();
        payTxt.setText("Payment Failed ID:" + s);

    }
}