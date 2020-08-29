package com.hdvision.gadleaderboard.model;

import com.hdvision.gadleaderboard.R;

/**
 * Created by derrick.kaffo on 29/08/2020.
 * kaffoderrick@gmail.com
 */
public class Learner {

    private String name;
    private int hours;
    private int score;
    private String country;
    private String badgeUrl;

    public Learner() {
    }

    public Learner(String name, int hours, int score, String country, String badgeUrl) {
        this.name = name;
        this.hours = hours;
        this.score = score;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }

}
