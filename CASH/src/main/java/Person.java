import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;

/**
 * @author ：ZXY
 * @date ：Created in 2020/5/20 19:06
 * @description：
 */

@Data
public class Person {
    private String name;
    private int age;
}

class main{
    public static void main(String[] args) {
        Person person=new Person();
        //免写get set 方法
        System.out.println(person.getName());
        System.out.println(person.getAge());
    }
}
