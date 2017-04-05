package com.seongil.recyclerviewlib.sample.mock;

import com.seongil.recyclerviewlib.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlib.sample.R;
import com.seongil.recyclerviewlib.sample.model.Transaction;
import com.seongil.recyclerviewlib.sample.model.TransactionAdvertisement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * @author seong-il, kim
 * @since 17. 4. 5
 */
public class TransactionDataGenerator {

    public static List<RecyclerViewItem> generate(int count) {
        List<RecyclerViewItem> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(generateAdvertisement());
            list.add(generateTransaction());
        }
        return list;
    }

    private static Transaction generateTransaction() {
        Transaction item = new Transaction();
        item.setAmount(12000);
        item.setMerchantName("Start Bucks");
        item.setTransactionType("Buy");
        item.setTransactionTimeInMillis(getTransactionTimeInMillisAsGMT());
        return item;
    }

    private static TransactionAdvertisement generateAdvertisement() {
        TransactionAdvertisement item = new TransactionAdvertisement();
        item.setAdvertisementName("Car Advertisement");
        item.setThumbnailId(R.drawable.advertisement_car);
        return item;
    }

    private static long getTransactionTimeInMillisAsGMT() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
    }
}
