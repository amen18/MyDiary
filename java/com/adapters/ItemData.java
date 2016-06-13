package com.adapters;

/**
 * Created by AMEN on 30.04.2016.
 */
public class ItemData {
        private String text;
        private Integer imageId;

        public ItemData(String text, Integer imageId){
            this.text=text;
            this.imageId=imageId;
        }

        public String getText(){
            return text;
        }

        public Integer getImageId(){
            return imageId;
        }
    }