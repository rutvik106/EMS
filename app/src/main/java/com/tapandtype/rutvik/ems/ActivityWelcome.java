package com.tapandtype.rutvik.ems;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

public class ActivityWelcome extends WelcomeActivity
{


    @Override
    protected WelcomeConfiguration configuration()
    {

        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.primary)
                .page(new TitlePage(R.drawable.ic_thumb_up_white_48dp,
                        "Thank you for downloading EMS")
                )
                .page(new BasicPage(R.drawable.logo,
                        "Welcome",
                        "If you have your username and password with URL, move on to next page to log in.\n" +
                                "\n" +
                                "If not, contact EMS admin on 9824143009, 9978812644.")
                        .background(R.color.primary)
                )
                .swipeToDismiss(true)
                .bottomLayout(WelcomeConfiguration.BottomLayout.STANDARD_DONE_IMAGE)
                .build();
    }

}
