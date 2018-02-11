package com.example.ks.suraksha.Utility;


public class Stats {

    public static class GenderData{
        private static float female=1;
        private static float male=1;

        public static void setGender(int femaleData,int maleData){
            female=femaleData;
            male=maleData;
        }

        public static float getMale() {
            return male;
        }

        public static float getFemale() {
            return female;
        }
    }

    public static class AadharStatus{
        private  static float have=2;
        private  static float haveNot=1;

        public void setAadharData(int have_data,int not_have_data){
            have=have_data;
            haveNot=not_have_data;
        }

        public static float getHaveNot() {
            return haveNot;
        }

        public static float getHave() {
            return have;
        }
    }

    public static class EnrollmentData{
        private static float schoolGoing=3;
        private static float dropOut=6;

        public void setEnrollmentData(int school_Going,int drop_Out){
            schoolGoing=school_Going;
            dropOut=drop_Out;
        }

        public static float getSchoolGoing() {
            return schoolGoing;
        }

        public static float getDropOut() {
            return dropOut;
        }
    }
}
