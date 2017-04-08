package com.seongil.recyclerviewlife.sample.model;

import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlife.sample.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.reactivex.Flowable;

/**
 * @author seong-il, kim
 * @since 17. 4. 8
 */
public class DataProvider {

    // ========================================================================
    // constants
    // ========================================================================
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.KOREA);
    private static final int[] THUMBNAIL_LIST = {
          R.drawable.hawaii1,
          R.drawable.hawaii2,
          R.drawable.hawaii3,
          R.drawable.hawaii4,
          R.drawable.hawaii5,
          R.drawable.hawaii6,
          R.drawable.hawaii7,
    };

    private static final int[] ADVERTISEMENT_ICON = {
          R.drawable.advertisement1,
          R.drawable.advertisement2,
          R.drawable.advertisement3,
          R.drawable.advertisement4,
          R.drawable.advertisement5
    };

    // ========================================================================
    // fields
    // ========================================================================
    private static int CNT_OF_ENTIRE_LOADED_ITEMS = 50;
    private static int generatedTitleCount = 0;
    private static int generatedThumbnailCount = 0;
    private static int generatedAdvertisementCount = 0;
    private static int generatedAllItemsCount = 0;
    private Random mRandGen = new Random();

    private static DataProvider sInstance;

    // ========================================================================
    // constructors
    // ========================================================================
    public static synchronized DataProvider getInstance() {
        if (sInstance == null) {
            sInstance = new DataProvider();
        }
        return sInstance;
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================

    // ========================================================================
    // methods
    // ========================================================================
    public Flowable<List<RecyclerViewItem>> generateData(final int count, final boolean force) {
        return Flowable.defer(() -> {
            List<RecyclerViewItem> result = new ArrayList<>(count);
            RecyclerViewItem element;
            for (int i = 1; i <= count; i++) {
                synchronized (DataProvider.class) {
                    element = generateRecyclerViewItem();
                    result.add(element);
                    generatedAllItemsCount++;
                    if (generatedAllItemsCount >= CNT_OF_ENTIRE_LOADED_ITEMS && !force) {
                        break;
                    }
                }
            }

            return Flowable.just(result);
        });
    }

    private RecyclerViewItem generateRecyclerViewItem() {
        synchronized (DataProvider.class) {
            int randNum = mRandGen.nextInt(3) + 1;
            switch (randNum) {
                case 1:
                    return generateTitleDateItem();
                case 2:
                    return generateThumbnailTitleItem();
                case 3:
                    return generateAdvertisementItem();
                default:
                    return generateTitleDateItem();
            }
        }
    }

    private ThumbnailTitleItem generateThumbnailTitleItem() {
        final int randNum = mRandGen.nextInt(THUMBNAIL_LIST.length) + 1;
        final int thumbnailResId = THUMBNAIL_LIST[randNum - 1];
        return new ThumbnailTitleItem(
              "Hawaii Picture " + generatedThumbnailCount++, thumbnailResId);
    }

    private TitleDateItem generateTitleDateItem() {
        String dateTime = DATE_FORMAT.format(new Date(System.currentTimeMillis()));
        return new TitleDateItem(
              "Simple Text with DateTime " + generatedTitleCount++, dateTime);
    }

    private AdvertisementItem generateAdvertisementItem() {
        final int randNum = mRandGen.nextInt(ADVERTISEMENT_ICON.length) + 1;
        final int iconId = ADVERTISEMENT_ICON[randNum - 1];

        return new AdvertisementItem("Advertisement " + generatedAdvertisementCount++, iconId);
    }

    public void initialize(int loadingItemCnt) {
        synchronized (DataProvider.class) {
            generatedAllItemsCount = 0;
            generatedTitleCount = 0;
            generatedAdvertisementCount = 0;
            generatedThumbnailCount = 0;

            if (loadingItemCnt > 0) {
                CNT_OF_ENTIRE_LOADED_ITEMS = loadingItemCnt;
            }
        }
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
