package com.example.campingweb.Model;



public class Review  {
    private String ID;
    private String content;
    private int satisfactionScore;

    public Review(String id, String content, int score){
        this.ID = id;
        this.content = content;
        this.satisfactionScore = score;
    }


    public int getSatisfactionScore() {
        return satisfactionScore;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setSatisfactionScore(int satisfactionScore) {
        this.satisfactionScore = satisfactionScore;
    }

    public String getID() {
        return ID;
    }

    public String getContent() {
        return content;
    }

    public String toString(){
        return content +"   by "+ID +"\n";
    }
}
