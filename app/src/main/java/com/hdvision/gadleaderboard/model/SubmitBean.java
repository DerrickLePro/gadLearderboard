package com.hdvision.gadleaderboard.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by derrick.kaffo on 30/08/2020.
 * kaffoderrick@gmail.com
 */
public class SubmitBean implements Parcelable {
    private String firstName;
    private String lastName;
    private String businessEmail;
    private String projectLink;

    public SubmitBean() {
    }

    public SubmitBean(String firstName, String lastName, String businessEmail, String projectLink) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.businessEmail = businessEmail;
        this.projectLink = projectLink;
    }

    protected SubmitBean(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        businessEmail = in.readString();
        projectLink = in.readString();
    }

    public static final Creator<SubmitBean> CREATOR = new Creator<SubmitBean>() {
        @Override
        public SubmitBean createFromParcel(Parcel in) {
            return new SubmitBean(in);
        }

        @Override
        public SubmitBean[] newArray(int size) {
            return new SubmitBean[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    @Override
    public String toString() {
        return "SubmitBean{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", businessEmail='" + businessEmail + '\'' +
                ", projectLink='" + projectLink + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(businessEmail);
        parcel.writeString(projectLink);
    }
}
