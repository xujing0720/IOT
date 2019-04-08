import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo {
    public static void main(String[] args) {
        String a="14";
        int b=10;
        System.out.println(Integer.valueOf(a)-b);
        System.out.println(190/100);
        Object o=new Integer[]{1,2};
        Date date=new Date();
        String t= String.valueOf(date);
        System.out.println(date.toString());

        //System.out.println(DoubleToFloat.pase(3.141592));
        long ss=System.currentTimeMillis();
        System.out.println(String.valueOf(ss));

        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(day));


    }
}
