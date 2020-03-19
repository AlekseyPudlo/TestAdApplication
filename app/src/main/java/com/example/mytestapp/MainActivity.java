package com.example.mytestapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.smaato.sdk.banner.ad.BannerAdSize;
import com.smaato.sdk.banner.widget.BannerError;
import com.smaato.sdk.banner.widget.BannerView;
import com.smaato.sdk.core.AdContentRating;
import com.smaato.sdk.core.Config;
import com.smaato.sdk.core.SmaatoSdk;
import com.smaato.sdk.core.log.LogLevel;

public class MainActivity extends AppCompatActivity {

    private static final String publisherId = "1100042525";
    private static final String adspaceId = "130635694";
    private final BannerAdSize bannerAdSize = BannerAdSize.MEDIUM_RECTANGLE_300x250;
    private BannerView bannerView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setGDPRConsent();

        Config config = Config.builder()
                .setLogLevel(LogLevel.DEBUG)
                .setHttpsOnly(false)
                .setAdContentRating(AdContentRating.MAX_AD_CONTENT_RATING_MA)
                .build();

        SmaatoSdk.init(getApplication(), config, publisherId);

        setContentView(R.layout.activity_main);

        bannerView = findViewById(R.id.bannerView);

        bannerView.setEventListener(new BannerView.EventListener() {
            @Override
            public void onAdLoaded(@NonNull BannerView bannerView) {
                System.out.println("***********  Loaded ***************");
                System.out.println("bannerView.getAdSpaceId: " + bannerView.getAdSpaceId());
            }
            @Override
            public void onAdFailedToLoad(@NonNull BannerView bannerView, @NonNull BannerError bannerError) {
                System.out.println(bannerError);
            }
            @Override
            public void onAdImpression(@NonNull BannerView bannerView) {}
            @Override
            public void onAdClicked(@NonNull BannerView bannerView) {
                Toast.makeText(MainActivity.this, "Ad clicked", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdTTLExpired(@NonNull BannerView bannerView) {}
        });
    }

    public void showAd(View view) {
        bannerView.loadAd(adspaceId, bannerAdSize);
    }

    private void setGDPRConsent() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        editor.putString("IABConsent_SubjectToGDPR", "1");
        editor.putString("IABConsent_ParsedVendorConsents","1");
        editor.putString("IABConsent_ParsedPurposeConsents","1111100000");
        editor.putString("IABConsent_ConsentString","BOvyXKmOvyXKmAGABBENC_-AAAAuh7_______9______9uz_Ov_v_f__33e8__9v_l_7_-___u_-33d4-_1vf99yfm1-7ftr3tp_87ues2_Xur__79__3z3_9pxP78k89r7337Mw_v-_v-b7JCON_IwAAA");
        editor.putBoolean("IABConsent_CMPPresent",true);
        editor.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bannerView.destroy();
    }
}
