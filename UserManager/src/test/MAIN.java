package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @author ：ZXY
 * @date ：Created in 2020/7/13 20:06
 * @description：
 */

public class MAIN {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person(1, "tom", "123");
        String jsonString = objectMapper.writeValueAsString(person);
        System.out.println("JsonString: " + jsonString);
    }

}
