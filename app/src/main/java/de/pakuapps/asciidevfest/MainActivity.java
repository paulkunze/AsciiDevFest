package de.pakuapps.asciidevfest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int[] SPACE_LENGTHS = {4,6,4,6,4,7,4,7,3,8,3,9,3,9,2,9,1,9,0,8,0,9,0,9,0,10,0,12,
            1,12,3,14,3,13,3,12,3,12,3,11,3,10,3,10,3,9,3,8,3,8,3,7,3,7,3,6,3};
    private static final int[] TEXT_LENGTHS = {1,8,1,8,1,8,1,8,2,8,2,8,2,8,5,8,7,8,9,8,9,8,9,8,9,8,8,8,7,8,
            3,8,3,8,3,8,3,8,3,8,3,8,3,8,3,8,3,8,3,8,3,8,3,8,3,8,3,8,3,8,3,8};
    private static final int LOGO_LETTER_COUNT = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generateLogo(View view) {
        EditText input = (EditText) findViewById(R.id.input);
        CheckBox hexBox = (CheckBox) findViewById(R.id.hex);
        String text = getLongText(input.getText().toString(), hexBox.isChecked());
        StringBuilder builder = new StringBuilder();
        int textPos = 0;
        builder.append("<pre>");
        for(int i = 0; i < SPACE_LENGTHS.length; i+=2){
            if(SPACE_LENGTHS.length > i + 1) {
            builder.append(getSpaces(SPACE_LENGTHS[i]));
            builder.append(getSubstring(text, textPos, textPos + TEXT_LENGTHS[i]));
            textPos += TEXT_LENGTHS[i];

                builder.append(getSpaces(SPACE_LENGTHS[i + 1]));
                builder.append(getSubstring(text, textPos, textPos + TEXT_LENGTHS[i + 1]));
                textPos += TEXT_LENGTHS[i + 1];
            }

            builder.append("<br/>");
        }
        builder.append("</pre>");

        Spanned asciiLogo = Html.fromHtml(builder.toString());
        TextView logo = (TextView) findViewById(R.id.logo);
        logo.setText(asciiLogo);
    }

    private String getSubstring(String input, int start, int end){
        return input.substring(start, end);
    }

    private String getLongText(String input, boolean toHex){
        if(input.length() == 0)
            input = "devfest";
        if(toHex){
            input = asciiToHex(input);
        }
        while(input.length() < LOGO_LETTER_COUNT){
            input += input;
        }
        return input;
    }

    private String asciiToHex(String ascii) {
        char[] chars = ascii.toCharArray();
        StringBuilder hexBuilder = new StringBuilder();
        for(char c : chars){
            hexBuilder.append(Integer.toHexString(c));
        }
        return hexBuilder.toString();
    }

    private String getSpaces(int count){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < count; i++)
            builder.append("&nbsp;");
        return builder.toString();
    }
}
