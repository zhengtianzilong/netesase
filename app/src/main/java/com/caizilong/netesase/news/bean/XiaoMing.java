package com.caizilong.netesase.news.bean;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/15  16:15
 * @desc ${TODD}
 */
public class XiaoMing {
    String name;
    int sex;
    int age;
    String addess;
    boolean hasGirlFriend ;
    int totalMoney;
    String id;


    public XiaoMing(Builder builder){

        this.name = builder.name;
        this.sex = builder.sex;
        this.age = builder.age;
        this.addess =  builder.addess;
        this.hasGirlFriend = builder.hasGirlFriend;
    }

    public static class Builder{
        String name;
        int sex;
        int age;
        String addess;
        boolean hasGirlFriend ;
        int totalMoney;
        String id;

        public Builder(){
            this.id = "440204XXXXXXX";
             this.name="";
            this.sex = 0;
            this.age = 20;
            this.addess="广东韶关";
            this.hasGirlFriend = false ;
            this.totalMoney =100;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSex(int sex) {
            this.sex = sex;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setAddess(String addess) {
            this.addess = addess;
            return this;
        }

        public Builder setHasGirlFriend(boolean hasGirlFriend) {
            this.hasGirlFriend = hasGirlFriend;
            return this;
        }

        public Builder setTotalMoney(int totalMoney) {
            this.totalMoney = totalMoney;
            return this;
        }

        public XiaoMing build(){
            return new XiaoMing(this);
        }
    }
}
