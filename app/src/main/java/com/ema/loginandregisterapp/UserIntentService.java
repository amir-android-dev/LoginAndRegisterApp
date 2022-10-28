package com.ema.loginandregisterapp;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ema.loginandregisterapp.model.Gender;

import java.util.Date;
import java.util.Random;

public class UserIntentService extends IntentService {

    static boolean stopRegisterService;

    public UserIntentService() {
        super("intent_service");
        stopRegisterService = true;
    }

    @SuppressLint("TimberArgCount")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (stopRegisterService) {
            try {
                //user
                User user = new User(randomAlphaNumeric(), randomAlphaNumeric(), randomAlphaNumeric(), randomAlphaNumeric(), randomDateGenerator(), randomGender(), randomImage());
                String stringFromUserObject = UserUtil.userToString(user);
                //intent
                Intent userIntent = new Intent();
                userIntent.putExtra(Constants.NEW_USER_SERVICE, stringFromUserObject);
                userIntent.setAction(Constants.NEW_USER_SERVICE_ACTION);
                sendBroadcast(userIntent);

                Log.e("TAGSERVIS", user.getFirstname());
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Date randomDateGenerator() {
        Random rnd;
        Date dt;
        long ms;
        rnd = new Random();
        ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
        dt = new Date(ms);
        return dt;
    }


    public static String randomImage() {
        Random random = new Random();
        return "https://picsum.photos/seed/" + random.nextInt() + "/200";

    }

    public static Gender randomGender() {
        int random = new Random().nextInt(3);
        return Gender.values()[random];
    }


    public static String randomAlphaNumeric() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        }
        return generatedString;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRegisterService = false;
    }
}
