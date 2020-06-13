package com.example.campingweb.Model;



public class Review  {
    private String id;
    private String content;
    private int satisfactionScore;

    public Review(String id, String content, int satisfactionScore){
        this.id = id;
        this.content = content;
        this.satisfactionScore = satisfactionScore;
    }


    public int getSatisfactionScore() {
        return satisfactionScore;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSatisfactionScore(int satisfactionScore) {
        this.satisfactionScore = satisfactionScore;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String toString(){
        return content +"   by "+ id +"\n";
    }
}
