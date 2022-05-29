package com.Page;

public class splitmethod {

    public String split(String sepettenalinanfiyat ) {

        String sonuc="";
        //System.out.println("Gelen deger :"+sepettenalinanfiyat);
        String splitbir=sepettenalinanfiyat.split("TL")[0];
        System.out.println("SplitBir :"+splitbir);
        String splitiki=splitbir.split(",")[0];
        System.out.println("Splitiki :"+splitiki);
        String splituc=splitbir.split(",")[1];
        System.out.println("SplitUc :"+splituc);
        //String birlestirme=splitiki+splituc;
        //System.out.println("Birlestirilmis hali :"+birlestirme);
        sonuc=splitiki+splituc;
        System.out.println("sonuc :"+sonuc);
        return sonuc;
    }

}
