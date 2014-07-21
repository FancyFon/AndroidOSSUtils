package testit;

import org.openqa.selenium.JavascriptExecutor;

import java.util.HashMap;
/**


 *
 * Created by mariusz.rafalski<mariusz.rafalski@fancyfon.com> on 2014-07-21.
 *
 */
public class Swiper {
    private final JavascriptExecutor driver;

    public Swiper(JavascriptExecutor driver) {
        this.driver = driver;
    }

    public void verticalSwipe(Double startY, Double endY){
        swipe(0.5, 0.5,startY,endY);
    }

    public void horizontalSwipe(Double startX, Double endY){
        swipe(startX, endY,0.5,0.5);
    }


    public void swipe(Double startX, Double endX,Double startY, Double endY ){
        HashMap<String, Double> swipePlace = new HashMap<String, Double>();
        swipePlace.put("startX", startX);
        swipePlace.put("startY", startY);
        swipePlace.put("endX", endX);
        swipePlace.put("endY", endY);
        swipePlace.put("duration", 1.8);
        driver.executeScript("mobile: swipe", swipePlace);
    }







}
