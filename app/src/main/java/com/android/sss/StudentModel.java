package com.android.sss;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by OPTIMUSDOM ubuntu151 on 23/9/15.
 */
public class StudentModel implements Parcelable {

    public String Name;
    public String AdmissionNumber;
    public String FatherName;
    public String MotherName;
    public String StudentClass;
    public String ContactNumber1;
    public String ContactNumber2;
    public String EmailId;
    public String RFID;
    public String GCMID;
    public String LoginId;
    public String Password;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAdmissionNumber() {
        return AdmissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        AdmissionNumber = admissionNumber;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getMotherName() {
        return MotherName;
    }

    public void setMotherName(String motherName) {
        MotherName = motherName;
    }

    public String getContactNumber1() {
        return ContactNumber1;
    }

    public void setContactNumber1(String contactNumber1) {
        ContactNumber1 = contactNumber1;
    }

    public String getStudentClass() {
        return StudentClass;
    }

    public void setStudentClass(String studentClass) {
        StudentClass = studentClass;
    }

    public String getContactNumber2() {
        return ContactNumber2;
    }

    public void setContactNumber2(String contactNumber2) {
        ContactNumber2 = contactNumber2;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getRFID() {
        return RFID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }

    public String getGCMID() {
        return GCMID;
    }

    public void setGCMID(String GCMID) {
        this.GCMID = GCMID;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    protected StudentModel(Parcel in) {
        Name = in.readString();
        AdmissionNumber = in.readString();
        FatherName = in.readString();
        MotherName = in.readString();
        StudentClass = in.readString();
        ContactNumber1 = in.readString();
        ContactNumber2 = in.readString();
        EmailId = in.readString();
        RFID = in.readString();
        GCMID = in.readString();
        LoginId = in.readString();
        Password = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(AdmissionNumber);
        dest.writeString(FatherName);
        dest.writeString(MotherName);
        dest.writeString(StudentClass);
        dest.writeString(ContactNumber1);
        dest.writeString(ContactNumber2);
        dest.writeString(EmailId);
        dest.writeString(RFID);
        dest.writeString(GCMID);
        dest.writeString(LoginId);
        dest.writeString(Password);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StudentModel> CREATOR = new Parcelable.Creator<StudentModel>() {
        @Override
        public StudentModel createFromParcel(Parcel in) {
            return new StudentModel(in);
        }

        @Override
        public StudentModel[] newArray(int size) {
            return new StudentModel[size];
        }
    };
}
