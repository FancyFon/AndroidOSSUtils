package com.appium.famoc.utilis;

import org.openqa.selenium.JavascriptExecutor;

import java.util.HashMap;
/**


 *
 * Created by mariusz.rafalski on 2014-07-21.
 */
public class Swiper {
    private final JavascriptExecutor driver;
    private final Double startX;
    private final Double endX;
    private final Double startY;
    private final Double endY;

    public JavascriptExecutor getDriver() {
        return driver;
    }

    public Double getStartX() {
        return startX;
    }

    public Double getEndX() {
        return endX;
    }

    public Double getStartY() {
        return startY;
    }

    public Double getEndY() {
        return endY;
    }
    public void swipe(){
        JavascriptExecutor js = driver;
        HashMap<String, Double> swipePlace = new HashMap<String, Double>();
        swipePlace.put("startX", startX);
        swipePlace.put("startY", startY);
        swipePlace.put("endX", endX);
        swipePlace.put("endY", endY);
        swipePlace.put("duration", 1.8);
        js.executeScript("mobile: swipe", swipePlace);
    }


    private Swiper(SwiperBuilder builder){
        this.driver = builder.driver;
        this.startX = builder.startX;
        this.endX = builder.endX;
        this.startY = builder.startY;
        this.endY = builder.endY;
    }



    public static class SwiperBuilder {
        private final JavascriptExecutor driver;
        private final Double startX;
        private final Double endX;
        private final Double startY;
        private final Double endY;

        public SwiperBuilder(JavascriptExecutor driver, Double startX, Double endX, Double startY, Double endY) {
            this.driver = driver;
            this.startX = startX;
            this.endX = endX;
            this.startY = startY;
            this.endY = endY;
        }


        public Swiper build() {
            return new Swiper(this);
        }
    }
}
