package com.example.ks.suraksha;


public class AdmissionInfo implements Cloneable{
    private long id=-1;
    private String serverId=null;
    private String photo=null;
    private boolean isPhotoSync=false;
    private String name;
    private int age;
    private String mother_name;
    private String father_name;
    private int noSiblings;
    private String occupation;
    private String nativePlace;
    private String enrollment;
    private String aadharStatus;
    private String aadharReason;
    private String gender;
    private String category;

    public AdmissionInfo(long id,String serverId,String photo,boolean isPhotoSync,String name,
                         int age, String mother_name,
                         String father_name, int noSiblings, String occupation,
                         String nativePlace, String enrollment, String aadharStatus,
                         String aadharReason, String gender,String category) {
        this.id=id;
        this.serverId=serverId;
        this.photo=photo;
        this.isPhotoSync=isPhotoSync;
        this.name = name;
        this.age = age;
        this.mother_name = mother_name;
        this.father_name = father_name;
        this.noSiblings = noSiblings;
        this.occupation = occupation;
        this.nativePlace = nativePlace;
        this.enrollment = enrollment;
        this.aadharStatus = aadharStatus;
        this.aadharReason = aadharReason;
        this.gender = gender;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isPhotoSync() {
        return isPhotoSync;
    }

    public void setPhotoSync(boolean photoSync) {
        isPhotoSync = photoSync;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public int getNoSiblings() {
        return noSiblings;
    }

    public void setNoSiblings(int noSiblings) {
        this.noSiblings = noSiblings;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getAadharStatus() {
        return aadharStatus;
    }

    public void setAadharStatus(String aadharStatus) {
        this.aadharStatus = aadharStatus;
    }

    public String getAadharReason() {
        return aadharReason;
    }

    public void setAadharReason(String aadharReason) {
        this.aadharReason = aadharReason;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public AdmissionInfo clone() throws CloneNotSupportedException {
        return (AdmissionInfo) super.clone();
    }
}
