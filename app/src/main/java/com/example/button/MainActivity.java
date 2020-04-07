package com.example.button;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int numberOfCoffees = 0;
    int coffeePrice = 5;
    int whippedCreamPrice = 5;
    int chocolatePrice = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private int calculatePrice(int cupPrice){
        return numberOfCoffees *cupPrice;
    }

    private String  createOrderSummary(String name, int price, boolean status_chocolate, boolean status_whipped){

        String message = getString(R.string.order_summary_name, name) + "\n" + getString(R.string.order_summary_whipped_cream, status_whipped) + "\n" + getString(R.string.order_summary_chocolate, status_chocolate) + "\n" +getString(R.string.order_summary_quantity, numberOfCoffees) + "\n" + getString(R.string.order_summary_price,"$"+price) + "\n" + getString(R.string.thank_you) ;
        return message;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int cupPrice=coffeePrice;
        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_checkbox);
        boolean status_whipped = checkBox.isChecked();
        CheckBox checkBox_chocolate = (CheckBox) findViewById(R.id.chocalate_checkbox);
        boolean status_chocolate = checkBox_chocolate.isChecked();
        EditText editText = (EditText) findViewById(R.id.name_field);
        String name = editText.getText().toString();
        if(status_whipped)
            cupPrice +=1;
        if(status_chocolate)
            cupPrice +=2;
        int price = calculatePrice(cupPrice) ;
        String message = createOrderSummary(name, price, status_chocolate, status_whipped);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:amrsamy77@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }
    }

    public void increment(View view) {
        if(numberOfCoffees < 100) {
            numberOfCoffees++;
        }
        else
            Toast.makeText(this,"You cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
        display(numberOfCoffees);
    }

    public void decrement(View view) {
        if(numberOfCoffees > 1) {
            numberOfCoffees--;
        }
        else
            Toast.makeText(this,"You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();

        display(numberOfCoffees);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}
