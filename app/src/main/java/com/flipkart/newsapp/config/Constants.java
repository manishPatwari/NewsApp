package com.flipkart.newsapp.config;

/**
 * Created by manish.patwari on 5/8/15.
 */
public class Constants {

    public static enum NewsType{
        LIST("list"), VIDEOS("videos"),GALLERY("gallery") ;

        private final String type;

        private NewsType(String type){
            this.type = type;
        }

        @Override
        public String toString(){
            return type;
        }
    }

    public static enum Category{
        ALL("all"), TOP_NEWS("top news"),INDIA("india"),WORLD("world"),TECH("tech") ;

        private final String category;

        private Category(String category){
            this.category = category;
        }

        @Override
        public String toString(){
            return category;
        }
    }

}
