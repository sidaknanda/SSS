package com.android.sss;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by OPTIMUSDOM ubuntu151 on 23/9/15.
 */
public class StudentModel implements Parcelable {

    public String StudentName;
    public String AdmissionNo;
    public String FatherName;
    public String MotherName;
    public String StudentClass;
    public String ContactNo1;
    public String ContactNo2;
    public String EmailId;
    public String RFID;
    public String GCMID;
    public String LoginId;
    public String Password;

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getAdmissionNo() {
        return AdmissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        AdmissionNo = admissionNo;
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

    public String getStudentClass() {
        return StudentClass;
    }

    public void setStudentClass(String studentClass) {
        StudentClass = studentClass;
    }

    public String getContactNo1() {
        return ContactNo1;
    }

    public void setContactNo1(String contactNo1) {
        ContactNo1 = contactNo1;
    }

    public String getContactNo2() {
        return ContactNo2;
    }

    public void setContactNo2(String contactNo2) {
        ContactNo2 = contactNo2;
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
        StudentName = in.readString();
        AdmissionNo = in.readString();
        FatherName = in.readString();
        MotherName = in.readString();
        StudentClass = in.readString();
        ContactNo1 = in.readString();
        ContactNo2 = in.readString();
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
        dest.writeString(StudentName);
        dest.writeString(AdmissionNo);
        dest.writeString(FatherName);
        dest.writeString(MotherName);
        dest.writeString(StudentClass);
        dest.writeString(ContactNo1);
        dest.writeString(ContactNo2);
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